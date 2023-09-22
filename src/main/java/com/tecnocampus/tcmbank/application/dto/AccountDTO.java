package com.tecnocampus.tcmbank.application.dto;

import com.tecnocampus.tcmbank.domain.Account;
import com.tecnocampus.tcmbank.domain.Movement;
import com.tecnocampus.tcmbank.domain.Transfer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class AccountDTO {
    private String id, iban;
    private double balance;
    private List<MovementDTO> movementsAndOutgoingTransfers;
    private List<MovementDTO> incomingTransfers;
    private UserDTO user;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.iban = account.getIban();
        this.balance = account.getBalance();
        this.movementsAndOutgoingTransfers = transformMovementsIntoDTO(account.getMovementsAndOutgoingTransfers());
        this.incomingTransfers = transformTransfersIntoDTO(account.getIncomingTransfers());
        this.user = new UserDTO(account.getUser());
    }

    private List<MovementDTO> transformMovementsIntoDTO(List<Movement> movements) {
        List<MovementDTO> movementDTOList = new ArrayList<>();

        for (Movement movement : movements)
            movementDTOList.add(new MovementDTO(movement));

        return movementDTOList;
    }

    private List<MovementDTO> transformTransfersIntoDTO(List<Transfer> transfers) {
        List<MovementDTO> transferDTOList = new ArrayList<>();

        for (Transfer transfer : transfers)
            transferDTOList.add(new MovementDTO(transfer));

        return transferDTOList;
    }

    public String getIban() throws Exception {
        if (iban == null || iban.equals(""))
            throw new Exception("IBAN is null or empty");

        return iban;
    }
}