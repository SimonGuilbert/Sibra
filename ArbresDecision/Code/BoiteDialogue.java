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
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

public class BoiteDialogue extends JDialog implements ActionListener{
  private JRadioButton choix1, choix2, choix3;
  protected int choix;

  public BoiteDialogue(){
	this.setModal(true);
	this.setTitle("Traitement des valeurs manquantes");
    this.setSize(715, 255);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    
    //Phrase du début
    JLabel premierePhrase = new JLabel("Des valeurs manquantes (notées \"?\") ont été repérées dans les fichiers de données");
    premierePhrase.setFont(new Font("Tahoma",Font.BOLD,14));
    premierePhrase.setForeground(Color.WHITE);
    JPanel premierPan = new JPanel();
    premierPan.add(premierePhrase);
    premierPan.setBackground(Color.DARK_GRAY);
    
    // Boutons radio
    JPanel panContour = new JPanel();
    //Font contour = new Font("Tahoma",Font.PLAIN,12);
    panContour.setBorder(BorderFactory.createTitledBorder(null, "  Comment voulez-vous traiter les valeurs manquantes ?", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Tahoma",Font.BOLD,11), Color.WHITE));
    //panContour.setBorder(BorderFactory.createTitledBorder("Comment voulez-vous traiter les valeurs manquantes ?"));
    choix1 = new JRadioButton("Considérer que les ? sont des valeurs à prendre en compte au même titre que les autres");
    choix1.setSelected(true);
    choix2 = new JRadioButton("Oublier les ? : l'algorithme se basera sur les valeurs non manquantes pour prédire la classe");
    choix3 = new JRadioButton("Remplacer les ? par la valeur majoritaire de l'attribut correspondant");
    ButtonGroup bg = new ButtonGroup(); // ButtonGroup nécessaire pour empêcher la sélection de plusieurs boutons radio
    bg.add(choix1);
    bg.add(choix2);
    bg.add(choix3);
    JPanel panRadio = new JPanel();
    panRadio.setLayout(new BoxLayout(panRadio, BoxLayout.Y_AXIS)); //Pour que les boutons soient organisés verticalement
    panRadio.add(choix1);
    panRadio.add(choix2);
    panRadio.add(choix3);
    choix1.setBackground(Color.DARK_GRAY);
    choix1.setForeground(Color.WHITE);
    choix2.setBackground(Color.DARK_GRAY);
    choix2.setForeground(Color.WHITE);
    choix3.setBackground(Color.DARK_GRAY);
    choix3.setForeground(Color.WHITE);
    panRadio.setBackground(Color.DARK_GRAY);
    panContour.add(panRadio);
    panContour.setBackground(Color.DARK_GRAY);
    
    //Image
    //ImageIcon image = new ImageIcon("Data/pointInterrogation5.png");
    ImageIcon image = new ImageIcon("Data/questionMark.jpg");
    JLabel labImage = new JLabel(image);
    JPanel panImage = new JPanel();
    panImage.add(labImage);
    panImage.setBackground(Color.DARK_GRAY);

    //Ensemble final
    JPanel panFinal = new JPanel();
    panFinal.add(panContour);
    panFinal.setBackground(Color.DARK_GRAY);
    
    //Bouton Valider
    JPanel bouton = new JPanel();
    JButton okBouton = new JButton("Valider");
    okBouton.addActionListener(this);
    bouton.add(okBouton);
    bouton.setBackground(Color.DARK_GRAY);
    
    this.getContentPane().add(premierPan, BorderLayout.NORTH);
    this.getContentPane().add(panFinal, BorderLayout.WEST);
    this.getContentPane().add(bouton, BorderLayout.SOUTH);
    this.getContentPane().add(panImage, BorderLayout.EAST);
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
		} 
		if (choix2.isSelected()) {
			this.choix = 2;
		} 
		if (choix3.isSelected()){
			this.choix = 3;
		}
	}
}