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
	private static int XS;
	private static int YS;
	//Xy of lower, rightside up helix
	private int lowerX;
	private int lowerY;
	//Xy of upper upsidedown helx
	private int upperX;
	private int upperY;
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
		double y = YS + len*Math.sin(Math.toRadians(helix == 0 ? -30:30));
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
		XS = pos[0]-Nucleotide.getImageSize();
		YS= pos[1]+Nucleotide.getImageSize();
		upperX =(int)(XS-Nucleotide.getImageSize()*(LENGTH-1)*Math.cos(Math.toRadians(30)));
		upperY = (int)(YS-Nucleotide.getImageSize()*(LENGTH+1)*Math.sin(Math.toRadians(30))-25);
		originalHelix = helix;
		upperHelix = new Helix(new Strand(LENGTH),false);
		lowerHelix = new Helix(new Strand(LENGTH),false);
		helicase = new Helicase(XS, YS,unzipRange,this,root);
		manager = new PrimeZoneManager();
	}
	/**
	 * Returns array of PrimeZones of given type
	 * @param  int type- Type of primezone
	 * @return  Array of primezones
	 */
	public PrimeZone[] getPrimeZone(int type){
		return manager.getZones(type);
	}
	/**
	 * Returns weather or not the machine is unzipped to current limit
	 * @return boolean
	 */
	public boolean isUnzipped() {
		return helicase.isUnzipped();
	}
	/**
	 * Sets how far the machine is allowed to unzip
	 * @param int- Limit of how many nucleotides the machine can unzip
	 */
	public void setUnzipRange(int newUnzip) {
		unzipRange = newUnzip;
		helicase.setUnzipLength(newUnzip);
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
			lowerX=(int)XS+h-(int)(h*Math.cos(Math.toRadians(-30)));
			lowerY=(int)YS+h+(int)(h*Math.sin(Math.toRadians(-30)))-Nucleotide.getImageSize();
			upperX += Nucleotide.getImageSize();
			upperHelix.setPos(upperX, upperY,30);
			lowerHelix.setPos(lowerX, lowerY,-30);
			unzipIndex++;
		}
	}
	/**
	*Strand is one or two
	*Pos is how many nucleotides away from Helicase
	*/
	public void addZone(int type,int helix,int strand, int pos, int spawnPosX, int spawnPosY){
		PrimeZone zone = generateZone(helix,strand,pos,false);
		new PrimeZoneActivater(spawnPosX,spawnPosY,type,manager,root);
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
		new PrimeZoneActivater(spawnPosX,spawnPosY,type,manager,root);
		manager.addZone(type,zone);
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
	public void hide(){
		helicase.hide();
	}
}
