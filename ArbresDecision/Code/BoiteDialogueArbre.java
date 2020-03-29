import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

public class BoiteDialogueArbre extends JDialog implements ActionListener{
  private JRadioButton choix1, choix2;
  protected String choixArbre;

  public BoiteDialogueArbre(){
	this.setModal(true);
	this.setTitle("Choix de l'arbre");
    this.setSize(305, 300); // En pixels
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    
    // Bienvenue
    JLabel premierePhrase = new JLabel("Bienvenue");
    premierePhrase.setFont(new Font("Tahoma",Font.BOLD,21));
    premierePhrase.setForeground(Color.WHITE);
    JPanel premierPan = new JPanel();
    premierPan.add(premierePhrase);                                                                                                                                                                              
    premierPan.setBackground(Color.DARK_GRAY);
    
    // Question
    JLabel question = new JLabel("Choisissez un arbre de décision                     ");
    // Obligé de mettre plein d'espaces pour décentrer car question.setHorizontalAlignment(SwingConstants.LEFT); ne fonctionne pas. Pas de message d'erreur mais rien ne se passe
    question.setForeground(Color.WHITE);
    question.setBackground(Color.DARK_GRAY);
    
    // Boutons radio
    JPanel panContour = new JPanel();
    choix1 = new JRadioButton("Id3");
    choix1.setForeground(Color.WHITE);
    choix1.setBackground(Color.DARK_GRAY);
    choix1.setSelected(true); // Le premier bouton radio est automatiquement sélectionné
    choix2 = new JRadioButton("C4.5");
    choix2.setForeground(Color.WHITE);
    choix2.setBackground(Color.DARK_GRAY);
    ButtonGroup bg = new ButtonGroup(); // ButtonGroup nécessaire pour empêcher la sélection de plusieurs boutons radio en meme temps
    bg.add(choix1);
    bg.add(choix2);
    JPanel panRadio = new JPanel();
    panRadio.add(choix1);
    panRadio.add(choix2);
    panRadio.setBackground(Color.DARK_GRAY);
    panContour.add(panRadio);
    panContour.setBackground(Color.DARK_GRAY);
    
    //Images
    ImageIcon image = new ImageIcon("Data/tree.jpg");
    JLabel labImage = new JLabel(image);
    
    //Ensemble final
    JPanel panFinal = new JPanel();
    panFinal.setLayout(new BoxLayout(panFinal, BoxLayout.Y_AXIS)); // Organisation verticale
    panFinal.add(premierPan);
    panFinal.add(question);
    panFinal.add(panContour);
    panFinal.setBackground(Color.DARK_GRAY);
    
    //Bouton Valider
    JPanel bouton = new JPanel();
    JButton okBouton = new JButton("Valider");
    okBouton.addActionListener(this); // Ajout d'un écouteur
    bouton.add(okBouton);
    bouton.setBackground(Color.DARK_GRAY);
    
    this.getContentPane().add(panFinal, BorderLayout.NORTH); // Choix de l'arbre et titre en haut
    this.getContentPane().add(bouton, BorderLayout.SOUTH); // Bouton en bas
    this.getContentPane().add(labImage, BorderLayout.CENTER); // Image au centre
    this.getContentPane().setBackground(Color.DARK_GRAY);
    this.setVisible(true); // On rend la boite de dialogue visible
  }

	public void actionPerformed(ActionEvent arg0) { // Quand on appuie sur le bouton
		this.setVisible(false); // Fermeture de la boite de dialogue
		this.setChoix(); // Enregistrement du choix dans this.choixArbre
	}
	
	public String getChoix() {
		return this.choixArbre;
	}
  
	private void setChoix(){ // Enregistre le choix écouté par le listener du bouton dans this.choixArbre ("id3" ou "c4.5")
		if (choix1.isSelected()){
			this.choixArbre = "id3";
		} else {
			this.choixArbre = "c4.5";
		}    
	}
}
