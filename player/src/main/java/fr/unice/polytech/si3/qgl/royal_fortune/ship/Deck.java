package fr.unice.polytech.si3.qgl.royal_fortune.ship;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Deck {
	private int width;
	private int length;
	
	public Deck() {}
	public Deck(int width, int length) {
		this.width = width;
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}
	
	@Override
	public String toString() {
		return "Deck [width=" + width + ", length=" + length + "]";
	}		
}
