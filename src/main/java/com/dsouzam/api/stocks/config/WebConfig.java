package com.dsouzam.api.stocks.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("com.dsouzam.api.stocks")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	    configurer.defaultContentType(MediaType.APPLICATION_JSON);
	  }

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
