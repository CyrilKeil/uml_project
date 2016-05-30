package src.administration.comptes;

import src.administration.TableauVols;
import src.administration.database.Database;
import src.equipage.MembreEquipage;
import src.exceptions.TableauVolsException;
import src.vol.Vol;

public class Manager {
	
	private TableauVols tableau;
	private Database db;

	public Manager(TableauVols tableau, Database db) throws ClassNotFoundException {
		this.tableau = tableau;
		this.db = db;
	}

	public boolean affecterMembreEquipageVol(Vol v, int idVol, MembreEquipage m, int idMembre) {
		return db.affecterMembreVolDB(idVol, idMembre);
	}

	public boolean retirerMembreEquipageVol(int idMembre) {
		return db.retirerMembreEquipageVol(idMembre);
	}

	public void afficherDonneesVol(int idVol) {
		db.afficherDonneesVol(idVol);
	}
	
	public boolean affecterMembreEquipageVol(int idVol, int idMembre) {
		return db.affecterMembreVolDB(idVol, idMembre);
	}
	
}
