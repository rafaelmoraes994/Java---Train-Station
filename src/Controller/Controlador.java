package Controller;
import java.util.ArrayList;
import java.util.Observable;

import Model.EstadoSinal;
import Model.Linha;
import Model.SinalAberto;
import View.Trilhos;

public class Controlador extends Observable implements Runnable{

	private static Controlador instance;
	public static Trilhos frame;
	public boolean trocaDirecao = false;
	private ArrayList<Linha> vTremEsq = new ArrayList<Linha>();
	private ArrayList<Linha> vTremDir = new ArrayList<Linha>();
	private Object lock = new Object();
	public EstadoSinal estado;
	public EstadoSinal estadoDir = new SinalAberto();
	public EstadoSinal estadoEsq = new SinalAberto();
	
	
	public enum Direcao{
		esquerda,
		direita
	}

	private Controlador() {
		this.addObserver(frame.p);
	}

	public static Controlador getInstance() {
		if (instance == null)
			instance = new Controlador();
		return instance;
	}

	public boolean verificaCriacao(Direcao direcao){
		synchronized(lock){
			ArrayList<Linha> vetor =  getVetorPelaDirecao(direcao);
			int posicaoInicial = getPosicaoInicialPorDirecao(direcao);
			if(vetor.size() == 0){
				return true;
			}
			if(vetor.get(vetor.size()-1).tremImg.getLocation().x == posicaoInicial){
				return false;
			}
			else{
				return true;
			}
		}
	}

	public EstadoSinal getEstadoPelaDirecao(Direcao direcao){
		switch(direcao){
		case direita:
			return estadoDir;
		case esquerda:
			return estadoEsq;
		default: return null;
		}
	}

	public Direcao getDirecaoContraria(Direcao direcao){
		switch(direcao){
		case direita:
			return Direcao.esquerda;
		case esquerda:
			return Direcao.direita;
		default: return direcao;
		}
	}

	public ArrayList<Linha> getVetorPelaDirecao(Direcao direcao){
		switch(direcao){
		case direita:
			return vTremDir;
		case esquerda:
			return vTremEsq;
		default: return new ArrayList<Linha>();
		}
	}

	public void adicionaLinha(Direcao direcao, Linha l, int x){
		synchronized(lock){
			ArrayList<Linha> vetor = getVetorPelaDirecao(direcao);
			l.tremImg.setBounds(x, 440, 50, 50);
			frame.p.add(l.tremImg);
			vetor.add(l);
		}
	}

	public int getPosicaoInicialPorDirecao(Direcao direcao){
		switch(direcao){
		case direita:
			return 995;
		case esquerda:
			return 0;
		default: return 0;
		}
	}

	public Linha getProx(Linha l, Direcao direcao){
		synchronized(lock){
			int i;
			switch(direcao){
			case direita:
				for(i=0; i < vTremDir.size()-1; i++){
					if(vTremDir.get(i) == l){
						return vTremDir.get(i+1);
					}
				}
			case esquerda:
				for(i=0; i < vTremEsq.size()-1; i++){
					if(vTremEsq.get(i) == l){
						return vTremEsq.get(i+1);
					}
				}
			default: return null;
			}
		}
	}

	public Linha getAnt(Linha l, Direcao direcao){
		synchronized(lock){
			int i;
			switch(direcao){
			case direita:
				for(i=1; i < vTremDir.size(); i++){
					if(vTremDir.get(i) == l){
						return vTremDir.get(i-1);
					}
				}
			case esquerda:
				for(i=1; i < vTremEsq.size(); i++){
					if(vTremEsq.get(i) == l){
						return vTremEsq.get(i-1);
					}
				}
			default: return null;
			}
		}
	}

	public int getVetSize(Direcao direcao){
		switch(direcao){
		case direita:
			return vTremDir.size();
		case esquerda:
			return vTremEsq.size();
		default: return 0;
		}
	}

	public void terminaThread(Linha l, Direcao direcao){
		synchronized(lock){
			ArrayList<Linha> vetor = getVetorPelaDirecao(direcao);
			l.setStop(true);
			vetor.remove(l);
			l.tremImg.setVisible(false);
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(250);
				int i;
				for(i=0; i < vTremEsq.size(); i++){
					setChanged();
					notifyObservers(vTremEsq.get(i));	
				}
				for(i=0; i < vTremDir.size(); i++){
					setChanged();
					notifyObservers(vTremDir.get(i));	
				}	
			}
			catch(InterruptedException e) {
			}
		}
	}

}