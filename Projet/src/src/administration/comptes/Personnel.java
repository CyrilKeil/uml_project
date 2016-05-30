package src.administration.comptes;

import src.administration.TableauVols;
import src.administration.database.Database;
import src.equipage.MembreEquipage;
import src.exceptions.TableauVolsException;

public class Personnel {
	
	private TableauVols tableau;
	private Database db;

	public Personnel(TableauVols tableau, Database db) throws ClassNotFoundException {
		this.tableau = tableau;
		this.db = db;
	}

	public void afficherMesVols(int idMembre) throws TableauVolsException {
		db.afficherMesVols(idMembre);
	}
	
}
