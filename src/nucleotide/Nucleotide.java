package nucleotide;

import javafx.scene.image.Image;

/**
 * 
 * @author clonex10100
 * Superclass for DnaNucleotide and RnaNucleotide
 * Represents a nucleotide of base base and returns the corrosponding image
 */
public class Nucleotide {
	protected char base;
	protected Image image;
	public Image getImage() {
		return image;
	}
	public char getBase() {
		return base;
	}
	
}
