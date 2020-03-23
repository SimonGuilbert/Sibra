import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

public class BoiteDialogue extends JDialog implements ActionListener{
  private JRadioButton choix1, choix2;
  protected int choix;

  public BoiteDialogue(){
	this.setModal(true);
	this.setTitle("Traitement des valeurs manquantes");
    this.setSize(715, 270);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    
    //Phrase du début
    JLabel premierePhrase = new JLabel("Des valeurs manquantes (notées \"?\") ont été repérées dans les fichiers de données");
    premierePhrase.setFont(new Font("Tahoma",Font.BOLD,14));
    premierePhrase.setForeground(Color.red);
    JPanel premierPan = new JPanel();
    premierPan.add(premierePhrase);
    
    // Boutons radio
    JPanel panContour = new JPanel();
    panContour.setBorder(BorderFactory.createTitledBorder("Comment voulez-vous traiter les valeurs manquantes ?"));
    choix1 = new JRadioButton("Considérer que les ? sont des valeurs à prendre en compte au même titre que les autres");
    choix1.setSelected(true);
    choix2 = new JRadioButton("Oublier les ? : l'algorithme se basera sur les valeurs non manquantes pour prédire la classe");
    ButtonGroup bg = new ButtonGroup(); // ButtonGroup nécessaire pour empêcher la sélection de plusieurs boutons radio
    bg.add(choix1);
    bg.add(choix2);
    JPanel panRadio = new JPanel();
    panRadio.setLayout(new BoxLayout(panRadio, BoxLayout.Y_AXIS)); //Pour que les boutons soient organisés verticalement
    panRadio.add(choix1);
    panRadio.add(choix2);
    panContour.add(panRadio);
    
    //Image
    ImageIcon image = new ImageIcon("Data/pointInterrogation5.png");
    JLabel labImage = new JLabel(image);
    
    //Ensemble final
    JPanel panFinal = new JPanel();
    panFinal.add(panContour);
    
    //Bouton Valider
    JPanel bouton = new JPanel();
    JButton okBouton = new JButton("Valider");
    okBouton.addActionListener(this);
    bouton.add(okBouton);
    
    this.getContentPane().add(premierPan, BorderLayout.NORTH);
    this.getContentPane().add(panFinal, BorderLayout.WEST);
    this.getContentPane().add(bouton, BorderLayout.SOUTH);
    this.getContentPane().add(labImage, BorderLayout.EAST);
    this.setVisible(true);
  }

	public void actionPerformed(ActionEvent arg0) {   
		this.setVisible(false);
		this.setChoix();
	}
	
	public int getChoix() {
		return this.choix;
	}
  
	public void setChoix(){
		if (choix1.isSelected()){
			this.choix = 1;
		} else {
			this.choix = 2;
		}    
	}
}