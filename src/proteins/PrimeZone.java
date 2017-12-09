package proteins;

import java.util.Arrays;

import nucleotides.Helix;
import nucleotides.Nucleotide;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PrimeZone{
	/*
	 *Zone is in the form
	 *[[x,y],[helix,strand,nucleotide]]
	 */
	private int[] nPos;
	private int[] pos;
	private Helix[] helix;
	private static int RANGE = 150;
	public PrimeZone(int[] coords, int[] nucleotide,Helix helix1,Helix helix2){
		nPos= nucleotide;
		pos = coords;
		helix = new Helix[]{helix1,helix2};
	}
	/**
	 * Gets the x,y coords of the center of the PrimeZone
	 * @return int[] {x,y}
	 */
	public int[] getCoords(){
		return pos;
	}
	/**
	 * Checked if given coords are within the primezone
	 * @param x- int
	 * @param y-int
	 * @return True or false
	 */
	public boolean inRange(double x, double y){
		if((pos[0] - RANGE < x && x < pos[0] + RANGE) &&
			(pos[1] - RANGE < y && y < pos[1] + RANGE)){
				return true;
		}
		else{
			return false;
		}
	}
	/**
	* Gets the complementary Dna nucleotide to the nucleotide position this PrimeZone
	* is linked to
	*/
	public Nucleotide getComplementaryDnaNucleotide() {
		return helix[nPos[0]].getNucleotide((nPos[1]+1)%2,nPos[2]).getDnaComplement();
	}
	/**
	* Gets the complementary Rna nucleotide to the nucleotide position this PrimeZone
	* is linked to
	*/
	public Nucleotide getComplementaryRnaNucleotide() {
		return helix[nPos[0]].getNucleotide((nPos[1]+1)%2,nPos[2]).getRnaComplement();
	}
	/**
	 * Adds a nucleotide to the helix and position specified by the primezone
	 * @param nucleotide
	 */
	public void addNucleotide(Nucleotide nucleotide){
		helix[nPos[0]].setNucleotide(nPos[1],nPos[2],nucleotide);
	}
	/**
	 * Adds the complementary Rna nucleotide to the nucleotide position this PrimeZone
	 * is linked to
	 */
	public void addComplementaryRnaNucleotide() {
		helix[nPos[0]].setNucleotide(nPos[1],nPos[2],this.getComplementaryRnaNucleotide());
	}
	public void addComplementaryDnaNucleotide() {
		helix[nPos[0]].setNucleotide(nPos[1],nPos[2],this.getComplementaryDnaNucleotide());
	}
	/**
	 * Toggles the bond of the nucleotide and helix specified in init
	 */
	public void toggleBond(){
		if(nPos[1] == 0 && nPos[2] -1 > 0){
			helix[nPos[0]].toggleBond(nPos[1],nPos[2]-1);
		}
		else if(nPos[1] == 1){
			try{
				helix[nPos[0]].toggleBond(nPos[1],nPos[2]+1);
			}catch(IllegalArgumentException e){}
		}
	}
	public void draw(int type,GraphicsContext gc){
		if(type == 0) {
			gc.strokeOval(pos[0]-RANGE/2,pos[1]-RANGE/2,RANGE,RANGE);
		}
		else if(type > 0 && type < 5) {
			gc.setFill(Color.BROWN);
			gc.fillOval(pos[0]-RANGE/2,pos[1]-RANGE/2,RANGE/2,RANGE/2);
		}
	}
}
