import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lecture {
	protected String nomFichier;
	
	//Constructeur
	protected Lecture(String unNomFichier) {
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
		}
		csvReader.close();
		return res;
	}	
}
