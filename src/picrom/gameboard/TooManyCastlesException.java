package picrom.gameboard;

public class TooManyCastlesException extends Exception {
	public TooManyCastlesException() {
		System.err.println(
				"Exception trigered due to too long castle generation, try to reduce number of castles or spacing between them or increase world size.");
	}
}
