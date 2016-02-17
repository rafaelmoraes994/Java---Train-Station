package View;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.Controlador;
import Model.CriaTrem;
import Model.EstadoSinal;
import Model.Linha;
import Model.SinalAberto;
import Model.SinalFechado;



public class Trilhos extends JFrame {

	public final int LARG_DEFAULT=1040;
	public final int ALT_DEFAULT=800;
	private BufferedImage img;
	int i;
	public PanelTrilho p = new PanelTrilho();
	public JLabel semaforo1 = new JLabel();
	public JLabel semaforo2 = new JLabel();

	public Trilhos()
	{
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARG_DEFAULT/2;
		int y=sa/2-ALT_DEFAULT/2;
		setBounds(x,y,LARG_DEFAULT,ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(p);
		p.setLayout(null);
		
		//Left Label
		JLabel criaTremEsq = new JLabel("Cria trem na esquerda");
		criaTremEsq.setBounds(70, 640, 200, 50);
		p.add(criaTremEsq);
		
		//Right Label
		JLabel criaTremDir = new JLabel("Cria trem na direita");
		criaTremDir.setBounds(840, 640, 200, 50);
		p.add(criaTremDir);

		// Left Buttons
		for(i=0; i<3; i++){
			JButton bVel = new JButton(20+10*i +"");
			bVel.setBounds(40+i*70, 700, 50, 30);
			p.add(bVel);
			bVel.addActionListener(new ActionListener() {
				int k = i;
				@Override
				public void actionPerformed(ActionEvent e) {
					new CriaTrem(Controlador.Direcao.esquerda, 20+10*k);
				}
			});	
		}

		// Right Buttons
		for(i=0; i<3; i++){
			JButton bVel = new JButton(20+10*i +"");
			bVel.setBounds(800+i*70, 700, 50, 30);
			p.add(bVel);
			bVel.addActionListener(new ActionListener() {
				int k = i;
				@Override
				public void actionPerformed(ActionEvent e) {
					new CriaTrem(Controlador.Direcao.direita, 20+10*k);
				}
			});	
		}	

		try {
			img = ImageIO.read(new File("images/Sinal Verde.png"));
		}catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		semaforo1.setIcon(new ImageIcon(img)); 
		semaforo1.setBounds(250, 234, 100, 100);
		semaforo2.setIcon(new ImageIcon(img)); 
		semaforo2.setBounds(755, 440, 100, 100);
		p.add(semaforo1);
		p.add(semaforo2);

		Controlador.frame = this;
		new Thread(Controlador.getInstance()).start();

	}

	public void setLocation (Linha l, int x, int y){
		l.tremImg.setLocation(x, y);
	}

	public void setSemafaro (Controlador.Direcao direcao){
		EstadoSinal estado = Controlador.getInstance().getEstadoPelaDirecao(direcao);
		if(estado instanceof SinalAberto){
			try {
				img = ImageIO.read(new File("images/Sinal Verde.png"));
			}catch (IOException ex) {
				System.out.println(ex.getMessage());
				System.exit(1);
			}
		}
		else if(estado instanceof SinalFechado){
			try {
				img = ImageIO.read(new File("images/Sinal Vermelho.png"));
			}catch (IOException ex) {
				System.out.println(ex.getMessage());
				System.exit(1);
			}
		}
		if(direcao == Controlador.Direcao.esquerda){
			semaforo1.setBounds(250, 234, 100, 100);
			semaforo1.setIcon(new ImageIcon(img));
		}
		else if(direcao == Controlador.Direcao.direita){
			semaforo2.setBounds(755, 440, 100, 100);
			semaforo2.setIcon(new ImageIcon(img));
		}
	}
}
