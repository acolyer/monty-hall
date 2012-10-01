package org.springsource.samples.montyhall.repository;

import org.springsource.samples.montyhall.domain.Game;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Random;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryGameRepository implements GameRepository {

	private Map<Long,Game> games = Collections.synchronizedMap(new HashMap<Long,Game>());

	public Game findById(Long id) {
	  return this.games.get(id);
	}

	public void storeGame(Game game) {
	  if (game.getId() == null) { 
	    synchronized(games) {
		Long newId = new Random().nextLong();
		while ((newId <= 0) || this.games.containsKey(newId)) {
			newId = new Random().nextLong();
		}
		game.setId(newId);
		games.put(newId,game);
	    }
	  }
	}
}

