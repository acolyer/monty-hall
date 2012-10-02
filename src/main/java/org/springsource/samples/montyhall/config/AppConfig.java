package org.springsource.samples.montyhall.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.springsource.samples.montyhall",
               excludeFilters = {@Filter(Controller.class)})
public class AppConfig {

}