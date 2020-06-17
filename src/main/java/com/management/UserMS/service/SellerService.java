package com.management.UserMS.service;

import java.util.regex.Pattern;

import javax.naming.InvalidNameException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.UserMS.dto.SellerDTO;

import com.management.UserMS.entity.Seller;
import com.management.UserMS.repository.SellerRepository;
@Service
public class SellerService {

	@Autowired
	SellerRepository sellerRepo;
	
	@Autowired
	SellerService sellerService;

	
	
	public void sellerRegisterion(SellerDTO sellerDTO) throws Exception {
		validateSeller(sellerDTO);
		Seller se=sellerDTO.createEntity();
		sellerRepo.save(se);
	}
	
	private void validateSeller(SellerDTO sellerDTO) throws Exception {
		// TODO Auto-generated method stub
		
		if(!isValidName(sellerDTO.getName()))
			throw new InvalidNameException("Invalid Name");
		if(!isValidEmail(sellerDTO.getEmail()))
			throw new Exception("Invalid Email");
		if(!isValidPhoneNumber(sellerDTO.getPhoneNumber()))
			throw new Exception("Invalid Phonenumber");
		if(!isvalidPassword(sellerDTO.getPassword()))
			throw new Exception("Invalid Password");
		if(!isAlreadyPhoneNumberExist(sellerDTO.getPhoneNumber()))
			throw new Exception("PhoneNumber Already exists");
		if(!isAlreadyEmailIdExist(sellerDTO.getEmail()))
			throw new Exception("Email Already exists");
		
	}

	private boolean isAlreadyEmailIdExist(String email) {
		// TODO Auto-generated method stub
		Seller seller=sellerRepo.findByEmail(email);
		if (seller!=null)
			return false;
		return true;
	}

	private boolean isAlreadyPhoneNumberExist(String phoneNumber) {
		// TODO Auto-generated method stub
		Seller seller = sellerRepo.findByPhoneNumber(phoneNumber);
		if (seller!=null)
			return false;
		return true;
	}

	private boolean isvalidPassword(String password) {
		// TODO Auto-generated method stub
		return Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{7,20}$",password);
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return Pattern.matches("^\\d{10}$", phoneNumber);
	}

	private boolean isValidEmail(String email) {
		// TODO Auto-generated method stub
		return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$",email);
	}

	private boolean isValidName(String name) {
		// TODO Auto-generated method stub
		return Pattern.matches("^[a-zA-Z]+[-a-zA-Z\\s]+([-a-zA-Z]+)$", name);
	}

	
	public boolean sellerLogin(SellerDTO sellerDTO) throws Exception {

		Seller seller = sellerRepo.findByEmail(sellerDTO.getEmail());
		if (seller != null) {
			if (seller.getPassword().equals(sellerDTO.getPassword())) {
			return true;
			} else {
				throw new Exception("BuyerLogin:Invalid Password");
			}
		}
		return false;
	}
	
	public boolean deactivateSeller(SellerDTO sellerDTO) throws Exception {

		Seller seller = sellerRepo.findByEmail(sellerDTO.getEmail());
		if (seller != null) {
			if(seller.getPassword().equals(sellerDTO.getPassword())) {
			seller.setIsActive(false);
			sellerRepo.save(seller);
			return true;
		} else {
			throw new Exception("Invalid Email ID/Password ");
		}
		}
			return false;
		}
	
}