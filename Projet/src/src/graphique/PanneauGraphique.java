package src.graphique;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;

import src.administration.database.Database;

public class PanneauGraphique extends JFrame {

	private Database db;
	//result = db.selectAll();
	
    public PanneauGraphique() {
        
    	//super();
 
        setTitle("Panneau d'affichage des données de vol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        
         Object[][] donnees = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };
         
        
        //Object[][] donnees = {};
         
         try {
			db = new Database();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        ArrayList<Object> result = new ArrayList<Object>();
        result = db.getDonneesTableauService();
        int j = 0;
        
        // Insertion des donnees dans le tableau
        for(int i = 0; i < result.size(); i++){
        	
        	int colonne = i;
        	
        	if(colonne > 4){
        		colonne -= 5*(colonne/5);
        	}
        	
        	if(i > 4 && i%5 == 0){
        		j++;
        	}
        	
        	donnees[j][colonne] = result.get(i);
        	
        }  	
        	
        String[] entetes = {"NumVol", "Site de départ", "Date de départ", "idAeroport", "idAvion"};
 
        JTable tableau = new JTable(donnees, entetes);
 
        getContentPane().add(tableau.getTableHeader(), BorderLayout.NORTH);
        getContentPane().add(tableau, BorderLayout.CENTER);
 
        pack();
    }
 
    public static void main(String[] args) {
        new PanneauGraphique().setVisible(true);
    }
	
}
