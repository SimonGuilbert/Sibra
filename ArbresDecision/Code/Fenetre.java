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
	private Matrice matriceApp;
	private Matrice matricePred;
	private ArrayList<JLabel> listeJLabelsApp;
	private ArrayList<JLabel> listeJLabelsPred;
	private ArrayList<String> listeClasses;
	
	public Fenetre(Arbre arb,Matrice mat1,Matrice mat2,ArrayList<String> listeC){
		
		// Format fenêtre
		this.setTitle("Matrices de confusion - Traitement terminé avec succès");
		this.setSize(1220, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Au milieu de l'écran
		//this.setResizable(false); // Empeche la redimension de la fenetre
		
		// Constructeur
		this.arbre = arb;
		this.matriceApp = mat1;
		this.matricePred = mat2;
		this.listeClasses = listeC;
		this.listeJLabelsApp = creationJLabels(matriceApp);
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
			panMatApp.add(label);	
		}
		panMatApp.setBackground(Color.DARK_GRAY);
		gauche.add(panMatApp);
		//panMatApp.setAlignmentX(SwingConstants.LEFT);
		panMatApp.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		//Matrice Prédiction
		JPanel panMatPred = new JPanel();
		panMatPred.setLayout(new BoxLayout(panMatPred, BoxLayout.Y_AXIS)); //Pour que les lignes soient organisées verticalement
		for (JLabel label : this.listeJLabelsPred) {
			label.setForeground(Color.WHITE);
			panMatPred.add(label);
		}
		panMatPred.setBackground(Color.DARK_GRAY);
		droit.add(panMatPred);
		panMatPred.setAlignmentX(SwingConstants.RIGHT);
		panMatPred.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		// Pourcentage Apprentissage
		JLabel pourcApp = new JLabel(this.calculPourc(matriceApp));
		pourcApp.setBackground(Color.DARK_GRAY);
		pourcApp.setForeground(Color.WHITE);
		pourcApp.setFont(new Font("Aucun",Font.BOLD,25));
		pourcApp.setBorder(BorderFactory.createLineBorder(Color.WHITE));
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
		pourcPred.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		JPanel pan3 = new JPanel();
		pan3.add(pourcPred);
		pan3.setBackground(Color.DARK_GRAY);
		droit.add(pan3);
		
		// Bouton1
	    JPanel boutons = new JPanel();
	    JButton okBouton = new JButton("Vérifier la structure de l'arbre (parcours en profondeur)");
	    okBouton.addActionListener(this);
	    boutons.add(okBouton);
	    boutons.setBackground(Color.DARK_GRAY);
	    
	    // Bouton Non merci
	    JButton bouton2 = new JButton("Non merci");
	    bouton2.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent event){				
	    	  System.exit(0);
	      }
	    });
	    boutons.add(bouton2);
		
	    this.getContentPane().add(boutons, BorderLayout.SOUTH);
	    this.getContentPane().add(gauche, BorderLayout.WEST);
	    this.getContentPane().add(droit, BorderLayout.EAST);
	    this.getContentPane().setBackground(Color.DARK_GRAY);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {   
		arbre.affStructure();
		System.exit(0);
	}
	
	private ArrayList<JLabel> creationJLabels(Matrice mat) {
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
			t.setHorizontalAlignment(SwingConstants.LEFT);
			res.add(t);
		}
		return res;
	}
	
	private String calculPourc(Matrice mat) {
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
		res = (int)((res/mat.getLongueur())*100*100)/100.;
		return " "+res+"% correct ";
	}
	
	private int getMaxClasse(){
		int max = 0;
		for (String classe : this.listeClasses) {
			if (classe.length()>max) {
				max = classe.length();
			}
		}
		return max;
	}
}