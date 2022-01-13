package maze;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public Node parent;
	public List<Node> children;
	public Point point;
	public boolean visited;
	
	public Node() {
		this.children = new ArrayList<Node>();
		this.visited = false;
	}
	
	public Node(Point point) {
		this();
		this.point = point;
	}
	
	public void visit() {
		this.visited = true;
	}
	
	public void reset() {
		this.parent = null;
		this.children.clear();
		this.visited = false;
	}
}
