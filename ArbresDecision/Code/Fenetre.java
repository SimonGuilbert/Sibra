import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class Fenetre extends JFrame implements ActionListener{
	private Arbre arbre;
	private Matrice matriceApp; // Matrice d'apprentissage
	private Matrice matricePred; // Matrice de prédiction
	private ArrayList<JLabel> listeJLabelsApp; // Liste alimentée avec la méthode creationJLabels()
	private ArrayList<JLabel> listeJLabelsPred; // Liste alimentée avec la méthode creationJLabels()
	private ArrayList<String> listeClasses; // Liste où chaque classe apparait exactement une fois
	
	public Fenetre(Arbre arb,Matrice mat1,Matrice mat2,ArrayList<String> listeC){
		
		// Format fenêtre
		this.setTitle("Matrices de confusion - Traitement terminé avec succès");
		this.setSize(1220, 480); // En pixels
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Au milieu de l'écran
		//this.setResizable(false); // Empeche la redimension de la fenetre
		
		// Constructeur
		this.arbre = arb;
		this.matriceApp = mat1;
		this.matricePred = mat2;
		this.listeClasses = listeC;
		this.listeJLabelsApp = creationJLabels(matriceApp); // Crée un JLabel pour chaque ligne de la matrice
		this.listeJLabelsPred = creationJLabels(matricePred);
		
		// JPanels gauche et droit
		JPanel gauche = new JPanel();
		gauche.setLayout(new BoxLayout(gauche, BoxLayout.Y_AXIS)); //Pour que les lignes soient organisées verticalement
		gauche.setBackground(Color.DARK_GRAY);
		JPanel droit = new JPanel();
		droit.setLayout(new BoxLayout(droit, BoxLayout.Y_AXIS)); //Pour que les lignes soient organisées verticalement
		droit.setBackground(Color.DARK_GRAY);
		
		// Titre Apprentissage
	    JLabel labApp = new JLabel("Apprentissage");
	    JPanel panTitreApp = new JPanel();
	    panTitreApp.add(labApp);
	    labApp.setForeground(Color.WHITE);
	    labApp.setFont(new Font("Tahoma", Font.BOLD, 20));
	    panTitreApp.setBackground(Color.DARK_GRAY);
	    gauche.add(panTitreApp);
	    //panTitreApp.setAlignmentX(SwingConstants.LEFT);
	    
	    // Titre Prédiction
	    JLabel labPred = new JLabel("Prédiction");
	    JPanel panTitrePred = new JPanel();
	    panTitrePred.add(labPred);
	    labPred.setForeground(Color.WHITE);
	    labPred.setFont(new Font("Tahoma", Font.BOLD, 20));
	    panTitrePred.setBackground(Color.DARK_GRAY);
	    droit.add(panTitrePred);
		
		// Matrice Apprentissage
		JPanel panMatApp = new JPanel();
		panMatApp.setLayout(new BoxLayout(panMatApp, BoxLayout.Y_AXIS)); //Pour que les lignes soient organisées verticalement
		for (JLabel label : this.listeJLabelsApp) {
			label.setForeground(Color.WHITE);
			panMatApp.add(label); // Ajout des lignes de la matrice 
		}
		panMatApp.setBackground(Color.DARK_GRAY);
		gauche.add(panMatApp);
		//panMatApp.setAlignmentX(SwingConstants.LEFT);
		panMatApp.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Bordure autour de la matrice
		
		//Matrice Prédiction
		JPanel panMatPred = new JPanel();
		panMatPred.setLayout(new BoxLayout(panMatPred, BoxLayout.Y_AXIS)); //Pour que les lignes soient organisées verticalement
		for (JLabel label : this.listeJLabelsPred) {
			label.setForeground(Color.WHITE);
			panMatPred.add(label); // Ajout des lignes de la matrice
		}
		panMatPred.setBackground(Color.DARK_GRAY);
		droit.add(panMatPred);
		panMatPred.setAlignmentX(SwingConstants.RIGHT); // Alignement
		panMatPred.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Bordure autour de la matrice
		
		// Pourcentage Apprentissage
		JLabel pourcApp = new JLabel(this.calculPourc(matriceApp));
		pourcApp.setBackground(Color.DARK_GRAY);
		pourcApp.setForeground(Color.WHITE);
		pourcApp.setFont(new Font("Aucun",Font.BOLD,25));
		pourcApp.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Bordure autour du pourcentage
		JPanel pan2 = new JPanel();
		pan2.add(pourcApp);
		pan2.setBackground(Color.DARK_GRAY);
		//pan2.setAlignmentX(SwingConstants.LEFT);
		gauche.add(pan2);

		// Pourcentage Prédiction
		JLabel pourcPred = new JLabel(this.calculPourc(matricePred));
		pourcPred.setBackground(Color.DARK_GRAY);
		pourcPred.setForeground(Color.WHITE);
		pourcPred.setFont(new Font("Aucun",Font.BOLD,25));
		pourcPred.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Bordure autour du pourcentage
		JPanel pan3 = new JPanel();
		pan3.add(pourcPred);
		pan3.setBackground(Color.DARK_GRAY);
		droit.add(pan3);
		
		// Bouton1
	    JPanel boutons = new JPanel();
	    JButton bouton1 = new JButton("Vérifier la structure de l'arbre (parcours en profondeur)");
	    bouton1.addActionListener(this); // Ajout d'un écouteur pour le 1er bouton
	    boutons.add(bouton1);
	    boutons.setBackground(Color.DARK_GRAY);
	    
	    // Bouton Non merci
	    JButton bouton2 = new JButton("Non merci");
	    bouton2.addActionListener(new ActionListener(){ // Ajout d'un écouteur pour le 2eme bouton
	      public void actionPerformed(ActionEvent event){ // Quand on appuie sur le deuxieme bouton			
	    	  System.exit(0); // Arret du programme
	      }
	    });
	    boutons.add(bouton2);
		
	    this.getContentPane().add(boutons, BorderLayout.SOUTH); // Boutons en bas de la fenetre
	    this.getContentPane().add(gauche, BorderLayout.WEST); // Matrcie d'apprentissage à gauche
	    this.getContentPane().add(droit, BorderLayout.EAST); // Matrice de prédiction à droite
	    this.getContentPane().setBackground(Color.DARK_GRAY);
		this.setVisible(true); // On rend la fenetre visible
	}
	
	public void actionPerformed(ActionEvent arg0) { // Quand on appuie sur le premier bouton  
		arbre.affStructure(); // Exécution de la méthode affStructure() de la classe Arbre
		System.exit(0); // Arret du programme
	}
	
	private ArrayList<JLabel> creationJLabels(Matrice mat) {
		// Méthode qui crée un JLabel pour chaque ligne de la matrice entrée en paramètre
		int i = 0;
		ArrayList<JLabel> res = new ArrayList<JLabel>();
		for (ArrayList<Integer> ligne : mat.getMatrice()) {
			String resTemp = " ";
			for (int valeur : ligne) {
				resTemp += valeur;
				if (valeur > 9) {
					resTemp += "  ";
				} else {
					resTemp += "   ";
				}
			}
			resTemp += "  "+this.listeClasses.get(i)+"  ";
			i += 1;
			JLabel t = new JLabel(resTemp);
			t.setHorizontalAlignment(SwingConstants.LEFT); // Alignement à gauche
			res.add(t);
		}
		return res;
	}
	
	private String calculPourc(Matrice mat) {
		// Calcul du pourcentage : somme de la diagonale de la matrice divisée par le nombre d'objets dans le fichier de données
		int compt = 0;
		double res = 0;
		for (ArrayList<Integer> ligne: mat.getMatrice()) {
			for (int i=0;i<ligne.size();i++) {
				if (i==compt) {
					res += ligne.get(i);
				}
			}
			compt++;
		}
		res = (int)((res/mat.getLongueur())*100*100)/100.; // Arrondi 2 chiffres après la virgule
		return " "+res+"% correct ";
	}
}
