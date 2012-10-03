package org.springsource.samples.montyhall.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.hateoas.Identifiable;

public class Game implements Identifiable<Long> {

	private Long id;
	private GameStatus status = GameStatus.AWAITING_INITIAL_SELECTION;
	private Door[] doors = new Door[3];
	private List<GameEvent> history = new ArrayList<GameEvent>();

	public Game() {
		int doorWithJuergen = new Random().nextInt(3);
		doors[0] = (doorWithJuergen == 0) ? new Door(1,Prize.JUERGEN) : new Door(1,Prize.SMALL_FURRY_ANIMAL);
		doors[1] = (doorWithJuergen == 1) ? new Door(2,Prize.JUERGEN) : new Door(2,Prize.SMALL_FURRY_ANIMAL);
		doors[2] = (doorWithJuergen == 2) ? new Door(3,Prize.JUERGEN) : new Door(3,Prize.SMALL_FURRY_ANIMAL);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GameStatus getStatus() {
		return this.status;
	}

	public List<Door> getDoors() {
		List<Door> doorList =  new ArrayList<Door>(3);
		doorList.add(this.doors[0]);
		doorList.add(this.doors[1]);
		doorList.add(this.doors[2]);
		return doorList;
	}

	public List<GameEvent> getHistory() {
		return this.history;
	}

	/** Select a door, and return the door opened by the host */
	public Door select(Door door) {
		if (this.status != GameStatus.AWAITING_INITIAL_SELECTION) {
			throw new IllegalStateException("Can't select a door from state " + this.status);
		}

		Door toSelect = this.doors[door.getId() - 1];
		toSelect.setStatus(DoorStatus.SELECTED);
		GameEvent selectEvent =  door.getId() == 1
			? GameEvent.SELECTED_DOOR_ONE
			: (door.getId() == 2 ? GameEvent.SELECTED_DOOR_TWO : GameEvent.SELECTED_DOOR_THREE);
		this.history.add(selectEvent);

		int doorToReveal = new Random().nextInt(3);
		while ( (this.doors[doorToReveal] == toSelect) || (this.doors[doorToReveal].getPrize() == Prize.JUERGEN) ) {
			doorToReveal = new Random().nextInt(3);
		}
		Door toReveal = this.doors[doorToReveal];
		toReveal.reveal();
		GameEvent revealEvent = doorToReveal == 0
			? GameEvent.REVEALED_DOOR_ONE
			: (doorToReveal == 1 ? GameEvent.REVEALED_DOOR_TWO : GameEvent.REVEALED_DOOR_THREE);
		this.history.add(revealEvent);
		this.status = GameStatus.AWAITING_FINAL_SELECTION;
		return toReveal;
	}

	public GameStatus open(Door door) {
		if (this.status != GameStatus.AWAITING_FINAL_SELECTION) {
			throw new IllegalStateException("Can't open a door from state " + this.status);
		}
		Door toOpen = this.doors[door.getId() -1];
		toOpen.setStatus(DoorStatus.OPENED);
		Prize prize = toOpen.getPrize();
		if (prize == Prize.JUERGEN) {
			this.status = GameStatus.WON;
		}
		else {
			this.status = GameStatus.LOST;
		}

		GameEvent openEvent = (door.getId() == 1
			? GameEvent.OPENED_DOOR_ONE
			: (door.getId() == 2 ? GameEvent.OPENED_DOOR_TWO : GameEvent.OPENED_DOOR_THREE)); 
		this.history.add(openEvent);
		if (this.status == GameStatus.WON) {
			this.history.add(GameEvent.WON);
		} else {
			this.history.add(GameEvent.LOST);
		}

		return this.status;
	}

}
