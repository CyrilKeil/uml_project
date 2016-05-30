package src.exceptions;

public class InvariantBroken extends Exception {

	private static final long serialVersionUID = -6051833560950749217L;

	public InvariantBroken(String erreur) {
		super(erreur);
	}
	
}
