package com.lucidworks.HttpUtility.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucidworks.HttpUtility.Service.HttpUtilityService;

@RestController
public class HttpUtilityController {
	
	@Autowired
	private HttpUtilityService httpUtilityService; 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtilityController.class);
	
	@GetMapping(value="/call-http",  produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getRequest(@RequestParam("method") String method, @RequestParam("target") String target) {

			String response = "";	
			if(method == null || method.isEmpty() || target == null || target.isEmpty()){
				
				response = "Null value(s) in request";
				LOGGER.error(response);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				
			}else if(!method.equalsIgnoreCase("get")) {
				
				response = "HTTP Method not supported - only GET is implemented";
				LOGGER.error(response);
				return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);	
				
			}else {
				
				try {
						response = httpUtilityService.get(target);
						
						if(response ==null || response.isEmpty()) {
							response = "Maximum number of retries exceeded. No response from target URI.";
							LOGGER.info(response);
							return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);	
							
						} else return new ResponseEntity<>(response, HttpStatus.OK);
						
				}catch(Exception e) {
						LOGGER.error(e.getMessage());
						return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
	
			}
	}
}
