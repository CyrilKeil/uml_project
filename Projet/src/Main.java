
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import src.administration.TableauVols;
import src.administration.database.Database;
import src.aeroport.Aeroport;
import src.aeroport.GestionAeroport;
import src.avion.Avion;
import src.avion.TypeAvion;
import src.equipage.MembreEquipage;
import src.equipage.Pilote;
import src.equipage.typemembre.Copilote;
import src.equipage.typemembre.PNC;
import src.exceptions.EquipageException;
import src.exceptions.InvariantBroken;
import src.exceptions.TableauVolsException;
import src.graphique.PanneauGraphique;
import src.vol.Vol;

public class Main {

	private static Database db;
	private static TableauVols tableau;
	private static Scanner sc = new Scanner(System.in);
	public static boolean connexionValide = false;

	public void update() {
		this.tableau.setListeTypesAvion(db.getListeTypesAvion());
	}

	public static void afficherMenuPrincipal() throws TableauVolsException, InvariantBroken, ClassNotFoundException,
			SQLException, EquipageException, FileNotFoundException, IOException {

		connexionValide = false;

		int choix = 0;

		do {

			System.out.println("\nVeuillez selectionner par son index, le niveau de droit souhaité");

			System.out.println("1 - Se connecter en tant qu'administrateur\n" + "2 - Se connecter en tant que manager\n"
					+ "3 - Se connecter en tant que membre du personnel\n" + "4 - Afficher le tableau des vols\n"
					+ "5 - Quitter l'application");

			choix = sc.nextInt();

		} while (choix < 1 || choix > 5);

		switch (choix) {

		case 1:

			passAdministrateur();
			break;

		case 2:

			passManager();
			break;

		case 3:

			connexionValide = true;
			menuPersonnel(connexionValide);
			break;

		case 4:
			PanneauGraphique.main(null);
			afficherMenuPrincipal();
			break;

		case 5:

			System.out.println("\nFermeture de l'application...");
			System.out.println("Merci d'avoir utilisé le tableau de gestion des vols.");
			System.exit(0);
			
			
		default:

			afficherMenuPrincipal();
			break;

		}

	}

	private static void passAdministrateur() throws TableauVolsException, InvariantBroken, ClassNotFoundException,
			SQLException, EquipageException, FileNotFoundException, IOException {

		int mdp;

		System.out.println("\nVeuillez saisir le mot de passe administrateur (4 chiffres) "
				+ "fournis par l'administrateur systeme :");

		mdp = sc.nextInt();

		if (mdp == 1010) {

			System.out.println("\nMot de passe correct, vous êtes connecté en tant qu'administrateur\n");
			connexionValide = true;
			menuAdministrateur(connexionValide);

		} else {

			System.out.println("Mot de passe incorrect");
			afficherMenuPrincipal();

		}

	}

	private static void passManager() throws TableauVolsException, InvariantBroken, ClassNotFoundException,
			SQLException, EquipageException, FileNotFoundException, IOException {

		int mdp;

		System.out.println(
				"\nVeuillez saisir le mot de passe manager (4 chiffres) " + "fournis par l'administrateur systeme :");

		mdp = sc.nextInt();

		if (mdp == 1111) {

			System.out.println("\nMot de passe correct, vous êtes connecté en tant que manager\n");
			connexionValide = true;
			menuManager(connexionValide);

		} else {

			System.out.println("Mot de passe incorrect");
			afficherMenuPrincipal();

		}

	}

