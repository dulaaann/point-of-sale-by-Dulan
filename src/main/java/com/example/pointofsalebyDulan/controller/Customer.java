package com.example.pointofsalebyDulan.controller;

import com.example.pointofsalebyDulan.dto.CustomerDTO;
import com.example.pointofsalebyDulan.dto.request.RequestCustomerDTO;
import com.example.pointofsalebyDulan.service.CustomerService;
import com.example.pointofsalebyDulan.service.impl.CustomerServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/customer")
public class Customer {
    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save")
    public String saveCustomer(@RequestBody CustomerDTO customerDTO){
        String saveCust = customerService.saveCustomerInIMPL(customerDTO);
        return saveCust;
    }

    @PutMapping(path = "/update")
    public String updateCustomer(@RequestBody RequestCustomerDTO requestCustomerDTO){
        String updateCust = customerService.updateCustomerIMPL(requestCustomerDTO);
        return updateCust;
    }

    @GetMapping(
            path = "/search-by-id",
            params = "id"
    )
    public CustomerDTO searchCustomer(@RequestParam (value= "id") int customerId){
        CustomerDTO foundCustomer = customerService.searchCustomerIMPL(customerId);
        return foundCustomer;
    }
}