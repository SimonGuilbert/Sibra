import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.lang.Math;

public class Arbre {
	protected Noeud racine;
	protected ArrayList<ArrayList<String>> donnees = new ArrayList<ArrayList<String>>();
	protected String choixArbre;
	protected int choixValManquantes;
	protected ArrayList<String> listeClasses;
	private Hashtable<String,ArrayList<String>> dicAttributs;

	// Constructeur première création de l'arbre
	protected Arbre(Noeud racine,ArrayList<ArrayList<String>> donnees,String choixArb,int choixVal) {
		this.racine = racine;
		this.donnees = donnees;
		this.choixArbre = choixArb;
		this.choixValManquantes = choixVal;
		if (this.choixValManquantes == 3) {
			this.changeAttributMajoritaire();
		}
		this.listeClasses = setListeClasses();
		this.dicAttributs = setDicAttributs();
		if (this.choixArbre.equals("id3")) { // Le choix de l'utilisateur dans la premiere boite de dialogue est Id3
			this.racine.setAttribut(meilleurGain());
		} else { // Le choix de l'utilisateur dans la premiere boite de dialogue est C4.5
			this.racine.setAttribut(meilleurGainC4_5());
		}
			
	}

	// Constructeur pour la lecture de l'arbre
	protected Arbre(Noeud racine) {
		this.racine = racine;
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
					if (!dic.get(this.donnees.get(0).get(i)).contains(this.donnees.get(j).get(i))){ //&& !this.donnees.get(j).get(i).equals("?")
						if (this.choixValManquantes == 1 || !this.donnees.get(j).get(i).equals("?")) {
							dic.get(this.donnees.get(0).get(i)).add(this.donnees.get(j).get(i));
						}
					}
				}		
			}
			return dic;
		}
	
	protected void setDonnees(ArrayList<ArrayList<String>> newDonnees) {
		this.donnees = newDonnees;
	}
	
