import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lecture {
	protected String nomFichier;
	protected boolean valeurManquantes = false; //on considère au début qu'il n'y a pas de valeurs manaquantes dans les données
	
	//Constructeur
	protected Lecture(String unNomFichier) {
		this.nomFichier = unNomFichier;	
	}
	
	protected void setNomfichier(String unNomFichier) {
		this.nomFichier = unNomFichier;
	}
	
	protected ArrayList<ArrayList<String>> data() throws IOException {
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
	
	protected void verifValManquantes(ArrayList<String> liste) {
		for (String valeur : liste) {
			if (valeur.equals("?")) {
				this.valeurManquantes = true;
			}
		}
	}
	
	protected int getChoixValManquantes() {
		if (!this.valeurManquantes) {
			return 0;
		}
		return this.choixBoiteDialogue();
	}
	
	protected int choixBoiteDialogue() {
		BoiteDialogue bd = new BoiteDialogue();	
		return bd.getChoix();
	}	
}
