# City Transportation MST — Assignment 3 (Prim vs Kruskal)

**Student:** Abylay Dosymbek · **Group:** SE‑2435

Small, clean Java project that reads a city road network from JSON and builds a Minimum Spanning Tree using **Prim** and **Kruskal**. It saves a compact JSON with edges, total cost, ops count and time.

## How to run (IntelliJ or Maven)
- Java 17
- Open the folder as a Maven project (pom.xml provided).
- Program args:
```
run -in data/ass_3_input.json -out out/ass_3_output.json
```
- Main class: `edu.aitu.daa.mst.MainApp`

## Files
- `data/ass_3_input.json` — input graphs (districts + candidate roads)
- `out/ass_3_output.json` — results per graph (created after run)
- `src/main/java/...` — tiny, readable implementations of Prim and Kruskal

## Notes
- Code aims to be super simple and well‑commented so it’s easy to defend.
- Ops metric = key algorithm steps (PQ pushes/pops for Prim, DSU ops for Kruskal).
- Both algorithms must produce the **same total cost** for each connected graph.
