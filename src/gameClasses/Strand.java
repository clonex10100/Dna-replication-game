package gameClasses;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;
/**
 * Represents a single strand of Nucleotides. Can mix and match dna and rna.
 * @author clonex10100
 */
public class Strand {
	private Nucleotide[] bases;
	private int length = 0;
	private boolean[] bonds;
	private int[] imageSize = Nucleotide.getImageSize();
	
	public Strand(int length) {
		bases = new Nucleotide[length];
		bonds = new boolean[length];
		for(int i = 0; i < length; i++){
			bonds[i] = true;
		}
	}
	/**
	 * Wrapper for length of Strand
	 * @return int length
	 */
	public int getLength(){
		return bases.length;
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
	 * Adds dna or rna Nucleotide to strand
	 * @param Nucleotide
	 */
	public void addNucleotideToEnd(Nucleotide nucleotide) {
		bases[length]= nucleotide;
		length++;
	}
	/**
	* Sets psotion to be empty
	* @param Pos: Index you want to remove
	*/
	public void removeNucleotide(int pos){
		//add input sanitisations
		if( pos < length){
			bases[pos]=null;
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
	public void addNucleotide(int pos, Nucleotide nucleotide){
		if( pos < length){
			bases[pos]=nucleotide;
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
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
		//Todo: Should be in inverse order so they match when new strand is printed with a 180 rotation
		Strand strand = new Strand(length);
		for(int i = 0; i < length; i++) {
			strand.addNucleotideToEnd(bases[i].getDnaComplement());
		}
		return strand;
	}
	/**
	 * Get strand of dna nucleotides with bases complementary to the ones in this strand
	 * @return New strand
	 */
	public Strand getComplementaryRnaStrand() {
		Strand strand = new Strand(length);
		for(int i = 0; i < length; i++) {
			strand .addNucleotideToEnd(bases[i].getRnaComplement());
		}
		return strand;
	}
	/**
	 * Draws all nucleotides in strand from left to right
	 * @param gc: GraphicsContext on which to draw strand
	 * @param x: Starting x value to draw strand
	 * @param y: Starting y value to draw strand
	 */
	public void draw(GraphicsContext gc,int x, int y) {
		for(int i = 0; i < length; i++) {
			if(bases[i] != null){
				bases[i].draw(gc,x,y);
				if(i+1< length){
					//todo add a way to specify two nucletides not to draw a line through
					if(bases[i+1] != null && bonds[i]){
						gc.strokeLine(x+imageSize[0]*.81, y+imageSize[1]*.81, x+imageSize[0]*1.10, y+imageSize[1]*.73);
						//gc.strokeLine(x, y, x+imageSize[0]*1.10, y+imageSize[1]*.73);
					}
				}
			}
			x+=imageSize[0];
		}
	}
	/**
	 * Draws all nucleotides in strand from left to right and then rotates them
	 * @param gc: GraphicsContext on which to draw strand
	 * @param x: Starting x value to draw strand
	 * @param y: Starting y value to draw strand
	 * @param r: Rotation in degrees to rotate strand
	 */
	public void draw(GraphicsContext gc, int x, int y, int r) {
	    gc.save();
	    gc.translate(x+50, y+50);
	    gc.rotate(r);
	    gc.translate(-(x+50), -(y+50));
	    this.draw(gc, x, y);
	    gc.restore();

	}
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < length; i++) {
			result += bases[i].toString();
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
