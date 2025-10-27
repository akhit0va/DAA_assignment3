
package edu.aitu.daa.mst;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MainApp {

    public static void main(String[] args) throws Exception {
        // if IDE didn't pass args, use safe defaults
        if (args == null || args.length == 0) {
            System.out.println("no args detected -> using defaults");
            args = new String[] {
                    "run", "-in", "data/ass_3_input.json", "-out", "out/ass_3_output.json"
            };
        }


        Map<String, String> arg = parseArgs(args);
        arg.putIfAbsent("cmd", "run");

        if (!"run".equals(arg.getOrDefault("cmd",""))) {
            System.out.println("usage: run -in data/ass_3_input.json -out out/ass_3_output.json");
            return;
        }
        String inPath = arg.getOrDefault("in", "data/ass_3_input.json");
        String outPath = arg.getOrDefault("out", "out/ass_3_output.json");

        // read input graphs from JSON (very small hand-made parser)
        String json = Files.readString(Path.of(inPath));
        List<Graph> graphs = JsonIo.readGraphs(json);

        List<Map<String, Object>> results = new ArrayList<>();
        for (Graph g : graphs) {
            Map<String, Object> one = new LinkedHashMap<>();
            one.put("graph_id", g.id);
            Map<String, Object> stats = new LinkedHashMap<>();
            stats.put("vertices", g.nodes.size());
            stats.put("edges", g.edges.size());
            one.put("input_stats", stats);

            // prim
            long t1 = System.nanoTime();
            Prim.Result pr = Prim.mst(g);
            long t2 = System.nanoTime();

            Map<String, Object> primRes = new LinkedHashMap<>();
            primRes.put("mst_edges", edgesAsMaps(pr.edges));
            primRes.put("total_cost", pr.totalCost);
            primRes.put("operations_count", pr.ops);
            primRes.put("execution_time_ms", (t2 - t1) / 1_000_000.0);
            one.put("prim", primRes);

            // kruskal
            long k1 = System.nanoTime();
            Kruskal.Result kr = Kruskal.mst(g);
            long k2 = System.nanoTime();

            Map<String, Object> krs = new LinkedHashMap<>();
            krs.put("mst_edges", edgesAsMaps(kr.edges));
            krs.put("total_cost", kr.totalCost);
            krs.put("operations_count", kr.ops);
            krs.put("execution_time_ms", (k2 - k1) / 1_000_000.0);
            one.put("kruskal", krs);

            results.add(one);
        }

        String outJson = JsonIo.writeResults(results);
        Files.createDirectories(Path.of(outPath).getParent());
        Files.writeString(Path.of(outPath), outJson);
        System.out.println("written: " + outPath);
    }

    static List<Map<String, Object>> edgesAsMaps(List<Edge> edges) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Edge e : edges) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("from", e.uName);
            m.put("to", e.vName);
            m.put("weight", e.w);
            list.add(m);
        }
        return list;
    }

    static Map<String, String> parseArgs(String[] args) {
        Map<String, String> m = new HashMap<>();
        if (args.length > 0) m.put("cmd", args[0]);
        for (int i = 1; i < args.length - 1; i++) {
            if (args[i].startsWith("-")) {
                String key = args[i].substring(1);
                String val = args[i+1];
                m.put(key, val);
                i++;
            }
        }
        return m;
    }
}
