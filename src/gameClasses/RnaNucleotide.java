package gameClasses;
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
		case 'A': image = new Image("a.png",imageHeight,imageWidth,true,false); break;
		case 'U': image = new Image("a.png",imageHeight,imageWidth,true,false); break;
		case 'G': image = new Image("a.png",imageHeight,imageWidth,true,false); break;
		case 'C': image = new Image("a.png",imageHeight,imageWidth,true,false); break;
		default: throw new IllegalArgumentException("Must be A, U, G, or c");
		}
		iv = new ImageView(image);
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
