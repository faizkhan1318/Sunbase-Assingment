package com.example.customerApplication.Service;

import com.example.customerApplication.Dtos.request.CustomerRequestDto;
import com.example.customerApplication.Dtos.response.CustomerResponseDto;
import com.example.customerApplication.Exceptions.CustomerAlreadyExist;
import com.example.customerApplication.Exceptions.CustomerNotFound;
import com.example.customerApplication.Models.Customer;
import com.example.customerApplication.Repository.CustomerRepository;
import com.example.customerApplication.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto, boolean sync) {

        Customer customer = customerRepository.findByEmail(customerRequestDto.getEmail());
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();

        //if customer is present and sync is true then update the list
        if(sync && customer != null){
            customerResponseDto = updateCustomer(customerRequestDto.getEmail(), customerRequestDto);
        }
        //else if customer is already present then throw exception
        else if(customer != null){
            throw new CustomerAlreadyExist("Customer Already exits");
        }
        //else add new customer to list
        else{
            customer = CustomerTransformer.customerRequestDtoToCustomer(customerRequestDto);
            customer.setUid(String.valueOf(UUID.randomUUID()));
            Customer savedCustomer = customerRepository.save(customer);

            //response dto
            customerResponseDto = CustomerTransformer.customerToCustomerResponseDto(customer);
            customerResponseDto.setMessage("user account created");

        }
        return customerResponseDto;

    }

    public CustomerResponseDto updateCustomer(String emailId, CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findByEmail(emailId);
        if(customer == null){
            throw new CustomerNotFound("Customer Not Found with this EmailId");
        }
        if(customerRequestDto.getFirstName() !=null){
            customer.setFirstName(customerRequestDto.getFirstName());
        }
        if(customerRequestDto.getLastName()!=null){
            customer.setLastName(customerRequestDto.getLastName());
        }
        if(customerRequestDto.getFirstName() !=null){
            customer.setStreet(customerRequestDto.getStreet());
        }
        if (customerRequestDto.getAddress() != null){
            customer.setAddress(customerRequestDto.getAddress());
        }
        if (customerRequestDto.getCity() != null){
            customer.setCity(customerRequestDto.getCity());
        }
        if (customerRequestDto.getState() != null){
            customer.setState(customerRequestDto.getState());
        }
        if (customerRequestDto.getPhone() != null){
            customer.setPhone(customerRequestDto.getPhone());
        }
        if (customerRequestDto.getEmail() != null){
            customer.setEmail(customerRequestDto.getEmail());
        }
        Customer savedCustomer = customerRepository.save(customer);
        CustomerResponseDto customerResponseDto = CustomerTransformer.customerToCustomerResponseDto(savedCustomer);
        customerResponseDto.setMessage("user information updated");

        return customerResponseDto;

    }

    public CustomerResponseDto getCustomerWithEmail(String email) {
        // get customer by email from repo
        Customer customer = customerRepository.findByEmail(email);

        //if customer is not present
        if (customer == null) {
            throw new CustomerNotFound("no account found with this email");
        }

        CustomerResponseDto customerResponseDto = CustomerTransformer.customerToCustomerResponseDto(customer);
        customerResponseDto.setMessage("account is found associated with this " + email);
        return customerResponseDto;
    }

    public String deleteCustomer(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("no account found with " + email);
        }
        customerRepository.deleteByEmail(email);
        return "account deleted";
    }




    //Get customer by page using pageable class
    public Page<CustomerResponseDto> getAllCustomers(int pageNo, int pageSize, String sortBy) {
        Pageable currentPage;

        if (sortBy != null) {
            currentPage = PageRequest.of(pageNo-1, pageSize, Sort.by(sortBy));
        }else {
            currentPage = PageRequest.of(pageNo-1, pageSize);
        }

        Page<Customer> customersPage = customerRepository.findAll(currentPage);
        return customersPage.map(this::convertToDto);
    }
    public CustomerResponseDto convertToDto(Customer customer){
        return CustomerTransformer.customerToCustomerResponseDto(customer);
    }

    public List<CustomerResponseDto> searchByType(String searchBy, String searchQuery) {
        List<Customer> customerList = new ArrayList<>();

        if(searchBy.equals("firstName")){
            customerList = customerRepository.findByFirstName(searchQuery);
        }else if(searchBy.equals("lastName")){
            customerList = customerRepository.findByLastName(searchQuery);
        }else if(searchBy.equals("city")){
            customerList = customerRepository.findByCity(searchQuery);
        }else if(searchBy.equals("email")){
            customerList = customerRepository.findByEmails(searchQuery);
        }
        List<CustomerResponseDto> searchList = new ArrayList<>();
        for(Customer customer : customerList){
            searchList.add(CustomerTransformer.customerToCustomerResponseDto(customer));
        }
        return searchList;
    }
}
