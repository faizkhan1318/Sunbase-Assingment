package com.example.customerApplication.Exceptions;

public class CustomerAlreadyExist extends RuntimeException {
    public CustomerAlreadyExist(String message) {
        super(message);
    }
}
