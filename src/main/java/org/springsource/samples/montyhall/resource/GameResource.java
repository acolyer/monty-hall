package org.springsource.samples.montyhall.resource;

import org.springframework.hateoas.ResourceSupport;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springsource.samples.montyhall.domain.GameStatus;

public class GameResource extends ResourceSupport {
  
	@JsonProperty("status")
	GameStatus status;

}
