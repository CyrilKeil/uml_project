package src.avion;

public class Avion {

	private TypeAvion type;
	private String ref;
	
	public Avion(TypeAvion type, String ref) {
		this.type = type;
		this.ref = ref;
	}
	
	public TypeAvion getType() {
		return this.type;
	}
	
	public String getReference() {
		return this.ref;
	}
	
	public String toString() {
		
		return "Type d'avion : " 
			+ "\n * Nom : " + this.type.getNom()
			+ "\n * Nombre minimum PNC : " + this.type.getMinPNC()
			+ "\n * Nombre maximum PNC : " + this.type.getMaxPNC()
			+ "\nReference : " + this.ref;
	
	}
	
}
