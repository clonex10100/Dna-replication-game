package gameStages;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import nucleotides.Helix;
import nucleotides.Nucleotide;
import nucleotides.Strand;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.scene.Scene;


public class HelixTesting extends Application {
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
		Strand dna = Strand.getRandomStrand(3,"dna");
		dna.setPos(100, 100);
		Helix helix = new Helix(dna,true);
		helix.setPos(100, 100,30);
		stage.setScene(scene);
		stage.show();
		helix.draw(gc);
		dna.draw(gc);
		gc.strokeLine(100, 30, 200, 30);
		//helix.setPos(200, 200);
		//gc.strokeLine(200, 200, 300, 200);
		
		//helix.draw(gc);
//	    new AnimationTimer()
//	    {
//	    	long lastNano = 0;
//	        public void handle(long currentNanoTime)
//	        {	
//	    		if((currentNanoTime - lastNano) > 900000000) { 
//	    			gc.setFill(new Color(1,1,1, 1.0) );
//	    			gc.fillRect(0,0, 1010,512);
//	    			gc.strokeLine(100, 300, 200, 300);
//	    			helix.draw(gc);
//	    			dna.draw(gc);
//	    			helix.setPos(100, 100,0);
//	    			helix.draw(gc);
//	    			lastNano = currentNanoTime;
//	    		}
//	        }
//	    }.start();
		
	}
}