import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lecture {
	private String nomFichier; // Nom du fichier csv
	private boolean valeurManquantes = false; //on considère au début qu'il n'y a pas de valeurs manaquantes dans les données
	
	//Constructeur
	public Lecture(String unNomFichier) {
		this.nomFichier = unNomFichier;	
	}
	
	public void setNomfichier(String unNomFichier) {
		this.nomFichier = unNomFichier;
	}
	
	public ArrayList<ArrayList<String>> data() throws IOException {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		String row = "";
		BufferedReader csvReader = new BufferedReader(new FileReader(this.nomFichier)); // Lecture du fichier
		while ((row = csvReader.readLine()) != null) { // Tant que la ligne n'est pas vide
		    String [] temp = row.split(","); // Le tableau temp contient chaque valeur de la ligne séparée par une virgule
		    ArrayList<String> temp2 = new ArrayList<String>();		    
		    Collections.addAll(temp2, temp); // Insère toutes les valeurs de temp dans temp2
		    res.add(temp2); // Ajout du tableau contenant les données d'une ligne dans le tableau contenant les listes des éléments de la ligne
		    if (!this.valeurManquantes) { // Si aucune valeur manquante n'a encore été détectée
		    	this.verifValManquantes(temp2); // On vérifie qu'aucune valeur de la liste temp2 est manquante
		    }
		}
		csvReader.close();
		return res;
	}	
	
	private void verifValManquantes(ArrayList<String> liste) {
		for (String valeur : liste) {
			if (valeur.equals("?")) {
				this.valeurManquantes = true; // On change la valeur de this.valeurManquantes si une valeur manquante est repérée dans la liste passée en paramètre
			}
		}
	}
	
	public int getChoixValManquantes() { 
		// Demande de récupération du choix des valeurs manquantes (1,2 ou 3 ou 0 s'il n'y en a pas)
		if (!this.valeurManquantes) {
			return 0;
		}
		return this.choixBoiteDialogue();
	}
	
	private int choixBoiteDialogue() {
		BoiteDialogue bd = new BoiteDialogue();	// Lancement de la boîte de dialogue si une valeur manquante a été repérée
		return bd.getChoix(); // Retourne le choix : 1,2 ou 3
	}	
	
	public String choixArbre() {
		// 1ere boîte de dialogue pour choisir l'arbre Id3 ou C4.5
		BoiteDialogueArbre bdA = new BoiteDialogueArbre();
		return bdA.getChoix(); // Retourne le choix "id3" ou "c4.5"
	}
}
