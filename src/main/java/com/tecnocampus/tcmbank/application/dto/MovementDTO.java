package com.tecnocampus.tcmbank.application.dto;

import com.tecnocampus.tcmbank.domain.Movement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class MovementDTO {
    private double amount;
    private Date date;
    private String id;

    public MovementDTO(Movement movement) {
        this.amount = movement.getAmount();
        this.date = movement.getDate();
        this.id = movement.getId();
    }
}