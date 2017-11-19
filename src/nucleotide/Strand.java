package nucleotide;

import javafx.scene.canvas.GraphicsContext;
import java.util.Random;
public class Strand {
	Nucleotide[] bases;
	int index = 0;
	public Strand(int length) {
		bases = new Nucleotide[length];
	}
	public void addNucleotide(Nucleotide nucleotide) {
		bases[index]= nucleotide;
		index++;
	}
	public static Strand getRandomStrand(int length,String dnaRna) {
		Random rand = new Random();
		Strand strand = new Strand(length);
		for(int i = 0; i < length; i++) {
			if(dnaRna.equals("dna")) {
				switch(rand.nextInt(4)) {
				case 0: strand.addNucleotide(new DnaNucleotide('A')); break;
				case 1: strand.addNucleotide(new DnaNucleotide('T')); break;
				case 2: strand.addNucleotide(new DnaNucleotide('G')); break;
				case 3: strand.addNucleotide(new DnaNucleotide('C')); break;
				}
			}
			else if (dnaRna.equals("rna")){
				switch(rand.nextInt(4)) {
				case 0: strand.addNucleotide(new RnaNucleotide('A')); break;
				case 1: strand.addNucleotide(new RnaNucleotide('U')); break;
				case 2: strand.addNucleotide(new RnaNucleotide('G')); break;
				case 3: strand.addNucleotide(new RnaNucleotide('C')); break;
				}
			}
			else {
				throw new IllegalArgumentException("Input must be \"dna\" or \"rna\" ");
			}
		}
		return strand;
	}
	public Strand getComplementaryDnaStrand() {
		Strand strand = new Strand(index);
		for(int i = 0; i < index; i++) {
			strand.addNucleotide(bases[i].getDnaComplement());
		}
		return strand;
	}
	public Strand getComplementaryRnaStrand() {
		Strand strand = new Strand(index);
		for(int i = 0; i < index; i++) {
			strand.addNucleotide(bases[i].getRnaComplement());
		}
		return strand;
	}
	public int getLength(){
		return bases.length;
	}
	public Nucleotide[] getBases() {
		return bases.clone();
	}
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < index; i++) {
			result += bases[i].toString();
		}
		return result;
	}
	//Draws all nucleotides on canvas object
	public void draw(GraphicsContext gc,int x, int y) {
		for(int i = 0; i < index; i++) {
			bases[i].draw(gc,x,y);
			//100 is size of image
			//Todo: remove magic number
			x+=100;
			//Todo: draw lines between nucleotides to represent covalent bonds
		}
	}
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Strand)) {
			return false;
		}
		Strand strand = (Strand) o;
		if(bases.length == strand.getLength()) {
			Nucleotide[] bases2 = strand.getBases();
			for(int i = 0; i < index; i++) {
				if(!(bases[i].equals(bases2[i]))) {
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
}
