package com.example.customerApplication.Controllers;

import com.example.customerApplication.Dtos.request.CustomerRequestDto;
import com.example.customerApplication.Dtos.response.CustomerResponseDto;
import com.example.customerApplication.Exceptions.CustomerAlreadyExist;
import com.example.customerApplication.Exceptions.CustomerNotFound;
import com.example.customerApplication.Security.JwtHelper;
import com.example.customerApplication.Service.ApiService;
import com.example.customerApplication.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    JwtHelper helperClass;
    @Autowired
    CustomerService customerService;

    ApiService apiService = new ApiService();

    @PostMapping("/add")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto, @RequestParam boolean sync){

        try{
            CustomerResponseDto customerResponseDto = customerService.createCustomer(customerRequestDto, sync);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);

        }catch (CustomerAlreadyExist e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{emailId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable String emailId, @RequestBody CustomerRequestDto customerRequestDto){
        try{
            CustomerResponseDto customerResponseDto = customerService.updateCustomer(emailId, customerRequestDto);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.OK);
        }catch (CustomerNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCustomerWithEmail/{email}")
    public ResponseEntity<CustomerResponseDto> getCustomerWithId(@PathVariable String email){

        try{
            CustomerResponseDto customerResponseDto = customerService.getCustomerWithEmail(email);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.FOUND);
        }catch (CustomerNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam String email){
        try {
            String result = customerService.deleteCustomer(email);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        }catch (CustomerNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<Page<CustomerResponseDto>> getAllCustomers(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam (required = false) String sortBy){
        Page<CustomerResponseDto> customerList = customerService.getAllCustomers(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }


    @GetMapping("/sync")
    public ResponseEntity<Object[]> getTokenFromApi(){
        Object[] customerObject = ApiService.getTokenFromApi();
        return new ResponseEntity<>(customerObject, HttpStatus.ACCEPTED);
    }

    @GetMapping("/searchByType")
    public ResponseEntity<List<CustomerResponseDto>> searchBySpecificType(@RequestParam String searchBy, @RequestParam String searchQuery){
        List<CustomerResponseDto>   searchedResult = customerService.searchByType(searchBy,searchQuery);
        return new ResponseEntity<>(searchedResult,HttpStatus.OK);
    }

}
