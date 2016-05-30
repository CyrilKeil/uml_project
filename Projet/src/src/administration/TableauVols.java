package src.administration;

import java.sql.SQLException;
import java.util.Vector;

import src.administration.*;
import src.administration.comptes.Administrateur;
import src.administration.comptes.Manager;
import src.administration.comptes.Personnel;
import src.administration.database.Database;
import src.aeroport.Aeroport;
import src.avion.Avion;
import src.avion.TypeAvion;
import src.equipage.MembreEquipage;
import src.equipage.typemembre.Equipage;
import src.exceptions.EquipageException;
import src.exceptions.InvariantBroken;
import src.exceptions.TableauVolsException;
import src.vol.Vol;

public class TableauVols {

	private String nomCompagnie;
	private Vector<Vol> listeVols;
	private Vector<MembreEquipage> listeMembresEquipage;
	private Vector<Avion> listeAvions;
	private Vector<TypeAvion> listeTypesAvion;	
	private Vector<Aeroport> listeAeroports;
	 
	private Administrateur compteAdmin;
	private Manager compteManager;
	private Personnel comptePersonnel;
	
	public TableauVols(String nom, Database db) throws ClassNotFoundException {
		
		this.nomCompagnie = nom;
		this.listeVols = new Vector<Vol>();
		this.listeMembresEquipage = new Vector<MembreEquipage>();
		this.listeAvions = new Vector<Avion>();
		this.listeTypesAvion = new Vector<TypeAvion>();
		this.listeAeroports = new Vector<Aeroport>();

		this.compteAdmin = new Administrateur(this, db);
		this.compteManager = new Manager(this, db);
		this.comptePersonnel = new Personnel(this, db);
	
	}
	
	public void update() throws InvariantBroken{
		// Liste types avion
		setListeTypesAvion(compteAdmin.getTypesAvions());
		
		// Liste avions
		Vector<Object> avionsToBeModified = compteAdmin.getAvions();
		Vector<Avion> avions = new Vector<Avion>();

		for(int i = 0; i < avionsToBeModified.size(); i+=2){
			int indexType = (int) avionsToBeModified.get(i);
			TypeAvion t = listeTypesAvion.get(indexType);
			String reference = (String)avionsToBeModified.get(i+1);
			avions.add(new Avion(t, reference));
		}
		setListeAvions(avions);
		
		// Liste membres
		Vector<MembreEquipage> membres = compteAdmin.getMembres();
		
		// Liste des aeroports
		Vector<Object> aeroportsToBeModified = compteAdmin.getAeroports();
		Vector<Aeroport> aeroports = new Vector<Aeroport>();
		for(int i = 0; i < aeroportsToBeModified.size(); i+=2){
			aeroports.add(new Aeroport((String)aeroportsToBeModified.get(i), (String)aeroportsToBeModified.get(i+1)));
		}
		setListeAeroports(aeroports);
		
		
	}
	
	public Vector<Aeroport> getListeAeroports() {
		return listeAeroports;
	}

	public void setListeAeroports(Vector<Aeroport> listeAeroports) {
		this.listeAeroports = listeAeroports;
	}

	public void ajouterMembreEquipage(MembreEquipage m) throws TableauVolsException, ClassNotFoundException, SQLException {
		this.compteAdmin.ajouterMembreEquipage(m);
	}		
	
	/*public void supprimerMembreEquipage(MembreEquipage m, int idMembre) throws TableauVolsException, ClassNotFoundException {		
		this.compteAdmin.supprimerMembreEquipage(m, idMembre);	
	}*/
	
	public void supprimerMembreEquipage(int idMembre) throws ClassNotFoundException, TableauVolsException, SQLException{
		this.compteAdmin.supprimerMembreEquipage(idMembre);
	}
	
	public boolean qualifierMembreEquipage(int idMembre, int idTypeAvion)
		throws EquipageException, InvariantBroken, TableauVolsException, SQLException {
		
		return this.compteAdmin.qualifierMembreEquipage(idMembre, idTypeAvion);
	}
	
	public int rechercherMembreEquipage(String nom, String prenom) {
		return this.compteAdmin.rechercherMembreEquipage(nom, prenom); 
	}
	
	public int indexMembreEquipage(MembreEquipage m) {
		return this.listeMembresEquipage.indexOf(m);
	}
	
	public void ajouterVol(Vol v, int idAeroport, int idAvion) 
			throws TableauVolsException, ClassNotFoundException, SQLException {
		
		this.compteAdmin.ajouterVol(v, idAeroport, idAvion);	
	}

