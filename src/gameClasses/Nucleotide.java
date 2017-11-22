package gameClasses;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
/**
 * Purpose: Defines or abstracts methods used by both RnaNucleotide.java and DnaNucleotide.java
 * @author Clonxe10100
 */
public abstract class Nucleotide {
	protected char base;
	protected Image image;
	protected ImageView iv;
	//Todo: Scaling based on display window size
	protected static int imageHeight = 100;
	protected static int imageWidth = 100;
	/**
	 * Acts as wrapper for Image representing the nucleotide.
	 * @return an Image representing the nucleotide.
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * Acts as wrapper for the Char representing the base of the nucleotide.
	 * @return Char base.
	 */
	public char getBase() {
		return base;
	}
	/**
	 * Wrapper for static variables imageHeight and Width
	 * @return int[2]: [Height,Width]
	 */
	public static int[] getImageSize(){
		return new int[] {imageWidth,imageHeight};
	}
	/**
	 * Draws the image representing the nucleotide on a graphics context.
	 * @param GraphicsContext gc: The GraphicsContext you want to draw the object on
	 * @param x: The x coord you want to draw the image at
	 * @param y: The y coord you want to draw the image at
	 */
	public void draw(GraphicsContext gc,int x,int y) {
		gc.drawImage(image, x, y);
	}
	/**
	 * Draws the image representing the nucleotide on a graphics context, with specified rotation.
	 * @param GraphicsContext gc: The GraphicsContext you want to draw the object on
	 * @param x: The x coord you want to draw the image at
	 * @param y: The y coord you want to draw the image at
	 * @param r: The rotation, in degrees, you want to draw the nucleotide at. 0 would draw it with the base pointing up.
	 */
	public void draw(GraphicsContext gc, int x, int y, int r) {
		ImageView iv = new ImageView(image);
		iv.setRotate(r);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image rotatedImage = iv.snapshot(params, null);
		gc.drawImage(rotatedImage, x, y);
	}
	/**
	 * Get a complementary DnaNucleotide
	 * @return DnaNucleoTide that has a complementary base to Nucleotide
	 */
	public abstract DnaNucleotide getDnaComplement();
	/**
	 * Get a complementary RnaNucleotide
	 * @return DnaNucleoTide that has a complementary base to Nucleotide
	 */
	public abstract RnaNucleotide getRnaComplement();
	@Override
	public String toString() {
		return String.valueOf(base);
	}
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Nucleotide)){
			return false;
		}
		Nucleotide nucleotide = (Nucleotide) o;
		if(base == nucleotide.getBase()) {
			return true;
		}
		else {
			return false;
		}
	}
}
