import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		//Lecture l = new Lecture("Data/golf.csv");
		Lecture l = new Lecture("Data/soybean-app.csv");
		Arbre a = new Arbre(new Noeud("PremierNoeud"),l.data());
		a.setFils();
		//System.out.println(a.racine.getFils().get(0).getFils().get(0).getFils());
		//System.out.println(a.racine.getFils().get(1).getFils().get(2).getAttribut());
		System.out.println(a.getPredClasses());
	}

}
