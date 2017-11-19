package display;
import nucleotide.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;


public class DnaSimStage1 extends Application {
	public static void main(String[] args){
		launch(args);	
	}
	@Override
	public void start(Stage stage) {
		DnaNucleotide x = new DnaNucleotide('A');
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		final Canvas canvas = new Canvas(1000, 500);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image A = x.getImage();
		
		gc.drawImage(A,100,100);
		root.getChildren().add(canvas);
		stage.setScene(scene);
		stage.show();
	}
}
