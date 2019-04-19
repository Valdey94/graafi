import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Integer;

// Data Structures 2015 Coursework
// Student: Valtteri Ylisalo
// Student Number: 415552
// E-mail: Ylisalo.T.Valtteri@student.uta.fi
// Tasks programmed: Graph (1p)
// Graph implementation: Edge list

class TGraph {

    // Attributes.
    private int[][] graphdata = new int[99][5];  // Graph rendering data for the interface.
    private List vertices;  // Vertex list.
    private List edges;     // Edge list.

    // Constructor.
    public TGraph() {
        vertices = new List();
        edges = new List();
        readData();
        buildGraph();
    }

    // Reads in the content of data.txt.
    public void readData() {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            for (int i = 0; i < 99; i++) {
                line = br.readLine();
                String[] values = line.split(",");
                graphdata[i][0]=Integer.parseInt(values[0]); // X-coordinate of a vertex.
                graphdata[i][1]=Integer.parseInt(values[1]); // Y-coordinate of a vertex.
                graphdata[i][2]=Integer.parseInt(values[2]); // First neighbor of a vertex.
                graphdata[i][3]=Integer.parseInt(values[3]); // Second neighbor of a vertex.
                graphdata[i][4]=Integer.parseInt(values[4]); // Third neighbor of a vertex.	   
            }
        } 
        catch(IOException e) {
            System.out.println("File not found.");
        }
    }

    // Builds graph from a file.
    public void buildGraph() {    
	    // Prints the content of data.txt on the command line.
        for(int i = 0; i < 99; i++) {
            System.out.print(graphdata[i][0]);
            System.out.print(",");
            System.out.print(graphdata[i][1]);
            System.out.print(",");
            System.out.print(graphdata[i][2]);
            System.out.print(",");
            System.out.print(graphdata[i][3]);
            System.out.print(",");
            System.out.print(graphdata[i][4]);
            System.out.print("\n");
        }

        // Creates all vertices defined in the file.
        for (int i = 1; i < 100; i++) {
            Vertex vertex = new Vertex(graphdata[i-1][0], graphdata[i-1][1], i);
            vertices.add(vertex);
        }

        // Builds a new list of edges that will contain all edges between the vertices defined in the file.
        for (int i = 1; i < 100; i++) {
            // Define the vertex from where to build the three edges to other vertices.
            Vertex startVertex = (Vertex)vertices.findNode(i-1).node;
            // Create 3 edges for the 3 vertices that are neighbor of the vertex.
            for (int neighbor = 1; neighbor < 4; neighbor++) {
                Vertex endVertex = (Vertex)vertices.findNode(graphdata[i-1][neighbor+1]-1).node;
                // Weight of the edge is calculated with Euclidean distance.
                int weight = countWeight(startVertex, endVertex);
                Edge edge = new Edge(startVertex, endVertex, weight);
                edges.add(edge);
            }
        }
    }

    // Prepares the graph data with vertice and edge data and prints them on the command line.
    public void prepareGraph() {
        // This variable is used to find the right edges for each vertex. It always increments 
        // three times for each vertex because each vertex always has three edges.
        int edgeIndex = 1;
        // Replaces the file-based graph data with data from vertices and edges.
        for (int i = 1; i < 100; i++) {
            // Find the right node and get its position for graphdata.
            Vertex vertex = (Vertex)vertices.findNode(i-1).node;
    	    graphdata[i-1][0] = vertex.x;
            graphdata[i-1][1] = vertex.y;
            // Every vertex in this work has always three edges, so we use a
            // "neighbor" variable that determines the first, second and third
            // neighbor vertex (linked with edge) for each vertex.
            for (int neighbor = 1; neighbor < 4; neighbor++) {
                Edge edge = (Edge)edges.findNode(edgeIndex-1).node;
                Vertex endVertex = edge.endVertex;
                graphdata[i-1][neighbor+1] = endVertex.index;
                edgeIndex++;
            }
        }

        /* This part is probably not required for the exercise but it's done anyway.
         */
        // Prints the data of vertices on the command line.
        int edge = 0;
        for (int vertex = 0; vertex < 99; vertex++) {
            // Prints the data of the vertex.
            System.out.println(vertices.findNode(vertex).node.toString());
            // Prints the data of the three edges of each vertex.
            for (int i = 0; i < 3; i++) {
                System.out.println(edges.findNode(edge).node.toString());
                edge++;
            }
        }
    }

    // Calculates the weight of the edge between the two vertices.
    public int countWeight(Vertex vertex1, Vertex vertex2) {
        // Checks whether the vertices were valid.
        if (vertex1 == null || vertex2 == null)
            return 0;
        // If vertices were valid, the weight of the edge between them is calculated
        // using Euclidean distance.
        else {
            int xDiff = (int)Math.pow((vertex1.x - vertex2.x), 2);
            int yDiff = (int)Math.pow((vertex1.y - vertex2.y), 2);
            int weight = xDiff + yDiff;
            return (int) Math.sqrt(weight);
        }
    }

    // Getter for graph data.
    public int[][] getGraphData() {
        return graphdata;
    }
    
    // Vertex class.
    public class Vertex {		
        // Attributes.
        private int x;         // x-position of vertex.
        private int y;         // y-position of vertex.
        private int index;     // index of vertex.
        private String state;  // state of vertex.

        // Constructor.
        public Vertex(int xValue, int yValue, int i) {
            x(xValue);
            y(yValue);
            index(i);
        }

        // Presents the object in a string form.
        public String toString() {
            return "Vertex index: "+index+", x: "+x+", y: "+y;
        }

        // Accessories.
        public void x(int x) {
            if (x >= 0)
                this.x = x;
        }
        public int x() {
            return x;
        }

        public void y(int y) {
            if (y >= 0)
                this.y = y;
        }
        public int y() {
            return y;
        }

        public void index(int i) {
            // In this work, indexing begins from 1.
            if (i >= 1)
                index = i;
        }
        public int index() {
            return index;
        }

        public void state(String s) {
            if (s.length() >= 0 && s != null)
                state = s;
        }

        public String state() {
            return state;
        }
    }

    // Edge class.
    public class Edge {	
        // Attributes.
        private Vertex startVertex;  // Start vertex (the first end) of the edge.
        private Vertex endVertex;    // End vertex (the other end) of the edge.
        private int weight;          // Weight of the edge.
        private String state;        // State of the eddge.

        // Constructor.
        public Edge(Vertex s, Vertex e, int w) {
            startVertex(s);
            endVertex(e);
            weight(w);
        }

        // Presents the object in a string form.
        public String toString() {
            return "Edge from "+startVertex.index+" to "+endVertex.index+", weight: "+weight;
        }

        // Accessories.
        public void startVertex(Vertex s) {
            if (s != null)
                startVertex = s;
        }
        public Vertex startVertex() {
            return startVertex;
        }

        public void endVertex(Vertex e) {
            if (e != null)
                endVertex = e;
        }
        public Vertex endVertex() {
            return endVertex;
        }

        public void weight(int w) {
            if (w >= 0)
                weight = w;
        }
        public int weight() {
            return weight;
        }
		
        public void state(String s) {
            if (s.length() >= 0 && s != null)
                state = s;
        }
        public String state() {
            return state;
        }
    }

    // Edges are stored in an edgelist in this implementation of graph.
    public class List {	
        // Attributes.
        private ListNode head;  // Head of the list.
        private ListNode tail;  // Tail of the list.

        // Constructor.
        public List() {
            this.head = null;
        }

        // Adds a new node to the end of the list.
        public void add(Object object) {
            ListNode node = new ListNode(object);	
            // If the list is empty, both head and null become node.
            if (isEmpty()) {
                head = node;
                tail = node;
            }
            // Otherwise the next one from tail becomes node and tail becomes node.
            else {
                tail.next = node;
                tail = node;
            }
        }

        // Find the node that's position is the parameter value.
        public ListNode findNode(int position) {
            // Checks if parameter value is valid.
            if (position >= 0) {
                ListNode node = head;
                // Find the right position.
                for (int i = 0; i < position; i++)
                    node = node.next();
                // Return the found node.
                return node;
            }
            // If position was invalid, null is returned.
            else
                return null;
        }

        // Checks whether the list is empty.
        public boolean isEmpty() {
            return head == null;
        }
    }

    // List Node class.
    public class ListNode {
        private Object node;    // Node information.
        private ListNode next;  // Reference to next list node.

        // Constructor.
        public ListNode(Object o) {
            node(o);
            next = null;
        }

        // Accessories.
        public void node(Object n) {
            node = n;
        }
        public Object node() {
            return node;
        }

        public void next(ListNode n) {
            next = n;
        }
        public ListNode next() {
            return next;
        }	
    } 
}