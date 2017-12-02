package proteins;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nucleotides.Nucleotide;
public class PrimeZoneActivater{
	private ImageView iv;
	private int size;
	private Image image;
	private int type;
	private PrimeZoneManager zones;
	private ImageView createIv(double x, double y) {
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setSmooth(true);
		iv.setPickOnBounds(true);
		iv.setCache(true);
		iv.setX(x);
		iv.setY(y);
		iv.setCursor(Cursor.HAND);
		iv.setOnMouseDragged((t) -> {
			ImageView i = (ImageView) (t.getSource());
			i.setX(t.getSceneX()-size/2);
			i.setY(t.getSceneY()-size/2);
		});
		iv.setOnMouseReleased((t) -> {
			double newX = t.getSceneX();
			double newY = t.getSceneY();
			if(zones.isInZone(newX, newY, type)) {
				iv.setVisible(false);
			}
		});
		return iv;
	}
	public PrimeZoneActivater(int x, int y,int type,PrimeZoneManager zones,Group root) {
		switch(type){
			case 0: size = 100;image = new Image("primase.png",size,size,false,false);break;
			case 1: size = 70;image = new Image("a.png",size,size,false,false);break;
			case 2: size = 70;image = new Image("t.png",size,size,false,false);break;
			case 3: size = 70;image = new Image("g.png",size,size,false,false);break;
			case 4: size = 70;image = new Image("c.png",size,size,false,false);break;
			case 5: size = 100;image = new Image("polimerace.png",size,size,false,false);break;
			case 6: size = 100;image = new Image("ligase.png",size,size,false,false);break;
		}
		iv = createIv((double)(x),(double)(y));
		this.zones = zones;
		this.type = type;
		root.getChildren().add(iv);
	}
}
