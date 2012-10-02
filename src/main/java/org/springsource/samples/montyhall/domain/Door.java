package org.springsource.samples.montyhall.domain;

import org.springframework.hateoas.Identifiable;

public class Door implements Identifiable<Integer> {

	private final Prize prize;
	private final int id;
	private DoorStatus status = DoorStatus.CLOSED;

	public Door(int id, Prize prize) {
		this.id = id;
		this.prize = prize;
	}

	public Integer getId() {
		return this.id;
	}

	public Prize getPrize() {
		return this.prize;
	}

	public DoorStatus getStatus() {
		return this.status;
	}

	public void setStatus(DoorStatus status) {
		boolean illegalTransitionAttempted = false;
		switch (status) {
			case CLOSED :
				if (this.status != DoorStatus.CLOSED) {
					illegalTransitionAttempted = true;
				}
				break;
			case SELECTED :
				if (this.status == DoorStatus.OPENED) {
					illegalTransitionAttempted = true;
				}
				break;
			case OPENED :
				break;
		}
		if (illegalTransitionAttempted) {
			throw new IllegalStateException("Cannot transition to " + status + " from " + this.status);
		}
		this.status = status;
	}

	public void reveal() {
		this.status = DoorStatus.OPENED;
	}

}
