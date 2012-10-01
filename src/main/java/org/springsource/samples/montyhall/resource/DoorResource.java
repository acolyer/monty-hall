package org.springsource.samples.montyhall.resource;

import org.springframework.hateoas.ResourceSupport;
import org.codehaus.jackson.annotate.JsonProperty;

import org.springsource.samples.montyhall.domain.DoorStatus;


public class DoorResource extends ResourceSupport {
	
	@JsonProperty("status")
	DoorStatus status;
	
	@JsonProperty("content")
	String content;
  
}
