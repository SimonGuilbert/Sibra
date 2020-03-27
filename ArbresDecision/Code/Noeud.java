import java.util.ArrayList;

public class Noeud{
	private ArrayList<Noeud> fils = new ArrayList<Noeud>();
	private String attributAssocie;
	private String nom;
	
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
