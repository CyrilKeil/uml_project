package src.avion;

import java.util.Vector;

import src.equipage.MembreEquipage;
import src.exceptions.EquipageException;
import src.exceptions.InvariantBroken;

public class TypeAvion {

	private String nom;
	private int minPNC;
	private int maxPNC;
	private Vector<MembreEquipage> personnelQualifie;
	
	public TypeAvion(String nom) {
		this.nom = nom;
		this.personnelQualifie = new Vector<MembreEquipage>();
	}
	
	public TypeAvion(String nom, int nbPNCMin, int nbPNCMax) {
		this.nom = nom;
		this.minPNC = nbPNCMin;
		this.maxPNC = nbPNCMax;
		this.personnelQualifie = new Vector<MembreEquipage>();
	}
	
	public void addQualifie(MembreEquipage p) throws EquipageException {
		
		if(this.personnelQualifie.contains(p))
			
			throw new EquipageException("Membre d'equipage deja present parmi le personnel qualifie");

		this.personnelQualifie.add(p);
	
	}
	
	public void delQualifie(MembreEquipage p) throws EquipageException {
	
		this.personnelQualifie.remove(p);
		
	}
	
	public void purgeQualifies() throws EquipageException, InvariantBroken {
		
		for(MembreEquipage m : this.personnelQualifie) {
			m.delQualification(this, true);
		}
		
		this.personnelQualifie.clear();
	
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public int getMinPNC() {
		return this.minPNC;
	}
	
	public int getMaxPNC() {
		return this.maxPNC;
	}
	
}
