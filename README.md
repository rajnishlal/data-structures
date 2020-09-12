# Data Structures

## Graph Library (Java)

The "GraphLibrary" project under "data-structures" repository contains a Java representation of a Graph class. A Graph has two types of elements, the first is vertices represented by Vertex and second edges represented by Edge. In this implementation each vertex can have a name and each edge is made up of two vertices (v1, v2). The edge has direction from vertex v1 to vertex v2 and can optionally have a weight and/or label. Undirected graph is a graph where edges are bi-directional, example if an undirected graph has vertices A, B then an edge (A, B) would be equivalent to two edges with direction (A, B) & (B, A). In this project a connected vertex is one that has a path to all other vertices in the graph.
There are JUnit tests that demonstrate the creation of graphs using the above mentioned classes, along with operations to add vertices/edges, manipulate (add/ remove edges and vertices), retrieve edges for a vertex, find all paths between two vertices and finding shortest path. There are two ways to get connectivity information about the graph:

1. calculateConnectivity - returns percentage connectivity value
2. getConnectedVertices - returns a list of connected vertices in graph

This graph library has the following features implemented:

* Allow edges to have a direction.
* Allow edges to have labels.
* Allow edges to have weights.
* Allow the graph to be stored on disk, this is by converting graph to XML format and recreating an instance using XML (see JUnit tests for example).
* Allow the graph to be manipulated and queried safely from multiple threads.
* Finding shortest path is available for both graphs and JUnit tests show cases for normal as well as weighted edges.


