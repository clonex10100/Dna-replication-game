package nucleotide;

import javafx.scene.image.Image;

public class DnaNucleotide extends Nucleotide{
	public DnaNucleotide(char baseNew){
		base = baseNew;
//		switch(base) {
//		case 'A': image = new Image("a.png",100,100,true,false);
//		case 'T': image = new Image("t.png",100,100,true,false);
//		case 'G': image = new Image("g.png",100,100,true,false);
//		case 'C': image = new Image("c.png",100,100,true,false);
//		}
		image = new Image("a.png",100,100,true,false);
	}
	public DnaNucleotide dnaComplement(){
		switch(base){
			case 'A': return new DnaNucleotide('T');
			case 'T': return new DnaNucleotide('A');
			case 'G': return new DnaNucleotide('C');
			case 'C': return new DnaNucleotide('G');
		}
		return null;
	}
	public RnaNucleotide rnaComplement(){
		switch(base){
			case 'A': return new RnaNucleotide('U');
			case 'T': return new RnaNucleotide('A');
			case 'G': return new RnaNucleotide('C');
			case 'C': return new RnaNucleotide('G');
		}
		return null;
	}
	
}
