package nucleotides;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Class that represents RnaNucleotides
 * @author tonyd
 */
public class RnaNucleotide extends Nucleotide{
	public RnaNucleotide(char baseNew){
		base = baseNew;
		switch(base) {
		case 'U': image = new Image("ru.png",imageSize,imageSize,true,false); break;
		case 'G': image = new Image("rg.png",imageSize,imageSize,true,false); break;
		case 'C': image = new Image("rc.png",imageSize,imageSize,true,false); break;
		case 'A': image = new Image("ra.png",imageSize,imageSize,true,false); break;
		default: throw new IllegalArgumentException("Must be A, U, G, or c");
		}
	}
	@Override
	public DnaNucleotide getDnaComplement(){
		switch(base){
			case 'A': return new DnaNucleotide('T');
			case 'U': return new DnaNucleotide('A');
			case 'G': return new DnaNucleotide('C');
			case 'C': return new DnaNucleotide('G');
		}
		return null;
	}
	@Override
	public RnaNucleotide getRnaComplement(){
		switch(base){
			case 'A': return new RnaNucleotide('U');
			case 'U': return new RnaNucleotide('A');
			case 'G': return new RnaNucleotide('C');
			case 'C': return new RnaNucleotide('G');
		}
		return null;
	}
}
