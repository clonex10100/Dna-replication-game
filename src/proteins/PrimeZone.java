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
	private int[][] zone;
	private Helix[] helix;
	private static int RANGE = 150;
	public PrimeZone(int[] coords, int[] nucleotide,Helix helix1,Helix helix2){
		zone = new int[][]{coords, nucleotide};
		helix = new Helix[]{helix1,helix2};
	}
	public int[] getCoords(){
	  return zone[0];
	}
	/**
	 * Checked if given coords are within the primezone
	 * @param x- int
	 * @param y-int
	 * @return True or false
	 */
	public boolean inRange(double x, double y){
		if((zone[0][0] - RANGE < x && x < zone[0][0] + RANGE) &&
			(zone[0][1] - RANGE < y && y < zone[0][1] + RANGE)){
			return true;
	    }
	    else{
	    	return false;
	    }
	}
	/**
	 * Adds a nucleotide to the helix and position specified by the primezone
	 * @param nucleotide
	 */
	public void addNucleotide(Nucleotide nucleotide){
	  helix[zone[1][0]].setNucleotide(zone[1][1],zone[1][2],nucleotide);
	}
	public void addComplementaryRnaNucleotide() {
		int length = helix[zone[1][0]].getLength();
		Helix h = helix[zone[1][0]];
		int pos;
		int pos2;
		if(zone[1][0] == 0) {
			pos = zone[1][2]-1;
			pos2 =length -zone[1][2];
		}
		else {
			pos = helix[zone[1][0]].getIndex()-zone[1][2];
			pos2 = length-h.getIndex()+zone[1][2]-1;
		}
		Nucleotide newNucleotide = h.getNucleotide((zone[1][1]+1)%2,pos).getRnaComplement();
		h.setNucleotide(zone[1][1], pos2, newNucleotide);
	}
	public Nucleotide getComplmentaryDnaNucleotide() {
		Helix h = helix[zone[1][0]];
		int pos;
		if(zone[1][0] == 0) {
			pos = zone[1][2]-1;
		}
		else {
			pos = helix[zone[1][0]].getIndex()-zone[1][2];
		}
		return h.getNucleotide((zone[1][1]+1)%2,pos).getDnaComplement();
	}
	public Nucleotide getComplmentaryRnaNucleotide() {
		Helix h = helix[zone[1][0]];
		int pos;
		if(zone[1][0] == 0) {
			pos = zone[1][2]-1;
		}
		else {
			pos = helix[zone[1][0]].getIndex()-zone[1][2];
		}
		return h.getNucleotide((zone[1][1]+1)%2,pos).getRnaComplement();
	}
	/**
	 * Toggles the bond of the nucleotide and helix specified in init
	 */
	public void toggleBond(){
		if(zone[1][2]-1>0){
			System.out.println(zone[1][1]);
			System.out.println(zone[1][2]);
			helix[zone[1][0]].toggleBond(zone[1][1],zone[1][2]-1);
		}
	}
	public void draw(int type,GraphicsContext gc){
		if(type == 0) {
			gc.strokeOval(zone[0][0]-RANGE/2,zone[0][1]-RANGE/2,RANGE,RANGE);
		}
		else {
			gc.setFill(Color.BROWN);
			gc.fillOval(zone[0][0]-RANGE/2,zone[0][1]-RANGE/2,RANGE/2,RANGE/2);
		}
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimeZone other = (PrimeZone) obj;
		if (!Arrays.deepEquals(zone, other.zone))
			return false;
		return true;
	}

}
