package proteins;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nucleotides.Nucleotide;
public class DnaPolymerace{
  private ImageView iv;
  private static int SIZE = 100;
  //Todo: create ligase image
  private static Image IMAGE = new Image("primase.png",SIZE,SIZE,false,false);
  private PrimeZoneManager zones;
  private ImageView createIv(double x, double y) {
    ImageView iv = new ImageView();
    iv.setImage(IMAGE);
    iv.setSmooth(true);
    iv.setPickOnBounds(true);
    iv.setCache(true);
    iv.setCursor(Cursor.HAND);
    iv.setX(x);
    iv.setY(y);
    iv.setOnMouseDragged((t) -> {
      ImageView i = (ImageView) (t.getSource());
      i.setX(t.getSceneX()-SIZE/2);
      i.setY(t.getSceneY()-SIZE/2);
    });
    iv.setOnMouseReleased((t) -> {
      double newX = t.getSceneX();
      double newY = t.getSceneY();
      if(zones.isInZone(newX, newY, 0)) {
        iv.setVisible(false);
      }
    });
    return iv;
  }
  public DnaPolymerace(int x, int y,PrimeZoneManager zones,Group root) {
    iv = createIv((double)(x),(double)(y));
    this.zones=zones;
    root.getChildren().add(iv);
  }
}
