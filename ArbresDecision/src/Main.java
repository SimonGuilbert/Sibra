import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		Lecture l = new Lecture("Data/golf.csv");
		//Lecture l = new Lecture("Data/soybean-app.csv");
		Noeud n = new Noeud();
		Arbre a = new Arbre(n,l.data());
		
		
	}

}
