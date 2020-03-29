import java.util.ArrayList;

public class Noeud{
	private ArrayList<Noeud> fils = new ArrayList<Noeud>(); // Liste des noeuds fils
	private String attributAssocie; // Attribut
	private String nom; // Valeur d'un attribut
	
	// Constructeur
	public Noeud(String unNom) {
		this.nom = unNom;
	}
	
	public String getAttribut() {
		return this.attributAssocie;
	}
	
	public void setNom(String unNom) {
		this.nom = unNom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setAttribut(String unAttribut) {
		this.attributAssocie = unAttribut;
	}
	
	public ArrayList<Noeud> getFils(){
		return this.fils;
	}
	
	public void ajoutFils(Noeud unFils) {
		this.fils.add(unFils);
	}
	
	public boolean estFeuille() {
		return this.getFils().isEmpty();
	}
}
