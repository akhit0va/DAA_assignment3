
package edu.aitu.daa.mst;

public class DSU {
    int[] p, r;
    public int ops = 0;
    public DSU(int n) {
        p = new int[n]; r = new int[n];
        for (int i = 0; i < n; i++) p[i] = i;
    }
    public int find(int x) {
        ops++;
        if (p[x] == x) return x;
        return p[x] = find(p[x]);
    }
    public boolean union(int a, int b) {
        ops++;
        a = find(a); b = find(b);
        if (a == b) return false;
        if (r[a] < r[b]) { int t=a; a=b; b=t; }
        p[b] = a;
        if (r[a] == r[b]) r[a]++;
        return true;
    }
}
