package com.lucidworks.HttpUtility.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lucidworks.HttpUtility.Bean.RestTemplateBean;

@Service
public class HttpUtilityService {
	
	@Autowired
	private RestTemplateBean restTemplateBean;

	@Value("${app.retry.count}")
	private int appRetryCount;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtilityService.class);
	
	public String get(String target) {
		String response = "";
		
		for(int i=0; i<appRetryCount; appRetryCount--) {
		
				response = restTemplateBean.get(target);
				
				if (response ==null) {
					LOGGER.info("No response from " + target + "retrying " + appRetryCount + " more times");
				}else {
					LOGGER.info("Success!");
					break;
				}
			}
		
		return response;
	}
	
}
