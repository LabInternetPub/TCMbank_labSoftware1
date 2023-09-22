package com.tecnocampus.tcmbank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity(name = "transfers")
public class Transfer extends Movement {
    @ManyToOne
    private Account destinationAccount;

    public Transfer(Account sourceAccount, Account destinationAccount, double amount) {
        super(sourceAccount, amount);

        if (destinationAccount == null)
            throw new IllegalArgumentException("Null account/s");

        this.destinationAccount = destinationAccount;
    }

    protected void setNullDestinationAccount() {this.destinationAccount = null;}
}