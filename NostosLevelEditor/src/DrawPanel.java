import java.awt.Graphics;

import javax.swing.JPanel;


public class DrawPanel extends JPanel{
	
	public DrawPanel(){
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		g.drawLine(0, 0, 700, 700);
	}
}
