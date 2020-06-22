package com.management.UserMS.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.management.UserMS.dto.BuyerDTO;
import com.management.UserMS.dto.CartDTO;
import com.management.UserMS.dto.ProductDTO;
import com.management.UserMS.dto.WishlistDTO;
import com.management.UserMS.repository.CartRepository;
import com.management.UserMS.repository.WishlistRepository;
import com.management.UserMS.service.BuyerService;

@RestController
@CrossOrigin
@RequestMapping(value="/api")
public class BuyerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BuyerService buyerService;
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	WishlistRepository wishlistRepo;

	
@PostMapping(value = "/buyer/register")
	public String buyerregistration(@RequestBody BuyerDTO buyerDTO) {
		ResponseEntity<String> responseEntity = null;

		try {
			
			logger.info("Buyer Registration is being done by "+buyerDTO.getName());
			buyerDTO.setIsActive(true);
			buyerDTO.setIsPrivileged(false);
			buyerDTO.setRewardPoints(0);
			buyerService.buyerRegistration(buyerDTO);
			String successMessage = environment.getProperty("BuyerRegistration.REGISTRATION_SUCCESS");
			responseEntity = new ResponseEntity<String>(successMessage, HttpStatus.OK);
			return "successfull";

		} catch (Exception exception) {
			return "Unsuccessfull";
		}

	
	}

	@PostMapping(value = "/buyer/login")
	public String buyerLogin(@RequestBody Buyer buyer) {

		ResponseEntity<String> responseEntity = null;

		try {
			
			
			buyerService.buyerLogin(buyer);
			String successMessage = environment.getProperty("BuyerLogin.LOGIN_SUCCESS");
			responseEntity = new ResponseEntity<String>(successMessage, HttpStatus.OK);
			return "successfull";

		} catch (Exception exception) {
			return "Invalid credentials..PLease try again";
		}

		
	}
	


	@PostMapping(value = "buyer/deactivate",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deactivateBuyer(@RequestBody BuyerDTO buyerDTO) throws Exception{
			try {
				buyerService.deactivateBuyer(buyerDTO);
			} catch (Exception e) {
				throw new Exception("Invalid Credentials");
			}
			return "Account Deactivated";
	
	}
	
	@GetMapping(value = "rewardPoint/{buyerId}")
	public int getRewardPoints(@PathVariable Integer buyerId) {
		System.out.println("BuyerID is:"+ buyerId);
		int Points = buyerService.getRewardPoints(buyerId);
		return Points;
	
	}
	
	@PutMapping(value = "rewardPoint/update/{buyerId}/{point}")
	public void updateRewardPoint(@PathVariable Integer buyerId,@PathVariable Integer point) {
	
		buyerService.updateRewardPoint(buyerId, point);

	}
	
	@GetMapping(value = "buyer/isPrivilege/{buyerId}")
	public boolean isBuyerPrivileged(@PathVariable Integer buyerId) {
		System.out.println("inside buyer privilege");
		return buyerService.IsPrivileged(buyerId);
	}
	
	
	@PostMapping(value = "/buyer/products/wishlist",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addProducttoWishlist(@RequestBody WishlistDTO wishlistDTO) throws Exception {
		try {
			buyerService.addProducttowishlist(wishlistDTO);
			return "prod added";
		} catch (Exception e) {
			return "Product not added";
		}
		
	}
	
	@PostMapping(value = "/buyer/products/cart",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addProducttoCart(@RequestBody CartDTO cartDTO) {
		try {
			buyerService.addProducttoCart(cartDTO);
			return "prod added";
		} catch (Exception e) {
			return "Product not added";
		}
		
	}
	
	@DeleteMapping(value = "buyer/cart/remove/{prodId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeProductfromCart(@PathVariable Integer prodId)
	{
		cartRepo.deleteById(prodId);
	
	}
	@DeleteMapping(value = "buyer/wishlist/remove/{prodId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeProductfromWishlist(@PathVariable Integer prodId)
	{
		wishlistRepo.deleteById(prodId);
	
	}
	
	@GetMapping(value = "buyer/wishlist/{buyerId}")
	public List<WishlistDTO> allWishlistItems(@PathVariable Integer buyerId) {
		System.out.println("inside buyer privilege");
		return buyerService.allWishlistItems(buyerId);
	}
	
	
	@GetMapping(value = "buyer/cart/{buyerId}")
	public List<CartDTO> allCartItems(@PathVariable Integer buyerId) {
		System.out.println("inside buyer privilege");
		return buyerService.allCartItems(buyerId);
	}
	

}



