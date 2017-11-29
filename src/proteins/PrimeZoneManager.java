package proteins;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.GraphicsContext;
import nucleotides.DnaNucleotide;
/**
 * Manages zone instances for each protein type.
 * @author clonex10100
 *
 */
public class PrimeZoneManager {
	HashMap<Integer,ArrayList<PrimeZone>> ZoneMap;
	public PrimeZoneManager() {
		/*
		 * 0: Primase
		 * 1-4: Draggable Nucleotide
		 * 5: Ligase
		 */
		ZoneMap = new HashMap<Integer,ArrayList<PrimeZone>>();
		ZoneMap.put(0,new ArrayList<PrimeZone>());
		ZoneMap.put(1,new ArrayList<PrimeZone>());
		ZoneMap.put(2,new ArrayList<PrimeZone>());
		ZoneMap.put(3,new ArrayList<PrimeZone>());
		ZoneMap.put(4,new ArrayList<PrimeZone>());
		ZoneMap.put(5,new ArrayList<PrimeZone>());
	}
	/**
	 * Adds a zone with tag p to zone manager
	 * @param p p=0,1,2
	 * @param zone PrimeZone zone
	 */
	public void addZone(int p, PrimeZone zone) {
		ZoneMap.get(p).add(zone);
	}
	/**
	 * Removes a specified zone with tag p
	 * @param p p=0,1,2
	 * @param zone
	 * @return True if found and deleted, false otherwise
	 */
	public boolean removeZone(int p,PrimeZone zone) {
		return ZoneMap.get(p).remove(zone);
	}
	/**
	 * Gets array of zones with tag p
	 * @param p=0,1,2
	 * @return Array of zones
	 */
	public PrimeZone[] getZones(int p) {
		return ZoneMap.get(p).toArray(new PrimeZone[0]);
	}

	public boolean isInZone(double x, double y, int p) {
		for(int i = 0; i < ZoneMap.get(p).size();i++) {
			if(ZoneMap.get(p).get(i).inRange(x, y)) {
				ZoneMap.get(p).get(i).addComplementaryRnaNucleotide();
				ZoneMap.get(p).remove(i);
				return true;
			}
		}
		return false;
	}
	public void draw(GraphicsContext gc){
		for(int i = 0; i < ZoneMap.get(0).size(); i++ ){
			ZoneMap.get(0).get(i).draw(0,gc);
		}
		for(int i = 0; i < ZoneMap.get(1).size(); i++ ){
			ZoneMap.get(1).get(i).draw(1,gc);
		}
		for(int i = 0; i < ZoneMap.get(2).size(); i++ ){
			ZoneMap.get(2).get(i).draw(2,gc);
		}
		for(int i = 0; i < ZoneMap.get(3).size(); i++ ){
			ZoneMap.get(3).get(i).draw(3,gc);
		}
		for(int i = 0; i < ZoneMap.get(4).size(); i++ ){
			ZoneMap.get(4).get(i).draw(4,gc);
		}

	}
}
