import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Arrays;
import java.lang.Math;

public class Arbre {
	private Noeud racine;
	private ArrayList<String []> donnees;
	private ArrayList<String> listeClasses;
	private Hashtable<String,ArrayList<String>> dicAttributs;

	
	// Constructeur
	protected Arbre(Noeud racine,ArrayList<String []> donnees) {
		this.racine = racine;
		this.donnees = donnees;
		this.listeClasses = setListeClasses();
		this.dicAttributs = setDicAttributs();
		this.racine.setNom(meilleurGain());
	}
		
	private ArrayList<String> setListeClasses(){
			ArrayList<String> res = new ArrayList<String>();
			for (String [] objet : this.donnees) {
				if (!res.contains(dernierElement(objet))){
					res.add(dernierElement(objet));	
				}	
			}
			res.remove(0); // Supression de la 1ere ligne (intitulé de la colonne)
			return res;
		}
		
	private Hashtable<String,ArrayList<String>> setDicAttributs(){
			Hashtable<String,ArrayList<String>> dic = new Hashtable<String,ArrayList<String>>();
			for (int i=0;i<((this.donnees.get(0).length)-1);i++) {
				dic.put(this.donnees.get(0)[i], new ArrayList<String>());
				for (int j=1;j<this.donnees.size();j++) {
					if (!dic.get(this.donnees.get(0)[i]).contains(this.donnees.get(j)[i]) && !this.donnees.get(j)[i].equals("?")){
						dic.get(this.donnees.get(0)[i]).add(this.donnees.get(j)[i]);
					}
				}		
			}
			return dic;
		}
	
	public String meilleurGain() {
		double meilleurVal = 0;
		String meilleurAttr = "Aucun";
		double total = gainTotal();
		for (String attribut : this.dicAttributs.keySet()) {
			double gainTemp = total-E(repartition(attribut));
			System.out.println(gainTemp);
			if (gainTemp>meilleurVal) {
				meilleurVal = gainTemp;
				meilleurAttr = attribut;
			}
		}
		return meilleurAttr;
	}
	
	private ArrayList<Double> repartition(String attr) {
		int rangAttribut = Arrays.asList(this.donnees.get(0)).indexOf(attr);
		ArrayList<Double> res = new ArrayList<Double>();
		for (String valeur : this.dicAttributs.get(attr)) {
			ArrayList<Integer> valGain = new ArrayList<Integer>();
			double nbTotal = 0;
				for (String classe : this.listeClasses) {
					int nbValeurs = 0;
					for (String [] objet : this.donnees) {
						if (objet[rangAttribut].equals(valeur) && dernierElement(objet).equals(classe)){
							nbValeurs += 1;
							nbTotal += 1;
						}
					}
					valGain.add(nbValeurs);		
				}
				res.add(I(valGain));
				res.add(nbTotal);		
		}
		return res;
	}
	
	private String dernierElement(String [] objet) {
		return objet[objet.length-1];
	}
	
	private double I(ArrayList<Integer> listeValeurs){
		int somme = calculSomme(listeValeurs);
		double res = 0;
		for (double valeur : listeValeurs) {
			if (valeur != 0) {
				res += -(valeur/somme)*(Math.log(valeur/somme)/Math.log(2));
			}
		}
		return res;
		
	}
	
	private int calculSomme(ArrayList<Integer> liste) {
		int res = 0;
		for (int nombre : liste) {
			res += nombre;
		}
		return res;
	}
	
	private double E(ArrayList<Double> valeurs) {
		double res = 0;
		int i = 0;
		while (i != valeurs.size()) {
			res += valeurs.get(i)*(valeurs.get(i+1)/(this.donnees.size()-1));
			i+=2;
		}
		return res;
	}
	
	private double gainTotal() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String classe : this.listeClasses) {
			int nbValeurs = 0;
			for (String [] objet : this.donnees) {
				if (dernierElement(objet).equals(classe)){
					nbValeurs += 1;
				}
			}
			res.add(nbValeurs);
		}
		return I(res);
	}
	
}
