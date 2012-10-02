package org.springsource.samples.montyhall.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springsource.samples.montyhall.controller.GameController;
import org.springsource.samples.montyhall.domain.Game;
import org.springsource.samples.montyhall.domain.Door;

import java.util.List;

@Component
public class DoorsResourceAssembler {

	private final DoorResourceAssembler doorAssembler;

	@Autowired
	public DoorsResourceAssembler(DoorResourceAssembler assembler) {
		this.doorAssembler = assembler;
	}

	public DoorsResource toResource(Game game) {
		DoorsResource resource = new DoorsResource();
		List<Door> doors = game.getDoors();
		resource.doors[0] = this.doorAssembler.toResource(game,doors.get(0));
		resource.doors[1] = this.doorAssembler.toResource(game,doors.get(1));
		resource.doors[2] = this.doorAssembler.toResource(game,doors.get(2));
		resource.add(linkTo(GameController.class).slash(game).slash("doors").withSelfRel());
		return resource;
	}

}