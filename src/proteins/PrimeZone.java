package proteins;

import java.util.Arrays;

import nucleotides.Helix;
import nucleotides.Nucleotide;

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
	 * Adds a nucleotide to the helix and position specified by the primezone
	 * @param nucleotide
	 */
	public void addNucleotide(Nucleotide nucleotide){
	  helix[zone[1][0]].setNucleotide(zone[1][1],zone[1][2],nucleotide);
	}
	public void addComplementaryNucleotide() {
		helix[zone[1][0]].setNucleotide(zone[1][1], zone[1][2], helix[zone[1][0]].getNucleotide((zone[1][1]+1)%2, helix[zone[1][0]].getLength()-zone[1][2]-1).getRnaComplement());
	}
	/**
	 * Toggles the bond of the nucleotide and helix specified in init
	 */
	public void toggleBond(){
		helix[zone[1][0]].toggleBond(zone[1][1],zone[1][2]);
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
