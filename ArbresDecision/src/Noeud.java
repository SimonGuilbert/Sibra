import java.util.ArrayList;

public class Noeud{
	private ArrayList<Noeud> fils = new ArrayList<Noeud>();
	private String attributAssocie;
	private String nom;
	
	// Constructeur
	protected Noeud(String unNom) {
		this.nom = unNom;
	}
	
	protected String getAttribut() {
		return this.attributAssocie;
	}
	
	protected void setNom(String unNom) {
		this.nom = unNom;
	}
	
	protected String getNom() {
		return this.nom;
	}
	
	protected void setAttribut(String unAttribut) {
		this.attributAssocie = unAttribut;
	}
	
	protected ArrayList<Noeud> getFils(){
		return this.fils;
	}
	
	protected void ajoutFils(Noeud unFils) {
		this.fils.add(unFils);
	}
}
