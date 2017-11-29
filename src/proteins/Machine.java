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
	private int unzipRange;
	private PrimeZoneManager man;
	private Helix helix;
	private Helix upperHelix;
	private Helix lowerHelix;
	private Helicase helicase;
	private Group root;
	public Machine(Helix helix,int unzipRange,Group root){
		int[] pos = helix.getPos();
		this.root = root;
		LENGTH = helix.getLength();
		this.unzipRange = unzipRange;
		xs = pos[0]-Nucleotide.getImageSize();
		ys= pos[1]+Nucleotide.getImageSize();
		cx = 50;
		cy = 200;
		x2 =(int)(xs-Nucleotide.getImageSize()*(LENGTH-1)*Math.cos(Math.toRadians(30)));
		y2 = ys-(int)(Nucleotide.getImageSize()*(LENGTH+1)*Math.sin(Math.toRadians(30)))-25;
		this.helix = helix;
		upperHelix = new Helix(new Strand(LENGTH),false);
		lowerHelix = new Helix(new Strand(LENGTH),false);
		helicase = new Helicase(xs, ys,unzipRange,this,root);
		man = new PrimeZoneManager();
	}
	/**
	 * Unzips the helix that Helicase is bound to by one nucleotide
	 */
	public void unzip(){
		//Swap the nucleotides
		upperHelix.addNucleotideToStart(1,helix.getNucleotide(1,LENGTH-1-index));
		upperHelix.shift(0);
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
		PrimeZone zone = new PrimeZone(new int[]{(int)(x),(int)(y)},new int[]{helix,strand,pos},upperHelix,lowerHelix);

	 	if(type == 0){
			new Primase(cx,cy, man, root);
			cy+=100;
		}
		else if(type==6){
			char n= zone.getComplmentaryDnaNucleotide().getBase();
			System.out.println(n);
			switch(n) {
			case 'A': type = 1;break;
			case 'T': type = 2;break;
			case 'G': type = 3;break;
			case 'C': type = 4;break;
			}
			new DragableNucleotide(cx,cy,type,man,root);
			cy+=100;
		}
	 	man.addZone(type,zone);
	}
	public PrimeZone[] getPrimeZone(int type){
		return man.getZones(type);
	}
	public boolean isUnzipped() {
		return helicase.isUnzipped();
	}
	public void setUnzipRange(int newUnzip) {
		unzipRange = newUnzip;
		helicase.setUnzipLength(newUnzip);
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
