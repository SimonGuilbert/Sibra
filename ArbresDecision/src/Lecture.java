import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lecture {
	protected String nomFichier;
	
	//Constructeur
	protected Lecture(String unNomFichier) {
		this.nomFichier = unNomFichier;	
	}
	
	protected ArrayList<String []> data() throws IOException { //List<List<String>>
		//List<List<String>> res = new ArrayList<List<String>>();
		ArrayList<String []> res = new ArrayList<String []>();
		String row = "";
		BufferedReader csvReader = new BufferedReader(new FileReader(this.nomFichier));
		while ((row = csvReader.readLine()) != null) {
		    String [] d = row.split(",");
		    res.add(d);
		}
		csvReader.close();
		return res;
	}
	
}
