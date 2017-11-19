package nucleotide;

import javafx.scene.image.Image;

public class DnaNucleotide extends Nucleotide{
	public DnaNucleotide(char baseNew){
		base = baseNew;
		switch(base) {
		case 'A': image = new Image("a.png",100,100,true,false); break;
		case 'T': image = new Image("a.png",100,100,true,false); break;
		case 'G': image = new Image("a.png",100,100,true,false); break;
		case 'C': image = new Image("a.png",100,100,true,false); break;
		default: throw new IllegalArgumentException("Expected A, T, G, or J");
		}
	}
	@Override
	public DnaNucleotide getDnaComplement(){
		switch(base){
			case 'A': return new DnaNucleotide('T');
			case 'T': return new DnaNucleotide('A');
			case 'G': return new DnaNucleotide('C');
			case 'C': return new DnaNucleotide('G');
		}
		return null;
	}
	@Override
	public RnaNucleotide getRnaComplement(){
		switch(base){
			case 'A': return new RnaNucleotide('U');
			case 'T': return new RnaNucleotide('A');
			case 'G': return new RnaNucleotide('C');
			case 'C': return new RnaNucleotide('G');
		}
		return null;
	}
	public String toString() {
		return "Dna: " + String.valueOf(base);
	}
	
}
