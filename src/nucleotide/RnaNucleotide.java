package nucleotide;


public class RnaNucleotide extends Nucleotide {
	public RnaNucleotide(char baseNew){
		base = baseNew;
	}
	public DnaNucleotide dnaComplement(){
		switch(base){
			case 'A': return new DnaNucleotide('T');
			case 'U': return new DnaNucleotide('A');
			case 'G': return new DnaNucleotide('C');
			case 'C': return new DnaNucleotide('G');
		}
		return null;
	}
	public RnaNucleotide rnaComplement(){
		switch(base){
			case 'A': return new RnaNucleotide('U');
			case 'U': return new RnaNucleotide('A');
			case 'G': return new RnaNucleotide('C');
			case 'C': return new RnaNucleotide('G');
		}
		return null;
	}
	
}
