import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyPressEvent implements KeyListener{

	public boolean kPressed;

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		kPressed = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		kPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
