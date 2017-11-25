package gameStages;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nucleotides.Helix;
import nucleotides.Nucleotide;
import nucleotides.Strand;
import proteins.Helicase;

public class HelicaseTesting extends Application{
	public static void main(String[] args){
		launch(args);	
	}
	@Override
	public void start(Stage stage) {
		StackPane root = new StackPane();	
		Scene scene = new Scene(root);
		final Canvas canvas = new Canvas(1000, 500);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		int a = 3;
		int xs = 153;
		int ys = 439;
		Strand dna = Strand.getRandomStrand(a, "dna");
		Helix original = new Helix(dna,true);
		original.setPos(xs, ys-Nucleotide.getImageSize());
		Helicase circle = new Helicase(original);
		stage.setScene(scene);
		stage.show();
	    new AnimationTimer()
	    {
	    	int i = 0;
        	long lastNano = 0;
	        public void handle(long currentNanoTime)
	        {	
	    		if((currentNanoTime - lastNano) > 900000000) {
		            if(i<a) {       	
			        	circle.unzip();
			        	i++;
		            }
	    			gc.setFill(new Color(1,1,1, 1.0) );
	    			gc.fillRect(0,0, 1010,512);
	    			circle.draw(gc);
	    			lastNano = currentNanoTime;
	    		}
	        }
	    }.start();
	}
}