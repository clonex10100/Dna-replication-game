package gameStages;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import nucleotides.Nucleotide;
import nucleotides.Strand;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.scene.Scene;


public class UnzipTesting extends Application {
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
		Strand dna = Strand.getRandomStrand(3,"dna");
		Strand dna2 = dna.getComplementaryDnaStrand();
		//Dna upsidown, dna2 rightside 
		int a = 3;
		int xs = 10;
		int ys = 400;
		dna.setPos((int)(xs+70*(a)+13), ys,180);
		dna2.setPos(xs, ys);
		Strand strand = new Strand(a);
		Strand strand2 = new Strand(a);
		//int x=(20+10*Nucleotide.getImageSize()) + (int)(h*Math.cos(Math.toRadians(-30)))-30;
		//int y=-300+h - (int)(h*Math.sin(Math.toRadians(-30)))+60;
		stage.setScene(scene);
		stage.show();
		
	    new AnimationTimer()
	    {
	    	int i=0;
	    	long lastNano = 0;
	    	int x2 = xs;
	    	int x;
	    	int y;
	        public void handle(long currentNanoTime)
	        {	

	    		if((currentNanoTime - lastNano) > 900000000) {
		            if(i<a) {
			        	strand.addNucleotideToStart(dna.getNucleotide(a-1-i));
			        	dna.removeNucleotide(a-1-i);
			        	strand2.addNucleotideToEnd(dna2.getNucleotide(i));
			        	dna2.removeNucleotide(i);
			        	x2 += 70;
			        	//I wrote this at 4am and have no idea what it does but it works
			        	int h = Nucleotide.getImageSize()*(i+1);
			    		x=(int)xs+h - (int)(h*Math.cos(Math.toRadians(-30)));
			    		y=(int)ys+h + (int)(h*Math.sin(Math.toRadians(-30)));
		            	i++;
		            }
	    			strand.setPos(x2, ys,210);
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
