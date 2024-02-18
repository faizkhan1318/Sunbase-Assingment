package com.example.customerApplication.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {
    private String firstName;

    private String lastName;

    private String street;

    private String address;

    private String city;

    private String state;

    private String email;

    private String phone;
    private Date joiedOn;
}
