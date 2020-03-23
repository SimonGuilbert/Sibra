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
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class Fenetre extends JFrame implements ActionListener{
	private Matrice matriceApp;
	private Matrice matricePred;
	private ArrayList<JLabel> listeJLabelsApp;
	private ArrayList<JLabel> listeJLabelsPred;
	private ArrayList<String> listeClasses;
	
	public Fenetre(Matrice mat1,Matrice mat2,ArrayList<String> listeC){
		
		// Format fenêtre
		this.setTitle("Matrices de confusion");
		this.setSize(1000, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		// Attribution variables
		this.matriceApp = mat1;
		this.matricePred = mat2;
		this.listeClasses = listeC;
		this.listeJLabelsApp = creationJLabels(matriceApp);
		this.listeJLabelsPred = creationJLabels(matricePred);
		
		// Titre
		JPanel panTitre = new JPanel();
	    JLabel lab = new JLabel("Apprentissage                                                                           Prédiction                   ");
	    lab.setForeground(Color.WHITE);
	    panTitre.add(lab);
	    lab.setFont(new Font("Tahoma", Font.BOLD, 15));
	    panTitre.setBackground(Color.DARK_GRAY);
		
		// Matrice Apprentissage
		JPanel panMatApp = new JPanel();
		panMatApp.setLayout(new BoxLayout(panMatApp, BoxLayout.Y_AXIS)); //Pour que les lignes soient organisées verticalement
		for (JLabel label : this.listeJLabelsApp) {
			//label.setBackground(Color.DARK_GRAY);
			label.setForeground(Color.WHITE);
			panMatApp.add(label);	
		}
		panMatApp.setBackground(Color.DARK_GRAY);
		
		//Matrice Prédiction
		JPanel panMatPred = new JPanel();
		panMatPred.setLayout(new BoxLayout(panMatPred, BoxLayout.Y_AXIS)); //Pour que les lignes soient organisées verticalement
		for (JLabel label : this.listeJLabelsPred) {
			//label.setBackground(Color.DARK_GRAY);
			label.setForeground(Color.WHITE);
			//JPanel temp = new JPanel();
			//temp.setLayout(new FlowLayout(FlowLayout.RIGHT));
			//temp.add(label);
			//panMatPred.add(temp);
			panMatPred.add(label);
		}
		panMatPred.setBackground(Color.DARK_GRAY);
		
		// Bouton OK
	    JPanel bouton = new JPanel();
	    JButton okBouton = new JButton("OK");
	    okBouton.addActionListener(this);
	    bouton.add(okBouton);
	    bouton.setBackground(Color.DARK_GRAY);
		
	    this.getContentPane().add(panTitre, BorderLayout.NORTH);
	    this.getContentPane().add(bouton, BorderLayout.SOUTH);
	    this.getContentPane().add(panMatApp, BorderLayout.WEST);
	    this.getContentPane().add(panMatPred, BorderLayout.EAST);
	    this.getContentPane().setBackground(Color.DARK_GRAY);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {   
		System.exit(0);
	}
	
	protected ArrayList<JLabel> creationJLabels(Matrice mat) {
		int i = 0;
		ArrayList<JLabel> res = new ArrayList<JLabel>();
		for (ArrayList<Integer> ligne : mat.getMatrice()) {
			String resTemp = " ";
			//for (int j=0;j<this.getMaxClasse()-this.listeClasses.get(i).length();j++) {
			//	System.out.println("ici"); 
			//	resTemp += " ";
			//}
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
			//res.add(new JLabel(resTemp));
		}
		return res;
	}
	
	protected int getMaxClasse(){
		int max = 0;
		for (String classe : this.listeClasses) {
			if (classe.length()>max) {
				max = classe.length();
			}
		}
		return max;
	}
}