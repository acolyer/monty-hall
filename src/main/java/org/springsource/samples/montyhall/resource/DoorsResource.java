package org.springsource.samples.montyhall.resource;

import org.springframework.hateoas.ResourceSupport;
import org.codehaus.jackson.annotate.JsonProperty;

public class DoorsResource extends ResourceSupport {
	
	@JsonProperty("doors")
	DoorResource[] doors = new DoorResource[3];
  
}
