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
	public static int SIZE = 70;
	private static int LENGTH;
	private int index = 0;
	private Circle createCircle(double x, double y, double r, Color color) {
		Circle circle = new Circle(x, y, r, color);
		circle.setCursor(Cursor.HAND);
		circle.setOnMousePressed((t) -> {
			cxs = t.getSceneX();
			Circle c = (Circle) (t.getSource());
		    c.toFront();
		});
		circle.setOnMouseDragged((t) -> {
			double offset = t.getSceneX() - cxs;
			if(offset > Nucleotide.getImageSize()) {
				offset = Nucleotide.getImageSize();
			}
			Circle c = (Circle) (t.getSource());
			if(offset > 0 ) {
				if(index <LENGTH) {
					c.setCenterX(c.getCenterX() + offset);
					if(t.getSceneX()-Nucleotide.getImageSize()-20 > lzip ) {
						machine.unzip();
						System.out.println(lzip);
						lzip = t.getSceneX()-20;
						index++;
					}
				}
				else {
					c.setCenterX(XS + index*Nucleotide.getImageSize());
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
		LENGTH = length;
		this.machine = machine;
		helicase = createCircle((double)(x),(double)(y),SIZE,new Color(1,0.27,0,1));
		group.getChildren().add(helicase);
		
	}

}
