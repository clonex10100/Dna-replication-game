package gameStages;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import nucleotides.DnaNucleotide;
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
		int a = 10;
		int xs = 100;
		int ys = 300;
		Strand dna = Strand.getRandomStrand(a, "dna");
		Helix original = new Helix(dna,true);
		original.setPos(xs, ys-Nucleotide.getImageSize());
		Strand strand = new Strand(a);
		Helix newUp = new Helix(strand,false);
		Strand strand2 = new Strand(a);
		Helix newDown = new Helix(strand2,false);
		stage.setScene(scene);
		stage.show();
	    new AnimationTimer()
	    {
	    	int i=0;
	    	long lastNano = 0;
	    	int x2 = xs-Nucleotide.getImageSize()*(a-1)+25;
	    	int x;
		    int y;
		   	int y2 = ys - (int)(Nucleotide.getImageSize()*(a+1)*Math.sin(Math.toRadians(30)))-25; 
	        public void handle(long currentNanoTime)
	        {	
	    		if((currentNanoTime - lastNano) > 900000000) {
		            if(i<a) {       	
			        	newUp.addNucleotideToEnd(1,original.getNucleotide(1,i));
			        	original.removeNucleotide(1,i);
			        	newDown.addNucleotideToStart(2,original.getNucleotide(2,a-1-i));
			        	original.removeNucleotide(2,a-i-1);
			        	x2 += Nucleotide.getImageSize();
			        	//I wrote this at 4am and have no idea what it does but it works
			        	int h = Nucleotide.getImageSize()*(i+1);
			    		x=(int)xs+h-(int)(h*Math.cos(Math.toRadians(-30)))-Nucleotide.getImageSize();
			    		y=(int)ys+h + (int)(h*Math.sin(Math.toRadians(-30)))-Nucleotide.getImageSize();		            	
			    		i++;
		            }
		            else {	
		            	newDown.setNucleotide(1,1,new DnaNucleotide('A'));
		            	newUp.setNucleotide(2, 1, new DnaNucleotide('G'));
		            }
	    			newUp.setPos(x, y,-30);
	    			newDown.setPos(x2, y2,30);
	    			gc.strokeLine(x, y2, x+100, y2);
	    			gc.setFill(new Color(1,1,1, 1.0) );
	    			gc.fillRect(0,0, 1010,512);
	    			original.draw(gc);
	    			newUp.draw(gc);
	    			newDown.draw(gc);	    
	    			lastNano = currentNanoTime;
	    		}
	        }
	    }.start();
	}
}