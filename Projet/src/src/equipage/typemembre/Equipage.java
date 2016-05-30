package src.equipage.typemembre;

import java.util.Vector;

import src.equipage.MembreEquipage;
import src.equipage.Pilote;
import src.exceptions.EquipageException;
import src.vol.Vol;

public class Equipage {

	private Vol vol;
	private Pilote pilote;
	private Copilote copilote;
	private Vector<PNC> pnc;
	private boolean auMin;
	private boolean auMax;
	
	public Equipage(Vol v) {
		this.vol = v;
		this.pilote = null;
		this.copilote = null;
		this.pnc = new Vector<PNC>(v.getMaxPNC());
		this.auMin = false; 
		this.auMax = false; 
	}
	
	public void addPilote(Pilote p) throws EquipageException {
			
		if(this.pilote != null)
			
			throw new EquipageException("Pilote deja existant");

		this.pilote = p;
	
	}
	
	public void addCopilote(Copilote c) throws EquipageException {
		
		if(this.copilote != null)
			
			throw new EquipageException("Co-Pilote deja existant");
	
		this.copilote = c;
		
	}
	
	public boolean addPNC(PNC p) throws EquipageException {
		
		int i;
		
		if(auMax) 
				
			throw new EquipageException("Nombre maximum de PNC atteint");	
		
		for(i = 0; i < this.pnc.size(); i++) {
			
			if(this.pnc.get(i).getNom().equals(p.getNom())
				&& this.pnc.get(i).getPrenom().equals(p.getPrenom()))
		
				throw new EquipageException("PNC deja existant");
			
		}
		
		this.pnc.add(p);
		
		if(!auMin)
			
			// Mise a jour du statut auMin
			checkAuMin();
		
		// Mise a jour du statut auMax
		checkAuMax();
		
		if(equipageComplet())
			
			return true;
		
		return false;
	
	}
	
	public void checkAuMin() {
		
		if(this.vol.getMinPNC() <= this.pnc.capacity()) 
			this.auMin = true;
		else
			this.auMin = false;
		
	}
	
	public void checkAuMax() {

		if(this.vol.getMaxPNC() == this.pnc.capacity())
			this.auMax = true;
		else
			this.auMax = false;
			
	}
	
	public boolean peutVoler() {
		
		return this.pilote != null
			&& this.copilote != null
			&& this.auMin;
	
	}
	
	public boolean equipageComplet() {
		
		return this.pilote != null 
			&& this.copilote != null 
			&& this.auMax;		
	
	}
	
	public boolean contientMembreEquipage(MembreEquipage m) {
		
		if(m instanceof Pilote && this.pilote != null) 
			
			if(m.getNom() == this.pilote.getNom()
				&& m.getPrenom() == this.pilote.getPrenom())
		
				return true;
		
			else 
				
				return false;
		
		if(m instanceof Copilote && this.copilote != null) 
			
			if(m.getNom() == this.copilote.getNom()
				&& m.getPrenom() == this.copilote.getPrenom())
		
				return true;
		
			else 
				
				return false;
		
		if(m instanceof PNC && this.pnc.size() > 0) {
			
			for(PNC p : this.pnc) {
				
				if(p.getNom() == m.getNom()
					&& p.getPrenom() == m.getPrenom())
					
					return true;
				
			}
		
			return false;
		
		}
		
		return false;
			
	}
	
	public boolean enleverMembreEquipage(MembreEquipage m) throws EquipageException {
		
		if(m instanceof Pilote && this.pilote != null) 
			
			if(m.getNom() == this.pilote.getNom()
				&& m.getPrenom() == this.pilote.getPrenom()) {
		
				this.pilote = null;
				return true;
		
			} else throw new EquipageException("Pilote ne faisant pas parti de l'equipage");
		
		if(m instanceof Copilote && this.copilote != null) 
			
			if(m.getNom() == this.copilote.getNom()
				&& m.getPrenom() == this.copilote.getPrenom()) {
		
				this.copilote = null;
				return true;
		
			} else throw new EquipageException("Pilote ne faisant pas parti de l'equipage");
		
		if(m instanceof PNC && this.pnc.size() > 0) {
			
			//for(int i = 0; i < this.pnc.size(); i++) {
			for(PNC p : this.pnc) {
				
				if(p.getNom() == m.getNom()
					&& p.getPrenom() == m.getPrenom())
					
					this.pnc.remove(p);
					return true;
				
			}
		
			throw new EquipageException("Pilote ne faisant pas parti de l'equipage");
		
		}
		
		return false;
		
	}
	
	public String toString() {
		// TODO : Completer le methode
		return "";
	}
	
}
