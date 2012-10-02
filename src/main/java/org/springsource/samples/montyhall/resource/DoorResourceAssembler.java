package org.springsource.samples.montyhall.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.stereotype.Component;
import org.springsource.samples.montyhall.controller.GameController;
import org.springsource.samples.montyhall.domain.Game;
import org.springsource.samples.montyhall.domain.Door;
import org.springsource.samples.montyhall.domain.DoorStatus;

@Component
public class DoorResourceAssembler {

	public DoorResourceAssembler() {
	}

	public DoorResource toResource(Game game, Door door) {
		DoorResource resource = new DoorResource();
		resource.status = door.getStatus();
		if (door.getStatus() == DoorStatus.OPENED) {
			resource.content = door.getPrize().toString();
		} else {
			resource.content = "UNKNOWN";
		}
		resource.add(linkTo(GameController.class).slash(game).slash("doors").slash(door).withSelfRel());
		return resource;
	}

}