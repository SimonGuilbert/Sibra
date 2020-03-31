import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.lang.Math;

public class Arbre {
	protected Noeud racine;
	protected ArrayList<ArrayList<String>> donnees = new ArrayList<ArrayList<String>>(); // Données récupérées par Lecture.data()
	protected String choixArbre; // "id3" ou "c4.5" retourné par Lecture.choixArbre()
	protected int choixValManquantes; // 0,1,2 ou 3 retourné par Lecture.getChoixValManquntes()
	protected ArrayList<String> listeClasses; // Liste où chaque classe apparait exactement une fois
	private Hashtable<String,ArrayList<String>> dicAttributs; // Dictionnaire ayant pour clé un attribut et pour valeur une liste des valeurs possibles de cet attribut
	private ArrayList<Integer> listeValInconnues = new ArrayList<Integer>();

	// Constructeur pour la création de l'arbre
	public Arbre(Noeud racine,ArrayList<ArrayList<String>> donnees,String choixArb,int choixVal) {
		this.racine = racine;
		this.donnees = donnees;
		this.choixArbre = choixArb;
		this.choixValManquantes = choixVal;
		if (this.choixValManquantes == 3) { // Si le choix de traitement des valeurs manquantes est égal à 3
			this.changeAttributMajoritaire(); // On remplace les points d'interrogation par la valeur de l'attribut associé majoritaire
		}
		if (this.choixValManquantes == 4) { // Si le choix de traitement des valeurs manquantes est égal à 4
			this.setValInconnues(); // On liste le nombre de valeurs manquantes par attribut
		}
		this.listeClasses = setListeClasses();
		this.dicAttributs = setDicAttributs();
		if (this.choixArbre.equals("id3")) { // Si le choix de l'utilisateur dans la premiere boite de dialogue est Id3
			this.racine.setAttribut(meilleurGain());
		} else { // Sinon le choix de l'utilisateur dans la premiere boite de dialogue est C4.5
			this.racine.setAttribut(meilleurGainC4_5());
		}
			
	}

	// Constructeur pour la lecture de l'arbre
	private Arbre(Noeud racine) {
		this.racine = racine;
	}
	
// ========================================================================================
// Détection des différentes classes et attributs dans le sous-ensemble this.donnees
// ========================================================================================
	
	// Création d'une liste qui répertorie l'ensemble des classes présentes dans le sous-ensemble this.donnees
	private ArrayList<String> setListeClasses(){
			ArrayList<String> res = new ArrayList<String>();
			for (ArrayList<String> objet : this.donnees) {
				if (!res.contains(dernierElement(objet))){ // Si la valeur dans la colonne de classe n'est pas déjà dans la liste res, on l'ajoute
					res.add(dernierElement(objet));	
				}	
			}
			res.remove(0); // Supression de la 1ere ligne (intitulé de la colonne)
			return res;
		}
	
	// Dictionnaire ayant pour clé un attribut et pour valeur une liste des valeurs possibles pour cet attribut
	private Hashtable<String,ArrayList<String>> setDicAttributs(){
			Hashtable<String,ArrayList<String>> dic = new Hashtable<String,ArrayList<String>>();
			for (int i=0;i<((this.donnees.get(0).size())-1);i++) { // Pour chaque attribut
				dic.put(this.donnees.get(0).get(i), new ArrayList<String>()); // On l'ajoute comme clé dans le dictionnaire et on initie sa valeur associée à une liste vide
				for (int j=1;j<this.donnees.size();j++) { // Pour chaque objet
					if (!dic.get(this.donnees.get(0).get(i)).contains(this.donnees.get(j).get(i))){ // Si la liste associée à la classe en cours de traitement ne contient pas la valeur de la ligne en cours de traitement
						if (this.choixValManquantes == 1 || !this.donnees.get(j).get(i).equals("?")) { // Si cette valeur n'est pas un ? ou si this.choixValManquantes == 1
							dic.get(this.donnees.get(0).get(i)).add(this.donnees.get(j).get(i)); // On ajoute cette valeur à la liste des valeurs de la classe en cours de traitement (dans le dictionnaire)
						}
					}
				}		
			}
			return dic;
		}
	
	public void setDonnees(ArrayList<ArrayList<String>> newDonnees) { // Permet de changer les données (pour l'étape de prédiction)
		this.donnees = newDonnees;
	}
	
// ========================================================================================
// Méthodes de remplacement des valeurs manquantes
// ========================================================================================	
	private void changeAttributMajoritaire() {
		for (ArrayList<String> objet : this.donnees) { // Pour chaque objet
			for (int i=0;i<this.donnees.get(0).size()-1;i++) { // Pour chaque valeur de l'objet
				if (objet.get(i).equals("?")) { // Si cette valeur est égale à ?
					objet.set(i, getAttributMajoritaire(i)); // On la modifie par la valeur majoritaire de l'attribut associé
				}
			}
		}
	}
	
