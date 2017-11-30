package gameStages;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nucleotides.Helix;
import nucleotides.*;
import nucleotides.Strand;
import proteins.*;
public class Test extends Application{
	public static void main(String[] args){
		launch(args);
	}
	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root);
		final Canvas canvas = new Canvas(1200, 700);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		int xs = 150;
		int ys = 300;
		Strand strand = Strand.getRandomStrand(10, "dna");
		strand.setPos(xs, ys);
		stage.setScene(scene);
		stage.show();
		strand.toggleBond(5);
		strand.setNucleotide(5,new RnaNucleotide('U'));
		Strand strand2 = Strand.getRandomStrand(10,"dna");
				strand2.setPos(xs, ys+200);
				strand2.toggleBond(5);

				strand2.addNucleotideToStart(null);
	 	new AnimationTimer(){
		long lastNano = 0;
		int i=0;
		boolean done = false;
		boolean done2 = false;
	  public void handle(long currentNanoTime){
	  	if(currentNanoTime - lastNano > 900000) {
    		gc.setFill(new Color(1,1,1, 1.0) );
    		gc.fillRect(0,0, 1200,700);
	      strand.draw(gc);
				// strand2.draw(gc);
				if(i > 100&&!done){
					strand.addNucleotideToStart(null);
					done = true;
				}
				i++;
	      lastNano = currentNanoTime;
	      }
				if(i>200&&!done2){
					strand.shift();
					done2 = true;
				}
	    }
		}.start();
	}
}
