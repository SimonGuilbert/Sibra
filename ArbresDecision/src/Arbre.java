import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.Math;

public class Arbre {
	protected Noeud racine;
	public ArrayList<ArrayList<String>> donnees;
	private ArrayList<String> listeClasses;
	private Hashtable<String,ArrayList<String>> dicAttributs;

	// Constructeur
	protected Arbre(Noeud racine,ArrayList<ArrayList<String>> donnees) {
		this.racine = racine;
		this.donnees = donnees;
		this.listeClasses = setListeClasses();
		this.dicAttributs = setDicAttributs();
		this.racine.setAttribut(meilleurGain());		
	}
	
	// ========================================================================================
	// Détection des différentes classes et attributs dans le sous-ensemble this.donnees
	// ========================================================================================
	
	// Création d'une liste qui répertorie l'ensemble des classes présentes dans le sous-ensemble this.donnees
	private ArrayList<String> setListeClasses(){
			ArrayList<String> res = new ArrayList<String>();
			for (ArrayList<String> objet : this.donnees) {
				if (!res.contains(dernierElement(objet))){
					res.add(dernierElement(objet));	
				}	
			}
			res.remove(0); // Supression de la 1ere ligne (intitulé de la colonne)
			return res;
		}
	
	// Dictionnaire ayant pour clé un attribut et pour valeur une liste des valeurs possibles pour cet attribut
	private Hashtable<String,ArrayList<String>> setDicAttributs(){
			Hashtable<String,ArrayList<String>> dic = new Hashtable<String,ArrayList<String>>();
			for (int i=0;i<((this.donnees.get(0).size())-1);i++) {
				dic.put(this.donnees.get(0).get(i), new ArrayList<String>());
				for (int j=1;j<this.donnees.size();j++) {
					if (!dic.get(this.donnees.get(0).get(i)).contains(this.donnees.get(j).get(i)) && !this.donnees.get(j).get(i).equals("?")){ //&& !this.donnees.get(j).get(i).equals("?")
						dic.get(this.donnees.get(0).get(i)).add(this.donnees.get(j).get(i));
					}
				}		
			}
			return dic;
		}
	
// ========================================================================================
// Méthodes de création des noeuds fils
// ========================================================================================	
	private ArrayList<ArrayList<String>> sousEnsemble(String valProchainNoeud) {
		int rangSelf = this.donnees.get(0).indexOf(this.racine.getAttribut());
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> objet : this.donnees){
			if (objet.get(rangSelf).equals(valProchainNoeud) || objet==(this.donnees.get(0))){
				ArrayList<String> resTemp = new ArrayList<String>();
				for (int k=0;k<objet.size();k++) {
					if (k != rangSelf) {
						resTemp.add(objet.get(k));
					}
				}
				res.add(resTemp);	
			}
		}
		return res;	
	}
	
	private boolean classesIdentiques(){
		for (ArrayList<String> objet : this.donnees) {
			if (this.donnees.get(0).size() == 1 && this.donnees.get(0).get(0).substring(0, 5).equals("class")) { //Fin du programme : quand il ne reste plus que la colonne class
				return true;
			}
			else if (!this.dernierElement(objet).equals(this.dernierElement(this.donnees.get(1))) && !objet.equals(this.donnees.get(0))){
				return false;
			}
		}
		return true;
	}
	
	private String classeMajoritaire() {
		Hashtable<String,Integer> effectifs = new Hashtable<String,Integer>();
		for (ArrayList<String> objet : this.donnees) {
			if (!objet.equals(this.donnees.get(0))) {
				String valeur = this.dernierElement(objet);
				if (effectifs.containsKey(valeur)) {
					effectifs.put(valeur, effectifs.get(valeur)+1);
				} else {
					effectifs.put(valeur,1);
				}
			}		
		}
		return this.calculMaximum(effectifs);
	}
	
	private String calculMaximum(Hashtable<String,Integer> dic) {
		String maxCle=null;
		Integer maxValeur = 0;
		for(String cle : dic.keySet()) {
			if (dic.get(cle) > maxValeur) {
				maxValeur = dic.get(cle);
				maxCle = cle;
			}
		}
		return maxCle;	
	}
	
	protected void setFils() {
		if (this.classesIdentiques()) {
			//System.out.println("FEUILLE,"+this.dernierElement(this.donnees.get(1)));
			this.racine.setAttribut(classeMajoritaire());
		} else {
			for (String valeur : this.dicAttributs.get(this.racine.getAttribut())) {
				//System.out.println(this.racine.getAttribut());
				//System.out.println("NOEUD,"+valeur);
				Noeud n = new Noeud(valeur);
				this.racine.ajoutFils(n);
				(new Arbre(n,sousEnsemble(valeur))).setFils();
			}	
		}
	}
	
// ========================================================================================
// Calcul du meilleur gain
// ========================================================================================
		public String meilleurGain() {
			double meilleurVal = -1;
			String meilleurAttr = "Aucun";
			double total = gainTotal();
			for (String attribut : this.dicAttributs.keySet()) {
				double gainTemp = total-E(repartition(attribut));
				if (gainTemp>meilleurVal) {
					meilleurVal = gainTemp;
					meilleurAttr = attribut;
				}
			}
			return meilleurAttr;
		}
		
		private ArrayList<Double> repartition(String attr) {
			int rangAttribut = this.donnees.get(0).indexOf(attr);
			ArrayList<Double> res = new ArrayList<Double>();
			for (String valeur : this.dicAttributs.get(attr)) {
				ArrayList<Integer> valGain = new ArrayList<Integer>();
				double nbTotal = 0;
					for (String classe : this.listeClasses) {
						int nbValeurs = 0;
						for (ArrayList<String> objet : this.donnees) {
							if (objet.get(rangAttribut).equals(valeur) && dernierElement(objet).equals(classe)){
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
		
		private String dernierElement(ArrayList<String> objet) {
			return objet.get(objet.size()-1);
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
				for (ArrayList<String> objet : this.donnees) {
					if (dernierElement(objet).equals(classe)){
						nbValeurs += 1;
					}
				}
				res.add(nbValeurs);
			}
			return I(res);
		}
}
