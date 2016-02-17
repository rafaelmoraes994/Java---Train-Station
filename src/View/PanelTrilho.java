package View;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Controller.Controlador;
import Model.Linha;


public class PanelTrilho extends JPanel implements Observer{

    private BufferedImage image;
    
    public PanelTrilho() {
       try {                
          image = ImageIO.read(new File("images/Trem.jpg"));
       } catch (IOException ex) {
    	   System.out.println(ex.getMessage());
    	   System.exit(1);
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 1024 , 768, null);
    }

	@Override
	public void update(Observable o, Object arg) {
		Linha l = (Linha)arg;
		Controlador.getInstance().frame.setLocation(l, l.x, l.y);
	}

}