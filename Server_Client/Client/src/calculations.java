import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class calculations extends main implements KeyListener {
	
	public calculations( ) {
		
		
	}

	public void pressed( KeyEvent e ) {
		System.out.println( "Pressed" );
		exit();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		 if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	            System.out.println("Right key pressed");
	            exit();
	        }
	        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	            System.out.println("Left key pressed");
	        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
