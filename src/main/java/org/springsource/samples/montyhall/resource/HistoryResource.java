package org.springsource.samples.montyhall.resource;

import org.springframework.hateoas.ResourceSupport;
import org.codehaus.jackson.annotate.JsonProperty;

import org.springsource.samples.montyhall.domain.GameEvent;


public class HistoryResource extends ResourceSupport {

	@JsonProperty("events")
	GameEvent[] events;
  
}