	public static void menuAdministrateur(boolean check) throws TableauVolsException, InvariantBroken,
			ClassNotFoundException, SQLException, EquipageException, FileNotFoundException, IOException {

		tableau.update();

		if (check) {

			int choix;

			do {

				System.out.print("\nMenu administrateur :\n\n" + "1 - Ajouter membre d'équipage\n"
						+ "2 - Creer un vol\n" + "3 - Creer un type d'avion\n" + "4 - Creer un avion\n"
						+ "5 - Ajouter un aéroport\n" + "6 - Supprimer un membre d'équipage\n"
						+ "7 - Supprimer un vol\n" + "8 - Supprimer un type d'avion\n" + "9 - Supprimer un avion\n"
						+ "10 - Qualifier un membre d'équipage\n\n" + "11 - Se déconnecter\n");

				choix = sc.nextInt();
				System.out.println();

			} while (choix < 1 || choix > 11);

			switch (choix) {

			case 1:

				int choixFonction;
				String nom = "";
				String prenom = "";
				String fonction = "";

				System.out.println("Nom du nouveau membre d'équipage :");
				nom = sc.next();

				System.out.println("\nPrénom du nouveau membre d'équipage :");
				prenom = sc.next();

				do {

					System.out.println("\nVoici la liste des fonctions existantes,"
							+ "\nSaisissez l'index de la fonction souhaitée :\n" + "\n\t0 - Pilote" + "\n\t1 - Copilote"
							+ "\n\t2 - PNC\n");

					choixFonction = sc.nextInt();

				} while (choixFonction < 0 || choixFonction > 2);

				MembreEquipage m;

				switch (choixFonction) {

				case 0:

					m = new Pilote(nom, prenom);
					m.messageAjout();
					tableau.ajouterMembreEquipage(m);
					break;

				case 1:

					m = new Copilote(nom, prenom);
					m.messageAjout();
					tableau.ajouterMembreEquipage(m);
					break;

				case 2:

					m = new PNC(nom, prenom);
					m.messageAjout();
					tableau.ajouterMembreEquipage(m);
					break;

				default:

					menuAdministrateur(true);
					break;

				}

				menuAdministrateur(true);
				break;
			case 2:

				int choixAvion;
				String numVol = "";
				String site = "";
				String nomAeroport = "";
				String codeIATA = "";

				System.out.println("Numéro du vol :");
				numVol = sc.next();
				
				/** V2 choix aeroport **/
				
				System.out.println("\nListe des aeroports : ");
				System.out.println(tableau.getListeAeroports());

				System.out.println("\nNom du site de départ du vol :");
				site = sc.next();

				System.out.println("\nVoici la liste des aéroports existants :");
				GestionAeroport.main(true);

				System.out.println("\nMerci de saisir en toute lettres en respectant la case précédente,\n"
						+ "Le nom de l'aéroport : ");
				nomAeroport = sc.next();

				System.out.println("\nLe code IATA associé : ");
				codeIATA = sc.next();

				System.out.println("\nVoici la liste des avions existants, "
						+ "\nsi le modèle souhaité n'est pas disponible, saisissez -1"
						+ "\nsinon saisissez l'index du modèle souhaité :");

				tableau.afficherListeAvions();
				choixAvion = sc.nextInt();

				System.out.println("\nSaisir la date de départ\n");

				System.out.print("Jour : ");
				int day = sc.nextInt();
				System.out.print("Mois : ");
				int month = sc.nextInt();
				System.out.print("Année : ");
				int year = sc.nextInt();

				tableau.ajouterVol(
						new Vol(numVol, site, new Aeroport(nomAeroport, codeIATA),
								tableau.getListeAvions().get(choixAvion), new Date(year, month, day)),
						0 /* idAeroport */, 0 /* idTypeAvion */);

				menuAdministrateur(true);
				break;

			case 3:

				String nomTypeAvion = "";
				int minPNC, maxPNC;

				System.out.println("Nom du type d'avion :");
				nomTypeAvion = sc.next();

				System.out.println("\nNombre minimum de personnel naviguant pour ce type d'avion :");
				minPNC = sc.nextInt();

				System.out.println("\nNombre maximum de personnel naviguant pour ce type d'avion :");
				maxPNC = sc.nextInt();

				tableau.ajouterTypeAvion(new TypeAvion(nomTypeAvion, minPNC, maxPNC));

				menuAdministrateur(true);
				break;

			case 4:

				int choixTypeAvion;
				String reference = "";

				System.out.println("Voici la liste des types d'avion existants, "
						+ "si le modèle souhaité n'est pas disponible, saisissez -1"
						+ "sinon saisissez l'index du modèle souhaité :");

				tableau.afficherListeTypesAvion();
				choixTypeAvion = sc.nextInt();

				if (choixTypeAvion != -1) {

					System.out.println("Référence de l'avion :");
					reference = sc.next();

					tableau.ajouterAvion(new Avion(tableau.getListeTypesAvion().get(choixTypeAvion), reference),
							choixTypeAvion);

				} else {

					System.out.println("Merci d'ajouter un nouveau type d'avion d'abord");

				}

				menuAdministrateur(true);
				break;

			case 5:

				GestionAeroport.main(false);
				menuAdministrateur(true);
				break;

			case 6:

				int indexMembre;

				System.out.println("Voici la liste des membres d'équipage existants, "
						+ "si le membre d'équipage à retenir n'est pas disponible, saisissez -1 "
						+ "sinon saisissez l'index du membre d'équipage à retenir :");

				tableau.afficherListeMembreEquipage();

				System.out.print("Votre choix : ");
				indexMembre = sc.nextInt();

				if (indexMembre == -1)
					menuAdministrateur(true);

				tableau.supprimerMembreEquipage(indexMembre);
				menuAdministrateur(true);
				break;

			case 7:

				int indexVol;

				System.out.println("Voici la liste des vols existants, "
						+ "si le vol à conserver n'est pas disponible, saisissez -1 "
						+ "sinon saisissez l'index du vol à conserver :");

				tableau.afficherListeVols();

				System.out.print("Votre choix : ");
				indexVol = sc.nextInt();

				if (indexVol == -1)
					menuAdministrateur(true);

				tableau.supprimerVol(indexVol);

				menuAdministrateur(true);
				break;

			case 8:

				int indexTypeAvion;

				System.out.println("Voici la liste des types d'avions existants, "
						+ "si le type d'avion à conserver n'est pas disponible, saisissez -1 "
						+ "sinon saisissez l'index du type d'avion à conserver :");

				tableau.afficherListeTypesAvion();

				System.out.print("Votre choix : ");
				indexTypeAvion = sc.nextInt();

				if (indexTypeAvion == -1)
					menuAdministrateur(true);

				tableau.supprimerTypeAvion(tableau.getListeTypesAvion().get(indexTypeAvion),
						tableau.rechercherTypeAvion(tableau.getListeTypesAvion().get(indexTypeAvion).getNom()));

				menuAdministrateur(true);
				break;

			case 9:

				int indexAvion;

				System.out.println("Voici la liste des avions existants, "
						+ "si l'avion à conserver n'est pas disponible, saisissez -1 "
						+ "sinon saisissez l'index de l'avion à conserver :");

				tableau.afficherListeAvions();

				System.out.print("Votre choix : ");
				indexAvion = sc.nextInt();

				if (indexAvion == -1)
					menuAdministrateur(true);

				tableau.supprimerAvion(tableau.getListeAvions().get(indexAvion),
						tableau.rechercherAvion(tableau.getListeAvions().get(indexAvion).getReference()));

				menuAdministrateur(true);
				break;

			case 10:

				int indexMembreAQualifier, indexTypeAvionSelectionne;

				System.out.println("Liste des membres qualifiables (saisir l'index à conserver "
						+ "ou -1 pour revenir en arrière) :");

				tableau.afficherListeMembreEquipage();

				System.out.print("Votre choix : ");
				indexMembreAQualifier = sc.nextInt();
				
				if (indexMembreAQualifier == -1)
					menuAdministrateur(true);

				System.out.println("Liste des types d'avions à qualifier pour un membre (saisir l'index "
						+ "à conserver ou -1 pour revenir en arrière) :");

				tableau.afficherListeTypesAvion();

				System.out.print("Votre choix : ");
				indexTypeAvionSelectionne = sc.nextInt();
				
				if (indexTypeAvionSelectionne <= -1)
					menuAdministrateur(true);
				
				if(indexTypeAvionSelectionne > tableau.getListeMembresEquipage().size()-1){
					System.out.println("Saisie invalide");
					menuAdministrateur(true);
				}
					
				tableau.qualifierMembreEquipage(indexMembreAQualifier, indexTypeAvionSelectionne);

				menuAdministrateur(true);
				break;

			case 11:

				connexionValide = false;
				afficherMenuPrincipal();
				break;

			default:

				menuAdministrateur(true);
				break;

			}

		} else
			System.out.println("Problème de connexion, merci de vous authentifier à nouveau");

	}

