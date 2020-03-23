import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException{
		//Lecture l = new Lecture("Data/golf.csv");
		// PARTIE APPRENTISSAGE
		Lecture l = new Lecture("Data/soybean-app.csv");
		Arbre a = new Arbre(new Noeud("PremierNoeud"),l.data(),l.getChoixValManquantes());
		a.setFils();
		Matrice matApp = new Matrice(a.getPredClasses(),a.listeDerniersElements(),a.listeClasses);
		
		// PARTIE PREDICTION
		l.setNomfichier("Data/soybean-pred.csv"); // On change de fichier
		a.setDonnees(l.data()); // On change les données (nouvelles données = soybean-pred.csv)
		Matrice matPred = new Matrice(a.getPredClasses(),a.listeDerniersElements(),a.listeClasses);
		
		// AFFICHAGE FENETRE
		Fenetre fen = new Fenetre(matApp,matPred,matApp.getListeClasse());
		

	}

}
