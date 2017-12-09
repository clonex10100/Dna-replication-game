package nucleotides;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;
import java.util.*;
/**
 * Represents a single strand of Nucleotides. Can mix and match dna and rna.
 * @author clonex10100
 */
public class Strand {
	private ArrayList<Nucleotide>  bases;
	private ArrayList<Boolean>  bonds;
	private int x;
	private int y;
	private int r;
	//1 is lowerStrand 0 is upperStrand
	private int type;
	public Strand(int type){
		this(type,0,0,0);
	}
	public Strand(int type,int x,int y) {
		this(type,x,y,0);
	}
	public Strand(int type,int x,int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		if(type == 0 || type == 1){
			this.type = type;
		}
		bases = new ArrayList<Nucleotide>();
		bonds = new ArrayList<Boolean>();
	}
	/**
	 * Generates a random strand of rna or dna
	 * @param length: Length of strand
	 * @param dnaRna: String "rna" or "dna" specifying whether the nucleotides generated
	 *     should be of type dna or rna
	 * @return New Strand
	 */
	public static Strand getRandomStrand(int length,int type,String dnaRna) {
		Random rand = new Random();
		Strand strand = new Strand(type);
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
	 * Returns complementary dna strand
	 * @returns new strand
	 */
	public Strand getComplementaryDnaStrand(){
		Strand strand = new Strand((type+1)%2);
		for(int i = 0; i < bases.size(); i++){
			strand.addNucleotideToEnd(bases.get(i).getDnaComplement());
		}
		return strand;
	}
	/**
	 * Get strand of dna nucleotides with bases complementary to the ones in this strand
	 * @return New strand
	 */
	public Strand getComplementaryRnaStrand() {
		Strand strand = new Strand((type+1)%2);
		for(int i = 0; i < bases.size(); i++){
			strand.addNucleotideToEnd(bases.get(i).getRnaComplement());
		}
		return strand;
	}

	/**
	 * Wrapper for length of Strand
	 * @return int length
	 */
	public int getLength(){
		return bases.size();
	}
	/**
	 * Wrapper for strand position
	 * @return Int[3] x,y,r
	 */
	public int[] getPos() {
		return new int[] {x,y,r};
	}
	/**
	 * 0 = UpperStrand, 1 is lowerStrand
	 * @return int, 0 or 1
	 */
	public int getType(){
		return type;
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
	public void setType(int type){
		if(type == 0 || type == 1){
			this.type = type;
		}
		else{
			throw new IllegalArgumentException("Type must be zero or one");
		}
	}
	/**
	* Returns Nucleotide at specified position
	* @param pos int of the index you want the nucleotide of
	* @return Nucleotide at pos in strand
	*/
	public Nucleotide getNucleotide(int pos){
		if( pos < bases.size()){
			return bases.get(pos);
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
		if(pos < bases.size()){
			bases.set(pos,nucleotide);
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
		if( pos < bases.size()){
			bases.set(pos,null);
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
		if( pos < bases.size() && pos > 0){
			bonds.set(pos,!bonds.get(pos));
		}
		else{
			throw new IllegalArgumentException("Expected pos to be less than length");
		}
	}
	/**
	 * Adds dna or rna Nucleotide to end of strand
	 * @param Nucleotide
	 */
	public void addNucleotideToEnd(Nucleotide nucleotide) {
		bases.add(nucleotide);
		bonds.add(true);
	}
	/**
	 * Draws all nucleotides in strand from left to right
	 * @param gc: GraphicsContext on which to draw strand
	 * Add type functionality
	 */
	public void draw(GraphicsContext gc) {
		int x2 = x;
		int y2 = y;
		if(r!=0) {
				gc.save();
				gc.translate(x, y);
				gc.rotate(r);
				gc.translate(-x, -y);
		}
		if(type==1){
			for(int i = 0; i < bases.size(); i++) {
				if(bases.get(i) != null){
					bases.get(i).draw(gc,x2,y);
					if(i+1< bases.size()){
						if(bases.get(i+1) != null && bonds.get(i)){
							gc.strokeLine(x2+Nucleotide.getImageSize()*.81, y+Nucleotide.getImageSize()*.81, x2+Nucleotide.getImageSize()*1.10, y+Nucleotide.getImageSize()*.73);
						}
					}
				}
				x2+=Nucleotide.getImageSize();
			}
		}
		else if(type == 0){
			int lx = x2+Nucleotide.getImageSize()*2;
			y2+=Nucleotide.getImageSize();
			x2+=Nucleotide.getImageSize();
			for(int i = 0; i < bases.size(); i++) {
				if(bases.get(i) != null){
					bases.get(i).draw(gc,x2,y2,180);
					if(i+1< bases.size()){
						if(bases.get(i+1) != null && bonds.get(i)){
							//gc.strokeLine(x2,y2,x2+50,y2+50);
							gc.strokeLine(lx-Nucleotide.getImageSize()*.81, y2-Nucleotide.getImageSize()*.81, lx-Nucleotide.getImageSize()*1.10, y2-Nucleotide.getImageSize()*.73);
						}
					}
				}
				x2+=Nucleotide.getImageSize();
				lx+=Nucleotide.getImageSize();
			}
		}
		else{
			System.out.println("Type is not 0 or 1");
		}
		if(r!=0) {
			gc.restore();
		}
	}
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i) == null) {
				result += "_";
			}
			else {
				result += bases.get(i).toString();
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
		if(bases.size() == strand.getLength()) {
			for(int i = 0; i < bases.size(); i++) {
				if(!(bases.get(i).equals(strand.getNucleotide(i)))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
