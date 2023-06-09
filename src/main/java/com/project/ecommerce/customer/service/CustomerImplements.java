package com.project.ecommerce.customer.service;


import com.project.ecommerce.customer.entities.CustomerEntities;


import java.util.List;
public interface CustomerImplements {
    String addCustomer(CustomerEntities customer);
    CustomerEntities getCustomer(String email);
    List<CustomerEntities> getAllCustomer();
    CustomerEntities updateCustomer(CustomerEntities customer);
    CustomerEntities loginCustomer(CustomerEntities customer) throws Exception;

}