// ========================================================================================
// Méthodes de remplacement des valeurs manquantes
// ========================================================================================	
	private void changeAttributMajoritaire() {
		for (ArrayList<String> objet : this.donnees) {
			for (int i=0;i<this.donnees.get(0).size()-1;i++) {
				if (objet.get(i).equals("?")) {
					objet.set(i, getAttributMajoritaire(i));
				}
			}
		}
	}
	
	private String getAttributMajoritaire(int rang) {
		Hashtable<String,Integer> effectifs = new Hashtable<String,Integer>();
		for (ArrayList<String> objet : this.donnees) {
			if (!objet.get(rang).equals("?")) {
				if (effectifs.containsKey(objet.get(rang))) {
					effectifs.put(objet.get(rang), effectifs.get(objet.get(rang))+1);
				} else {
					effectifs.put(objet.get(rang),1);
				}
			}		
		}
		return this.calculMaximum(effectifs);
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
		if (this.donnees.get(0).size() == 1 && this.donnees.get(0).get(0).substring(0, 5).equals("class")) { //Fin du programme : quand il ne reste plus que la colonne class
			return true;
		}
		for (ArrayList<String> objet : this.donnees) {
			if (!this.dernierElement(objet).equals(this.dernierElement(this.donnees.get(1))) && !objet.equals(this.donnees.get(0))){
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
			this.racine.setAttribut(classeMajoritaire());
		} else {
			for (String valeur : this.dicAttributs.get(this.racine.getAttribut())) {
				Noeud n = new Noeud(valeur);
				this.racine.ajoutFils(n);
				(new Arbre(n,sousEnsemble(valeur),this.choixArbre,this.choixValManquantes)).setFils();
			}	
		}
	}
	
// ========================================================================================
// Calcul du meilleur gain
// ========================================================================================
	private String meilleurGain() {
		if (this.classesIdentiques()) {
			return this.donnees.get(0).get(0); // S'il n'y a qu'une seule classe le meilleur gain n'a pas d'importance puisque this.racine.attribut est redéfini dans this.setFils()
		}
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
	
	private String meilleurGainC4_5(){
		if (this.classesIdentiques()) {
			return this.donnees.get(0).get(0); // Si c'est une feuille le meilleur gain n'a pas d'importance car this.racine.attribut est redéfini dans this.setFils()
		}
		Hashtable<String,Double> res = new Hashtable<String,Double>();
		double total = gainTotal();
		for (String attribut : this.dicAttributs.keySet()) {
			double gainTemp = total-E(repartition(attribut));
			res.put(attribut, gainTemp);
		}
		return gainAvecIV(res);
	}
	
	private String gainAvecIV(Hashtable<String,Double> dic) {
		double meilleurVal = -1;
		String meilleurAttr = "AucunAucun";
		double moyenne = this.moyenne(dic.values());
		for (String attribut : dic.keySet()) {
			if (dic.get(attribut)>moyenne) {
				dic.put(attribut, dic.get(attribut)/IV(repartitionC4_5(attribut)));
			}
			if (dic.get(attribut)>meilleurVal) {
				meilleurVal = dic.get(attribut);
				meilleurAttr = attribut;
			}
		}
		return meilleurAttr;
	}
	
	private double moyenne(Collection<Double> liste) {
		double somme = 0;
		for (double valeur : liste) {
			somme += valeur;
		}
		return somme/liste.size();
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
	
	private ArrayList<Double> repartitionC4_5(String attr){
		int rangAttribut = this.donnees.get(0).indexOf(attr);
		ArrayList<Double> res = new ArrayList<Double>();
		for (String valeur : this.dicAttributs.get(attr)) {
			double nbTotal = 0;
				for (ArrayList<String> objet : this.donnees) {
					if (objet.get(rangAttribut).equals(valeur)){
						nbTotal += 1;
					}
				}
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
	
	private double IV(ArrayList<Double> valeurs) {
		double res = 0;
		int taille = this.donnees.size()-1;
		for (double valeur : valeurs) {
			res += -(valeur/taille)*(Math.log(valeur/taille)/Math.log(2));
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
				
// ========================================================================================
// Parcours de l'arbre
// ========================================================================================		
	protected String parcoursArbre(ArrayList<String> objet,ArrayList<String> nomsColonnes){
		for (Noeud fils : this.racine.getFils()) {
			if (fils.getNom().equals(objet.get(nomsColonnes.indexOf(this.racine.getAttribut())))) {
				if (fils.estFeuille()) {
					return fils.getAttribut();			
				} else {
					return (new Arbre(fils)).parcoursArbre(objet, nomsColonnes);
				}
			}
		}
		// Dans le cas où la valeur de l'attribut n'a pas été trouvée dans l'arbre, on continue l'algorithme
		// avec un noeud fils du noeud racine pris au hasard
		int nbAleatoire = (int)(Math.random() * (this.racine.getFils().size())); // nombre aléatoire entre 0 et le nombre de fils du noeud racine
		if (this.racine.getFils().get(nbAleatoire).estFeuille()) {
			return this.racine.getFils().get(nbAleatoire).getAttribut();			
		} else {
			return (new Arbre(this.racine.getFils().get(nbAleatoire))).parcoursArbre(objet, nomsColonnes);
		}
	}
	
	protected ArrayList<String> getPredClasses(){
		ArrayList<String> res = new ArrayList<String>();
		for (ArrayList<String> objet : this.donnees) {
			if (!objet.equals(this.donnees.get(0))) {
				res.add(this.parcoursArbre(objet, this.donnees.get(0)));
			}
		}
		return res;
	}		
	
// ========================================================================================
// Fonctions de création de la matrice de confusion
// ========================================================================================
	protected ArrayList<String> listeDerniersElements() {
		ArrayList<String> res = new ArrayList<String>();
		for (ArrayList<String> objet : this.donnees) {
			if (!objet.equals(this.donnees.get(0))) {
				res.add(this.dernierElement(objet));
			}
		}
		return res;
	}	
	
// ========================================================================================
// Affichage de la structure de l'arbre
// ========================================================================================
	protected void affStructure() {                                    
		System.out.println(" _____ _                   _                  ");
		System.out.println("/  ___| |                 | |                 ");
		System.out.println("\\ `--.| |_ _ __ _   _  ___| |_ _   _ _ __ ___ ");
		System.out.println(" `--. \\ __| '__| | | |/ __| __| | | | '__/ _ \\");
		System.out.println("/\\__/ / |_| |  | |_| | (__| |_| |_| | | |  __/");
		System.out.println("\\____/ \\__|_|   \\__,_|\\___|\\__|\\__,_|_|  \\___|");
		System.out.println("");
		System.out.println("ATTRIBUT PERE,VALEUR FILS,ATTRIBUT FILS");
		this.parcoursProfondeur();
	}
	protected void parcoursProfondeur(){
		for (Noeud fils : this.racine.getFils()) {
			if (!fils.estFeuille()) {
				System.out.println(this.racine.getAttribut()+","+fils.getNom()+","+fils.getAttribut());
				(new Arbre(fils)).parcoursProfondeur();
			} else {
				System.out.println(this.racine.getAttribut()+","+fils.getNom()+",CLASSE "+fils.getAttribut());
			}
		}
	}
}




