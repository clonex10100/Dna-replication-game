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
import proteins.Helicase;
import proteins.Machine;

public class HelicaseTesting extends Application{
	boolean drag = false;
	boolean f = false;
	int dsx;
	int dsy;
	int my;
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
		int xs = 50;
		int ys = 200;
		Helix original = new Helix(Strand.getRandomStrand(length, "dna"),true);
		original.setPos(xs, ys);
		Machine circle = new Machine(original,root);
		
		stage.setScene(scene);
		stage.show();
//		root.setOnMousePressed(new EventHandler<MouseEvent>() {
//			@Override
//			
//			public void handle(MouseEvent mouseEvent) {
//				  int[] pos = circle.getPos();
//				  System.out.println("HUESTHUETS");
//				  	if(pos[0] - Machine.SIZE <  mouseEvent.getSceneX() && mouseEvent.getSceneX() < pos[0] + Helicase.SIZE && 
//				  			pos[1] - Helicase.SIZE < mouseEvent.getSceneY() && mouseEvent.getSceneY()< pos[1] + Helicase.SIZE
//				  			&& !drag) 
//				  	{
//				  			System.out.println("fuck");
//				  			drag = true;
//				  			dsx = pos[0];
//				  			dsy = pos[1];
//				  	}
//			}
//		});
//		root.setOnMouseMoved(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				System.out.println("ueo");
//				  	if(drag) {
//				  		
//				  		System.out.println("uea");
//				  		my = (int)(mouseEvent.getScreenY());
//				  	}
//				  	
//			}
//		});
//		root.setOnMouseReleased(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				  	drag = false;
//				  	
//			}
//		});
	    new AnimationTimer()
	    {
        	long lastNano = 0;
	        public void handle(long currentNanoTime)
	        {	
	    		if((currentNanoTime - lastNano) > 60000) {
	    			gc.setFill(new Color(1,1,1, 1.0) );
	    			gc.fillRect(0,0, 1010,512);
	    			circle.draw(gc);
	    			gc.strokeLine(500, 300, 600, 300);
	    			lastNano = currentNanoTime;
	    		}
	        }
	    }.start();
	}
}