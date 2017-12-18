package nucleotides;
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
import nucleotides.Nucleotide;
import nucleotides.Strand;
import java.util.ArrayList;
import nucleotides.Helix;
public class Test extends Application{
	public static void main(String[] args){
		launch(args);
	}
	@Override
	public void start(Stage stage){
		Group root = new Group();
		Scene scene = new Scene(root);
		final Canvas canvas = new Canvas(1000, 700);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Strand test = Strand.getRandomStrand(10,1,"dna");
		Strand testc = Strand.getRandomStrand(10,0,"dna");
		test.setPos(100,200);
		testc.setPos(100,400);
		//Helix helix = new Helix(test,false);
		//testc.setPos(100,100);
		//test.draw(gc);
		//testc.draw(gc);
		stage.setScene(scene);
		stage.show();
		new AnimationTimer(){
		long lastNano = 0;
		int i = 1;
		int t = 0;
		Helix helix;
		Helix helixc;
		public void handle(long currentNanoTime){

			if((currentNanoTime - lastNano)/1000000000> 1){
				gc.setFill(new Color(1,1,1, 1.0) );
				gc.fillRect(0,0, 1200,700);
				if(i==4){
					 helix = new Helix(test,false);
					 helixc = new Helix(testc,false);
				}
				if(i>5){
					helix.draw(gc);
					helixc.draw(gc);
				}
				if(i>10){
					helix.setRotation(30);
					helixc.setRotation(30);
				}
				else{
					test.draw(gc);
					testc.draw(gc);
				}

				gc.strokeLine(100,200,200,200);
				gc.strokeLine(100,400,200,400);
		//		if(i==2){	
		//		helix.setNucleotide(0,3,new RnaNucleotide('U'));
		//		}
		//		if(i==5){
		//			helix.setNucleotide(1,3,new RnaNucleotide('U'));
			//	}
			//	if(i==8){
			//		test.addNucleotideToEnd(new DnaNucleotide('A'));
			//		test.toggleBond(3);
			//	}
			//	if(i==10){
			//		test.setRotation(30);
				//}
				t =(t+1)%2;
			//	test.setType(t);
			//	test.draw(gc);
				//testc.draw(gc);
				lastNano = currentNanoTime;
				i++;
			}
		}
		}.start();	
	}
}
