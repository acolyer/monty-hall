package org.springsource.samples.montyhall.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springsource.samples.montyhall.domain.Game;
import org.springsource.samples.montyhall.domain.Door;
import org.springsource.samples.montyhall.domain.DoorStatus;
import org.springsource.samples.montyhall.resource.GameResource;
import org.springsource.samples.montyhall.resource.GameResourceAssembler;
import org.springsource.samples.montyhall.resource.DoorResource;
import org.springsource.samples.montyhall.resource.DoorStatusResource;
import org.springsource.samples.montyhall.resource.DoorResourceAssembler;
import org.springsource.samples.montyhall.resource.DoorsResource;
import org.springsource.samples.montyhall.resource.DoorsResourceAssembler;
import org.springsource.samples.montyhall.resource.HistoryResource;
import org.springsource.samples.montyhall.resource.HistoryResourceAssembler;
import org.springsource.samples.montyhall.repository.GameRepository;
import org.springsource.samples.montyhall.resource.ClickStreamResource;
import org.springsource.samples.montyhall.service.ClickStreamService;

@Controller
@RequestMapping("/games")
public class GameController {
	// Keeping things simple with a single controller for the
	// GameResource aggregate root.
	
	private final GameRepository repository;
	private final GameResourceAssembler gameResourceAssembler;
	private final DoorResourceAssembler doorResourceAssembler;
	private final DoorsResourceAssembler doorsResourceAssembler;
	private final HistoryResourceAssembler historyResourceAssembler;
	private final ClickStreamService clickStreamService;

	@Autowired
	public GameController(GameRepository repo, 
	                      GameResourceAssembler assembler,
	                      DoorResourceAssembler doorAssembler,
	                      DoorsResourceAssembler doorsAssembler,
	                      HistoryResourceAssembler historyAssembler,
	                      ClickStreamService clicks) {
		this.repository = repo;
		this.gameResourceAssembler = assembler;
		this.doorResourceAssembler = doorAssembler;
		this.doorsResourceAssembler = doorsAssembler;
		this.historyResourceAssembler = historyAssembler;
		this.clickStreamService = clicks;
	}

	@RequestMapping(method=RequestMethod.POST)
	public HttpEntity<GameResource> createGame() {
		Game game = new Game();
		this.repository.storeGame(game);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(GameController.class).slash(game).toUri());
		return new ResponseEntity<GameResource>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public HttpEntity<GameResource> show(@PathVariable Long id) {
		Game game = this.repository.findById(id);
		if (game == null) { 
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<GameResource>(headers, HttpStatus.NOT_FOUND);		
		}

		GameResource resource = this.gameResourceAssembler.toResource(game);
		return new ResponseEntity<GameResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(value="/{id}/doors", method=RequestMethod.GET) 
	public HttpEntity<DoorsResource> showDoors(@PathVariable Long id) {
		Game game = this.repository.findById(id);
		if (game == null) {
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<DoorsResource>(headers, HttpStatus.NOT_FOUND);		
		}

		DoorsResource resource = this.doorsResourceAssembler.toResource(game);
		return new ResponseEntity<DoorsResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(value="/{gameId}/doors/{doorId}", method=RequestMethod.GET)
	public HttpEntity<DoorResource> showDoor(@PathVariable Long gameId, @PathVariable Long doorId) {
		Game game = this.repository.findById(gameId);
		if (game == null || doorId < 1 || doorId > 3) {
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<DoorResource>(headers, HttpStatus.NOT_FOUND);		
		}

		Door door = game.getDoors().get((int)(doorId - 1));
		DoorResource resource = this.doorResourceAssembler.toResource(game,door);
		return new ResponseEntity<DoorResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(value="/{gameId}/doors/{doorId}", method=RequestMethod.PUT)
	public HttpEntity<DoorResource> updateDoor(@PathVariable Long gameId, 
	                                           @PathVariable Long doorId,
	                                           @RequestBody DoorStatusResource doorResource) {
		Game game = this.repository.findById(gameId);
		if (game == null || doorId < 1 || doorId > 3) {
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<DoorResource>(headers, HttpStatus.NOT_FOUND);		
		}

		Door door = game.getDoors().get((int)(doorId - 1));

		try {
			if (doorResource.status == DoorStatus.SELECTED) {
				game.select(door);
			} else if (doorResource.status == DoorStatus.OPENED) {
				game.open(door);
			}
		} 
		catch (IllegalStateException ex) {
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<DoorResource>(headers, HttpStatus.CONFLICT);					
		}

		DoorResource resource = this.doorResourceAssembler.toResource(game,door);
		return new ResponseEntity<DoorResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(value="/{id}/history", method=RequestMethod.GET)
	public HttpEntity<HistoryResource> showHistory(@PathVariable Long id) {
		Game game = this.repository.findById(id);
		if (game == null) {
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<HistoryResource>(headers, HttpStatus.NOT_FOUND);		
		}

		HistoryResource resource = this.historyResourceAssembler.toResource(game);
		return new ResponseEntity<HistoryResource>(resource, HttpStatus.OK);
	}

	/** and a handler method for the click stream data */
	@RequestMapping(value="/{id}/clicks", method=RequestMethod.POST)
	public HttpEntity<GameResource> recordClickStreamData(@PathVariable Long id,
	                                                      @RequestBody ClickStreamResource clicks) {
		Game game = this.repository.findById(id);
		if (game == null) { 
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<GameResource>(headers, HttpStatus.NOT_FOUND);		
		}

		this.clickStreamService.recordClickStream(game,clicks);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<GameResource>(headers, HttpStatus.CREATED);		
	}

	/** OPTIONS processing for CORS */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/**", method=RequestMethod.OPTIONS)
	public HttpEntity handleOptionsRequest() {
		// a CORS preflight request will be handled by our interceptor
		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow","GET, HEAD, POST, PUT, OPTIONS");
		return new ResponseEntity(headers, HttpStatus.OK);
	}

}
