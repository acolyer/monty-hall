package org.springsource.samples.montyhall.repository;

import org.springsource.samples.montyhall.domain.Game;

public interface GameRepository {

	Game findById(Long gameId);

	void storeGame(Game game);

}
