package src.equipage;

import src.exceptions.InvariantBroken;

public class Pilote extends MembreEquipage {
	
	public Pilote(String nom, String prenom) throws InvariantBroken {
		super(nom, prenom, 0);
	}
	
	public String toString() {
		
		return super.toString() 
				+ "Nom : " + this.getNom() 
				+ "Prenom : " + this.getPrenom()
				+ "Fonction : " + this.getFonction();
		
	}

	@Override
	public void messageAjout() {
		System.out.println("Vous venez d'ajouter un nouveau pilote dans l'application");
	}
	
}
