package proteins;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nucleotides.Nucleotide;
/**
 * Circle that unzips the strand.
 * @author clonex10100
 *
 */
public class Helicase {
	private static double XS;
	private double cxs;
	private double lzip;
	private Circle helicase;
	private Machine machine;
	public static int SIZE = Nucleotide.getImageSize();
	private int unzipLength;
	private int index = 0;
	private boolean unzipped = false;
	private Circle createCircle(double x, double y, double r, Color color) {
		Circle circle = new Circle(x, y, r, color);
		circle.setCursor(Cursor.HAND);
		circle.setOnMousePressed((t) -> {
			cxs = t.getSceneX();
			Circle c = (Circle) (t.getSource());
		    c.toFront();
		});
		circle.setOnMouseDragged((t) -> {
			Circle c = (Circle) (t.getSource());
			double offset = t.getSceneX() - cxs;
			if(t.getSceneX()+offset>XS+Nucleotide.getImageSize()*unzipLength) {
				c.setCenterX(XS+Nucleotide.getImageSize()*unzipLength+50);
				unzipped = true;
				while(index<unzipLength) {
					machine.unzip();
					index++;
				}
			}
			else if(offset > 0 && index < unzipLength) {
				c.setCenterX(c.getCenterX() + offset);
				if(c.getCenterX()-Nucleotide.getImageSize()-20 > lzip ) {
						machine.unzip();
						lzip = lzip+Nucleotide.getImageSize();
						index++;
				}
			}
			cxs = t.getSceneX();
		});
		return circle;
	}
	public Helicase(int x, int y,int length,Machine machine,Group group) {
		lzip = x;
		XS = x;
		cxs = x;
		unzipLength = length;
		this.machine = machine;
		helicase = createCircle((double)(x),(double)(y),SIZE,new Color(1,0.27,0,1));
		group.getChildren().add(helicase);

	}
	public boolean isUnzipped() {
		return unzipped;
	}
	public int getPos() {
		return (int)(helicase.getCenterX());
	}
	public void setUnzipLength(int length){
		unzipLength = length;
	}
}
