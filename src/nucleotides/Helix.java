package nucleotides;

import javafx.scene.canvas.GraphicsContext;

/**
 * Purpose: Store two strands right next to each other and is able to perform some actions on both of them
 * @author clonex10100
 *
 */
public class Helix {
	//{Upperstrand,lowerstrand}
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
		strands[0].setPos(this.x,this.y);
		strands[1].setPos((int)(this.x-XSHIFT), (int)(this.y+Nucleotide.getImageSize()-YSHIFT));
	}
	public Helix(Strand strand,boolean generateComplementaryStrand) {
		this(strand, generateComplementaryStrand ? strand.getComplementaryDnaStrand():new Strand((strand.getType()+1)%2));
	}
	public Helix(Strand strand1,Strand strand2) {
		if(strand1.getLength() > strand2.getLength()) {
			while(strand1.getLength()>strand2.getLength()){
				strand2.addNucleotideToEnd(null);
			}
		}
		if(strand1.getType() == strand2.getType()){
			throw new IllegalArgumentException("Strands must be opisite types");
		}
		this.length = strand1.getLength();
		if(strand1.getType()==0){
			int[] pos = strand1.getPos();
			x = pos[0];
			y = pos[1];
			r = pos[2];
			strands = new Strand[]{strand1,strand2};
		}
		else{
			System.out.println(strand1.getPos()[0]);
			System.out.println(strand1.getPos()[1]);
			int[] pos = strand1.getPos();
			x = pos[0]+(int)(XSHIFT);
			y = pos[1]-Nucleotide.getImageSize()+(int)(YSHIFT);
			r = pos[2];
			System.out.println(Integer.toString(x)+" " + Integer.toString(y));
			strands = new Strand[]{strand2,strand1};

		}
		this.updateStrandPos();
	}
	/**
	 * Gets x, y and rotation of helix
	 * @return x- left most coord
	 * @return y- right most coord
	 */
	public int[] getPos() {
		return new int[] {x,y,r};
	}
	/**
	 * Gets the length of helix
		* @return int length
	*/
	public int getLength() {
		return length;
	}
	/**
	 * Sets the position of helix from the top left
	 * @param x- x position
	 * @param y- y position
	 * @param r- rotation of helix
	 */
	public void setPos(int x,int y,int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.updateStrandPos();
	}
	public void setPos(int x,int y) {
		this.x = x;
		this.y= y;
		this.updateStrandPos();
	}
	/** sets the rotation of helix. The center is the
	 * top left corner
	 * @param Rotation- Int < 360 and > -360
	 */
	public void setRotation(int r){
		this.r = r;
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
	 * Adds nucleotide to end of strand
	 * @param strand- int 1 or 2
	 * @param nucleotide
	 */
	public void addNucleotideToEnd(int strand, Nucleotide nucleotide) {
		strands[strand].addNucleotideToEnd(nucleotide);
		strands[(strand+1)%2].addNucleotideToEnd(null);
		length++;
	}
	public void addNucleotideToEnd(Nucleotide nucleotide1, Nucleotide nucleotide2){
		strands[0].addNucleotideToEnd(nucleotide1);
		strands[1].addNucleotideToEnd(nucleotide2);
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
		gc.strokeLine(x,y,x+100,y);
		strands[0].draw(gc);
		strands[1].draw(gc);
		gc.restore();
	}
}
