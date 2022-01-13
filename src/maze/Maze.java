package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
	private int width, height;
	private Node[][] nodes;
	private Random random;
	private Node start;
	
	public Maze(int width, int height, Point startingPoint) {
		this.width = width;
		this.height = height;
		this.nodes = new Node[this.width][this.height];
		for (int x = 0; x < this.width; x++) for (int y = 0; y < this.height; y++) this.nodes[x][y] = new Node(new Point(x, y));
		this.random = new Random();
		this.start = this.nodes[startingPoint.x][startingPoint.y];
	}
	
	public Maze(Maze that) {
		this(that.width, that.height, that.start.point);
	}
	
	public void generate() {
		
		Node current = this.start;
		
		while (current != null) {
			current.visit();
			List<Node> possibleNodes = new ArrayList<Node>();
			Node node;
			int dx = current.point.x + 1;
			if (dx < this.width) {
				node = this.nodes[dx][current.point.y];
				if (!node.visited) possibleNodes.add(node);
			}
			dx = current.point.x - 1;
			if (dx > -1) {
				node = this.nodes[dx][current.point.y];
				if (!node.visited) possibleNodes.add(node);
			}
			
			int dy = current.point.y + 1;
			if (dy < this.height) {
				node = this.nodes[current.point.x][dy];
				if (!node.visited) possibleNodes.add(node);
			}
			dy = current.point.y - 1;
			if (dy > -1) {
				node = this.nodes[current.point.x][dy];
				if (!node.visited) possibleNodes.add(node);
			}
			
			if (possibleNodes.size() == 0) current = current.parent;
			else {
				Node choice = possibleNodes.get(random.nextInt(possibleNodes.size()));
				current.children.add(choice);
				choice.parent = current;
				current = choice;
			}
		}
	}
	
	public void reset() {
		for (var i : this.nodes) for (var n : i) n.reset();
	}
	
	private static Node invertAccordingTo(Node node, Node parent) {
		if (node != null) {
			node.children.add(invertAccordingTo(node.parent, node));
			for (int i = 0; i < node.children.size(); i++) if (node.children.get(i) == null) {
				node.children.remove(i);
				i--;
			}
			node.parent = parent;
		}
		return node;
	}
	
	public List<Point> getPathBetween(Point a, Point b) {
		List<Point> path = new ArrayList<Point>();
		this.start = invertAccordingTo(this.nodes[b.x][b.y], null);
		Node node = this.nodes[a.x][a.y];
		while (node != null) {
			path.add(node.point);
			node = node.parent;
		}
		return path;
	}
	
	public int width() {
		return this.width;
	}
	
	public int height() {
		return this.height;
	}
	
	public Node[][] nodes(){
		return this.nodes;
	}
}
