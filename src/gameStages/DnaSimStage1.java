package gameStages;
import gameClasses.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;


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
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Strand dna = Strand.getRandomStrand(5,"dna");
		//Strand dna2 = dna.getComplementaryDnaStrand();
		DnaNucleotide dna3 = new DnaNucleotide('C');
		dna.toggleBond(3);
		dna.draw(gc,517,210,180);
		dna.draw(gc,100,300);	
		//dna2.draw(gc, 400, 100,180);
		//dna3.draw(gc, 100, 200,0);
		root.getChildren().add(canvas);
		stage.setScene(scene);
		stage.show();
	}
}
