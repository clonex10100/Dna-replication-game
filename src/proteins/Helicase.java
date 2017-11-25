package proteins;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nucleotides.*;

public class Helicase {
	private int x;
	private int y;
	private int xs;
	private int ys;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int xc;
	private int index = 0;
	private int length;
	private static int SIZE = 100;
	Helix helix;
	Helix upperHelix;
	Helix lowerHelix;
	public Helicase(Helix helix){
		int[] pos = helix.getPos();
		length = helix.getLength();
		x = pos[0]-Nucleotide.getImageSize();
		y = pos[1];
		xs = x;
		xc = x;
		ys = y+Nucleotide.getImageSize();
    	x2 =(int)(xs-Nucleotide.getImageSize()*(length-1)*Math.cos(Math.toRadians(30)));
	   	y2 = ys - (int)(Nucleotide.getImageSize()*(length+1)*Math.sin(Math.toRadians(30)))-25; 
		this.helix = helix;
		upperHelix = new Helix(new Strand(length),false);
		lowerHelix = new Helix(new Strand(length),false);
	}
	/**
	 * Unzips the helix that Helicase is bound to by one nucleotide
	 */
	public void unzip(){
		upperHelix.addNucleotideToStart(2,helix.getNucleotide(2,length-1-index));
		helix.removeNucleotide(2,length-index-1);
	    lowerHelix.addNucleotideToEnd(1,helix.getNucleotide(1,index));
	    helix.removeNucleotide(1,index);
		int h = Nucleotide.getImageSize()*(index+1);
		x1=(int)xs+h-(int)(h*Math.cos(Math.toRadians(-30)));
		y1=(int)ys+h+(int)(h*Math.sin(Math.toRadians(-30)))-Nucleotide.getImageSize();
		x2 += Nucleotide.getImageSize();
		upperHelix.setPos(x2, y2,30);
		lowerHelix.setPos(x1, y1,-30);
		xc+= Nucleotide.getImageSize();
		index++;
	}
	public void draw(GraphicsContext gc) {
		gc.setStroke(new Color(0,0,0,1.0));
		gc.setFill(new Color (1,0.269,0,1.0));
		helix.draw(gc);
		upperHelix.draw(gc);
		lowerHelix.draw(gc);
		gc.strokeOval(xc,ys-Nucleotide.getImageSize()+10, SIZE,SIZE);
		gc.fillOval(xc,ys-Nucleotide.getImageSize()+10,SIZE,SIZE);

	}
}
