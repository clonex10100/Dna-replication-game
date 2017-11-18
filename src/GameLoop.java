import nucleotide.*;
public class GameLoop {
	public static void main(String[] args){
		DnaNucleotide dna = new DnaNucleotide('A');
		dna.display();
		DnaNucleotide complement = dna.dnaComplement();
		complement.display();
	}
}