	public void supprimerVol(int idVol) throws TableauVolsException, ClassNotFoundException {
		this.compteAdmin.supprimerVol(idVol);		
	}
	
	public int rechercherVol(String numVol) {
		return this.compteAdmin.rechercherVol(numVol); 
	}
	
	public int indexVol(Vol v) {
		return this.listeVols.indexOf(v);
	}
	
	public void ajouterTypeAvion(TypeAvion t) throws TableauVolsException, ClassNotFoundException, SQLException {				
		this.compteAdmin.ajouterTypeAvion(t);
	}
	
	public void supprimerTypeAvion(TypeAvion t, int idTypeAvion) 
			throws TableauVolsException, EquipageException, ClassNotFoundException {
		
		this.compteAdmin.supprimerTypeAvion(t, idTypeAvion);
	}
	
	public int rechercherTypeAvion(String nom){
		for(int i = 0; i < listeTypesAvion.size(); i++){
			if(listeTypesAvion.get(i).getNom() == nom){
				return i;
			}
		}
		return -1;
	}
	
	public int indexTypeAvion(TypeAvion t) {
		return this.listeTypesAvion.indexOf(t);
	}
	
	public void ajouterAvion(Avion a, int idTypeAvion) 
			throws TableauVolsException, ClassNotFoundException, SQLException {
		
		this.compteAdmin.ajouterAvion(a, idTypeAvion);
	}
	
	public void setNomCompagnie(String nomCompagnie) {
		this.nomCompagnie = nomCompagnie;
	}

	public void setListeVols(Vector<Vol> listeVols) {
		this.listeVols = listeVols;
	}

	public void setListeMembresEquipage(Vector<MembreEquipage> listeMembresEquipage) {
		this.listeMembresEquipage = listeMembresEquipage;
	}

	public void setListeAvions(Vector<Avion> listeAvions) {
		this.listeAvions = listeAvions;
	}

	public void setListeTypesAvion(Vector<TypeAvion> listeTypesAvion) {
		this.listeTypesAvion = listeTypesAvion;
	}

	public void supprimerAvion(Avion a, int idAvion) throws TableauVolsException, ClassNotFoundException {
		this.compteAdmin.supprimerAvion(a, idAvion);			
	}
	
	public int rechercherAvion(String ref) {
		for(int i = 0; i < listeAvions.size(); i++){
			if(listeAvions.get(i).getReference() == ref){
				return i;
			}
		}
		return -1;
	}
	
	public void afficherDonneesVol(int idMembre) throws TableauVolsException {
		this.compteManager.afficherDonneesVol(idMembre);
	}
	
	public void afficherMesVols(int idMembre) throws TableauVolsException {
		this.comptePersonnel.afficherMesVols(idMembre);
	}
	
	public void afficherListeVols() {		
		this.compteAdmin.afficherListeVols();
	}
	
	public void afficherListeMembreEquipage() {
		this.compteAdmin.afficherListeMembreEquipage();
	}
	
	public void afficherListeTypesAvion() {		
		this.compteAdmin.afficherListeTypesAvion();
	}
	
	public void afficherListeAvions() {
		this.compteAdmin.afficherListeAvions();
	}
	
	public void afficherListeFonction() {
		this.compteAdmin.afficherListeFonction();
	}
	
	public String getFonctionDB(int choixFonction) {
		return this.compteAdmin.getFonctionDB(choixFonction);
	}
	
	public Vector<Vol> getListeVols() {
		return this.listeVols;
	}
	
	public Vector<MembreEquipage> getListeMembresEquipage() {
		return this.listeMembresEquipage;
	}
	
	public Vector<Avion> getListeAvions() {
		return this.listeAvions;
	}
	
	public Vector<TypeAvion> getListeTypesAvion() {
		return this.listeTypesAvion;
	}
	
	public String getNomCompagnie() {
		return this.nomCompagnie;
	}
	
	public boolean affecterMembreEquipageVol(int idVol, int idMembre) throws TableauVolsException, EquipageException {
		
		if(this.compteManager.affecterMembreEquipageVol(idVol, idMembre)) {
		
			System.out.println("Affectation effectuée avec succès");
			return true;
		
		} else return false;
	
	}

	public boolean retirerMembreEquipageVol(int idMembre) throws TableauVolsException {	
		
		if(this.compteManager.retirerMembreEquipageVol(idMembre)) {
			
			System.out.println("Retrait effectué avec succès");
			return true;
		
		} else return false;
		
	}
	
}
