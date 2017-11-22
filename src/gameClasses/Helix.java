package gameClasses;

import javafx.scene.canvas.GraphicsContext;

/**
 * Purpose: Store two strands right next to each other and is able to perform some actions on both of them
 * @author clonex10100
 *
 */
public class Helix {
	Strand strand1;
	Strand strand2;
	int[] imageSize = Nucleotide.getImageSize();
	int length;
	public Helix(Strand strand) {
		strand1 = strand;
		length = strand.getLength();
		strand2 = strand.getComplementaryDnaStrand();
	}
	public Helix(Strand strand1,Strand strand2) {
		this.strand1 = strand1;
		this.strand2 = strand2;
		length = strand1.getLength();
	}
	/**
	 * Draws both strands next to each other
	 * @param gc- graphics context to draw helix on
	 * @param x- left start of the drawing
	 * @param y- upper start of the drawing
	 */
	public void draw(GraphicsContext gc,int x,int y) {
		strand1.draw(gc, x, y);
		strand2.draw(gc, (int)(x+imageSize[0]*(length-1)+imageSize[0]*.17),(int)(y-imageSize[0]*.90),180);
	}
	
}
