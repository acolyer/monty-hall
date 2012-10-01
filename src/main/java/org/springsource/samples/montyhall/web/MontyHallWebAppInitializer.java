package org.springsource.samples.montyhall.web;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class MontyHallWebAppInitializer implements WebApplicationInitializer {

  @java.lang.Override
  public void onStartup(ServletContext servletContext) throws ServletException {
	  // Spring annotation based configuration from the 'config' package
	  AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
	  root.scan("org.springsource.samples.montyhall.config");

	  // Servlet setup...
	  servletContext.addListener(new ContextLoaderListener(root));

	  ServletRegistration.Dynamic appServlet = 
		  servletContext.addServlet("appServlet", new DispatcherServlet(root));
	  appServlet.setLoadOnStartup(1);
	  Set<String> mappingConflicts = appServlet.addMapping("/");
	  if ( !mappingConflicts.isEmpty() )  {
		  throw new IllegalStateException ("Could not bind to '/', ensure Tomcat version > 7.0.14 ");
	  }
  }

}
