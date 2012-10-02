package org.springsource.samples.montyhall.resource;

import org.codehaus.jackson.annotate.JsonProperty;


/** Used when mapping incoming POST requests with Click Stream data */
public class ClickStreamResource  {

	@JsonProperty("data")
	public String data;

}
