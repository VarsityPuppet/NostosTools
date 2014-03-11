import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class LevelItem {

	
	protected BufferedImage img;
	protected int posx;
	protected int realx;
	protected int posy;
	protected int realy;
	protected int height;
	protected int width;
	

	public LevelItem (String imageName, int posx, int posy){
		
		try {
			img = ImageIO.read(new File(imageName));
		} catch (IOException e) {
		}
		
		height = img.getHeight();
		width = img.getWidth();
		
		this.realx = posx;
		this.realy = posy;
		this.posx = realx - width/2;
		this.posy = realy - height/2;
	}
	
	
	public void setPosition(Point p){
		
		this.realx = p.x;
		this.realy = p.y;
		this.posx = realx - width/2;
		this.posy = realy - height/2;
	}
	
	
}
