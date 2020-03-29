import java.util.ArrayList;

public class Matrice {
	protected ArrayList<String> prediction; // Liste des classes prédites pour chaque objet retournée par Arbre.getPredClasses()
	protected ArrayList<String> reel; // Liste des classes réelles pour chaque objet retournée par Arbre.listeDerniersElements()
	protected ArrayList<String> listeClasses; // Liste des classes : même attribut que Arbre.listeClasses
	protected ArrayList<ArrayList<Integer>> matrice = new ArrayList<ArrayList<Integer>>();
	
	//Constructeur 
	public Matrice(ArrayList<String> prediction, ArrayList<String> reel, ArrayList<String> classes) {
		this.prediction = prediction;
		this.reel = reel;
		this.listeClasses = classes;
		this.setValeurs(); // Rempli la matrice
	}
	
	private ArrayList<Integer> remplissageZero() {
		// Retourne une liste remplie de 0 de la même taille que listeClasses
		ArrayList<Integer> liste = new ArrayList<Integer>();
		for (int i=0;i<this.listeClasses.size();i++) {
			liste.add(0);
		}
		return liste;
	}
			
	private void setValeurs(){
		for (String classe : this.listeClasses) {
			ArrayList<Integer> res = this.remplissageZero();
			for (int i=0;i<this.getLongueur();i++) {
				if (this.reel.get(i).equals(classe)) {
					res.set(this.listeClasses.indexOf(this.prediction.get(i)), res.get(this.listeClasses.indexOf(this.prediction.get(i)))+1); // Equivalent de l[x] = l[x]+1
				}
			}
			this.matrice.add(res);
		}
	}
	
	public int getLongueur() {
		// Renvoie le nombre d'exemples du fichier de données
		return this.prediction.size(); // la taille de prediction est forcément la même que celle de reel (car même nombre d'exemples)		
	}
	
	public ArrayList<String> getListeClasse(){
		return this.listeClasses;
	}
	
	public ArrayList<ArrayList<Integer>> getMatrice(){
		// Renvoie la matrice remplie par this.setValeurs()
		return this.matrice;
	}
}
