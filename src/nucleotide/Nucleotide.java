package nucleotide;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * 
 * @author clonex10100
 * Superclass for DnaNucleotide and RnaNucleotide
 * Represents a nucleotide of base base and returns the corresponding image
 */
public abstract class Nucleotide {
	protected char base;
	protected Image image;
	public Image getImage() {
		return image;
	}
	public void draw(GraphicsContext gc,int x,int y) {
		gc.drawImage(image, x, y);
	}
	public char getBase() {
		return base;
	}
	public abstract DnaNucleotide getDnaComplement();
	public abstract RnaNucleotide getRnaComplement();
	@Override
	public String toString() {
		return String.valueOf(base);
	}
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Nucleotide)){
			return false;
		}
		Nucleotide nucleotide = (Nucleotide) o;
		if(base == nucleotide.getBase()) {
			return true;
		}
		else {
			return false;
		}
	}
}
