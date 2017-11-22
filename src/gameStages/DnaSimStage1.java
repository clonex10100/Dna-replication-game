package gameStages;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import nucleotides.Helix;
import nucleotides.Strand;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.scene.Scene;


public class DnaSimStage1 extends Application {
	public static void main(String[] args){
		launch(args);	
	}
	@Override
	public void start(Stage stage) {
		/*
		 * Everything here is just for testing purposes. Once I get the game classes done
		 * than I will start working on this.
		 * There will be several application classes for different states of the game.
		 * 
		 * As of right now i'm looking for it to progress like this:
		 * Stage 1: Drag helicase through the middle of two dna strands to unzip them.
		 * Stage 2: Drag primase to the seperated dna strands and it automaticaly places RNA primer where you dragged it.
		 * Stage 3: DNA polymerace moves along the seperated dna strands. At the bottom there is a bar containing all 4 DNA nucleotides.
		 * The player must drag the correct one to the polymerace
		 * Stage 4: Animation of the whole process happening.
		 */
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		final Canvas canvas = new Canvas(1000, 500);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Strand dna = Strand.getRandomStrand(10,"dna");
		dna.setPos(100, 300);
		System.out.println(dna);
		Helix helix = new Helix(dna);
		helix.setPos(0, 400);
		stage.setScene(scene);
		stage.show();      
	    new AnimationTimer()
	    {
	    	int x=0;
	        public void handle(long currentNanoTime)
	        {
	            // Clear the canvas
	            gc.setFill( new Color(1,1,1, 1.0) );
	            gc.fillRect(0,0, 1010,512);
	            helix.setPos(x,200);
	            helix.draw(gc);
	            x+=1;
	           
	        }
	    }.start();
		
	}
}
