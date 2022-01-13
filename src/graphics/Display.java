package graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.List;
import maze.*;

public class Display extends Canvas implements KeyListener {
	private static final long serialVersionUID = 1L;

	private int width, height;
	private int cellWidth, borderWidth;
	private Maze maze;

	public static final Point origin = new Point(0, 0);
	private Point beginning, end;

	public Display(int width, int height, int cellWidth, int borderWidth) {
		// this is for a decorated frame
		this.width = width * (cellWidth + borderWidth) + borderWidth + 15 - 1;
		this.height = height * (cellWidth + borderWidth) + borderWidth + 35 + 2;
		// for undecorated frame
		/*this.width = width * (cellWidth + borderWidth) + borderWidth;
		this.height = height * (cellWidth + borderWidth) + borderWidth;*/
		this.cellWidth = cellWidth;
		this.borderWidth = borderWidth;
		this.maze = new Maze(width, height, new Point(2 * width / 3, height / 3));
		this.beginning = new Point(0, 0);
		this.end = new Point(width - 1, height - 1);
	}

	public int width() {
		return this.width;
	}

	public int height() {
		return this.height;
	}

	public void run() {
		/*int frames = 0;
		double unprocessedSeconds = 0.0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1.0 / 60.0;
		int tickCount = 0;
		boolean ticked = false;

		for (;;) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;

			while (unprocessedSeconds > secondsPerTick) {
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + " fps");
					previousTime += 1000;
					frames = 0;
				}
			}

			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
		}*/
		
		long elapsedTime = 0;
		int frames = 0;
		
		for (;;) {
			long time = System.nanoTime();
			
			this.render();
			
			frames++;
			elapsedTime += System.nanoTime() - time;
			if (elapsedTime >= 1000000000) {
				System.out.println(frames + " frames rendered in " + elapsedTime / 1000000000.0 + " seconds ");
				elapsedTime = 0;
				frames = 0;
			}
		}
	}

	private boolean g = false;
	private boolean s = false;
	
	private List<Point> solution;

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		Graphics gfx = bs.getDrawGraphics();
		gfx.setColor(Color.BLACK);
		gfx.fillRect(0, 0, this.width, this.height);
		
		if (this.g) {
			this.maze.reset();
			this.maze.generate();
			this.g = false;
			this.s = false;
		}
		
		if (this.s) this.solution = this.maze.getPathBetween(this.beginning, this.end);
		
		for (int x = 0; x < this.maze.width(); x++)
			for (int y = 0; y < this.maze.height(); y++) {
				Node node = this.maze.nodes()[x][y];
				if ((this.s) && (this.solution != null)) {
					if (this.solution.contains(node.point)) gfx.setColor(Color.RED);
					else gfx.setColor(Color.WHITE);
				}
				else gfx.setColor(Color.WHITE);
				gfx.fillRect(x * (cellWidth + borderWidth) + borderWidth, y * (cellWidth + borderWidth) + borderWidth, cellWidth, cellWidth);
				if (node.parent != null) {
					if (node.parent.point.x == node.point.x) {
						if (node.parent.point.y < node.point.y) gfx.fillRect(
									node.point.x * (cellWidth + borderWidth) + borderWidth, 
									(node.parent.point.y + 1) * (cellWidth + borderWidth), 
									cellWidth,
									borderWidth
								);
						else if (node.parent.point.y > node.point.y) gfx.fillRect(
									node.point.x * (cellWidth + borderWidth) + borderWidth, 
									(node.point.y + 1) * (cellWidth + borderWidth), 
									cellWidth, 
									borderWidth
								);
					}
					else if (node.parent.point.y == node.point.y) {
						if (node.parent.point.x < node.point.x) gfx.fillRect(
									(node.parent.point.x + 1) * (cellWidth + borderWidth), 
									node.point.y * (cellWidth + borderWidth) + borderWidth, 
									borderWidth, cellWidth
								);
						else if (node.parent.point.x > node.point.x) gfx.fillRect(
									(node.point.x + 1) * (cellWidth + borderWidth),
									node.point.y * (cellWidth + borderWidth) + borderWidth, 
									borderWidth, 
									cellWidth
								);
					}
				}
			}
		gfx.setColor(Color.GREEN);
		gfx.fillRect(
				this.beginning.x * (cellWidth + borderWidth) + borderWidth, 
				this.beginning.y * (cellWidth + borderWidth) + borderWidth, 
				cellWidth, 
				cellWidth
			);
		gfx.fillRect(
				this.end.x * (cellWidth + borderWidth) + borderWidth, 
				this.end.y * (cellWidth + borderWidth) + borderWidth, 
				cellWidth, 
				cellWidth
			);
		gfx.dispose();
		bs.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'g':
			this.g = !this.g;
			break;
		case 's':
			this.s = !this.s;
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
