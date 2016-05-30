package src.administration.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Date;

import src.aeroport.Aeroport;
import src.avion.Avion;
import src.avion.TypeAvion;
import src.equipage.MembreEquipage;
import src.equipage.Pilote;
import src.equipage.typemembre.Copilote;
import src.equipage.typemembre.PNC;
import src.exceptions.InvariantBroken;
import src.vol.Vol;



public class Database implements DatabaseInterface{
	
	private Connection connection;
	
	public Database() throws ClassNotFoundException, SQLException {
		System.out.println("Création de la base de données...");
		Class.forName("org.sqlite.JDBC");
		this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		System.out.println("Base de données créée avec succès !");
	}
	
	@Override
	public void creationTablesDB() throws ClassNotFoundException {
			
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			System.out.println("Création des tables...");

			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Equipage (idEquipage INTEGER not NULL,"
			  											 			   + "noEquipage INTEGER,"
			  											 			   + "idVol INTEGER,"
			  											 			   + "PRIMARY KEY ( idEquipage ),"
			  											 			   + "FOREIGN KEY ( idVol ) REFERENCES Vol)");
			
			System.out.println("Création de la table 'Equipage' réussie !");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Aeroport (idAeroport INTEGER not NULL,"
														 			   + "nomAeroport VARCHAR(15),"
														 			   + "codeIATA CHAR(3),"
														 			   + "PRIMARY KEY ( idAeroport ))");
			
			System.out.println("Création de la table 'Aeroport' réussie !");
				
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Vol (idVol INTEGER not NULL,"
													 			  + "numVol VARCHAR(6),"
													 			  + "site VARCHAR(15),"
													 			  + "idAeroport INTEGER," // Vol > Aeroport > nomAeroport (destination)
													 			  + "idAvion INTEGER," // Vol > Avion > TypeAvion > libelleTypeAvion
													 			  + "dateDepart DATE,"
													 			  + "CONSTRAINT numVolConstraint UNIQUE( numVol ),"
													 			  + "PRIMARY KEY ( idVol ),"
													 			  + "FOREIGN KEY ( idAeroport ) REFERENCES Aeroport,"
													 			  + "FOREIGN KEY ( idAvion ) REFERENCES Avion)"); 
			
			System.out.println("Création de la table 'Vol' réussie !");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Avion (idAvion INTEGER not NULL,"
													 			    + "idTypeAvion INTEGER," // Avion > TypeAvion > libelleTypeAvion
													 			    + "refAvion VARCHAR(8),"
													 			    + "PRIMARY KEY ( idAvion ),"
													 			    + "FOREIGN KEY ( idTypeAvion ) REFERENCES TypeAvion)");
		
			System.out.println("Création de la table 'Avion' réussie !");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS TypeAvion (idTypeAvion INTEGER not NULL,"
														 	 			+ "libelleTypeAvion VARCHAR(4),"
														 	 	   	    + "minPNC SMALLINT,"
														 	 	   	    + "maxPNC SMALLINT,"
														 	 	   	    + "PRIMARY KEY ( idTypeAvion ))");
			
			System.out.println("Création de la table 'TypeAvion' réussie !");
			
			// Table d'association
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS PersonnelQualifie (idPersonnelQualifie INTEGER not NULL,"
													 	 			  			+ "idTypeAvion INTEGER," 		// TypeAvion > PersonnelQualifie > MembreEquipage 
													 	 			  			+ "idMembreEquipage INTEGER,"  // TypeAvion < PersonnelQualifie < MembreEquipage
													 	 			  			+ "PRIMARY KEY ( idPersonnelQualifie ),"
													 	 			  			+ "FOREIGN KEY ( idTypeAvion ) REFERENCES TypeAvion,"
													 	 			  			+ "FOREIGN KEY ( idMembreEquipage ) REFERENCES MembreEquipage)"); 
			
			System.out.println("Création de la table 'PersonnelQualifie' réussie !");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS MembreEquipage (idMembreEquipage INTEGER not NULL,"
								 			  		 + "nom VARCHAR(10),"
								 			  		 + "prenom VARCHAR(10),"
								 			  		 + "idFonction INTEGER," // MembreEquipage > Fonction > nomFonction
								 			  		 + "noEquipage INTEGER,"
								 			  		 + "idVol INTEGER,"
								 			  		 + "PRIMARY KEY ( idMembreEquipage ),"
								 			  		 + "FOREIGN KEY ( idFonction ) REFERENCES Fonction,"
								 			  		 + "FOREIGN KEY ( noEquipage ) REFERENCES Equipage,"
								 			  		 + "FOREIGN KEY ( idVol ) REFERENCES Vol)");
			System.out.println("Création de la table 'MembreEquipage' réussie !");
			
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Fonction (idFonction INTEGER not NULL,"
 			  		 											  	   + "nomFonction VARCHAR(10),"
 			  		 											  	   + "PRIMARY KEY ( idFonction ))");
			
		/* 
			 * Table Fonction :
			 
			 * 0 - Pilote
			 * 1 - CoPilote
			 * 2 - PNC
		*/
			
			System.out.println("Création de la table 'Fonction' réussie !");
			
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
	}
	
