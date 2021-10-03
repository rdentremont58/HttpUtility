package com.lucidworks.HttpUtility.Bean;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateBean {
	
	private RestTemplate restTemplate;

	@Value("${app.connection.timeout}")
	private int appConnectionTimeout;
	
	@Value("${app.read.timeout}")
	private int appReadTimeout;
	
	@Value("${app.retry.count}")
	private int appRetryCount;
	
	@Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) 
    {
		this.restTemplate = restTemplateBuilder
           .setConnectTimeout(Duration.ofMillis(appConnectionTimeout))
           .setReadTimeout(Duration.ofMillis(appConnectionTimeout))
           .build();
		
		return this.restTemplate;
 
    }
	
	public String get(String target) {
		return this.restTemplate.getForObject(target, String.class);
	}
}
