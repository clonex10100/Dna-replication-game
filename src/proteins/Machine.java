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
	//Xy of draggable protien creation
	private int cx;
	private int cy;
	private int index = 0;
	private static int LENGTH;
	private int unzipLength = 6;
	private PrimeZoneManager man;
	private Helix helix;
	private Helix upperHelix;
	private Helix lowerHelix;
	private Helicase helicase;
	private Group root;
	public Machine(Helix helix, Group root){
		int[] pos = helix.getPos();
		this.root = root;
		LENGTH = helix.getLength();
		xs = pos[0]-Nucleotide.getImageSize();
		ys= pos[1]+Nucleotide.getImageSize();
		cx = 50;
		cy = 200;
		x2 =(int)(xs-Nucleotide.getImageSize()*(LENGTH-1)*Math.cos(Math.toRadians(30)));
		y2 = ys-(int)(Nucleotide.getImageSize()*(LENGTH+1)*Math.sin(Math.toRadians(30)))-25;
		this.helix = helix;
		upperHelix = new Helix(new Strand(LENGTH),false);
		lowerHelix = new Helix(new Strand(LENGTH),false);
		helicase = new Helicase(xs, ys,unzipLength,this,root);
		man = new PrimeZoneManager();
	}
	/**
	 * Unzips the helix that Helicase is bound to by one nucleotide
	 */
	public void unzip(){
		//Swap the nucleotides
		upperHelix.addNucleotideToStart(1,helix.getNucleotide(1,LENGTH-1-index));
		helix.removeNucleotide(1,LENGTH-index-1);
		lowerHelix.addNucleotideToEnd(0,helix.getNucleotide(0,index));
		helix.removeNucleotide(0,index);
	   	//Edit the positions accounting for swapped nucleotides
		int h = Nucleotide.getImageSize()*(index+1);
		x1=(int)xs+h-(int)(h*Math.cos(Math.toRadians(-30)));
		y1=(int)ys+h+(int)(h*Math.sin(Math.toRadians(-30)))-Nucleotide.getImageSize();
		x2 += Nucleotide.getImageSize();
		upperHelix.setPos(x2, y2,30);
		lowerHelix.setPos(x1, y1,-30);
		index++;
	}
	/**
	*Strand is one or two
	*Pos is how many nucleotides away from Helicase
	*/
	public void addZone(int type,int helix,int strand, int pos){
		double x = helicase.getPos()-Nucleotide.getImageSize()*(pos-1)*Math.cos(Math.toRadians(helix == 0 ? -30:30))-25;
		double y = ys + Nucleotide.getImageSize()*(pos-1)*Math.sin(Math.toRadians(helix == 0 ? -30:30));
		man.addZone(type, new PrimeZone(new int[]{(int)(x),(int)(y)},new int[]{helix,strand,pos},upperHelix,lowerHelix));
		switch(type) {
		case 0: new Primase(cx,cy, man, root); cy+=100; break;
		}
	}
	public boolean isUnzipped() {
		return helicase.isUnzipped();
	}
	/**
	 * Draws all the helixes the machine controls.
	 * @param gc
	 */
	public void draw(GraphicsContext gc) {
		helix.draw(gc);
		upperHelix.draw(gc);
		lowerHelix.draw(gc);
		man.draw(gc);
	}
}
