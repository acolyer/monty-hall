package org.springsource.samples.montyhall.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springsource.samples.montyhall.controller.GameController;
import org.springsource.samples.montyhall.domain.Game;
import org.springsource.samples.montyhall.domain.GameEvent;
import java.util.List;

@Component
public class HistoryResourceAssembler {

 	public HistoryResourceAssembler() {
	}
	
	public HistoryResource toResource(Game game) {
		HistoryResource resource = new HistoryResource();
		List<GameEvent> history = game.getHistory();
		resource.events = history.toArray(new GameEvent[history.size()]);
		resource.add(linkTo(GameController.class).slash(game).slash("history").withSelfRel());
		return resource;
	}
}