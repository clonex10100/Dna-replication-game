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
		Strand dna = Strand.getRandomStrand(7,"dna");
		Strand dna2 = dna.getComplementaryDnaStrand();
		dna.setPos(500, 100,180);
		dna2.setPos(100, 300);
		Strand strand = new Strand(10);
		Strand strand2 = new Strand(10);
		//int x=(20+10*Nucleotide.getImageSize()) + (int)(h*Math.cos(Math.toRadians(-30)))-30;
		//int y=-300+h - (int)(h*Math.sin(Math.toRadians(-30)))+60;
		stage.setScene(scene);
		stage.show();
		
	    new AnimationTimer()
	    {
	    	int i=0;
	    	long lastNano = 0;
	    	int x2 = 0;
	    	int x = 0;
	    	int y = 0;
	        public void handle(long currentNanoTime)
	        {	

	    		if(currentNanoTime - lastNano > 900000000) {
		            if(i<7) {
			        	strand.addNucleotide(i,dna.getNucleotide(6-i));
			        	dna.removeNucleotide(6-i);
			        	strand2.addNucleotideToEnd(dna2.getNucleotide(i));
			        	dna2.removeNucleotide(i);
			        	x2 += Nucleotide.getImageSize();
			        	//I wrote this at 4am and have no idea what it does but it works
			        	int h = Nucleotide.getImageSize()*(strand2.getLength());
			    		x=100+h - (int)(h*Math.cos(Math.toRadians(-30)));
			    		y=260+h + (int)(h*Math.sin(Math.toRadians(-30)));
		            	i++;
		            }
	    			strand.setPos(x2, 100,210);
	    			strand2.setPos(x, y,-30);
	    			gc.setFill(new Color(1,1,1, 1.0) );
	    			gc.fillRect(0,0, 1010,512);
	    			dna.draw(gc);
	    			dna2.draw(gc);
	    			strand.draw(gc);
	    			strand2.draw(gc);	    
	    			//dna.draw(gc);
	    			lastNano = currentNanoTime;
	    		}
	        }
	    }.start();
		
	}
}
