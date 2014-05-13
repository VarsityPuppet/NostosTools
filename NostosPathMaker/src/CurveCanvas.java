/**
 * Canvas for painting controlled curves into
 */
import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CurveCanvas extends Canvas implements Runnable, MouseListener, MouseMotionListener,
		KeyListener {

	Image offscreen;
	ControlCurve curve;
	MouseEvent mouseEvent;
	private boolean kPressed;
	private boolean shiftPressed;

	public CurveCanvas(MouseEvent mouseEvent, ControlCurve curve) {
		this.mouseEvent = mouseEvent;
		this.curve = curve;

		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void paint(Graphics g) {
		if (offscreen == null) {
			offscreen = createImage(size().width, size().height);
		}

		// g.fillRect(, y, width, height);
		Graphics og = offscreen.getGraphics();
		og.clearRect(0, 0, size().width, size().height);

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

		g.drawImage(offscreen, 0, 0, null);

	}

	public void update(Graphics g) {
		paint(g);
	}

	public void update() {
		update(getGraphics());
	}

	public void run() {

		Event e = mouseEvent.get();
		if (e.id == Event.MOUSE_DOWN) {

		} else if (e.id == Event.MOUSE_DRAG) {

		} else if (e.id == Event.MOUSE_UP) {
			if (e.shiftDown()) {

			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyChar() == 'k')
			kPressed = true;

		if(arg0.isShiftDown())
			shiftPressed = true;

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyChar() == 'k')
			kPressed = false;
		if(!arg0.isShiftDown())

			shiftPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Create Key
		if(kPressed && curve.selectKeyPoint(e.getX(), e.getY()) == -1){
				curve.addKeyPoint(e.getX(), e.getY());
		}
		if (!kPressed && curve.selectPoint(e.getX(), e.getY()) == -1 &&
				curve.selectKeyPoint(e.getX(), e.getY()) == -1)
			curve.addPoint(e.getX(), e.getY());

		update();
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(shiftPressed)
			curve.removePoint(); // Shift Click removes control points
		update();
	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		//if(curve.selectKeyPoint(e.getX(), e.getY()) == -1)
			curve.setPoint(e.getX(), e.getY());
		//else if(curve.selectPoint(e.getX(), e.getY()) == -1)
			curve.setKeyPoint(e.getX(), e.getY());
		update();
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
