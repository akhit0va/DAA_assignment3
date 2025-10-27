
package edu.aitu.daa.mst;

public class Edge implements Comparable<Edge> {
    public final int u, v, w;
    public final String uName, vName;
    public Edge(int u, int v, int w, String uName, String vName) {
        this.u = u; this.v = v; this.w = w;
        this.uName = uName; this.vName = vName;
    }
    @Override public int compareTo(Edge o) { return Integer.compare(this.w, o.w); }
}
