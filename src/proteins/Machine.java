package proteins;


import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nucleotides.*;
/**
 * Class to hold and manage the protiens and helixes that make up the main part of the game
 * @author clonex10100
 *
 */
public class Machine {
	//Original xy of center of first helix
	private int xs;
	private int ys;
	//Xy of lower, rightside up helix
	private int x1;
	private int y1;
	//Xy of upper upsidedown helx
	private int x2;
	private int y2;
	private int index = 0;
	private static int LENGTH;
	Helix helix;
	Helix upperHelix;
	Helix lowerHelix;
	Helicase helicase;
	public Machine(Helix helix, Group root){
		int[] pos = helix.getPos();
		LENGTH = helix.getLength();
		xs = pos[0]-Nucleotide.getImageSize();
		ys= pos[1]+Nucleotide.getImageSize();
    	x2 =(int)(xs-Nucleotide.getImageSize()*(LENGTH-1)*Math.cos(Math.toRadians(30)));
	   	y2 = ys - (int)(Nucleotide.getImageSize()*(LENGTH+1)*Math.sin(Math.toRadians(30)))-25; 
		this.helix = helix;
		upperHelix = new Helix(new Strand(LENGTH),false);
		lowerHelix = new Helix(new Strand(LENGTH),false);
		helicase = new Helicase(xs, ys,LENGTH,this,root);
	}
	/**
	 * Unzips the helix that Helicase is bound to by one nucleotide
	 */
	public void unzip(){
		upperHelix.addNucleotideToStart(2,helix.getNucleotide(2,LENGTH-1-index));
		helix.removeNucleotide(2,LENGTH-index-1);
	    lowerHelix.addNucleotideToEnd(1,helix.getNucleotide(1,index));
	    helix.removeNucleotide(1,index);
		int h = Nucleotide.getImageSize()*(index+1);
		x1=(int)xs+h-(int)(h*Math.cos(Math.toRadians(-30)));
		y1=(int)ys+h+(int)(h*Math.sin(Math.toRadians(-30)))-Nucleotide.getImageSize();
		x2 += Nucleotide.getImageSize();
		upperHelix.setPos(x2, y2,30);
		lowerHelix.setPos(x1, y1,-30);
		index++;
	}
	public void draw(GraphicsContext gc) {
		//gc.setStroke(new Color(0,0,0,1.0));
		//gc.setFill(new Color (1,0.269,0,1.0));
		helix.draw(gc);
		upperHelix.draw(gc);
		lowerHelix.draw(gc);
		//gc.strokeOval(xc,yc, SIZE,SIZE);
		//gc.fillOval(xc,yc,SIZE,SIZE);

	}
}
