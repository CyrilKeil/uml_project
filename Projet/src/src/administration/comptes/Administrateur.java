package src.administration.comptes;

import java.sql.SQLException;
import java.util.Vector;

import src.administration.TableauVols;
import src.administration.database.Database;
import src.aeroport.Aeroport;
import src.avion.Avion;
import src.avion.TypeAvion;
import src.equipage.MembreEquipage;
import src.exceptions.EquipageException;
import src.exceptions.InvariantBroken;
import src.exceptions.TableauVolsException;
import src.vol.Vol;

public class Administrateur {
	
	private TableauVols tableau;
	private Database db;
	
	public Administrateur(TableauVols tableau, Database db) throws ClassNotFoundException {
		this.tableau = tableau;
		this.db = db;
	}
	
	public void ajouterMembreEquipage(MembreEquipage m) 
			throws TableauVolsException, ClassNotFoundException, SQLException {
		
		if(tableau.getListeMembresEquipage().contains(m)) 
			
			throw new TableauVolsException("Membre deja existant");
		
		tableau.getListeMembresEquipage().addElement(m);
		db.ajoutMembreEquipageDB(m);
	
	}
	
	public void supprimerMembreEquipage(int idMembre) 
			throws TableauVolsException, ClassNotFoundException, SQLException {
		
		/*if(!tableau.getListeMembresEquipage().contains(m)) 
			
			throw new TableauVolsException("Membre inexistant");
		
		try {
			
			for(int i = 0; i < tableau.getListeTypesAvion().size(); i++) {
			
				tableau.getListeTypesAvion().get(i).delQualifie(m);
			
			}
		
		} 
		
		catch (EquipageException e) { e.printStackTrace(); }
		
		tableau.getListeMembresEquipage().remove(m);*/
		db.retirerMembreEquipageDB(idMembre);
		
	}
	
	public boolean qualifierMembreEquipage(int idMembre, int idTypeAvion) 
			throws EquipageException, InvariantBroken, TableauVolsException, SQLException {
			tableau.getListeMembresEquipage().get(idMembre).addQualification(tableau.getListeTypesAvion().get(idTypeAvion));
			tableau.getListeTypesAvion().get(idTypeAvion).addQualifie(tableau.getListeMembresEquipage().get(idMembre));
			db.qualifierMembreDB(idMembre, idTypeAvion);
			return true;
			
	}
	
	public int rechercherMembreEquipage(String nom, String prenom) {
		return db.rechercherMembreEquipage(nom, prenom);
	}
	
	public void afficherListeMembreEquipage() {
		db.afficherListeMembreEquipage();
	}
	
	public void ajouterVol(Vol v, int idAeroport, int idAvion) 
			throws TableauVolsException, ClassNotFoundException, SQLException {
		
		if(tableau.getListeVols().contains(v)) 
			
			throw new TableauVolsException("Vol deja existant");
		
		if(!tableau.getListeAvions().contains(v.getAvion()))
				
			throw new TableauVolsException("Avion non existant");
		
		tableau.getListeVols().addElement(v);
		db.ajoutVolDB(v, idAeroport, idAvion);
		
	}
	
	public void supprimerVol(int idVol) throws TableauVolsException, ClassNotFoundException {
		db.retirerVolDB(idVol);
		
	}
	
	public int rechercherVol(String numVol) {
		return db.rechercherVol(numVol);
	}
	
	public void ajouterTypeAvion(TypeAvion t) throws TableauVolsException, ClassNotFoundException, SQLException {
		
		if(tableau.getListeTypesAvion().contains(t)) 
			
			throw new TableauVolsException("Type d'avion deja existant");
		
		tableau.getListeTypesAvion().addElement(t);
		db.ajoutTypeAvionDB(t);
		
	}
	
	public void afficherListeVols() {
		db.afficherListeVols();
	}
	
	public void supprimerTypeAvion(TypeAvion t, int idTypeAvion) 
			throws TableauVolsException, EquipageException, ClassNotFoundException {
		
		if(!tableau.getListeTypesAvion().contains(t)) 
			
			throw new TableauVolsException("Type d'avion inexistant");
		
		// Enlever les avions associes a ce type ?
		
		try {
		
			tableau.getListeTypesAvion().get(tableau.indexTypeAvion(t)).purgeQualifies();
			
		}
		
		catch (InvariantBroken e) { e.printStackTrace(); }
		
		tableau.getListeTypesAvion().remove(t);
		db.retirerTypeAvionDB(idTypeAvion);
		
	}
	
	public Vector<TypeAvion> getTypesAvions(){
		return db.getListeTypesAvion();
	}
	
	public Vector<Object> getAvions(){
		return db.getListeAvions();
	}
	
	public int rechercherTypeAvion(String nom) {
		return db.rechercherTypeAvion(nom);
	}
	
	public void afficherListeTypesAvion() {
		db.afficherListeTypesAvion();
	}
	
	public void ajouterAvion(Avion a, int idTypeAvion) 
			throws TableauVolsException, ClassNotFoundException, SQLException {
		
		if(tableau.getListeAvions().contains(a)) 
			
			throw new TableauVolsException("Avion deja existant");
		
		tableau.getListeAvions().addElement(a);
		db.ajoutAvionDB(a, idTypeAvion);
		
	}
	
	public void supprimerAvion(Avion a, int idAvion) throws TableauVolsException, ClassNotFoundException {
	
		if(!tableau.getListeAvions().contains(a)) 
			
			throw new TableauVolsException("Avion inexistant");
		
		tableau.getListeAvions().remove(a);
		db.retirerAvionDB(idAvion);
		
	}
	
	public int rechercherAvion(String ref) {
		return db.rechercherAvion(ref);
	}
	
	public void afficherListeAvions() {
		db.afficherListeAvions();
	}
	
	public void afficherListeFonction() {
		db.afficherListeFonction();
	}
	
	public String getFonctionDB(int choixFonction) {
		return db.getFonctionDB(choixFonction);
	}

	public Vector<MembreEquipage> getMembres() throws InvariantBroken {
		return db.getMembres();
	}
	
	public Vector<Object> getAeroports(){
		return db.getAeroports();
	}
	
}
