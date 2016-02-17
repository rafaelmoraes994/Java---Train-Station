package Model;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Controller.Controlador;


public class CriaTrem {

	Controlador.Direcao direcao;
	int velocidade;

	public CriaTrem (Controlador.Direcao dir, int vel){
		direcao = dir;
		velocidade = vel;
		
		int i = 0;
		int x, y = 440;
		BufferedImage img = null;

		if(Controlador.getInstance().verificaCriacao(direcao) == false){
			return;
		}
		
		JLabel t1 = new JLabel();
		try {
			if(direcao == Controlador.Direcao.esquerda){
				img = ImageIO.read(new File("images/TremPreto.png"));
			}
			else{
				img = ImageIO.read(new File("images/TremVermelho.png"));
			}
		}catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		t1.setIcon(new ImageIcon(img));

		Linha l1;
		Color c;
		if(direcao == Controlador.Direcao.esquerda){
			c = Color.black;
		}
		else{
			c = Color.red;
		}
		x = Controlador.getInstance().getPosicaoInicialPorDirecao(direcao);
		l1 = new Linha(i, t1, c, x, y, direcao, velocidade);
		Controlador.getInstance().adicionaLinha(direcao, l1, x);

		new Thread(l1).start();
	}
}
