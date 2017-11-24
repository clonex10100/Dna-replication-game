package nucleotides;

import javafx.scene.canvas.GraphicsContext;

/**
 * Purpose: Store two strands right next to each other and is able to perform some actions on both of them
 * @author clonex10100
 *
 */
public class Helix {
	private Strand strand1;
	private Strand strand2;
	private static int imageSize = Nucleotide.getImageSize();
	private int length;
	private int x;
	private int y;
	private int r;
	//values to shift x and y by on upsidedown strand to get
	//both strands to vissualy line up.
	private static double XSHIFT = 12;
	private static double YSHIFT = imageSize*0.07;
	private void updateStrandPos() {
		System.out.println(this.y);
		strand1.setPos((int)(this.x-XSHIFT), (int)(this.y-YSHIFT),r);
		strand2.setPos(this.x+(int)(imageSize*length),this.y,180);
	}
	public Helix(Strand strand,boolean generateComplementaryStrand) {
		this(strand, generateComplementaryStrand ? strand.getComplementaryDnaStrand():new Strand(strand.getLength()));
		
	}
	public Helix(Strand strand1,Strand strand2) {
		if(strand1.getLength() != strand2.getLength()) {
			throw new IllegalArgumentException("Strands must have the same length");
		}
		this.strand1 = strand1;
		this.strand2 = strand2;
		this.length = strand1.getLength();
		int[] pos = strand1.getPos();
		x = pos[0];
		y = pos[1];
		r = pos[2];
		this.updateStrandPos();
	}
	/**
	 * Gets x, y and rotation of helix
	 * @return x- left most coord
	 * @return y- right most coord
	 */
	public int[] getPos() {
		return new int[] {x,y-imageSize,r};
	}
	/**
	 * Gets the length of helix
	 * @return int length
	 */
	public int getLength() {
		return length;
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
	/**
	 * Sets the position of helix from the top left
	 * @param x- x position
	 * @param y- y position
	 */
	public void setPos(int x,int y) {
		this.x = x;
		this.y= y+imageSize;
		this.updateStrandPos();
	
	}
	public void setPos(int x,int y,int r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	/**
	 * Sets nucleotide in specified strand at specified position
	 * @param strand int 1 or 2 
	 * @param pos pos < length
	 * @param nucleotide nucleotide
	 */
	public void setNucleotide(int strand, int pos, Nucleotide nucleotide) {
		if(strand ==1) {
			strand1.setNucleotide(pos,nucleotide);
		}
		else if(strand ==2) {
			strand2.setNucleotide(pos,nucleotide);
		}
		else {
			throw new IllegalArgumentException("Expected 1 or 2");
		}
	}
	/**
	 * Removes nucleotide in specified strand at specified postion
	 * @param strand- int 1 or 2
	 * @param pos int < length
	 */
	public void removeNucleotide(int strand, int pos) {
		if(strand ==1) {
			strand1.removeNucleotide(pos);
		}
		else if(strand ==2) {
			strand2.removeNucleotide(pos);
		}
		else {
			throw new IllegalArgumentException("Expected 1 or 2");
		}
	}
	/**
	 * Adds nucleotide to start of strand
	 * @param strand- int 1 or 2
	 * @param nucleotide
	 */
	public void addNucleotideToStart(int strand, Nucleotide nucleotide) {
		if(strand ==1) {
			
		}
		else if(strand ==2) {
			
		}
		else {
			throw new IllegalArgumentException("Expected 1 or 2");
		}
	}
	/**
	 * Adds nucleotide to end of strand
	 * @param strand- int 1 or 2
	 * @param nucleotide
	 */
	public void addNucleotideToEnd(int strand, Nucleotide nucleotide) {
		if(strand ==1) {
			strand1.addNucleotideToEnd(nucleotide);
		}
		else if(strand ==2) {
			strand2.addNucleotideToEnd(nucleotide);
		}
		else {
			throw new IllegalArgumentException("Expected 1 or 2");
		}
	}
	public void toggleBond(int strand, int pos) {
		if(strand ==1) {
			strand1.toggleBond(pos);
		}
		else if(strand ==2) {
			strand2.toggleBond(pos);
		}
		else {
			throw new IllegalArgumentException("Expected 1 or 2");
		}
	}
	/**
	 * Draws both strands next to each other
	 * @param gc- graphics context to draw helix on
	 * @param x- left start of the drawing
	 * @param y- upper start of the drawing
	 */
	public void draw(GraphicsContext gc) {
	    gc.save();
	    gc.translate(x, y-imageSize);
	    gc.rotate(r);
	    gc.translate(-x, -(y-imageSize));
		strand1.draw(gc);
		strand2.draw(gc);
		gc.restore();
	}
}
