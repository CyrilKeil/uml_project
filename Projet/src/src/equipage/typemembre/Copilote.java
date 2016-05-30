package src.equipage.typemembre;

import src.equipage.MembreEquipage;
import src.exceptions.InvariantBroken;

public class Copilote extends MembreEquipage {

	public Copilote(String nom, String prenom) throws InvariantBroken {
		super(nom, prenom, 1);
	}
	
	public String toString() {
		
		return super.toString() 
				+ "Nom : " + this.getNom() 
				+ "Prenom : " + this.getPrenom()
				+ "Fonction : " + this.getFonction();
		
	}
	
	@Override
	public void messageAjout() {
		System.out.println("Vous venez d'ajouter un nouveau copilote dans l'application");
	}
	
}
