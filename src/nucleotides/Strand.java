package nucleotides;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;
import java.util.Arrays;
/**
 * Represents a single strand of Nucleotides. Can mix and match dna and rna.
 * @author clonex10100
 */
public class Strand {
	private Nucleotide[] bases;
	private int x;
	private int y;
	private int r;
	private int index = 0;
	private boolean[] bonds;
	private static int IMAGESIZE = Nucleotide.getImageSize();
	public Strand(int length){
		this(length,0,0,0);
	}
	public Strand(int length,int x,int y) {
		this(length,x,y,0);
	}
	public Strand(int length,int x,int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		bases = new Nucleotide[length];
		bonds = new boolean[length];
		for(int i = 0; i < bases.length; i++){
			bonds[i] = true;
			bases[i] = null;
		}
	}
	/**
	 * Generates a random strand of rna or dna
	 * @param length: Length of strand
	 * @param dnaRna: String "rna" or "dna" specifying whether the nucleotides generated
	 *     should be of type dna or rna
	 * @return New Strand
	 */
	public static Strand getRandomStrand(int length,String dnaRna) {
		Random rand = new Random();
		Strand strand = new Strand(length);
		for(int i = 0; i < length; i++) {
			if(dnaRna.equals("dna")) {
				switch(rand.nextInt(4)) {
				case 0: strand.addNucleotideToEnd(new DnaNucleotide('A')); break;
				case 1: strand.addNucleotideToEnd(new DnaNucleotide('T')); break;
				case 2: strand.addNucleotideToEnd(new DnaNucleotide('G')); break;
				case 3: strand.addNucleotideToEnd(new DnaNucleotide('C')); break;
				}
			}
			else if (dnaRna.equals("rna")){
				switch(rand.nextInt(4)) {
				case 0: strand.addNucleotideToEnd(new RnaNucleotide('A')); break;
				case 1: strand.addNucleotideToEnd(new RnaNucleotide('U')); break;
				case 2: strand.addNucleotideToEnd(new RnaNucleotide('G')); break;
				case 3: strand.addNucleotideToEnd(new RnaNucleotide('C')); break;
				}
			}
			else {
				throw new IllegalArgumentException("Input must be \"dna\" or \"rna\" ");
			}
		}
		return strand;
	}
	/**
	 * Get strand of dna nucleotides with bases complementary to the ones in this strand
	 * @return New strand
	 */
	public Strand getComplementaryDnaStrand() {
		Strand strand = new Strand(bases.length);
		for(int i = bases.length-1; i >= 0; i--) {
			if(bases[i]!= null) {
				strand.addNucleotideToEnd(bases[i].getDnaComplement());
			}
			else {
				strand.addNucleotideToEnd(null);
			}
		}
		return strand;
	}
	/**
	 * Get strand of dna nucleotides with bases complementary to the ones in this strand
	 * @return New strand
	 */
	public Strand getComplementaryRnaStrand() {
		Strand strand = new Strand(bases.length);
		for(int i = bases.length-1; i >= 0; i--) {
			if(bases[i]!= null) {
				strand.addNucleotideToEnd(bases[i].getDnaComplement());
			}
			else {
				strand.addNucleotideToEnd(null);
			}
		}
		return strand;
	}
	/**
	 * Wrapper for length of Strand
	 * @return int length
	 */
	public int getLength(){
		return bases.length;
	}
	/**
	 * Wrapper for strand position
	 * @return Int[3] x,y,r
	 */
	public int[] getPos() {
		return new int[] {x,y,r};
	}
	/**
	 * Gets the index of the strand
	 * (Or the amount of nucleotides that have been added
	 * with addNucleotideToEnd())
	 * @return int
	 */
	public int getIndex() {
		return index;
	}
	/**
	* Sets the position of the strands left most corner with default rotation
	* @param x x pos
	* @param y y pos
	* @param r rotation 0= straight left
	*/
	public void setPos(int x,int y,int r){
	 	this.x=x;
	 	this.y=y;
		this.r=r;
 	}
	public void setPos(int x,int y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Sets the rotation
	 * @param r rotation
	 */
	public void setRotation(int r) {
		this.r = r;
	}
	/**
	* Returns Nucleotide at specified position
	* @param pos int of the index you want the nucleotide of
	* @return Nucleotide at pos in strand
	*/
	public Nucleotide getNucleotide(int pos){
		if( pos < bases.length){
			return bases[pos];
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	/**
	*Sets nucleotide at specified position to specified nucleotide
	* @param Pos, position to replace
	* @param Nucleotide to put at pos
	*/
	public void setNucleotide(int pos, Nucleotide nucleotide){
		if(pos < bases.length){
			bases[pos]=nucleotide;
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	/**
	* Sets postion to be empty
	* @param Pos: Index you want to remove
	*/
	public void removeNucleotide(int pos){
		if( pos < bases.length){
			bases[pos]=null;
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	/**
	*Toggles whether the bond between the 3 prime end of this nucleotide
	*and the 5 prime end of the next one is broken or not
	*@param Pos. Position of nucleotide that the bond is attatched to the suger
	*/
	public void toggleBond(int pos){
		System.out.println("Toggle");
		pos-=1;
		if( pos < bases.length && pos > 0){
			System.out.println("Toggled");
			bonds[pos] = !bonds[pos];
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	/**
	 * Adds dna or rna Nucleotide to bigening of strand
	 * @param Nucleotide
	 */
	public void addNucleotideToStart(Nucleotide nucleotide) {
		for(int i=bases.length-1; i > 0; i--) {
			bases[i] = bases[i-1];
			bonds[i] = bonds[i-1];
		}
		bonds[0] = true;
		bases[0] = nucleotide;
	}
	public void shift(){
		for(int i = 0; i < bases.length-1;i++){
			bases[i]=bases[i+1];
			bonds[i] = bonds[i+1];
		}
		bonds[bonds.length-1] =true;
		bases[bases.length-1] =null;
	}
	/**
	 * Adds dna or rna Nucleotide to end of strand
	 * @param Nucleotide
	 */
	public void addNucleotideToEnd(Nucleotide nucleotide) {
		if(index < bases.length) {
			bases[index]= nucleotide;
			index++;
		}
		else {
			System.out.println("Strand full");
		}
	}
	/**
	 * Splits strand into 2
	 * @params pos- returns strand[0:pos]
	 */
	public Strand splitStrand(int pos) {
		if( pos <= bases.length){
			Strand strand = new Strand(pos,x,y,r);
			for(int i = 0; i < pos; i++) {
				strand.addNucleotideToEnd(bases[i]);
				this.removeNucleotide(i);
			}
			return strand;
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	/**
	 * Draws all nucleotides in strand from left to right
	 * @param gc: GraphicsContext on which to draw strand
	 */
	public void draw(GraphicsContext gc) {
		int x2 = x;
		if(r!=0) {
		    gc.save();
		    gc.translate(x, y);
		    gc.rotate(r);
		    gc.translate(-x, -y);
		}
		for(int i = 0; i < bases.length; i++) {
			if(bases[i] != null){
				bases[i].draw(gc,x2,y);
				if(i+1< bases.length){
					System.out.println(Arrays.asList(bonds).indexOf(false));
					//System.out.println(Arrays.toString(bonds));
					if(bases[i+1] != null && bonds[i]){
						gc.strokeLine(x2+IMAGESIZE*.81, y+IMAGESIZE*.81, x2+IMAGESIZE*1.10, y+IMAGESIZE*.73);
					}
				}
			}
			x2+=IMAGESIZE;
		}
		if(r!=0) {
			gc.restore();
		}
	}
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < bases.length; i++) {
			if(bases[i] == null) {
				result += "_";
			}
			else {
				result += bases[i].toString();
			}
		}
		return result;
	}
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Strand)) {
			return false;
		}
		Strand strand = (Strand) o;
		if(bases.length == strand.getLength()) {
			for(int i = 0; i < bases.length; i++) {
				if(!(bases[i].equals(strand.getNucleotide(i)))) {
					return false;
				}
			}
			return true;
		}

		return false;
	}
}
