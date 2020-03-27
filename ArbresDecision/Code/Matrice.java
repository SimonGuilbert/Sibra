import java.util.ArrayList;

public class Matrice {
	protected ArrayList<String> prediction;
	protected ArrayList<String> reel;
	protected ArrayList<String> listeClasses;
	protected ArrayList<ArrayList<Integer>> matrice = new ArrayList<ArrayList<Integer>>();
	
	//Constructeur 
	public Matrice(ArrayList<String> prediction, ArrayList<String> reel, ArrayList<String> classes) {
		this.prediction = prediction;
		this.reel = reel;
		this.listeClasses = classes;
		this.setValeurs();
	}
	
	private ArrayList<Integer> remplissageZero() {
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
		return this.prediction.size(); // la taille de prediction est forcément la même que celle de reel (car même nombre d'exemples)		
	}
	
	public ArrayList<String> getListeClasse(){
		return this.listeClasses;
	}
	
	public ArrayList<ArrayList<Integer>> getMatrice(){
		return this.matrice;
	}
}
