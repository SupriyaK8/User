package com.management.UserMS.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.management.UserMS.dto.SellerDTO;
import com.management.UserMS.service.SellerService;

@RestController
@CrossOrigin
@RequestMapping(value="/api")
public class SellerController {

	private static final Logger logger = LoggerFactory.getLogger(SellerController.class);	
	@Autowired
	private SellerService sellerService;

	
	@PostMapping(value = "/seller/register")
	public String sellerRegistration(@RequestBody SellerDTO sellerDTO) {
		ResponseEntity<String> responseEntity = null;

		try {
			
			logger.info("seller Registration is being done by "+sellerDTO.getName());
			sellerDTO.setIsActive(true);
			
			sellerService.sellerRegistration(sellerDTO);
			String successMessage = environment.getProperty("sellerRegistration.REGISTRATION_SUCCESS");
			responseEntity = new ResponseEntity<String>(successMessage, HttpStatus.OK);
			return "successfull";

		} catch (Exception exception) {
			return "unsuccessfull";
		}

	
	}
	

	@PostMapping(value = "/seller/login")
	public String sellerLogin(@RequestBody SellerDTO sellerDTO) {

		ResponseEntity<String> responseEntity = null;

		try {
			
			
			sellerService.sellerLogin(sellerDTO);
			String successMessage = environment.getProperty("sellerLogin.LOGIN_SUCCESS");
			responseEntity = new ResponseEntity<String>(successMessage, HttpStatus.OK);
			return "successfull";

		} catch (Exception exception) {
			return "Invalid Credentials...Please try again";
		}

		
	}
	
	
	@PostMapping(value = "seller/deactivate",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deactivateSeller(@RequestBody SellerDTO sellerDTO){
			try {
				sellerService.deactivateSeller(sellerDTO);
				return "Account Deactivated ";

			} catch (Exception e) {
				return " Account Deactivation Unsuccessful. Give correct credentials";
			}
	}
	
	
}
