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
import java.util.ArrayList;
public class MainGameLoop extends Application{
	public static void main(String[] args){
		launch(args);
	}
	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root);
		final Canvas canvas = new Canvas(1000, 700);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		int length = 10;
		int unzipRange = 5;
		int xs = 200;
		int ys = 350;
		Helix originalHelix = new Helix(Strand.getRandomStrand(10,0,"dna"),true);
		originalHelix.setPos(xs, ys);
		Machine machine = new Machine(originalHelix,unzipRange,root);
		stage.setScene(scene);
		stage.show();
		new AnimationTimer(){
		int stage = 0;
		int tUnzipRange = unzipRange;
		long lastNano = 0; int upperIndex = unzipRange-1; int lowerIndex = 0; boolean done = false;
		ArrayList<Integer> PolimeraceUpperZones = new ArrayList<Integer>();
		ArrayList<Integer> PolimeraceLowerZones = new ArrayList<Integer>();
		ArrayList<Integer> LigaseLowerZones = new ArrayList<Integer>();
		int unzipped = 0;
		public void handle(long currentNanoTime){
			if(currentNanoTime - lastNano > 900000) {
				gc.setFill(new Color(1,1,1, 1.0) );
				gc.fillRect(0,0, 1200,700);
				if(stage == 0 || stage == 5) {
					if(machine.isUnzipped()) {
						stage+=1;
					}
				}
				else if(stage == 1) {
					//Add two polimerace zones.
					machine.addZone(0,0,1,upperIndex,50, 300);
					machine.addZone(0,1,0,lowerIndex,50,400);
					PolimeraceUpperZones.add(upperIndex);
					PolimeraceLowerZones.add(lowerIndex);
					LigaseLowerZones.add(lowerIndex+5);
					upperIndex--;
					lowerIndex++;
					stage++;
				}
				else if(stage==2) {
					if(machine.getPrimeZone(0).length==0) {
						stage++;
					}
				}
				else if(stage==3) {
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
						if(lowerIndex ==  tUnzipRange){
							if(done){
								stage = 7;
							}
							else{
								stage++;
							}
						}
						else{
							System.out.println(".");
							machine.addComplementaryNucleotideZone(0,1,upperIndex,50,300);
							machine.addComplementaryNucleotideZone(1,0,lowerIndex,50,400);
							upperIndex--;
							lowerIndex++;
						}
					}
				}
				else if(stage==4) {
					machine.setUnzipRange(10);
					unzipped+=10;
					upperIndex = 9;
					tUnzipRange += 5;
					stage = 5;
				}
				else if(stage ==6){

					machine.addZone(0,1,0,lowerIndex,50,300);
					PolimeraceLowerZones.add(lowerIndex);
					machine.addComplementaryNucleotideZone(0,1,upperIndex,50,400);
					upperIndex--;
					lowerIndex++;
					stage=2;
					done = true;
				}
				else if(stage == 7){
					for(int i = 0; i < PolimeraceUpperZones.size();i++){
						machine.addZone(5,0,1,PolimeraceUpperZones.get(i),50, 300);
					}

					for(int i = 0; i < PolimeraceLowerZones.size();i++){
						machine.addZone(5,1,0,PolimeraceLowerZones.get(i),50, 350 + 50 * i);
					}
					stage++;
				}
				else if(stage == 8){
					if(machine.getPrimeZone(5).length == 0){
						stage++;
					}
				}
				else if(stage == 9){
					for(int i = 0; i < LigaseLowerZones.size();i++){
						machine.addZone(6,1,0,LigaseLowerZones.get(i),50, 400);
					}
					stage++;
				}
				machine.draw(gc);
				lastNano = currentNanoTime;
				}
			}
		}.start();
	}
}
