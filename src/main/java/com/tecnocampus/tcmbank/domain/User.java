package com.tecnocampus.tcmbank.domain;

import com.tecnocampus.tcmbank.application.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "users")
public class User {
    @Id
    private String id = UUID.randomUUID().toString();

    private String name, surname, dni, address;
    private Date birthDate;

    public User(UserDTO userDTO) throws Exception {
        checkNullArgument(userDTO);

        this.id = UUID.randomUUID().toString();
        initPersonalData(userDTO);
    }

    private void initPersonalData(UserDTO userDTO) throws Exception {
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.dni = userDTO.getDni();
        this.address = userDTO.getAddress();
        this.birthDate = parseBirthDate(userDTO.getBirthDate());
    }

    private void checkNullArgument(Object argument) {
        if (argument == null)
            throw new IllegalArgumentException("Null argument");
    }

    private Date parseBirthDate(String birthDateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDateFormat.parse(birthDateString);
    }

    public void updateUser(UserDTO userDTO) throws Exception {
        checkNullArgument(userDTO);

        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.dni = userDTO.getDni();
        this.address = userDTO.getAddress();
        this.birthDate = parseBirthDate(userDTO.getBirthDate());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User)
            return this.id.equals(((User) o).id);

        return false;
    }
}