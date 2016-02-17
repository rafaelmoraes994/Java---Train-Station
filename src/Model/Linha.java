package Model;
import java.awt.Color;

import javax.swing.JLabel;

import Controller.Controlador;


public class Linha implements Runnable {
	public int id;
	public JLabel tremImg = new JLabel();
	public Color cor;
	public boolean stop = false;
	public int x, y, velocidade;
	public Controlador.Direcao direcao;
	

	public Linha(int i, JLabel img, Color c, int posX, int posY, Controlador.Direcao dir, int vel) {
		id = i;
		tremImg = img;
		cor = c;
		x = posX;
		y = posY;
		direcao = dir;
		velocidade = vel;
		
	}

	public void setStop(boolean flag) {
		stop = flag;
	}

	public void run() {
		while(true) {
			if(stop == true){
				break;
			}
			try {
				Thread.sleep(250);
				Linha ant = Controlador.getInstance().getAnt(this, direcao);
				Linha prox = Controlador.getInstance().getProx(this, direcao);
				if(direcao == Controlador.Direcao.esquerda){
					if(x + velocidade == 240 && Controlador.getInstance().estadoEsq instanceof SinalAberto){
						Controlador.getInstance().estadoDir = new SinalFechado();
						Controlador.frame.setSemafaro(Controlador.Direcao.direita);
					}
					else if(x + velocidade >= 255 && Controlador.getInstance().getEstadoPelaDirecao(Controlador.Direcao.esquerda) instanceof
							SinalFechado && Controlador.getInstance().getVetSize(Controlador.Direcao.direita) == 0){
								Controlador.getInstance().estadoEsq = new SinalAberto();
								Controlador.frame.setSemafaro(Controlador.Direcao.esquerda);
						
					}
					else if(x + velocidade >= 255 && x + velocidade < 880 && Controlador.getInstance().getEstadoPelaDirecao(Controlador.Direcao.esquerda) instanceof SinalFechado){
						continue;
					}
					else if(ant != null && x + tremImg.getSize().width/2 + velocidade + 5 >= Controlador.getInstance().getAnt(this, direcao).x){
						continue;
					}
					if(x >= 120 && x < 280 && velocidade == 20){
						x += velocidade;
						y -= 9;
					}
					else if(x >= 120 && x < 270 && velocidade == 30){
						x += velocidade;
						y -= 14;
					}
					else if(x >= 120 && x < 280 && velocidade == 40){
						x += velocidade;
						y -= 18;
					}
					else if(x == 270){
						x += velocidade;
						y -= 7;
					}
					else if(x == 280){
						x += velocidade;
						y -= 5;
					}
					else if(x >= 740 && x < 860 && velocidade == 20){
						x += velocidade;
						y -= 6;
					}
					else if(x >= 750 && x < 860 && velocidade == 30){
						x += velocidade;
						y -= 11;
					}
					else if(x >= 760 && x < 860 && velocidade == 40){
						x += velocidade;
						y -= 15;
					}
					else if(x == 860){
						x += velocidade;
						y -= 8;
					}
					else if(x == 880){
						x += velocidade;
					}
					else if(x >= 1024){
						Controlador.getInstance().terminaThread(this, Controlador.Direcao.esquerda);
						if(prox == null){
							Controlador.getInstance().estadoEsq = new SinalFechado();
							Controlador.frame.setSemafaro(Controlador.Direcao.esquerda);
							if(Controlador.getInstance().getVetSize(Controlador.Direcao.direita) > 0){
								Controlador.getInstance().estadoDir = new SinalAberto();
								Controlador.frame.setSemafaro(Controlador.Direcao.direita);
							}
						}
						continue;
					}
					else{
						x += velocidade;
					}
					continue;
				}

				else if(direcao == Controlador.Direcao.direita){
					if(x - velocidade == 755 && Controlador.getInstance().estadoDir instanceof SinalAberto){
						Controlador.getInstance().estadoEsq = new SinalFechado();
						Controlador.frame.setSemafaro(Controlador.Direcao.esquerda);
					}
					else if(x - velocidade <= 775 && Controlador.getInstance().getEstadoPelaDirecao(Controlador.Direcao.direita) instanceof 
							SinalFechado && Controlador.getInstance().getVetSize(Controlador.Direcao.esquerda) == 0){
								Controlador.getInstance().estadoDir = new SinalAberto();
								Controlador.frame.setSemafaro(Controlador.Direcao.direita);
						
					}
					else if(x - velocidade <= 775 && x - velocidade > 135 && Controlador.getInstance().getEstadoPelaDirecao(Controlador.Direcao.direita) instanceof SinalFechado){
						continue;
					}
					else if(ant != null && x - velocidade <= Controlador.getInstance().getAnt(this, direcao).x + Controlador.getInstance().getAnt(this, direcao).tremImg.getSize().width/2 + 5){
						continue;
					}
					if(x == 895 && velocidade == 20){
						x -= velocidade;
						y -= 3;
					}
					else if(x == 905 && velocidade == 30){
						x -= velocidade;
						y -= 2;
					}
					else if(x == 875 && velocidade == 40){
						x -= velocidade;
						y -= 25;
					}
					else if(x <= 894 && x > 755 && velocidade == 20){
						x -= velocidade;
						y -= 11;
					}
					else if(x <= 904 && x > 755 && velocidade == 30){
						x -= velocidade;
						y -= 17;	
					}
					else if(x <= 874 && x > 755 && velocidade == 40){
						x -= velocidade;
						y -= 22;	
					}
		
					else if(x == 755){
						x -= velocidade;
						y -= 7;
					}
					else if(x == 295 && velocidade == 20){
						x -= velocidade;
						y -= 5;
					}
					else if(x == 305 && velocidade == 30){
						x -= velocidade;
						y -= 4;
					}
					else if(x == 275 && velocidade == 40){
						x -= velocidade;
						y -= 15;
					}
					else if(x <= 294 && x > 154 && velocidade == 20){
						x -= velocidade;
						y -= 6;
					}
					else if(x <= 304 && x > 154 && velocidade == 30){
						x -= velocidade;
						y -= 9;
					}
					else if(x <= 274 && x > 154 && velocidade == 40){
						x -= velocidade;
						y -= 11;
					}
					else if(x == 135){

						x -= velocidade;
					}
					else if(x <= 0){
						Controlador.getInstance().terminaThread(this, Controlador.Direcao.direita);
						if(prox == null){
							Controlador.getInstance().estadoDir = new SinalFechado();
							Controlador.frame.setSemafaro(Controlador.Direcao.direita);
							if(Controlador.getInstance().getVetSize(Controlador.Direcao.esquerda) > 0){
								Controlador.getInstance().estadoEsq = new SinalAberto();
								Controlador.frame.setSemafaro(Controlador.Direcao.esquerda);
							}
						}
						continue;
					}
					else{
						x -= velocidade;
					}
					continue;
				}
			}
			catch(InterruptedException e) {
			}
		}
	}
}