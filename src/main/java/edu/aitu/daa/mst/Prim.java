
package edu.aitu.daa.mst;

import java.util.*;

public class Prim {
    public static class Result {
        public List<Edge> edges = new ArrayList<>();
        public int totalCost = 0;
        public int ops = 0;
    }

    public static Result mst(Graph g) {
        int n = g.nodes.size();
        List<List<Edge>> adj = new ArrayList<>();
        for (int i=0;i<n;i++) adj.add(new ArrayList<>());
        for (Edge e : g.edges) {
            adj.get(e.u).add(e);
            adj.get(e.v).add(new Edge(e.v, e.u, e.w, e.vName, e.uName));
        }
        boolean[] used = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Result res = new Result();

        // start from 0
        used[0] = true;
        pq.addAll(adj.get(0));

        while (!pq.isEmpty() && res.edges.size() < n-1) {
            Edge e = pq.poll();
            res.ops++;
            if (used[e.v]) continue;
            used[e.v] = true;
            res.edges.add(e);
            res.totalCost += e.w;
            for (Edge ne : adj.get(e.v)) {
                res.ops++;
                if (!used[ne.v]) pq.add(ne);
            }
        }
        return res;
    }
}
