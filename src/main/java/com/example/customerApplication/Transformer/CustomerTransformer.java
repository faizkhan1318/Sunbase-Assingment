package com.example.customerApplication.Transformer;

import com.example.customerApplication.Dtos.request.CustomerRequestDto;
import com.example.customerApplication.Dtos.response.CustomerResponseDto;
import com.example.customerApplication.Models.Customer;

public class CustomerTransformer {
    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto) {
        return Customer.builder()
                .firstName(customerRequestDto.getFirstName())
                .lastName(customerRequestDto.getLastName())
                .email(customerRequestDto.getEmail())
                .phone(customerRequestDto.getPhone())
                .city(customerRequestDto.getCity())
                .address(customerRequestDto.getAddress())
                .state(customerRequestDto.getState())
                .street(customerRequestDto.getStreet())
                .build();
    }

    public static CustomerResponseDto customerToCustomerResponseDto(Customer customer) {
        return CustomerResponseDto.builder()
                .Uid(customer.getUid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .state(customer.getState())
                .street(customer.getStreet())
                .address(customer.getAddress())
                .joinedOn(customer.getJoinedDate())
                .phone(customer.getPhone())
                .city(customer.getCity())
                .build();
    }
}
