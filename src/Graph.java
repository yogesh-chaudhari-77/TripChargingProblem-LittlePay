import java.util.HashMap;

/**
 * Graph implementation using hashmap using adjacency list
 * @author Yogeshwar Chaudhari
 */
public class Graph {

	HashMap<String, HashMap<String, Double>> nodes = null;
	
	public Graph() {
		nodes = new HashMap<String, HashMap<String, Double>>();
	}
	
	/**
	 * 
	 * @param startPoint : String
	 * @param endPoint : String
	 * @param value : double : weight of the edge
	 */
	public void addEdge(String startPoint, String endPoint, double value) {
		
		// Check if the node is part of the graph
		if ( this.getNodes().containsKey(startPoint) == false ) {
			this.getNodes().put(startPoint, new HashMap<String, Double>());
		}
		
		// Now connect startPoint with the endPoint and associate the weight representing the price
		
		this.getNodes().get(startPoint).put(endPoint, value);
		
	}

	@Override
	public String toString() {

		StringBuilder graphString = new StringBuilder();
		
		for(String node : this.getNodes().keySet()) {
			
			graphString.append(node).append(" ==> ");
			
			HashMap<String, Double> linkedNodes = this.getNodes().get(node);
			
			for(String linkedNode : linkedNodes.keySet()) {
				graphString.append("[").append(linkedNode).append(",").append(linkedNodes.get(linkedNode)).append("]").append(",");
			}
			
			graphString.append("\n");
			
		}
		
		return graphString.toString();
	}
	
	// Getters and Setters
	public HashMap<String, HashMap<String, Double>> getNodes() {
		return nodes;
	}

	public void setNodes(HashMap<String, HashMap<String, Double>> nodes) {
		this.nodes = nodes;
	}

}
