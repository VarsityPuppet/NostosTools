import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener,
		MouseMotionListener, Runnable {

	private static final float EPSILON = 20;
	ArrayList<LevelItem> icons;
	Image offscreen;

	boolean MOUSEDOWN;
	boolean MOUSEUP;
	private int selected;
	private boolean DRAGGING;
	public int originalHeight;
	public int originalWidth;
	public double scale = 1.0;
	protected int currentWidth;
	protected int currentHeight;

	public DrawPanel(Dimension d) {

		addMouseListener(this);
		addMouseMotionListener(this);

		icons = new ArrayList<LevelItem>();

		this.setBackground(Color.lightGray);
		
		originalHeight = d.height;
		originalWidth = d.width;
		currentHeight = originalHeight;
		currentWidth = originalWidth;

		offscreen = createImage(originalWidth, originalHeight);
		
	}

	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		
		if(offscreen == null)
			offscreen = createImage(currentWidth, currentHeight);
		
		offscreen.getGraphics().clearRect(0, 0, originalWidth, originalHeight);
		
//		AffineTransform trans = new AffineTransform();
//		trans.scale(scale, scale);
//		
		Graphics2D g2d = (Graphics2D)offscreen.getGraphics();
		//g2d.setTransform(trans);
		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, originalWidth, originalHeight);
		g2d.setColor(Color.green);
		
		for (int i = 0; i < icons.size(); i++) {
			LevelItem temp = icons.get(i);
			if(i == selected)
				g2d.fillOval(temp.posx, temp.posy, temp.width, temp.height);

			g2d.drawImage(temp.img, temp.posx, temp.posy, temp.width, temp.height, null);
			g2d.setColor(Color.green);
			
			g2d.fillOval(temp.realx - 10 , temp.realy - 10, 20, 20);
			
		}
	
		g.drawImage(offscreen,  originalWidth/2 - currentWidth/2,  0, currentWidth, currentHeight,Color.white, null);//, dx2, dy2, sx1, sy1, sx2, sy2, observer)
	}

	@Override
	public void update(Graphics g) {
		super.update(g);
		paint(g);

	}

	public void addImageToView() {

		int x = this.getMousePosition().x;
		int y = this.getMousePosition().y;

		LevelItem temp = new LevelItem("Image/bugs_maggot.png", x, y);

		icons.add(temp);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse clicked!");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Enters Screen!");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Exits Screen!");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Pressed!");

		int x = this.getMousePosition().x;
		int y = this.getMousePosition().y;

		float closest = EPSILON;
		for (int i = 0; i < icons.size(); i++) {
			float d = (float) Math.sqrt(Math.pow(
					(float) (icons.get(i).realx - x), 2)
					+ Math.pow((float) (icons.get(i).realy - y), 2));

			if (d < closest) {
				closest = d;
				if (d < EPSILON) 
					selected = i;
			}
		}
		if (closest == EPSILON){
			this.addImageToView();
			selected = icons.size() - 1;
		}
		DRAGGING = true;
		MOUSEDOWN = true;

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Released!");

		MOUSEUP = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse is dragging!");

		if (DRAGGING) {
			icons.get(selected).setPosition(getMousePosition());
		}
		
		System.out.printf("Mouse: x:%d y:%d", getMousePosition().x, getMousePosition().y);

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse has moved!");
	}

	public void scale(double scale2) {
		// TODO Auto-generated method stub
		this.scale = scale2;
		
		//viewPanel.setSize(viewPanel.getWidth(), (int) (DRAWPANELHEIGHT*scale) );
		currentHeight = (int) (originalHeight*scale2);
		currentWidth = (int) (originalWidth*scale2);
		
		System.out.printf("\ncurrentHeight: %d currentWidth: %d", currentHeight, currentWidth);
		
		repaint();
	}
}
