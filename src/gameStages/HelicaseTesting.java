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
import proteins.*;
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
		int unzipRange = 5;
		int xs = 50;
		int ys = 200;
		Helix originalHelix = new Helix(Strand.getRandomStrand(length, "dna"),true);
		originalHelix.setPos(xs, ys);
		Machine machine = new Machine(originalHelix,unzipRange,root);
		stage.setScene(scene);
		stage.show();
	    new AnimationTimer(){
			int stage = 0;
			long lastNano = 0;
			int upperIndex = unzipRange;
			int lowerIndex = 2;
	        public void handle(long currentNanoTime){
	        	if(currentNanoTime - lastNano > 900000) {
    				gc.setFill(new Color(1,1,1, 1.0) );
    				gc.fillRect(0,0, 1010,512);
	        		if(stage == 0) {
	        			if(machine.isUnzipped()) {
	        				stage+=1;
	        			}
	        		}
	        		if(stage == 1) {
	        			machine.addZone(0,0,0,upperIndex);
	        			machine.addZone(0,1,1,lowerIndex);
	        			upperIndex--;
	        			lowerIndex++;
	        			stage++;
	        		}
	        		if(stage==2) {
	        			if(machine.getPrimeZone(0).length==0) {
	        				stage++;
	        			}
	        		}
	        		if(stage==3) {
	        			boolean empty = false;
	        			for(int i =1; i<=4;i++) {
	        				if(machine.getPrimeZone(i).length==0) {
	        					empty=true;
	        				}
	        				else {
	        					empty=false;
	        					break;
	        				}
	        			}
	        			if(empty) {
									if(upperIndex==1){
										stage++;
									}
									else{
		        				machine.addZone(6,0,0,upperIndex);
		        				machine.addZone(6,1,1,lowerIndex);
		        				upperIndex--;
		        				lowerIndex++;
									}
	        			}
	        		}
	        		if(stage==4) {
	        			machine.setUnzipRange(10);
								upperIndex = unzipRange+1;
	        			stage = 0;
	        		}
	        		machine.draw(gc);
	        		lastNano = currentNanoTime;
	        	}
	        }
	    }.start();
	}
}
