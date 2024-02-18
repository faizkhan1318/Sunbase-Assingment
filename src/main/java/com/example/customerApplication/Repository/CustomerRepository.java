package com.example.customerApplication.Repository;


import com.example.customerApplication.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findByEmail(String email);

    public Customer deleteByEmail(String email);


    @Query("select customer from Customer customer where customer.firstName like %:searchQuery%")
    List<Customer> findByFirstName(String searchQuery);

    @Query("select customer from Customer customer where customer.lastName like %:searchQuery%")
    List<Customer> findByLastName(String searchQuery);

    @Query("select customer from Customer customer where customer.city like %:searchQuery%")
    List<Customer> findByCity(String searchQuery);

    @Query("select customer from Customer customer where customer.email like %:searchQuery%")
    List<Customer> findByEmails(String searchQuery);
}
