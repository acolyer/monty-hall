package org.springsource.samples.montyhall.service;

import org.springframework.stereotype.Service;
import org.springsource.samples.montyhall.domain.Game;
import org.springsource.samples.montyhall.resource.ClickStreamResource;

@Service
public class ClickStreamService {

	public ClickStreamService() {
		// TODO - inject SI outbound channel
	}

	public void recordClickStream(Game game, ClickStreamResource clicks) {
		// TODO, post as message on outbound channel
	}

}
