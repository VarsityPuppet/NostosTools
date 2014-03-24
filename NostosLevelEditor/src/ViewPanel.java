import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class ViewPanel extends JPanel{
	
	public int originalHeight;
	
	private Border border = BorderFactory.createEmptyBorder(50, 50, 50, 50);

	private Dimension dSize;
	private Dimension dDraw;

	private DrawPanel drawPanel;
	
	public ViewPanel(Dimension d){
		
		this.dSize = d;
		originalHeight = d.height;
		this.setLayout(new BorderLayout());
		this.setPreferredSize(d);
		this.setBackground(Color.LIGHT_GRAY);
		
		this.setBorder(border);
		
		dDraw = new Dimension(dSize.width - 100, dSize.height - 100);
		
		drawPanel = new DrawPanel(dDraw);
		
		drawPanel.setSize(dDraw);
		
		this.add(drawPanel, BorderLayout.CENTER);
		
	}
	
	
	public void scale(double scale){
		
		drawPanel.scale(scale);
		
		this.setPreferredSize(new Dimension(getWidth(), drawPanel.currentHeight + 100));
		
		revalidate();
	}
	
}
