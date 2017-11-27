package proteins;
public class PrimeZone{
  /*
  *Zone is in the form
  *[[x,y],[helix,strand,nucleotide]]
  */
  private int[][] zone;
  private Helix[] helix;
  private static int RANGE 150;
  public PrimeZone(int type, int[] coords, int[] nucleotide,Helix helix1,Helix helix2){
    zone = new {coords, nucleotide};
    helix = {helix1,helix2};
  }
  public int[] getCoords(){
    return zone[0]
  }
  public void addNucleotide(Nucleotide nucleotide){
    helix[zone[1][0]].addNucleotide(zone[1][1],zone[1][2],nucleotide);
  }
  public void toggleBond(){
    helix[zone[1][0]].toggleNucleotide(zone[1][1],zone[1][2]);
  }
  public boolean inRange(int x, int y){
    if((zone[1][0] - RANGE < x && x < zone[1][0] + RANGE) &&
       (zone[0][1] - RANGE < y && y < zone[1][1] + RANGE)){
       return true; 
    }
    else{
      return false;
    }
  }
}
