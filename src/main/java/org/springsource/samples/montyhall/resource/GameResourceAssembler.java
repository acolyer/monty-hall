package org.springsource.samples.montyhall.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springsource.samples.montyhall.controller.GameController;
import org.springsource.samples.montyhall.domain.Game;

@Component
public class GameResourceAssembler extends ResourceAssemblerSupport<Game, GameResource> {

	public GameResourceAssembler() {
		super(GameController.class, GameResource.class);
	}

	@Override
	public GameResource toResource(Game game) {
		GameResource resource = createResource(game);
		resource.status = game.getStatus();
		resource.add(linkTo(GameController.class).slash(game).slash("doors").withRel("doors"));
		resource.add(linkTo(GameController.class).slash(game).slash("history").withRel("history"));
		return resource;
	}

}

