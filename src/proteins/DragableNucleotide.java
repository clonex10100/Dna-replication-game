package proteins;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nucleotides.Nucleotide;
public class DragableNucleotide{
  private ImageView iv;
  private static int SIZE = 70;
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
      i.setX(t.getSceneX()-SIZE/2);
      i.setY(t.getSceneY()-SIZE/2);
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
  public DragableNucleotide(int x, int y,int type,PrimeZoneManager zones,Group root) {
    switch(type){
      case 1: image = new Image("a.png",SIZE,SIZE,false,false); break;
      case 2: image = new Image("t.png",SIZE,SIZE,false,false); break;
      case 3: image = new Image("g.png",SIZE,SIZE,false,false); break;
      case 4: image = new Image("c.png",SIZE,SIZE,false,false); break;
    }
    iv = createIv((double)(x),(double)(y));
    this.zones=zones;
    this.type = type;
    root.getChildren().add(iv);
  }
}
