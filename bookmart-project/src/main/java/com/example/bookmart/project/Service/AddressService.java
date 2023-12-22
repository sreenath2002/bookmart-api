package com.example.bookmart.project.Service;

import com.example.bookmart.project.Exception.AddresException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Request.AddAddresRequest;
import com.example.bookmart.project.Request.UpdateAddressRequest;
import com.example.bookmart.project.model.Address;

import java.util.List;

public interface AddressService {

    public Address AddAddress(AddAddresRequest req) throws UserException, AddresException;

    public  Address UpdateAddress(Long addressId, UpdateAddressRequest req) throws  AddresException,UserException;

    List<Address> getAddressesByUserId(Long userId);
}