	private int sqlCount(String table) throws SQLException {
		
		Statement statement = this.connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM " + table);
		
		int count = 0;
		
		while(rs.next()) 
			count = rs.getInt("total"); 
		
		return count;
		
	}
	
	@Override
	public void ajoutVolDB(Vol v, int idAeroport, int idTypeAvion) throws ClassNotFoundException, SQLException {
		
		int counter = sqlCount("Vol");
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO Vol VALUES(?,?,?,?,?,?)");
			
			ps.setInt(1, counter);
			ps.setString(2, v.getNumVol());
			ps.setString(3, v.getSite());
			ps.setInt(4, idAeroport); // TODO :
			ps.setInt(5, idTypeAvion); // TODO :
			ps.setDate(6, v.getDepart());
			ps.executeUpdate();

		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
	}
	
	@Override
	public void ajoutAvionDB(Avion a, int idTypeAvion) throws ClassNotFoundException, SQLException {
		
		int counter = sqlCount("Avion");
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO Avion VALUES(?,?,?)");
			
			ps.setInt(1, counter);
			ps.setInt(2, idTypeAvion);
			ps.setString(3, a.getReference());
			ps.executeUpdate();

		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
	}
	
	@Override
	public void ajoutTypeAvionDB(TypeAvion t) throws ClassNotFoundException, SQLException {
		
		int counter = sqlCount("TypeAvion");
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO TypeAvion VALUES(?,?,?,?)");
			
			ps.setInt(1, counter);
			ps.setString(2, t.getNom());
			ps.setInt(3, t.getMinPNC()); // TODO : vérifier que ça ne pose pas de problemes Smallint -> Int
			ps.setInt(4, t.getMaxPNC()); // TODO : vérifier que ça ne pose pas de problemes Smallint -> Int
			ps.executeUpdate();

		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
	}
		
