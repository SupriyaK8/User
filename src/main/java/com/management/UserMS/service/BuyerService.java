package com.management.UserMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.naming.InvalidNameException;


import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.management.UserMS.dto.BuyerDTO;
import com.management.UserMS.dto.CartDTO;

import com.management.UserMS.dto.ProductDTO;
import com.management.UserMS.dto.WishlistDTO;
import com.management.UserMS.entity.Buyer;
import com.management.UserMS.entity.Cart;
import com.management.UserMS.entity.Wishlist;
import com.management.UserMS.repository.BuyerRepository;
import com.management.UserMS.repository.CartRepository;
import com.management.UserMS.repository.WishlistRepository;

@Service
public class BuyerService {
	private static final Logger logger = LoggerFactory.getLogger(BuyerService.class);
	@Autowired
	BuyerRepository buyerRepo;
	

	@Autowired
	WishlistRepository wishlistRepo;

	@Autowired
	CartRepository cartRepo;

	
	
	public void buyerRegistration(BuyerDTO buyerDTO) throws Exception {
		logger.info("Buyer details are going to validate");
		buyerValidation(buyerDTO);
		logger.info("Buyer details are validated successfully");
		Buyer buyerEntity = new Buyer();
		BeanUtils.copyProperties(buyerDTO, buyerEntity);
		buyerRepo.save(buyerEntity);
		logger.info("Buyer details are saved in DB successfully");
		
	}
	public void buyerLogin(Buyer buyer) throws Exception {

		Buyer buyerEntity = buyerRepo.findByEmail(buyer.getEmail());
		if (buyerEntity != null) {
			if (buyerEntity.getPassword().equals(buyer.getPassword())) {
				
			}  else {
				throw new Exception("sellerLogin.INVALID_PASSWORD");
			}

		} else {
			throw new Exception("sellerLogin.INVALID_EMAILID");
		}

	}
	private void buyerValidation(BuyerDTO buyerDTO) throws Exception {

		logger.info("Buyer details are being validated");
		// TODO Auto-generated method stub
		if(!isValidName(buyerDTO.getName()))
			throw new InvalidNameException("BuyerRegistration: Invalid Name");
		if(!isValidEmail(buyerDTO.getEmail()))
			throw new Exception("BuyerRegistration: Invalid Email");
		if(!isValidPhoneNumber(buyerDTO.getPhoneNumber()))
			throw new Exception("BuyerRegistration:Invalid Phone number");
		if(!isvalidPassword(buyerDTO.getPassword()))
			throw new Exception("BuyerRegistration: Invalid Password");
		if(!isAlreadyPhoneNumberExist(buyerDTO.getPhoneNumber()))
			throw new Exception("BuyerRegistration: Phone number already exists");
		if(!isAlreadyEmailIdExist(buyerDTO.getEmail()))
			throw new Exception("BuyerRegistration: Email already exists");
		
		
	}

	private boolean isAlreadyEmailIdExist(String email) {
		// TODO Auto-generated method stub
		Buyer buyer=buyerRepo.findByEmail(email);
		if (buyer!=null)
			return false;
		return true;
	}

	private boolean isAlreadyPhoneNumberExist(String phoneNumber) {
		// TODO Auto-generated method stub
		Buyer buyer=buyerRepo.findByPhoneNumber(phoneNumber);
		if (buyer!=null)
			return false;
		return true;
	}

	private boolean isvalidPassword(String password) {
		return Pattern.matches("(?=.\\d)(?=.[a-z])(?=.[A-Z])(?=.[!@#$%^&*]).{7,20}$",password);
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		
		return Pattern.matches("^\\d{10}$", phoneNumber);
	}

	private boolean isValidEmail(String email) {
		
		return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$",email);
	}

	private boolean isValidName(String name) {
		
		return Pattern.matches("^[a-zA-Z]+[-a-zA-Z\\s]+([-a-zA-Z]+)$", name);
	}

	
	

	

	public void buyerDeactivation(BuyerDTO buyerDTO) throws Exception {

		Buyer buyer = buyerRepo.findByEmail(buyerDTO.getEmail());
		if (buyer != null) {
			buyer.setIsActive(false);
			buyerRepo.save(buyer);
		} else {
			System.out.println("Invalid Email ID ");
		}

	}
	
	
	public int getRewardPoints(Integer buyerId) {
		System.out.println("BuyerId"+buyerId);
		Buyer buyer=buyerRepo.findByBuyerId(buyerId);
		return buyer.getRewardPoints();

		
	}
	
	public void updateRewardPoints(Integer buyerId, Integer point) {
		Buyer buyer =buyerRepo.findByBuyerId(buyerId);
		if (buyer!=null){
		buyer.setRewardPoints(point);
		buyerRepo.save(buyer);
		}else {
			System.out.println("Invalid BuyerId");
		}
		
	}

public boolean IsPrivileged(Integer buyerId) {
		
		Buyer buyer= buyerRepo.findByBuyerId(buyerId);
		
		if((buyer.getIsPrivileged())==false) {
			
			return false;
		}
		else {
			
			return true;
		}
		
	}
	public void addProducttowishlist(WishlistDTO wishlistDTO) {
		
		
		Wishlist wishlist =wishlistDTO.createEntity();
		wishlistRepo.save(wishlist);
		
		
		
	}
	public void addProducttoCart(CartDTO cartDTO) {
	

		Cart cart =cartDTO.createEntity();
		cartRepo.save(cart);
		
		
		
	}
	public List<WishlistDTO> WishlistItems(Integer buyerId) {
		
		
		List<Wishlist> inwishlist= wishlistRepo.findAll();
		List<WishlistDTO> wishlistDTOs = new ArrayList<>();
		for (Wishlist wishlist : inwishlist) {
			WishlistDTO wishlistDTO=WishlistDTO.valueOf(wishlist);
			wishlistDTOs.add(wishlistDTO);
		}
		return wishlistDTOs;

	}
	public List<CartDTO> CartItems(Integer buyerId) {
	
		List<Cart> incart= cartRepo.findAll();
		List<CartDTO> cartDTOs = new ArrayList<>();
		for (Cart cart : incart) {
			CartDTO cartDTO=CartDTO.valueOf(cart);
			cartDTOs.add(cartDTO);
		}
		return cartDTOs;	
		

	}
	
}
