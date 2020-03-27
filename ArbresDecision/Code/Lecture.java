import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lecture {
	private String nomFichier;
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
		BufferedReader csvReader = new BufferedReader(new FileReader(this.nomFichier));
		while ((row = csvReader.readLine()) != null) {
		    String [] temp = row.split(",");
		    ArrayList<String> temp2 = new ArrayList<String>();		    
		    Collections.addAll(temp2, temp); // Insère toutes les valeurs de temp dans temp2
		    res.add(temp2);
		    if (!this.valeurManquantes) {
		    	this.verifValManquantes(temp2);
		    }
		}
		csvReader.close();
		return res;
	}	
	
	private void verifValManquantes(ArrayList<String> liste) {
		for (String valeur : liste) {
			if (valeur.equals("?")) {
				this.valeurManquantes = true;
			}
		}
	}
	
	public int getChoixValManquantes() {
		if (!this.valeurManquantes) {
			return 0;
		}
		return this.choixBoiteDialogue();
	}
	
	private int choixBoiteDialogue() {
		BoiteDialogue bd = new BoiteDialogue();	
		return bd.getChoix();
	}	
	
	public String choixArbre() {
		BoiteDialogueArbre bdA = new BoiteDialogueArbre();
		return bdA.getChoix();
	}
}
