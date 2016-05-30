package src.vol;

import java.sql.Date;

import src.aeroport.Aeroport;
import src.avion.Avion;
import src.equipage.Pilote;
import src.equipage.typemembre.Copilote;
import src.equipage.typemembre.Equipage;
import src.equipage.typemembre.PNC;
import src.exceptions.EquipageException;

public class Vol {

	private String numVol;
	private Date depart;
	private String site;
	private Aeroport dest;
	private Avion av;
	private Equipage equipage;
	
	public Vol(String num, Date dep) {
		this.numVol = num;
		this.depart = dep;
		this.equipage = new Equipage(this);
	}
	
	public Vol(String num, String site, Aeroport dest, Avion av, Date dep) {
		this.numVol = num;
		this.site = site;
		this.dest = dest; // Aeroport
		this.av = av;
		this.depart = dep;
		this.equipage = new Equipage(this);
	}
	
	public void addPilote(Pilote p) throws EquipageException {
		this.equipage.addPilote(p);
	}
	
	public void addCopilote(Copilote c) throws EquipageException {
		this.equipage.addCopilote(c);
	}
	
	public boolean addPNC(PNC pnc) throws EquipageException {
		return this.equipage.addPNC(pnc);
	}
	
	public boolean equipageAuComplet() {
		return this.equipage.equipageComplet();
	}
	
	public int getMinPNC() {
		return av.getType().getMinPNC();
	}
	
	public int getMaxPNC() {
		return av.getType().getMaxPNC();
	}
	
	public Avion getAvion() {
		return this.av;
	}
	
	public Equipage getEquipage() {
		return this.equipage;
	}
	
	public void setEquipageNull() {
		this.equipage = null;
	}

	public String getNumVol() {
		return numVol;
	}

	public void setNumVol(String numVol) {
		this.numVol = numVol;
	}

	public Date getDepart() {
		return depart;
	}

	public void setDepart(Date depart) {
		this.depart = depart;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Aeroport getDest() {
		return dest;
	}

	public void setDest(Aeroport dest) {
		this.dest = dest;
	}
	
	public String toString() {
		// TODO : Completer le methode
		return "";
	}
	
}
