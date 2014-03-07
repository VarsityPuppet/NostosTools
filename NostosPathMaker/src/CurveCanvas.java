/**
 * Canvas for painting controlled curves into
 */
import java.applet.*;
import java.awt.*;

public class CurveCanvas extends Canvas implements Runnable{

  Image offscreen;
  ControlCurve curve;
  MouseEvent mouseEvent;

  public CurveCanvas(MouseEvent mouseEvent, ControlCurve curve){
    this.mouseEvent = mouseEvent;
    this.curve = curve;
  }
  
  public void paint(Graphics g) {
    if (offscreen == null) {
      offscreen = createImage(size().width, size().height);
    }

    //g.fillRect(, y, width, height);
    Graphics og = offscreen.getGraphics();
    og.clearRect(0,0,size().width,size().height);
    
    int HPADDING = 50;
    int VPADDING = 100;
    
    og.setColor(Color.gray);
    og.fillRect(0 + HPADDING + 50, 0, 480, 100);
    og.fillRect(0, 0 + VPADDING, 100, 320);
    og.fillRect(0 + HPADDING + 50, 270 + VPADDING + 50, 480, 100);
    og.fillRect(480 + HPADDING + 50, 0 + VPADDING, 100, 320);
    og.drawLine(0, 0, 680, 520);
    og.drawLine(0, 520, 680, 0);
    curve.paint(og);

    g.drawImage(offscreen,0,0,null);

  }

  public void update(Graphics g) {
    paint(g);
  }

  public void update() {
    update(getGraphics());
  }

  public void run() {
    for (;;) {
      Event e = mouseEvent.get();
      if (e.id == Event.MOUSE_DOWN) {
	if (curve.selectPoint(e.x,e.y) == -1) { /*no point selected add new one*/
	  curve.addPoint(e.x,e.y);
	  update();
	}
      } else if (e.id == Event.MOUSE_DRAG) {
	curve.setPoint(e.x,e.y);
	  update();
      } else if (e.id == Event.MOUSE_UP) {
	if(e.shiftDown()) {
	  curve.removePoint(); //Shift Click removes control points
	  update();
	}
      }
    }
  }


}

