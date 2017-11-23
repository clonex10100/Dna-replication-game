package nucleotides;

import javafx.scene.canvas.GraphicsContext;

/**
 * Purpose: Store two strands right next to each other and is able to perform some actions on both of them
 * @author clonex10100
 *
 */
public class Helix {
	Strand strand1;
	Strand strand2;
	int imageSize = Nucleotide.getImageSize();
	int length;
	int x;
	int y;
	public Helix(Strand strand) {
		length = strand.getLength();
		strand1 = strand;
		strand2 = strand.getComplementaryDnaStrand();
		x = strand1.getPos()[0];
		y = (int)(strand1.getPos()[1]-imageSize*.93);
		strand2.setPos((int)(x+imageSize*(length-1)+imageSize*.17),y,180);

	}
	public Helix(Strand strand1,Strand strand2) {
		this.strand1 = strand1;
		this.strand2 = strand2;
		x = strand1.getPos()[0];
		y = (int)(strand1.getPos()[1]-imageSize*.93);
		strand2.setPos((int)(x+imageSize*(length-1)+imageSize*.17),y,180);
		length = strand1.getLength();
	}
	/**
	 * Sets the position of helix from the top left
	 * @param x- x position
	 * @param y- y position
	 */
	public void setPos(int x,int y) {
		this.x = x;
		this.y= y;
		strand1.setPos(x, (int)(y+imageSize*.93));
		strand2.setPos((int)(x+imageSize*(length-1)+imageSize*.18),y);
	}
	/**
	 * Gets x, y and rotation of helix
	 * @return x- left most coord
	 * @return y- right most coord
	 */
	public int[] getPos() {
		return new int[] {x,y};
	}
	/**
	 * Draws both strands next to each other
	 * @param gc- graphics context to draw helix on
	 * @param x- left start of the drawing
	 * @param y- upper start of the drawing
	 */
	public void draw(GraphicsContext gc) {
		strand1.draw(gc);
		strand2.draw(gc);
	}
	/**
	 * Get nucleotide
	 * @param Stard, 1 or 2 for strand 1 or 2
	 * @param Pos on strand
	 */
	public Nucleotide getNucleotide(int strand, int pos) {
		if(strand ==1) {
			return strand1.getNucleotide(pos);
		}
		else if(strand ==2) {
			return strand2.getNucleotide(pos);
		}
		else {
			throw new IllegalArgumentException("Expected 1 or 2");
		}
	}
	public Strand[] unzip(int i) {
		Strand strandr1 = new Strand(i);
		Strand strandr2 = new Strand(i);
    	strandr1.addNucleotideToEnd(strand2.getNucleotide(i));
    	strand1.removeNucleotide(i);
    	strandr2.addNucleotideToStart(strand1.getNucleotide(length-i-1));
    	strand2.removeNucleotide(length-i-1);
    	int x2 = 70*i+25;
    	//I wrote this at 4am and have no idea what it does but it works
    	int h = Nucleotide.getImageSize()*(i+1);
		x=x-10+h - (int)(h*Math.cos(Math.toRadians(-30)));
		y=y-30+h + (int)(h*Math.sin(Math.toRadians(-30)));
		strandr1.setPos(x2, 300-(int)(70*.93),210);
		strandr2.setPos(x, y,-30);
		return new Strand[] {strandr1,strandr2};
	}
	/*
	 * Todo:
	 * Input protection: clone strands before storing them
	 * Function to split helix into component strands;
	 * Strand interface components: setNucleotide, toggleBond ect:
	 */
}
