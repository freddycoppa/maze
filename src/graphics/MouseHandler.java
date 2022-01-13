package graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import maze.Point;

public class MouseHandler extends MouseAdapter implements MouseListener, Runnable {
	public static Point cellSelected;
	
	private Thread thread;
	private MouseEvent e;
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.thread = new Thread(this);
		this.e = e;
		this.thread.start();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			thread.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		e.consume();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void run() {
		System.out.println("Mouse clicked!");
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = (e.getX() - 5) / 15;
			int y = (e.getY() - 5) / 15;
			if (MouseHandler.cellSelected != null) {
				if ((x == MouseHandler.cellSelected.x) && (y == MouseHandler.cellSelected.y)) MouseHandler.cellSelected = null;
				else {
					MouseHandler.cellSelected.x = x;
					MouseHandler.cellSelected.y = y;
				}
			}
			else MouseHandler.cellSelected = new Point(x, y);
		}
	}
}
