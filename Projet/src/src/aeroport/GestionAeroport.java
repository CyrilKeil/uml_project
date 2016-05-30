package src.aeroport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class GestionAeroport {
	
	public static void lire() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		ObjectInputStream entree = new ObjectInputStream(new FileInputStream("aeroport.serial"));
		Aeroport aeroport = (Aeroport)entree.readObject();
		System.out.print(aeroport.nomAeroport + " - ");
		System.out.println(aeroport.codeIATA);
		entree.close();
		
	}
	
	public static void ecrire() throws FileNotFoundException, IOException {
		
		String nomAeroport = null, 
				   codeIATA = null;
			
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Saisir le nom de l'aéroport à sérialisé : ");
		nomAeroport = sc.nextLine();
		
		System.out.println("\nSaisir le code IATA associé : ");
		codeIATA = sc.nextLine();
		
		ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream("aeroport.serial"));
		sortie.writeObject(new Aeroport(nomAeroport, codeIATA));
		sortie.close();
		
	}
	
	/*
	 * TODO : Ajouter les aeroports et les codes IATA en tant que destination des vols.
	 */
	public static void main(boolean lecture) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		if(lecture) lire();
		else ecrire();
		
	}

}
