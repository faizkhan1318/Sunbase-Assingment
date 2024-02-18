package com.example.customerApplication.Dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
    private String Uid;

    private String message;

    private Date joinedOn;

    private String firstName;

    private String lastName;

    private String street;

    private String address;

    private String city;

    private String state;

    private String email;

    private String phone;
}
