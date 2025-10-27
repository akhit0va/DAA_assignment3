
package edu.aitu.daa.mst;

import java.util.*;

public class Graph {
    public final int id;
    public final List<String> nodes = new ArrayList<>();
    public final List<Edge> edges = new ArrayList<>();
    public final Map<String, Integer> idx = new HashMap<>();

    public Graph(int id) { this.id = id; }

    public void addNode(String name) {
        if (!idx.containsKey(name)) {
            idx.put(name, nodes.size());
            nodes.add(name);
        }
    }

    public void addEdge(String from, String to, int w) {
        addNode(from);
        addNode(to);
        int u = idx.get(from);
        int v = idx.get(to);
        edges.add(new Edge(u, v, w, from, to));
    }
}
