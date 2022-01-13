package graphics;

import javax.swing.JFrame;
import maze.*;

public class Test {
	public static final int WIDTH  = 45;
	public static final int HEIGHT = 45;
	public static final String TITLE = "Guess";

	public static void main(String[] args) {
		Display display = new Display(WIDTH, HEIGHT, 10, 5);
		JFrame frame = new JFrame();
		frame.add(display);
		frame.addKeyListener(display);
		//MouseHandler mh = new MouseHandler();
		//frame.addMouseListener(mh);
		//frame.addMouseMotionListener(mh);
		frame.setUndecorated(false);
		frame.pack();
		frame.setSize(display.width(), display.height());
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		display.run();
		
		/*Maze maze = new Maze(101, 101, new Point(50, 50));
		long time = System.nanoTime();
		maze.generate();
		System.out.println("Time taken to generate maze: " + ((double) System.nanoTime() - time) / 1000000);
		time = System.nanoTime();
		maze.getPathBetween(new Point(0, 0), new Point(50, 50));
		System.out.println("Time taken to solve maze: " + ((double) System.nanoTime() - time) / 1000000);*/
	}
}
