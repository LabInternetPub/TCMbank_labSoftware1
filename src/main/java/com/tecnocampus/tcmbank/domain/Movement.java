package com.tecnocampus.tcmbank.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
@Getter
@Entity(name = "movements")
public class Movement {
    @Id
    protected String id = UUID.randomUUID().toString();

    @ManyToOne
    protected Account sourceAccount;

    protected Date date;
    protected double amount;

    public Movement(Account sourceAccount, double amount) {
        if (sourceAccount == null)
            throw new IllegalArgumentException("Null source account");

        this.date = Calendar.getInstance().getTime();
        this.amount = amount;
        this.sourceAccount = sourceAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Movement)
            return this.id.equals(((Movement) o).id);

        return false;
    }

    protected void setNullSourceAccount() {this.sourceAccount = null;}
}