	public static void menuManager(boolean check) throws TableauVolsException, InvariantBroken, ClassNotFoundException,
			SQLException, EquipageException, FileNotFoundException, IOException {
		tableau.update();
		if (check) {

			// tableau.setTypeCompte(new Manager());

			int choix;

			do {

				System.out.println("Menu manager :\n\n" + "1 - Affecter un membre d'equipage a un vol\n"
						+ "2 - Retirer un membre d'equipage a un vol\n" + "3 - Afficher les donnees d'un vol\n\n"
						+ "4 - Se déconnecter\n");

				choix = sc.nextInt();

			} while (choix < 1 || choix > 4);

			switch (choix) {

			case 1:
				int indexMembreAQualifier, indexTypeAvionSelectionne;

				System.out.println("Liste des membres qualifiables (saisir l'index à conserver "
						+ "ou -1 pour revenir en arrière) :");

				tableau.afficherListeMembreEquipage();

				System.out.print("Votre choix : ");
				indexMembreAQualifier = sc.nextInt();
				
				if (indexMembreAQualifier == -1)
					menuManager(true);

				System.out.println("Liste des types d'avions à qualifier pour un membre (saisir l'index "
						+ "à conserver ou -1 pour revenir en arrière) :");

				tableau.afficherListeTypesAvion();

				System.out.print("Votre choix : ");
				indexTypeAvionSelectionne = sc.nextInt();
				
				if (indexTypeAvionSelectionne <= -1)
					menuManager(true);
					
				tableau.qualifierMembreEquipage(indexMembreAQualifier, indexTypeAvionSelectionne);

				menuManager(true);
				break;

			case 2:
				
				System.out.println("Liste des membres qualifiables (saisir l'index à conserver "
						+ "ou -1 pour revenir en arrière) :");

				tableau.afficherListeMembreEquipage();

				System.out.print("Votre choix : ");
				indexMembreAQualifier = sc.nextInt();
				
				if (indexMembreAQualifier == -1)
					menuManager(true);
				
				// Retirer qualif 
				System.out.println(tableau.getListeMembresEquipage().size());
				tableau.getListeMembresEquipage().get(indexMembreAQualifier).delAllQualifs();
				
				menuManager(true);
				break;

			case 3:

				menuManager(true);
				break;

			case 4:

				connexionValide = false;
				afficherMenuPrincipal();
				break;

			default:

				menuManager(true);
				break;

			}

		} else
			System.out.println("Problème de connexion, merci de vous authentifier à nouveau");

	}

