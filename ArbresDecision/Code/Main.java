import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		
		// PARTIE APPRENTISSAGE
		Lecture l = new Lecture("Data/soybean-app.csv");
		Arbre a = new Arbre(new Noeud("NoeudRacine"),l.data(),l.choixArbre(),l.getChoixValManquantes()); // Création du noeud racine
		a.setFils(); // Création du reste de l'arbre
		Matrice matApp = new Matrice(a.getPredClasses(),a.listeDerniersElements(),a.listeClasses); //Transformation de 2 listes en matrice
		
		// PARTIE PREDICTION
		l.setNomfichier("Data/soybean-pred.csv"); // On change de fichier
		a.setDonnees(l.data()); // On change les données (nouvelles données = soybean-pred.csv)
		Matrice matPred = new Matrice(a.getPredClasses(),a.listeDerniersElements(),a.listeClasses); //Transformation de 2 listes en matrice
		
		// AFFICHAGE FENETRE
		Fenetre fen = new Fenetre(a,matApp,matPred,matApp.getListeClasse());
	
	}

}
