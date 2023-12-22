package com.example.bookmart.project.Service;

import com.example.bookmart.project.Exception.AddresException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Repository.AddresRepository;
import com.example.bookmart.project.Repository.UserRepository;
import com.example.bookmart.project.Request.AddAddresRequest;
import com.example.bookmart.project.Request.UpdateAddressRequest;
import com.example.bookmart.project.model.Address;
import com.example.bookmart.project.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImp implements  AddressService{


    @Autowired
    private  final UserRepository userRepository;

    private  final UserServiceImp userServiceImp;
     private  final AddresRepository addresRepository;

    public AddressServiceImp(UserRepository userRepository, UserServiceImp userServiceImp, AddresRepository addresRepository) {
        this.userRepository = userRepository;
        this.userServiceImp = userServiceImp;
        this.addresRepository = addresRepository;
    }

    @Override
    public Address AddAddress(AddAddresRequest req) throws UserException , AddresException {
        Address address =new Address();

        System.out.println(req.getUserId());

        User user=userServiceImp.findUserById(req.getUserId());
        if(user==null)
        {
            throw new UserException("User Not Found");

        }
        address.setFirstName(req.getFirstName());
        address.setLastName(req.getLastName());
        address.setBuildingnumber(req.getBuildingNumber());
        address.setStreetAddress(req.getStreetAddress());
        address.setLandmark(req.getLandmark());
        address.setCountry(req.getCountry());
        address.setState(req.getState());
        address.setUser(user);
        address.setCity(req.getCity());
        address.setZipCode(req.getZipCode());
        address.setMobile(req.getMobile());

        return  addresRepository.save(address);

    }
    @Override
    public List<Address> getAddressesByUserId(Long userId) {
        return addresRepository.findByUserId(userId);
    }
    @Override
    public Address UpdateAddress(Long addressId, UpdateAddressRequest req) throws AddresException ,UserException{
        System.out.println(req.getFirstName());
        Optional<Address> existingAddressOptional = addresRepository.findById(addressId);
       User user=userServiceImp.findUserById(req.getUserId());

       if(user==null)
       {
           throw  new UserException("User not found");
       }
        if (existingAddressOptional.isPresent()) {
            Address existingAddress = existingAddressOptional.get();


            existingAddress.setFirstName(req.getFirstName());
            existingAddress.setLastName(req.getLastName());
            existingAddress.setBuildingnumber(req.getBuildingNumber());
            existingAddress.setStreetAddress(req.getStreetAddress());
            existingAddress.setLandmark(req.getLandmark());
            existingAddress.setCountry(req.getCountry());
            existingAddress.setState(req.getState());
            existingAddress.setCity(req.getCity());
            existingAddress.setZipCode(req.getZipCode());
            existingAddress.setMobile(req.getMobile());
            existingAddress.setUser(user);


            // Save the updated address
            return addresRepository.save(existingAddress);
        } else {

            throw new AddresException("Address with ID " + addressId + " not found.");
        }
    }

}
