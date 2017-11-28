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
import nucleotides.Nucleotide;
import nucleotides.Strand;
import proteins.Helicase;
import proteins.Machine;
public class HelicaseTesting extends Application{
	public static void main(String[] args){
		launch(args);
	}
	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root);
		final Canvas canvas = new Canvas(1000, 500);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		int length = 10;
		int xs = 50;
		int ys = 200;
		Helix originalHelix = new Helix(Strand.getRandomStrand(length, "dna"),true);
		originalHelix.setPos(xs, ys);
		Machine machine = new Machine(originalHelix,root);
		stage.setScene(scene);
		stage.show();

	    new AnimationTimer()
	    {
					boolean done = false;
        	long lastNano = 0;
	        public void handle(long currentNanoTime)
	        {
	    			if((currentNanoTime - lastNano) > 60000) {
	    				gc.setFill(new Color(1,1,1, 1.0) );
	    				gc.fillRect(0,0, 1010,512);
	    				if(machine.isUnzipped()&&!done) {
	    					System.out.println("unzipped");
								machine.addZone(0,0,3);
								done = true;
	    				}
	    				machine.draw(gc);
	    				lastNano = currentNanoTime;
	    			}
	        }
	    }.start();
	}
}
