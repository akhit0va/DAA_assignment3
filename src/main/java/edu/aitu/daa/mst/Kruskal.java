
package edu.aitu.daa.mst;

import java.util.*;

public class Kruskal {
    public static class Result {
        public List<Edge> edges = new ArrayList<>();
        public int totalCost = 0;
        public int ops = 0;
    }

    public static Result mst(Graph g) {
        int n = g.nodes.size();
        DSU dsu = new DSU(n);
        List<Edge> sorted = new ArrayList<>(g.edges);
        Collections.sort(sorted);
        Result res = new Result();
        for (Edge e : sorted) {
            res.ops++;
            if (dsu.union(e.u, e.v)) {
                res.edges.add(e);
                res.totalCost += e.w;
                if (res.edges.size() == n - 1) break;
            }
        }
        res.ops += dsu.ops;
        return res;
    }
}