	public static void menuPersonnel(boolean check) throws TableauVolsException, InvariantBroken,
			ClassNotFoundException, SQLException, EquipageException, FileNotFoundException, IOException {
		tableau.update();
		if (check) {

			// tableau.setTypeCompte(new Personnel());

			int choix;

			do {

				System.out.println("Menu du personnel :\n\n" + "1 - Afficher mes vols\n\n" + "2 - Se déconnecter\n");

				choix = sc.nextInt();

			} while (choix < 1 || choix > 2);

			switch (choix) {

			case 1:
				
				menuPersonnel(true);
				break;

			case 2:

				connexionValide = false;
				afficherMenuPrincipal();
				break;

			default:

				menuPersonnel(true);
				break;

			}

		} else
			System.out.println("Problème de connexion, merci de vous authentifier à nouveau");

	}

	public static void main(String[] args) throws ClassNotFoundException, InvariantBroken, TableauVolsException,
			SQLException, EquipageException, FileNotFoundException, IOException {

		Database db = new Database();

		// db.deleteContenuTable("Equipage"); db.deleteContenuTable("Aeroport");
		// db.deleteContenuTable("Vol"); db.deleteContenuTable("Avion");
		// db.deleteContenuTable("TypeAvion");
		// db.deleteContenuTable("PersonnelQualifie");
		// db.deleteContenuTable("MembreEquipage");
		// db.deleteContenuTable("Fonction");
		db.creationTablesDB();
		//
		//
		// db.deleteContenuTable("TypeAvion");
		// db.deleteContenuTable("vol");
		// db.deleteTables();

		// db.ajoutFonctionDB();

		// db.afficherTableauService();

		System.out.println("*****************************************************\n"
				+ "*                                                   *\n"
				+ "*               Air France - Bienvenue              *\n"
				+ "*                                                   *\n"
				+ "*****************************************************");

		tableau = new TableauVols("Air France", db);

		/**
		 * 
		 * Important : La fonction update est à rappeler à chaque fois que nous
		 * modifions les données de la base de données (INSERT / DELETE)
		 * 
		 **/

		tableau.update();
		afficherMenuPrincipal();

	}

}
