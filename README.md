City Transportation MST — Assignment 3 (Prim vs Kruskal)

Student: Shugyla Akhit
Group: SE-2403
Course: Design & Analysis of Algorithms (DAA)
Topic: Optimization of a City Transportation Network using Minimum Spanning Tree (MST)

1) Problem & Objective

A city plans to connect all districts with roads so that:

every district is reachable from any other (connected), and

the total construction cost is minimal.

This is exactly the Minimum Spanning Tree problem on a weighted undirected graph:

vertices = districts, edges = potential roads, weight = road cost.

Goals of the assignment

Implement Prim’s and Kruskal’s algorithms for MST.

For each graph & algorithm, record:

the MST edge list,

the MST total cost,

numbers of vertices and edges in the original graph,

the operations count (key steps),

the execution time (ms).

Compare the algorithms and conclude when each is preferable.

2) Project Structure
mst-city/
├─ pom.xml
├─ README.md                       <-- this document
├─ defense_notes.txt               <-- short oral defense cheat-sheet
├─ data/
│  └─ ass_3_input.json            <-- input graphs (districts/roads)
├─ out/
│  └─ ass_3_output.json           <-- generated results per graph
└─ src/main/java/edu/aitu/daa/mst/
   ├─ MainApp.java                 <-- entry point + I/O + metrics
   ├─ Graph.java                   <-- graph: nodes, edges, name→index
   ├─ Edge.java                    <-- (u,v,w) + original names
   ├─ Prim.java                    <-- Prim with binary heap (PQ)
   ├─ Kruskal.java                 <-- Kruskal with DSU (union-find)
   ├─ DSU.java                     <-- disjoint set union
   └─ JsonIo.java                  <-- tiny JSON reader/writer

Design goals: clean, readable, student-friendly code; minimal dependencies; easy to defend.

3) Input Format (data/ass_3_input.json)

The file contains an array of graphs. Each graph has:

id — integer identifier,

nodes — list of vertex names (strings),

edges — list of objects { "from": "...", "to": "...", "weight": <int> }.

Example:
{
  "graphs": [
    {
      "id": 1,
      "nodes": ["A","B","C","D"],
      "edges": [
        {"from":"A","to":"B","weight":3},
        {"from":"A","to":"C","weight":5},
        {"from":"B","to":"C","weight":1},
        {"from":"C","to":"D","weight":4}
      ]
    }
  ]
}

JsonIo is a compact parser tailored to this template (keeps the project lightweight).

4) Output Format (out/ass_3_output.json)

After running, results are written as:
{
  "results": [
    {
      "graph_id": 1,
      "input_stats": {"vertices": 10, "edges": 15},
      "prim": {
        "mst_edges": [{"from":"A","to":"B","weight":3}, ...],
        "total_cost": 37,
        "operations_count": 124,
        "execution_time_ms": 0.27
      },
      "kruskal": {
        "mst_edges": [{"from":"B","to":"C","weight":1}, ...],
        "total_cost": 37,
        "operations_count": 108,
        "execution_time_ms": 0.19
      }
    }
  ]
}

Key rule: prim.total_cost == kruskal.total_cost for each connected graph (MST sum is unique; structure can differ).

6) Algorithms & Complexity
Prim (binary heap, adjacency lists)

Builds the tree by growing from a start vertex; picks the minimum outgoing edge each step using a priority queue.

Complexity: O(E log V) with a binary heap (typical in practice).

Good when:

graph is stored as adjacency lists,

you’ll run from a seed many times,

dense graphs with an adjacency matrix (then O(V²) variant is simple).

Kruskal (sort edges + DSU)

Sorts all edges by weight; adds an edge if it connects two different components (checked by DSU).

Complexity: sorting dominates, O(E log E) ≈ O(E log V); DSU ops are near-constant amortized.

Good when:

graph is naturally an edge list,

you want a forest on a disconnected graph,

easy correctness argument + simple code.

Why do both give the same cost?
MST total weight is unique for a given connected graph (though the exact chosen edges can differ if there are equal weights).

7) Metrics (what is counted)

vertices / edges — from the input graph.

operations_count

Prim: counts key PQ interactions (poll/push) & relax checks during the growth.

Kruskal: counts edge inspections + DSU’s find/union (summed).
These are comparable within one algorithm and indicative across algorithms (not a formal instruction-count).

execution_time_ms — wall-clock duration of the algorithm call (System.nanoTime), converted to milliseconds.

8) Validation & Edge Cases

Connectedness: Both algorithms assume the graph is connected to produce a single MST. Kruskal gracefully produces a forest if disconnected (not required here but useful).

Multiple equal-weight edges: Allowed; cost stays correct; exact edge set may differ between Prim & Kruskal.

Self-loops / duplicates: Not expected in the template; if present, they won’t be chosen in MST.

Input sanitization: JsonIo maps vertex names to indices and preserves readable names for output.

9) Results Interpretation (how to read out/ass_3_output.json)

For each graph_id:

Compare prim.total_cost vs kruskal.total_cost → must match.

Look at operations_count and execution_time_ms → use them to discuss performance trends:

When E ≈ V, both are fast; Prim’s PQ is light, Kruskal’s sort is small.

As E grows, Kruskal’s sort often dominates; Prim grows with the number of pushes/pops.

Dense graphs (E close to V²) favor Prim with adjacency matrix (O(V²)) or a well-tuned heap approach.
