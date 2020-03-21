import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		//Lecture lApp = new Lecture("Data/golf.csv");
		// PARTIE APPRENTISSAGE
		Lecture lApp = new Lecture("Data/soybean-app.csv");
		Arbre a = new Arbre(new Noeud("PremierNoeud"),lApp.data());
		a.setFils();
		Matrice matApp = new Matrice(a.getPredClasses(),a.listeDerniersElements(),a.listeClasses);
		matApp.afficher();
		
		// PARTIE PREDICTION
		Lecture lPred = new Lecture("Data/soybean-pred.csv");
		a.setDonnees(lPred.data()); // On change les données (nouvelles données = soybean-pred.csv)
		Matrice matPred = new Matrice(a.getPredClasses(),a.listeDerniersElements(),a.listeClasses);
		matPred.afficher();
	}

}
