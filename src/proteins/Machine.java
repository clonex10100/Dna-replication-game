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
	private static int LENGTH;
	private int unzipIndex = 0;
	private int unzipRange;
	private PrimeZoneManager manager;
	private Helix originalHelix;
	private Helix upperHelix;
	private Helix lowerHelix;
	private Helicase helicase;
	private Group root;
	private PrimeZone generateZone(int helix,int strand,int pos,boolean flag){
		double len = Nucleotide.getImageSize()*(pos-1);
		double x = (helicase.getPos()-50)-len*Math.cos(Math.toRadians(helix == 0 ? -30:30));
		double y = ys + len*Math.sin(Math.toRadians(helix == 0 ? -30:30));
		if(strand == 0&&flag){
			y+=Nucleotide.getImageSize();
		}
		return new PrimeZone(new int[]{(int)(x),(int)(y)},new int[]{helix,strand,pos},upperHelix,lowerHelix);
	}
	public Machine(Helix helix,int unzipRange,Group root){
		LENGTH = helix.getLength();
		this.unzipRange = unzipRange;
		this.root = root;
		int[] pos = helix.getPos();
		xs = pos[0]-Nucleotide.getImageSize();
		ys= pos[1]+Nucleotide.getImageSize();
		x2 =(int)(xs-Nucleotide.getImageSize()*(LENGTH-1)*Math.cos(Math.toRadians(30)));
		y2 = (int)(ys-Nucleotide.getImageSize()*(LENGTH+1)*Math.sin(Math.toRadians(30))-25);
		originalHelix = helix;
		upperHelix = new Helix(new Strand(LENGTH),false);
		lowerHelix = new Helix(new Strand(LENGTH),false);
		helicase = new Helicase(xs, ys,unzipRange,this,root);
		manager = new PrimeZoneManager();
	}
	/**
	 * Unzips the helix that Helicase is bound to by one nucleotide
	 */
	public void unzip(){
		if(unzipIndex < LENGTH){
			upperHelix.addNucleotideToStart(1,originalHelix.getNucleotide(1,LENGTH-1-unzipIndex));
			originalHelix.removeNucleotide(1,LENGTH-unzipIndex-1);
			//Quick and dirty fix for upperHelix nucleotide misalignment
			upperHelix.shift(0);
			lowerHelix.addNucleotideToEnd(0,originalHelix.getNucleotide(0,unzipIndex));
			originalHelix.removeNucleotide(0,unzipIndex);
	  	//Edit the positions accounting for added nucleotides
			int h = Nucleotide.getImageSize()*(unzipIndex+1);
			x1=(int)xs+h-(int)(h*Math.cos(Math.toRadians(-30)));
			y1=(int)ys+h+(int)(h*Math.sin(Math.toRadians(-30)))-Nucleotide.getImageSize();
			x2 += Nucleotide.getImageSize();
			upperHelix.setPos(x2, y2,30);
			lowerHelix.setPos(x1, y1,-30);
			unzipIndex++;
		}
	}
	/**
	*Strand is one or two
	*Pos is how many nucleotides away from Helicase
	*/
	public void addZone(int type,int helix,int strand, int pos, int spawnPosX, int spawnPosY){
		PrimeZone zone = generateZone(helix,strand,pos,false);
		switch(type){
			case 0: new Primase(spawnPosX,spawnPosY,manager,root);;break;
			case 1: new DragableNucleotide(spawnPosX,spawnPosY,1,manager,root);break;
			case 2: new DragableNucleotide(spawnPosX,spawnPosY,2,manager,root);break;
			case 3: new DragableNucleotide(spawnPosX,spawnPosY,3,manager,root);break;
			case 4: new DragableNucleotide(spawnPosX,spawnPosY,4,manager,root);break;
		}
		manager.addZone(type,zone);
	}
	/**
	*Creates a draggable nucleotide
	*/
	public void addComplementaryNucleotideZone(int helix,int strand, int pos, int spawnPosX, int spawnPosY){
		PrimeZone zone = generateZone(helix,strand,pos,true);
		int type = 0;
		char b = zone.getComplmentaryDnaNucleotide().getBase();
		switch(b) {
			case 'A': type = 1;break;
			case 'T': type = 2;break;
			case 'G': type = 3;break;
			case 'C': type = 4;break;
		}
		new DragableNucleotide(spawnPosX,spawnPosY,type,manager,root);
		manager.addZone(type,zone);
	}
	public PrimeZone[] getPrimeZone(int type){
		return manager.getZones(type);
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
		originalHelix.draw(gc);
		upperHelix.draw(gc);
		lowerHelix.draw(gc);
		manager.draw(gc);
	}
}
