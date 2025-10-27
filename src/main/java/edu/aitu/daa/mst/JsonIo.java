
package edu.aitu.daa.mst;

import java.util.*;
import java.util.regex.*;

public class JsonIo {

    // tiny JSON reader tailored to given format to keep code short
    public static List<Graph> readGraphs(String json) {
        List<Graph> graphs = new ArrayList<>();
        Pattern gpat = Pattern.compile("\\{\\s*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"nodes\"\\s*:\\s*\\[(.*?)\\]\\s*,\\s*\"edges\"\\s*:\\s*\\[(.*?)\\]\\s*\\}",
                Pattern.DOTALL);
        Matcher gm = gpat.matcher(json);
        while (gm.find()) {
            int id = Integer.parseInt(gm.group(1));
            Graph g = new Graph(id);
            // nodes
            for (String s : gm.group(2).split(",")) {
                String name = s.replaceAll("[^A-Za-z0-9]", "").trim();
                if (!name.isEmpty()) g.addNode(name);
            }
            // edges
            Pattern ep = Pattern.compile("\\{\\s*\"from\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"to\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"weight\"\\s*:\\s*(\\d+)\\s*\\}");
            Matcher em = ep.matcher(gm.group(3));
            while (em.find()) {
                g.addEdge(em.group(1), em.group(2), Integer.parseInt(em.group(3)));
            }
            graphs.add(g);
        }
        return graphs;
    }

    public static String writeResults(List<Map<String, Object>> results) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"results\": [\n");
        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> r = results.get(i);
            sb.append("    ").append(toJson(r));
            if (i + 1 < results.size()) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n}");
        return sb.toString();
    }

    // very small object -> json (only what we need)
    static String toJson(Object o) {
        if (o instanceof Map<?,?> m) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            int k = 0;
            for (var e : m.entrySet()) {
                if (k++ > 0) sb.append(", ");
                sb.append("\"").append(e.getKey()).append("\": ").append(toJson(e.getValue()));
            }
            sb.append("}");
            return sb.toString();
        } else if (o instanceof List<?> l) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i=0;i<l.size();i++) {
                if (i>0) sb.append(", ");
                sb.append(toJson(l.get(i)));
            }
            sb.append("]");
            return sb.toString();
        } else if (o instanceof String s) {
            return "\"" + s + "\"";
        } else {
            return String.valueOf(o);
        }
    }
}
