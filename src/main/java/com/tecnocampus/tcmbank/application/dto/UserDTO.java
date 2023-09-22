package com.tecnocampus.tcmbank.application.dto;

import com.tecnocampus.tcmbank.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDTO {
    private String id, name, surname, dni, address, birthDate;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.dni = user.getDni();
        this.address = user.getAddress();
        this.birthDate = user.getBirthDate().toString();
    }

    public String getName() throws Exception {
        if (name == null || name.equals(""))
            throw new Exception("Name is null or empty");

        return name;
    }

    public String getSurname() throws Exception {
        if (surname == null || surname.equals(""))
            throw new Exception("Surname is null or empty");

        return surname;
    }

    public String getDni() throws Exception {
        if (dni == null || dni.equals(""))
            throw new Exception("DNI is null or empty");

        return dni;
    }

    public String getAddress() throws Exception {
        if (address == null || address.equals(""))
            throw new Exception("Address is null or empty");

        return address;
    }

    public String getBirthDate() throws Exception {
        if (surname == null || surname.equals(""))
            throw new Exception("Birth date is null or empty");

        return birthDate;
    }
}