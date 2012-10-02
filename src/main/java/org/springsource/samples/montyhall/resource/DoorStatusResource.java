package org.springsource.samples.montyhall.resource;

import org.codehaus.jackson.annotate.JsonProperty;

import org.springsource.samples.montyhall.domain.DoorStatus;

/** Used when mapping incoming PUT / PATCH requests with partial info */
public class DoorStatusResource  {

	@JsonProperty("status")
	public DoorStatus status;

}