	@Override
	public void ajoutMembreEquipageDB(MembreEquipage m) throws ClassNotFoundException, SQLException {
		
		int counter = sqlCount("MembreEquipage");
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO MembreEquipage VALUES(?,?,?,?,?,?)");
			
			ps.setInt(1, counter);
			ps.setString(2, m.getNom());
			ps.setString(3, m.getPrenom());
			
			if(m.getFonction().getNomFonction() == "Pilote")
				ps.setInt(4, 0);
			
			if(m.getFonction().getNomFonction() == "Copilote")
				ps.setInt(4, 1);
			
			if(m.getFonction().getNomFonction() == "PNC")
				ps.setInt(4, 2);
			
			ps.setString(5, null); // A la creation, le membre n'a pas de n° d'equipage
			ps.setInt(6, -1); // A la creation, le membre n'a pas de vol attribue
			ps.executeUpdate();
			
			System.out.println("Ajout effectué avec succès");

		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
	
}
	@Override
	public void retirerMembreEquipageDB(int idMembre) throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		Statement statement = this.connection.createStatement();
		statement.executeUpdate("DELETE FROM MembreEquipage WHERE idMembreEquipage = " + idMembre + "");
	}
	
	@Override
	public void ajoutFonctionDB() throws ClassNotFoundException {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			
			Statement statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Fonction");
			
			while(rs.next()) {
				
				System.out.print(rs.getString("idFonction") + " : ");
				System.out.print(rs.getString("nomFonction") + "\n");
				
			}

		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
	}
	
	@Override
	public boolean retirerMembreEquipageVol(int idMembre) {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("UPDATE MembreEquipage "
												+ "SET idVol = -1 "
												+ "WHERE idMembreEquipage = " + idMembre + "");
			
			return true;
			
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}		
		
		return false;
					
	}
	
	@Override
	public void retirerVolDB(int idVol) throws ClassNotFoundException {
		
		try {
	
			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
	
			statement.executeUpdate("DELETE FROM Vol WHERE idVol = " + idVol + "");
		
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
	
	}
	
	@Override
	public void retirerTypeAvionDB(int idTypeAvion) throws ClassNotFoundException {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);

			statement.executeUpdate("DELETE FROM TypeAvion WHERE idTypeAvion = " + idTypeAvion + "");			
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
	
	}
	
	@Override
	public void retirerAvionDB(int idAvion) throws ClassNotFoundException {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);

			statement.executeUpdate("DELETE FROM Avion WHERE idAvion = " + idAvion);
		
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
	
	}
	
	@Override
	public void qualifierMembreDB(int idMembre, int idTypeAvion) throws SQLException {
		
		int counter = sqlCount("PersonnelQualifie");
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO PersonnelQualifie VALUES(?,?,?)");
			
			ps.setInt(1, counter);
			ps.setInt(2, idTypeAvion);
			ps.setInt(3, idMembre); 
			ps.executeUpdate();

		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
	}
	
	@Override
	public void retirerQualificationMembreDB(int idMembre, int idTypeAvion){		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			PreparedStatement ps = this.connection.prepareStatement("DELETE FROM PersonnelQualifie WHERE idTypeAvion = ? AND idMembreEquipage = ?");

			ps.setInt(1, idTypeAvion);
			ps.setInt(2, idMembre); 
			ps.executeUpdate();

		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
	}
	
	@Override
	public int rechercherVol(String numVol) {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
		
			ResultSet rs = statement.executeQuery("SELECT idVol, numVol "
												+ "FROM Vol "
												+ "WHERE numVol = '" + numVol + "'");

			return rs.getInt("idVol");
			
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
		return -1;	
		
	}
	
	@Override
	public int rechercherMembreEquipage(String nom, String prenom) {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
		
			ResultSet rs = statement.executeQuery("SELECT idMembreEquipage, nom, prenom "
												+ "FROM MembreEquipage "
												+ "WHERE nom = " + nom + "AND prenom = '" + prenom + "'");

			return rs.getInt("idMembreEquipage");
			
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
		return -1;	
		
	}
	
	@Override
	public int rechercherTypeAvion(String nom) {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT idTypeAvion, libelleTypeAvion "
												+ "FROM TypeAvion "
												+ "WHERE libelleTypeAvion = '" + nom + "'");
			
			return rs.getInt("idTypeAvion");
			
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
		return -1;	
		
	}

	@Override
	public int rechercherAvion(String ref) {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
		
			ResultSet rs = statement.executeQuery("SELECT idAvion, refAvion "
												+ "FROM Avion "
												+ "WHERE refAvion = '" + ref + "'");

			return rs.getInt("idAvion");
			
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
		return -1;	
		
	}
	
	@Override
	public void deleteContenuTable(String nomTable) {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);

			statement.executeUpdate("DELETE FROM " + nomTable);
		
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
	}
	
	@Override
	public void deleteTables() {
	
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);

			statement.executeUpdate("DROP TABLE MembreEquipage");
		
		} catch (SQLException ex) { 
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
	}
	
	@Override
	public void afficherListeVols() {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM Vol");
			
			while(rs.next()) {
				
				// read the result set	
				System.out.print("Index: " + rs.getInt("idVol"));
				System.out.print("\nNuméro du vol: " + rs.getString("numVol"));
				System.out.print("\nSite de départ: " + rs.getString("site"));
				System.out.print("\nDestination: " + rs.getInt("idAeroport"));
				System.out.println("\n");
			
			}
				
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}
		
	}	
	
	@Override
	public void afficherListeMembreEquipage() {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM MembreEquipage");
			
			while(rs.next()) {
				
				// read the result set	
				System.out.print("Index: " + rs.getInt("idMembreEquipage"));
				System.out.print("\nNom: " + rs.getString("nom"));
				System.out.print("\nPrénom: " + rs.getString("prenom"));
				System.out.print("\nID Fonction: " + rs.getString("idFonction"));
				System.out.println("\n");
			
			}
				
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}
		
	}
	
	
	@Override
	public void afficherListeTypesAvion() {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM TypeAvion");
			
			while(rs.next()) {
				
				// read the result set	
				System.out.print("Index: " + rs.getInt("idTypeAvion"));
				System.out.print("\nLibelle: " + rs.getString("libelleTypeAvion"));
				System.out.print("\nMin PNC: " + rs.getInt("minPNC"));
				System.out.print("\nMax PNC: " + rs.getInt("maxPNC"));
				System.out.println("\n");
			
			}
				
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}
		
		
	}
	
	@Override
	public void afficherListeAvions() {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM Avion");
			
			while(rs.next()) {
				
				// read the result set	
				System.out.print("Index: " + rs.getInt("idAvion"));
				System.out.print("\nID Type Avion: " + rs.getInt("idTypeAvion"));
				System.out.print("\nRéférence avion: " + rs.getString("refAvion"));
				System.out.println("\n");
			
			}
				
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}
		
	}
	
	@Override
	public void afficherListeFonction() {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM Fonction");
			
			while(rs.next()) {
				
				// read the result set	
				System.out.print("Index: " + rs.getInt("idFonction"));
				System.out.print("\nFonction: " + rs.getString("nomFonction"));
				System.out.println("\n");
			
			}
				
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}
		
	}
	
	@Override
	public void afficherDonneesVol(int idVol) {
			
			try {
	
				this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
				
				Statement statement = this.connection.createStatement();
				statement.setQueryTimeout(30);
				
				ResultSet rs = statement.executeQuery("SELECT * "
													+ "FROM Vol "
													+ "WHERE Vol.id = " + idVol + "");
					
			} catch (SQLException ex) { 
				
				System.err.print("SQL Exception : ");
				System.err.println(ex.getMessage()); 
			
			}
			
		}
	
	@Override
	public void afficherMesVols(int idMembre) {
		
		try {
	
			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * "
												+ "FROM Vol, MembreEquipage "
												+ "WHERE MembreEquipage.idMembreEquipage = " + idMembre + "AND "
												+ "MembreEquipage.idVol = Vol.idVol");
				
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}
		
	}
	
	@Override
	public String getFonctionDB(int idFonction) {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT nomFonction "
												+ "FROM Fonction "
												+ "WHERE idFonction = '" + idFonction + "'");
			
			return rs.getString("nomFonction");
				
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}
		
		return null;
		
	}
	
	@Override
	public ArrayList<Object> getDonneesTableauService() {
	
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
		
			ArrayList<Object> listSelectDB = new ArrayList<Object>();
			listSelectDB.clear();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM Vol");
			ResultSetMetaData rsmd = rs.getMetaData();
						
			while(rs.next()) {
				listSelectDB.add(rs.getString("numVol"));
				listSelectDB.add(rs.getString("site"));
				Date dateDepart = rs.getDate("dateDepart");
				String date = dateDepart.getDay() + "/" + dateDepart.getMonth() + "/" + dateDepart.getYear();
				listSelectDB.add(date);
				listSelectDB.add(rs.getInt("idAeroport"));
				listSelectDB.add(rs.getInt("idAvion"));
			
			}
			return listSelectDB;
			//return listSelectDB;
			
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
			return null;
		
		} finally {
		
			try {
			
				if (this.connection != null)
			
					this.connection.close();
			
			} catch (SQLException ex) { 
				System.err.print("SQL Exception : ");
				System.err.println(ex.getMessage()); 
			}
			
		}	
			
	}
	
	@Override
	public Vector<TypeAvion> getListeTypesAvion(){
		Vector<TypeAvion> typesAvion = new Vector<TypeAvion>();
		try{
			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM TypeAvion");
			
			while(rs.next()) {
				// On lit et on créé un nouvel objet
				//int id = rs.getInt("idTypeAvtion");
				String libelle = rs.getString("libelleTypeAvion");
				int min = rs.getInt("minPNC");
				int max = rs.getInt("maxPNC");
				TypeAvion t = new TypeAvion(libelle, min, max);
				
				typesAvion.addElement(t);
				
			}
		}
		catch(SQLException ex){
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
		return typesAvion;
	}
	
	@Override
	public Vector<MembreEquipage> getMembres() throws InvariantBroken {
		Vector<MembreEquipage> membres = new Vector<MembreEquipage>();
		try{
			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM MembreEquipage");
			
			while(rs.next()){
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				int fonction = rs.getInt("idFonction");
				
				switch (fonction){
				case 0 : 
					MembreEquipage m = new Pilote(nom, prenom);
					membres.add(m);
					break;
				case 1 :
					MembreEquipage m2 = new Copilote(nom, prenom);
					membres.add(m2);
					break;
				case 2 : 
					MembreEquipage m3 = new PNC(nom, prenom);
					membres.add(m3);
					break;
				}
				
			}
		}
		catch(SQLException ex){
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
		return membres;
	}

	@Override
	public Vector<Object> getListeAvions(){
		Vector<Object> avions = new Vector<Object>();
		try{
			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM Avion");
			
			while(rs.next()) {
				int id = rs.getInt("idTypeAvion");
				avions.add(id);
				
				String ref = rs.getString("refAvion");
				avions.add(ref);
			}
		}
		catch(SQLException ex){
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
		return avions;
	}
	
	@Override
	public boolean affecterMembreVolDB(int idVol, int idMembre) {
		
		try {

			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("UPDATE MembreEquipage "
												+ "SET idVol = " + idVol 
												+ "WHERE idMembreEquipage = " + idMembre + "");
			
			return true;
			
		} catch (SQLException ex) { 
			
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		
		}		
		
		return false;
		
	}
	
	public Vector<Object> getAeroports(){
		Vector<Object> aeroports = new Vector<Object>();
		try{
			this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("SELECT * FROM Aeroport");
			
			while(rs.next()) {
				String nom = rs.getString("nomAeroport");
				aeroports.add(nom);
				
				String iata = rs.getString("codeIATA");
				aeroports.add(iata);
			}
		}
		catch(SQLException ex){
			System.err.print("SQL Exception : ");
			System.err.println(ex.getMessage()); 
		}
		
		return aeroports;
		
	}

	
}