package nucleotides;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;
/**
 * Represents a single strand of Nucleotides. Can mix and match dna and rna.
 * @author clonex10100
 */
public class Strand {
	private Nucleotide[] bases;
	private int length;
	private int index = 0;
	private boolean[] bonds;
	private int imageSize = Nucleotide.getImageSize();
	private int x;
	private int y;
	private int r;
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
		this.length = length;
		for(int i = 0; i < length; i++){
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
		Strand strand = new Strand(length);
		for(int i = length-1; i >= 0; i--) {
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
		Strand strand = new Strand(length);
		for(int i = length-1; i >= 0; i--) {
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
		return length;
	}
	/**
	 * Wrapper for strand position
	 * @return Int[3] x,y,r
	 */
	public int[] getPos() {
		return new int[] {x,y,r};
	}
	/**
	 * Wrapper for rotation of nucleotide
	 * @return int rotation
	 */
	public int getRotation() {
		return r;
	}
	/**
	 * Wrapper for array of bases in strand
	 * @return Array of nucleotides in strand
	 */
	public Nucleotide[] getBases() {
		return bases.clone();
	}
	/**
	* Returns Nucleotide at specified position
	* @param pos int of the index you want the nucleotide of
	* @return Nucleotide at pos in strand
	*/
	public Nucleotide getNucleotide(int pos){
		if( pos < length){
			return bases[pos];
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	/**
	 * Sets the position of the strands left most corner with default rotation
	 * @param x x pos
	 * @param y y pos
	 */
	public void setPos(int x,int y){
		this.x=x;
		this.y=y;		
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
	/**
	 * Sets the rotation
	 * @param r rotation
	 */
	public void setRotation(int r) {
		this.r = r;
	}
	/**
	*Sets nucleotide at specified position to specified nucleotide
	* @param Pos, position to replace
	* @param Nucleotide to put at pos
	*/
	public void setNucleotide(int pos, Nucleotide nucleotide){
		if( pos < length){
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
		if( pos < length){
			bases[pos]=null;
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	public void addNucleotideToStart(Nucleotide nucleotide) {
		for(int i=length-1; i > 0; i--) {
			bases[i] = bases[i-1];
		}
		bases[0] = nucleotide;
		
	}
	/**
	 * Adds dna or rna Nucleotide to strand
	 * @param Nucleotide
	 */
	public void addNucleotideToEnd(Nucleotide nucleotide) {
		if(index < length) {	
			bases[index]= nucleotide;
			index++;
		}
		else {
			System.out.println("Strand full");
		}
	}
	/**
	*Toggles whether the bond between the 3 prime end of this nucleotide 
	*and the 5 prime end of the next one is broken or not
	*@param Pos. Position of nucleotide that the bond is attatched to the suger
	*/
	public void toggleBond(int pos){
		if( pos < length){
			bonds[pos] = !bonds[pos];
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	/**
	 * Splits strand into 2
	 * @params pos- returns strand[0:pos]
	 */
	public Strand splitStrand(int pos) {
		if( pos <= length){
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
		for(int i = 0; i < length; i++) {
			if(bases[i] != null){
				bases[i].draw(gc,x2,y);
				if(i+1< length){
					if(bases[i+1] != null && bonds[i]){
						gc.strokeLine(x2+imageSize*.81, y+imageSize*.81, x2+imageSize*1.10, y+imageSize*.73);
					}
				}
			}
			x2+=imageSize;
		}
		if(r!=0) {
			gc.restore();
		}
	}
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < length; i++) {
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
			Nucleotide[] bases2 = strand.getBases();
			for(int i = 0; i < length; i++) {
				if(!(bases[i].equals(bases2[i]))) {
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
}
