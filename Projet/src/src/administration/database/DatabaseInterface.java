package src.administration.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import src.avion.Avion;
import src.avion.TypeAvion;
import src.equipage.MembreEquipage;
import src.exceptions.InvariantBroken;
import src.vol.Vol;

public interface DatabaseInterface {
	
	public void creationTablesDB() throws ClassNotFoundException;
	public void ajoutVolDB(Vol v, int idAeroport, int idTypeAvion) throws ClassNotFoundException, SQLException;
	public void ajoutAvionDB(Avion a, int idTypeAvion) throws ClassNotFoundException, SQLException;
	public void ajoutTypeAvionDB(TypeAvion t) throws ClassNotFoundException, SQLException;
	public void ajoutMembreEquipageDB(MembreEquipage m) throws ClassNotFoundException, SQLException;
	public void retirerMembreEquipageDB(int idMembre) throws SQLException;
	public void ajoutFonctionDB() throws ClassNotFoundException;
	public boolean retirerMembreEquipageVol(int idMembre);
	public void retirerVolDB(int idVol) throws ClassNotFoundException;
	public void retirerTypeAvionDB(int idTypeAvion) throws ClassNotFoundException;
	public void retirerAvionDB(int idAvion) throws ClassNotFoundException;
	public void qualifierMembreDB(int idMembre, int idTypeAvion) throws SQLException;
	public void retirerQualificationMembreDB(int idMembre, int idTypeAvion);
	public int rechercherVol(String numVol);
	public int rechercherMembreEquipage(String nom, String prenom);
	public int rechercherTypeAvion(String nom);
	public int rechercherAvion(String ref);
	public void deleteContenuTable(String nomTable);
	public void deleteTables();
	public void afficherListeVols();
	public void afficherListeMembreEquipage();
	public void afficherListeTypesAvion();
	public void afficherListeAvions();
	public void afficherListeFonction();
	public void afficherDonneesVol(int idVol);
	public void afficherMesVols(int idMembre);
	public String getFonctionDB(int idFonction);
	public ArrayList<Object> getDonneesTableauService();
	public Vector<TypeAvion> getListeTypesAvion();
	public Vector<MembreEquipage> getMembres() throws InvariantBroken;
	public Vector<Object> getListeAvions();
	public boolean affecterMembreVolDB(int idVol, int idMembre);
	
}
