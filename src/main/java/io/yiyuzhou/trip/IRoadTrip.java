package io.yiyuzhou.trip;

import java.util.List;

public class IRoadTrip {
	public static void main(String[] args) {
		IRoadTrip a3 = new IRoadTrip(args);

		a3.acceptUserInput();
	}

	public IRoadTrip(String[] args) {
		/* TODO */
	}

	public int getDistance(String country1, String country2) {
		/* TODO */
		return -1;
	}

	public List<String> findPath(String country1, String country2) {
		/* TODO */
		return null;
	}

	public void acceptUserInput() {
		/* TODO */
		System.out.println("IRoadTrip - skeleton");
	}
}
