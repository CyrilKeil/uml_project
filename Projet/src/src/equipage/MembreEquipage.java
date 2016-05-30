package src.equipage;

import java.util.Vector;

import src.avion.TypeAvion;
import src.exceptions.EquipageException;
import src.exceptions.InvariantBroken;

public abstract class MembreEquipage {

	private String nom;
	private String prenom;
	private Fonction fonction;
	private Vector<TypeAvion> qualifications;
	
	public class Fonction {
		
		private String fonction;
		private String descriptionFonction;
		
		public Fonction(int idFonction) {
		
			switch(idFonction) {
			
			// Pilote
			case 0 :
				this.fonction = "Pilote";
				this.descriptionFonction = "Il contrôle l'avion durant le vol";
				break;
				
			// Copilote
			case 1 :
				this.fonction = "Copilote";
				this.descriptionFonction = "Il assiste le pilote dans sa tâche";
				break;
				
			// PNC
			case 2 :
				this.fonction = "PNC";
				this.descriptionFonction = "Ils participent au confort des passagers de l'avion durant le vol";
				break;
				
			default:
				this.fonction = "";
				this.descriptionFonction = "";
				break;
			
			}
	
		}
		
		public String getNomFonction() {
			return this.fonction;
		}
		
	}
	
	public MembreEquipage(String nom, String prenom, int idFonction) throws InvariantBroken {
		this.nom = nom;
		this.prenom = prenom;
		this.fonction = new Fonction(idFonction);
		this.qualifications = new Vector<TypeAvion>(2);
	}
	
	public abstract void messageAjout();
	
	public String getNom() {
		return this.nom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public Fonction getFonction() {
		return this.fonction;
	}
	
	public void afficherQualifs(){
		for(int i = 0; i < qualifications.size(); i++){
			System.out.println(qualifications.get(i).getNom());
		}
	}
	
	public boolean peutVoler(TypeAvion type) {
		return this.qualifications.contains(type);
	}
	
	public boolean addQualification(TypeAvion type) throws EquipageException, InvariantBroken {
		
		if(this.qualifications.contains(type))
			
			throw new EquipageException("Membre d'equipage deja qualifie pour ce type d'avion");

		if(this.qualifications.capacity() == 2) 
			
			throw new EquipageException("Membre d'equipage deja qualifie pour deux types d'avion");

		this.qualifications.addElement(type);
		//type.addQualifie(this);
		
		return true;
	
	}
	
	public void delAllQualifs(){
		this.qualifications = new Vector<TypeAvion>();
	}
	
	public boolean delQualification(TypeAvion type, boolean fromType) throws EquipageException, InvariantBroken {	
		
		if(!this.qualifications.contains(type))
		
			throw new EquipageException("Membre d'equipage non qualifie pour ce type d'avion");

		this.qualifications.remove(type);
		
		if(!fromType)
			
			type.delQualifie(this);
			
		return true;
		
	}
	
	public String toString() {
		
		String qualifs = null;
		
		for(int i = 0; i < this.qualifications.size(); i++) {
			qualifs += this.qualifications.get(i).getNom() + "\n";
		}
		
		return "Qualifications : \n" + qualifs;
		
	}
	
}
