package com.tecnocampus.tcmbank.domain;

import com.tecnocampus.tcmbank.application.dto.AccountDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "accounts")
public class Account {
    private static final int MIN_STARTING_BALANCE = 50;
    private static final int MAX_STARTING_BALANCE = 10000;
    private static final int MIN_BALANCE = 0;
    private static final int MAX_DEPOSIT = 2000;

    @Id
    private String id = UUID.randomUUID().toString();

    private String iban;
    private double balance;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "sourceAccount")
    private List<Movement> movementsAndOutgoingTransfers;

    @OneToMany(mappedBy = "destinationAccount")
    private List<Transfer> incomingTransfers;

    public Account(AccountDTO accountDTO, User user) throws Exception {
        checkNullArgument(accountDTO);
        checkNullArgument(user);

        this.iban = accountDTO.getIban();
        this.user = user;
        this.movementsAndOutgoingTransfers = new ArrayList<>();
        this.incomingTransfers = new ArrayList<>();

        checkStartingBalance(accountDTO.getBalance());
        this.balance = accountDTO.getBalance();
    }

    private void checkNullArgument(Object argument) {
        if (argument == null)
            throw new IllegalArgumentException("Null argument");
    }

    private void checkStartingBalance(double balance) throws Exception {
        if (balance < MIN_STARTING_BALANCE || balance > MAX_STARTING_BALANCE)
            throw new Exception("Starting balance must be in range " +
                    "[" + MIN_STARTING_BALANCE + ", " + MAX_STARTING_BALANCE + "]");
    }

    public void operation(Movement movement) {
        checkNullArgument(movement);

        if (movement instanceof Transfer)
            checkAndPerformTransfer((Transfer) movement);
        else
            checkAndPerformMovement(movement);
    }

    private void checkAndPerformTransfer(Transfer transfer) {
        if (transfer.getAmount() <= 0)
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");

        if (transfer.getSourceAccount() == this) {
            checkAndPerformOutgoingTransfer(transfer);

        } else
            performIncomingTransfer(transfer);
    }

    private void checkAndPerformOutgoingTransfer(Transfer transfer) {
        double outgoingAmount = transfer.getAmount();

        checkMinBalance(-outgoingAmount);

        movementsAndOutgoingTransfers.add(transfer);

        balance -= outgoingAmount;
    }

    private void performIncomingTransfer(Transfer transfer) {
        incomingTransfers.add(transfer);

        balance += transfer.getAmount();
    }

    private void checkAndPerformMovement(Movement movement) {
        double movementAmount = movement.getAmount();

        checkMovementRestrictions(movementAmount);

        movementsAndOutgoingTransfers.add(movement);
        balance += movementAmount;
    }

    private void checkMovementRestrictions(double movementAmount) {
        if (movementAmount == 0)
            throw new IllegalArgumentException("Quantity can not be zero");

        else if (movementAmount > 0)
            checkMaxDeposit(movementAmount);

        else
            checkMinBalance(movementAmount);
    }

    private void checkMaxDeposit(double quantity) {
        if (quantity > MAX_DEPOSIT)
            throw new IllegalArgumentException("Maximum permitted deposit is " + MAX_DEPOSIT);
    }

    private void checkMinBalance(double quantity) {
        if (balance + quantity < MIN_BALANCE)
            throw new IllegalArgumentException("Your balance can not go under " + MIN_BALANCE);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Account)
            return this.id.equals(((Account) o).id);

        return false;
    }

    @PreRemove
    private void preRemove() {
        for (Movement movement : movementsAndOutgoingTransfers)
            movement.setNullSourceAccount();

        for (Transfer transfer : incomingTransfers)
            transfer.setNullDestinationAccount();
    }
}