	private String getAttributMajoritaire(int rang) { // Retourne la valeur majoritaire de l'attribut du rang entré en paramètre
		Hashtable<String,Integer> effectifs = new Hashtable<String,Integer>(); // Dictionnaire ayant pour clé une valeur d'attribut et pour valeur le nombre d'apparition de cette valeur d'attribut
		for (ArrayList<String> objet : this.donnees) { // Pour chaque objet
			if (!objet.get(rang).equals("?")) { // Si valeur de l'objet au rang du paramètre est différent d'un point d'interrogation
				if (effectifs.containsKey(objet.get(rang))) { // Si le dictionnaire connait deja cette valeur
					effectifs.put(objet.get(rang), effectifs.get(objet.get(rang))+1); // On l'incrémente de 1
				} else {
					effectifs.put(objet.get(rang),1); // Sinon on crée une nouvelle clé initiée à 1
				}
			}		
		}
		return this.calculMaximum(effectifs);
	}
	
	private void setValInconnues() {
		for (int i=0;i<this.donnees.get(0).size();i++) {
			int resTemp = 0;
			for (ArrayList<String> objet : this.donnees) {
				if (objet.get(i).equals("?")) {
					resTemp++;
				}				
			}
			this.listeValInconnues.add(resTemp);
			
		}
	}
	
// ========================================================================================
// Méthodes de création des noeuds fils
// ========================================================================================	
	private ArrayList<ArrayList<String>> sousEnsemble(String valProchainNoeud) { // Crée un sous-ensemble à partir de la valeur rentrée en paramètre
		int rangSelf = this.donnees.get(0).indexOf(this.racine.getAttribut()); // Conversion du nom de la l'attribut associé au noeud courant en rang (Integer)
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> objet : this.donnees){ // Pour chaque objet de l'ensemble total
			if (objet.get(rangSelf).equals(valProchainNoeud) || objet==(this.donnees.get(0))){ // Si cet objet est le premier (noms des colonnes) ou si la valeur de l'objet au rangSelf est égale à la valeur entrée en paramètre
				ArrayList<String> resTemp = new ArrayList<String>();
				for (int k=0;k<objet.size();k++) {
					if (k != rangSelf) {
						resTemp.add(objet.get(k)); // On ajoute toutes les valeurs de cet objet sauf celle du rangSelf
					}
				}
				res.add(resTemp);	
			}
		}
		return res;	
	}
	
	private boolean classesIdentiques(){ // Permet de savoir si toutes les valeurs de classes d'un sous-ensemble sont identiques
		if (this.donnees.get(0).size() == 1 && this.donnees.get(0).get(0).substring(0, 5).equals("class")) { //Fin du programme : quand il ne reste plus que la colonne class
			return true;
		}
		for (ArrayList<String> objet : this.donnees) { // Pour chaque objet
			if (!this.dernierElement(objet).equals(this.dernierElement(this.donnees.get(1))) && !objet.equals(this.donnees.get(0))){ // Si la valeur de la dernière colonne est différente que la denière valeur du 2eme objet
				return false;
			}
		}
		return true;
	}
	
	private String classeMajoritaire() { // Retoune la classe majoritaire du sous-ensemble
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
	
	private String calculMaximum(Hashtable<String,Integer> dic) { // Retourne la clé de la valeur maximale d'un dictionnaire
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
	
	public void setFils() {
		if (this.classesIdentiques()) { // Si les classes sont toutes identiques on peut stopper la récursivité
			this.racine.setAttribut(classeMajoritaire()); // attributAssocie du noeud racine prnd la valeur de la classe majoritaire du sous-ensemble (qui est dans la grande majorité des cas la classe majoritaire d'une seule classe)
		} else {
			for (String valeur : this.dicAttributs.get(this.racine.getAttribut())) { // Sinon, pour chaque valeur possible de l'attribut
				Noeud n = new Noeud(valeur);
				this.racine.ajoutFils(n); // On ajoute un noeud ayant pour nom cette valeur à la liste des fils du noeud courant
				(new Arbre(n,sousEnsemble(valeur),this.choixArbre,this.choixValManquantes)).setFils(); // Récursivité
			}	
		}
	}
	
// ========================================================================================
// Calcul du meilleur gain
// ========================================================================================
	private String meilleurGain() { // Retourne le nom de l'attribut qui apporte le meilleur gain d'information (uniquement pour l'algorithme id3)
		if (this.classesIdentiques()) {
			return this.donnees.get(0).get(0); // S'il n'y a qu'une seule classe le meilleur gain n'a pas d'importance puisque this.racine.attribut est redéfini dans this.setFils()
		}
		double meilleurVal = -1;
		String meilleurAttr = "Aucun";
		double total = gainTotal();
		for (String attribut : this.dicAttributs.keySet()) { // Pour chaque attribut
			double gainTemp = total-E(repartition(attribut));
			if (this.choixValManquantes == 4) {
				int nb = this.listeValInconnues.get(this.donnees.get(0).indexOf(attribut)); // Nombre de valeurs inconnues
				gainTemp = gainTemp + nb*ratio(nb); // Adaptation du gainTemp aux valeurs manquantes
			}
			if (gainTemp>meilleurVal) {
				meilleurVal = gainTemp;
				meilleurAttr = attribut;
			}
		}
		return meilleurAttr;
	}
	
	private double ratio(int nombre) {
		if (calculSomme(this.listeValInconnues) == 0) {
			return 0;
		}
		return (this.donnees.size()-1-nombre)/calculSomme(this.listeValInconnues);
	}
	
	private String meilleurGainC4_5(){ // Retourne le nom de l'attribut qui apporte le meilleur gain d'information (uniquement pour l'algorithme C4.5)
		if (this.classesIdentiques()) {
			return this.donnees.get(0).get(0); // Si c'est une feuille le meilleur gain n'a pas d'importance car this.racine.attribut est redéfini dans this.setFils()
		}
		Hashtable<String,Double> res = new Hashtable<String,Double>();
		double total = gainTotal();
		for (String attribut : this.dicAttributs.keySet()) {
			double gainTemp = total-E(repartition(attribut));
			res.put(attribut, gainTemp); // Sauvegarde de tous les gains temporairement dans un dictionnaire
		}
		return gainAvecIV(res);
	}
	
	private String gainAvecIV(Hashtable<String,Double> dic) { // Modifie la valeur du gain si necessaire et retourne le nom du meilleur attribut
		double meilleurVal = -1;
		String meilleurAttr = "Aucun";
		double moyenne = this.moyenne(dic.values());
		for (String attribut : dic.keySet()) { // Pour chaque attribut
			if (dic.get(attribut)>moyenne) { // Si son gain d'information est supérieur à la moyenne
				dic.put(attribut, dic.get(attribut)/IV(repartitionC4_5(attribut))); // On le divise par la fonction IV
			}
			if (this.choixValManquantes == 4) {
				int nb = this.listeValInconnues.get(this.donnees.get(0).indexOf(attribut)); // Nombre de valeurs inconnues
				dic.put(attribut, dic.get(attribut)+nb*ratio(nb)); // Adaptation du gainTemp aux valeurs manquantes
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
		for (double valeur : liste) { // Pour quaque gain d'information
			somme += valeur;
		}
		return somme/liste.size();
	}

	private ArrayList<Double> repartition(String attr) { // Divise les données en fonction des classes et d'un attribut entré en paramètre pour utiliser les fonctions E et I
		int rangAttribut = this.donnees.get(0).indexOf(attr); // Transformation du nom de l'attribut en rang (int)
		ArrayList<Double> res = new ArrayList<Double>();
		for (String valeur : this.dicAttributs.get(attr)) { // Pour chaque valeur possible de l'attribut
			ArrayList<Integer> valGain = new ArrayList<Integer>();
			double nbTotal = 0;
				for (String classe : this.listeClasses) { // Pour chaque classe
					int nbValeurs = 0;
					for (ArrayList<String> objet : this.donnees) { // Pour chaque objet
						if (objet.get(rangAttribut).equals(valeur) && dernierElement(objet).equals(classe)){ // Si objet[rangSelf] == valeur et si objet[dernierRang]==classe
							nbValeurs += 1;
							nbTotal += 1;
						}
					}
					valGain.add(nbValeurs);		
				}
				res.add(I(valGain)); // Utlilisation de la fonction I (necessaire pour la fonction E)	
				res.add(nbTotal); // Fréquence d'apparition (necessaire pour la fonction E)	
		}
		return res;
	}
	
	private ArrayList<Double> repartitionC4_5(String attr){ // Deuxieme répartition (seulement pour l'algo C4.5) utile pour la fonction IV
		int rangAttribut = this.donnees.get(0).indexOf(attr); // Transformation du nom de l'attribut en rang (int)
		ArrayList<Double> res = new ArrayList<Double>();
		for (String valeur : this.dicAttributs.get(attr)) { // Pour chaque valeur de l'attribut
			double nbTotal = 0;
				for (ArrayList<String> objet : this.donnees) { // Pour chaque objet
					if (objet.get(rangAttribut).equals(valeur)){ // Si objet[rangSelf] == valeur
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
	
	private double I(ArrayList<Integer> listeValeurs){ // Retourne un nombre utile au calcul de la fonction E
		int somme = calculSomme(listeValeurs);
		double res = 0;
		for (double valeur : listeValeurs) { // Pour chaque valeur ( qui correspond au nombre d'éléments de chaque classe pour un attribut)
			if (valeur != 0) { // Permet d'éviter la division par 0
				res += -(valeur/somme)*(Math.log(valeur/somme)/Math.log(2)); // Fonction I
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
	
	private double IV(ArrayList<Double> valeurs) { // Utilisé uniquement pour l'algo C4.5 ssi la valeur du gain est supérieure à la moyenne
		double res = 0;
		int taille = this.donnees.size()-1;
		for (double valeur : valeurs) {
			res += -(valeur/taille)*(Math.log(valeur/taille)/Math.log(2));
		}
		return res;
	}
	
	private double E(ArrayList<Double> valeurs) { // Utilise le résultat de la fonction I et l'effectif d'un attribut dans les données
		// Renvoie un nombre qui sera utile pour le calcul du gain
		double res = 0;
		int i = 0;
		while (i != valeurs.size()) { 
			res += valeurs.get(i)*(valeurs.get(i+1)/(this.donnees.size()-1)); // Fonction E
			i+=2; // On a déjà utilisé 2 éléments de la liste --> i += 2
		}
		return res;
	}
	
	private double gainTotal() { // Renvoie le résultat de la fonction I sans faire de sélection sur un attribut
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (String classe : this.listeClasses) { // Pour chaque classe
			int nbValeurs = 0;
			for (ArrayList<String> objet : this.donnees) { // Pour chaque classe
				if (dernierElement(objet).equals(classe)){ // Si objet[dernierRang] == classe
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
	private String parcoursArbre(ArrayList<String> objet,ArrayList<String> nomsColonnes){ // Parcours l'arbre en profondeur en se guidant des attributs et de leurs valeurs jusqu'à arriver sur une feuille
		for (Noeud fils : this.racine.getFils()) {
			if (fils.getNom().equals(objet.get(nomsColonnes.indexOf(this.racine.getAttribut())))) {
				if (fils.estFeuille()) {
					return fils.getAttribut();			
				} else {
					return (new Arbre(fils)).parcoursArbre(objet, nomsColonnes); // Récursivité
				}
			}
		}
		// Dans le cas où la valeur de l'attribut n'a pas été trouvée dans l'arbre, on continue l'algorithme avec un noeud fils du noeud racine pris au hasard
		int nbAleatoire = (int)(Math.random() * (this.racine.getFils().size())); // nombre aléatoire compris entre 0 et le nombre de fils du noeud racine
		if (this.racine.getFils().get(nbAleatoire).estFeuille()) {
			return this.racine.getFils().get(nbAleatoire).getAttribut();			
		} else {
			return (new Arbre(this.racine.getFils().get(nbAleatoire))).parcoursArbre(objet, nomsColonnes); // Récursivité
		}
	}
	
	public ArrayList<String> getPredClasses(){ // Retourne une liste de la taille du nombre de lignes dans le fichier de données contenant la liste prédite pour chaque objet
		ArrayList<String> res = new ArrayList<String>();
		for (ArrayList<String> objet : this.donnees) { // Pour chaque obejt
			if (!objet.equals(this.donnees.get(0))) { // Si ce n'est pas le premier objet (en-tête du fichier de données)
				res.add(this.parcoursArbre(objet, this.donnees.get(0))); // On calcule la classe prédite avec la méthode parcoursArbre()
			}
		}
		return res;
	}		
	
// ========================================================================================
// Fonctions de création de la matrice de confusion
// ========================================================================================
	public ArrayList<String> listeDerniersElements() { // Retourne une liste contenant les derniers éléments (=classes) de chaque objet
		ArrayList<String> res = new ArrayList<String>();
		for (ArrayList<String> objet : this.donnees) { // Pour chaque objet
			if (!objet.equals(this.donnees.get(0))) { // Si ce n'est pas la ligne des en-têtes
				res.add(this.dernierElement(objet)); // On ajoute la dernière valeur à la liste
			}
		}
		return res;
	}	
	
// ========================================================================================
// Affichage de la structure de l'arbre
// ========================================================================================
	public void affStructure() {                                    
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
	
	private void parcoursProfondeur(){
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
