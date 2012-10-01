package org.springsource.samples.montyhall.resource;

import org.springframework.hateoas.ResourceSupport;
import org.codehaus.jackson.annotate.JsonProperty;

import org.springsource.samples.montyhall.domain.DoorStatus;

/** Used when mapping incoming POST requests with Click Stream data */
public class ClickStreamResource  {
	
	@JsonProperty("data")
	public String data;
  
}
