package org.springsource.samples.montyhall.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springsource.samples.montyhall.web.CorsInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan("org.springsource.samples.montyhall.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CorsInterceptor());
	}

}
