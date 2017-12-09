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
	private int unzipIndex = 0;
	private int unzipRange;
	private PrimeZoneManager manager;
	private Helix originalHelix;
	private Helix upperHelix;
	private Helix lowerHelix;
	private Helicase helicase;
	private Group root;
	private PrimeZone generateZone(int helix,int strand,int pos,boolean flag){
		//double len = Nucleotide.getImageSize()*(pos-1);
		//double x = (helicase.getPos()-50)-len*Math.cos(Math.toRadians(helix == 0 ? -30:30));
		//int h = Nucleotide.getImageSize()*
		//double y = YS + len*Math.sin(Math.toRadians(helix == 0 ? -30:30));
		//if(strand == 0&&flag){
		//	y+=Nucleotide.getImageSize();
		//}
		int h = Nucleotide.getImageSize()*unzipIndex;
		int x = (int)(XS+h+Nucleotide.getImageSize()-Math.cos(Math.toRadians(30))*(Nucleotide.getImageSize()*(unzipIndex-pos)));
		int ly = (int)(YS-25+Math.sin(Math.toRadians(30))*(Nucleotide.getImageSize()*(unzipIndex-pos)));
		//int ux = lx;
		int uy =  (int)(YS+25-Math.sin(Math.toRadians(30))*(Nucleotide.getImageSize()*(unzipIndex-pos)));
			
		return new PrimeZone(new int[]{x,helix == 0 ? uy:ly},new int[]{helix,strand,pos},upperHelix,lowerHelix);
	}
	public Machine(Helix helix,int unzipRange,Group root){
		this.unzipRange = unzipRange;
		this.root = root;
		int[] pos = helix.getPos();
		XS = pos[0]-Nucleotide.getImageSize();
		YS= pos[1]+Nucleotide.getImageSize();
		originalHelix = helix;
		upperHelix = new Helix(new Strand(0),false);
		lowerHelix = new Helix(new Strand(0),false);
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
		if(unzipIndex < unzipRange){
			upperHelix.addNucleotideToEnd(0,originalHelix.getNucleotide(0,unzipIndex));
			originalHelix.removeNucleotide(0,unzipIndex);
			lowerHelix.addNucleotideToEnd(1,originalHelix.getNucleotide(1,unzipIndex));
			originalHelix.removeNucleotide(1,unzipIndex);
			upperX=(int)(XS-Nucleotide.getImageSize()*(upperHelix.getLength()-1)*Math.cos(Math.toRadians(30)));
			upperY = (int)(YS-Nucleotide.getImageSize()*(upperHelix.getLength()+1)*Math.sin(Math.toRadians(30))-25);
		
			//int h = Nucleotide.getImageSize()*(unzipIndex+1);
			//int lx =XS+h-(int)(h*Math.cos(Math.toRadians(-30)))-(int)(Nucleotide.getImageSize()*.9);
			//int ux = (int)(lx +Math.cos(Math.toRadians(60))*70*2);
			//int hy = h +Nucleotide.getImageSize();
			//int uy = YS-(int)(h*Math.sin(Math.toRadians(30)))+Nucleotide.getImageSize();
			//int ly = YS+(int)(h*Math.sin(Math.toRadians(30)))-Nucleotide.getImageSize()+25;

			int h = Nucleotide.getImageSize()*unzipIndex;
			int lx = (int)(XS+h-30-Math.cos(Math.toRadians(30))*h);
			int ly = (int)(YS-25+Math.sin(Math.toRadians(30))*h);
			int ux = lx +Nucleotide.getImageSize()*2;
			int uy =  (int)(YS+25-Math.sin(Math.toRadians(30))*h-Math.sin(Math.toRadians(60))*(70*2-5));
			System.out.println("X Y : "+Integer.toString(lx)+" " + Integer.toString(ux));
			upperHelix.setPos(ux, uy,30);
			lowerHelix.setPos(lx,ly,-30);
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
		char b = zone.getComplementaryDnaNucleotide().getBase();
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
