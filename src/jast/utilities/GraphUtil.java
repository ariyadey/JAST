package jast.utilities;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

//Singleton design pattern
public class GraphUtil {
    private static Graph graph = null;

    private GraphUtil() {

    }

    public static Graph getGraphInstance(String id) {
        if (graph == null) {
            graph = new SingleGraph(id);
        }
        return graph;
    }
}
