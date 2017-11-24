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


public class UnzipTestWithHelixes extends Application {
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
		int xs = 100;
		int ys = 400;
		Strand dna = Strand.getRandomStrand(a, "dna");
		Helix original = new Helix(dna,true);
		original.setPos(xs, ys-Nucleotide.getImageSize());
		Strand strand = new Strand(a);
		Strand strand2 = new Strand(a);
		stage.setScene(scene);
		stage.show();
		
	    new AnimationTimer()
	    {
	    	int i=0;
	    	long lastNano = 0;
	    	int x2 = xs-25;
	    	int x;
	    	int y;
	        public void handle(long currentNanoTime)
	        {	

	    		if((currentNanoTime - lastNano) > 900000000) {
		            if(i<a) {
			        	
			        	strand.addNucleotideToEnd(original.getNucleotide(1,i));
			        	original.removeNucleotide(1,i);
			        	strand2.addNucleotideToStart(original.getNucleotide(2,a-1-i));
			        	original.removeNucleotide(2,a-1-i);
			        	x2 += 70;
			        	//I wrote this at 4am and have no idea what it does but it works
			        	int h = Nucleotide.getImageSize()*(i+1);
			    		x=(int)xs+h-(int)(h*Math.cos(Math.toRadians(-30)))-25;
			    		y=(int)ys+h + (int)(h*Math.sin(Math.toRadians(-30)));
		            	i++;
		            }
	    			strand.setPos(x2, ys,210);
	    			strand2.setPos(x, y,-30);
	    			gc.setFill(new Color(1,1,1, 1.0) );
	    			gc.fillRect(0,0, 1010,512);
	    			original.draw(gc);
	    			strand.draw(gc);
	    			strand2.draw(gc);	    
	    			//dna.draw(gc);
	    			lastNano = currentNanoTime;
	    		}
	        }
	    }.start();
		
	}
}