import java.util.ArrayList;

public class Noeud{
	private String nom = "Aucun";
	private ArrayList<Noeud> fils = new ArrayList<Noeud>();
	
	protected String getNom() {
		return this.nom;
	}
	
	protected void setNom(String unNom) {
		this.nom = unNom;
	}
	
	protected ArrayList<Noeud> getFils(){
		return this.fils;
	}
	
	

}
