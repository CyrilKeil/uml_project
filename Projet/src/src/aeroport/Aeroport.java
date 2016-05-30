package src.aeroport;

import java.io.Serializable;

public class Aeroport implements Serializable {

	private static final long serialVersionUID = 4422889482381373767L;

	public String nomAeroport;
	public String codeIATA;
	
	public Aeroport(String nomAeroport, String codeIATA) {
		this.nomAeroport = nomAeroport;
		this.codeIATA = codeIATA;
	}
	
}
