package nucleotides;

import javafx.scene.canvas.GraphicsContext;

/**
 * Purpose: Store two strands right next to each other and is able to perform some actions on both of them
 * @author clonex10100
 *
 */
public class Helix {
	private Strand[] strands;
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
		strands[0].setPos((int)(this.x-XSHIFT), (int)(this.y-YSHIFT));
		strands[1].setPos(this.x+(int)(imageSize*length),this.y,180);
	}
	public Helix(Strand strand,boolean generateComplementaryStrand) {
		this(strand, generateComplementaryStrand ? strand.getComplementaryDnaStrand():new Strand(strand.getLength()));
	}
	public Helix(Strand strand1,Strand strand2) {
		if(strand1.getLength() != strand2.getLength()) {
			throw new IllegalArgumentException("Strands must have the same length");
		}
		strands = new Strand[] {strand1,strand2};
		this.length = strands[0].getLength();
		int[] pos = strands[0].getPos();
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
	* Returns the index of the leading strand
	* @return index- integer
	*/
	public int getIndex() {
		return strands[0].getIndex();
	}
	/**
	 * Sets the position of helix from the top left
	 * @param x- x position
	 * @param y- y position
	 * @param r- rotation of helix
	 */
	public void setPos(int x,int y,int r) {
		this.x = x;
		this.y = y+imageSize;
		this.r = r;
		this.updateStrandPos();
	}
	public void setPos(int x,int y) {
		this.x = x;
		this.y= y+imageSize;
		this.updateStrandPos();
	}
	/**
	* Get nucleotide
	* @param Stard, 1 or 2 for strand 1 or 2
	* @param Pos on strand
	*/
	public Nucleotide getNucleotide(int strand, int pos) {
			return strands[strand].getNucleotide(pos);
	}
	/**
	 * Sets nucleotide in specified strand at specified position
	 * @param strand int 1 or 2
	 * @param pos pos < length
	 * @param nucleotide nucleotide
	 */
	public void setNucleotide(int strand, int pos, Nucleotide nucleotide) {
		strands[strand].setNucleotide(pos,nucleotide);
		this.updateStrandPos();
	}
	/**
	 * Removes nucleotide in specified strand at specified postion
	 * @param strand- int 1 or 2
	 * @param pos int < length
	 */
	public void removeNucleotide(int strand, int pos) {
		strands[strand].removeNucleotide(pos);
	}
	/**
	* Toggles the bond between two nucleotides on the specified strand
	* @param strand- int 1 or 2
	* @param pos- pos > 0 and pos < length
	*/
	public void toggleBond(int strand, int pos) {
		strands[strand].toggleBond(pos);
	}
	/**
	 * Adds nucleotide to start of strand
	 * @param strand- int 1 or 2
	 * @param nucleotide- Nucleotide to adding
	 */
	public void addNucleotideToStart(int strand, Nucleotide nucleotide) {
		strands[strand].addNucleotideToStart(nucleotide);
	}
	/**
	* Shifts all nucleotides in strand over one
	* Usefull to keep both strands aligned after adding to one of them
	* @param strand- 0 or 1
	*/
	public void shift(int strand){
		strands[strand].shift();
	}
	/**
	 * Adds nucleotide to end of strand
	 * @param strand- int 1 or 2
	 * @param nucleotide
	 */
	public void addNucleotideToEnd(int strand, Nucleotide nucleotide) {
		strands[strand].addNucleotideToEnd(nucleotide);
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
		strands[0].draw(gc);
		strands[1].draw(gc);
		gc.restore();
	}
}
