/*******************************************************************************
 * Copyright (C) 2018 Rajnish R Lal <rajnishlal@gmail.com>
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package graph;

import java.util.ArrayList;
import java.util.List;

import graph.error.EdgeAlreadyExistsException;
import graph.error.EdgeDoesNotExistException;
import graph.error.VertexAlreadyExistsException;
import graph.error.VertexDoesNotExistException;

/**
 * 
 * Graph library class to represent a graph. This is a directed graph, edge
 * direction is from vertex v1 to vertex v2.
 *
 */
public class Graph {

	private final List<Vertex> vertices;
	private final List<Edge> edges;

	public Graph() {
		synchronized (this) {
			vertices = new ArrayList<Vertex>();
			edges = new ArrayList<Edge>();
		}
	}

	public Graph(List<Vertex> vList, List<Edge> eList) {
		synchronized (this) {
			vertices = vList;
			edges = eList;
		}
	}

	/**
	 * Validate if vertex is present in graph
	 * 
	 * @param v vertex
	 * @throws VertexDoesNotExistException
	 */
	protected synchronized void validateVertexInGraph(Vertex v) throws VertexDoesNotExistException {
		if (v == null || !vertices.contains(v)) {
			throw new VertexDoesNotExistException(v);
		}
	}

	/**
	 * Validate if edge is present in graph
	 * 
	 * @param v edge
	 * @throws EdgeDoesNotExistException
	 */
	protected synchronized void validateEdgeInGraph(Edge e) throws EdgeDoesNotExistException {
		if (e == null || !edges.contains(e)) {
			throw new EdgeDoesNotExistException(e);
		}
	}

	/**
	 * Add a vertex
	 * 
	 * @param v vertex to be added
	 * @throws VertexAlreadyExistsException
	 */
	public synchronized void addVertex(Vertex v) throws VertexAlreadyExistsException {
		if (v != null) {
			for (Vertex ev : vertices) {
				if (ev.equals(v)) {
					throw new VertexAlreadyExistsException(v);
				}
			}
			vertices.add(v);
		}
	}

	/**
	 * Remove a vertex
	 * 
	 * @param v vertex to be removed
	 */
	public synchronized void removeVertex(Vertex v) throws VertexDoesNotExistException {
		List<Edge> rem = findEdgesContainingVertex(v);
		for (Edge re : rem) {
			try {
				removeEdge(re);
			} catch (EdgeDoesNotExistException ex) {
				System.out.println("WARNING: Unable to remove edge that does not exist:\n" + ex.getMessage());
			}
		}
		vertices.remove(v);
	}

	/**
	 * Find a list of edges that have vertex v as edge vertex v1
	 * 
	 * @param v vertex
	 * @return list of edges with v1 equal to v
	 * @throws VertexDoesNotExistException
	 */
	public List<Edge> findEdgesForVertex(Vertex v) throws VertexDoesNotExistException {
		validateVertexInGraph(v);
		// Find all edges for vertex
		ArrayList<Edge> ret = new ArrayList<Edge>();
		for (Edge ee : edges) {
			if (ee.getV1().equals(v)) {
				ret.add(ee);
			}
		}
		return ret;
	}

	/**
	 * Find a list of edges that have vertex v as edge vertex v1 or v2
	 * 
	 * @param v vertex
	 * @return list of edges with v1 or v2 equal to v
	 * @throws VertexDoesNotExistException
	 */
	protected synchronized List<Edge> findEdgesContainingVertex(Vertex v) throws VertexDoesNotExistException {
		validateVertexInGraph(v);
		// Find all edges that contain this vertex
		ArrayList<Edge> ret = new ArrayList<Edge>();
		for (Edge ee : edges) {
			if (ee.getV1().equals(v) || ee.getV2().equals(v)) {
				ret.add(ee);
			}
		}
		return ret;
	}

	/**
	 * Add an edge
	 * 
	 * @param e edge to be added
	 * @throws EdgeAlreadyExistsException
	 * @throws VertexDoesNotExistException
	 */
	public synchronized void addEdge(Edge e) throws VertexDoesNotExistException, EdgeAlreadyExistsException {
		if (e != null) {
			validateVertexInGraph(e.getV1());
			validateVertexInGraph(e.getV2());
			if (edges.contains(e)) {
				throw new EdgeAlreadyExistsException(e);
			}
			edges.add(e);
		}
	}

	/**
	 * Remove an edge
	 * 
	 * @param e edge to be removed
	 * @throws EdgeDoesNotExistException
	 */
	public synchronized void removeEdge(Edge e) throws EdgeDoesNotExistException {
		validateEdgeInGraph(e);
		edges.remove(e);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder br = new StringBuilder("Graph:\n\tVertices:\n");
		for (Vertex ev : vertices) {
			br.append("\t" + ev + "\n");
		}
		br.append("\tEdges:\n");
		for (Edge ee : edges) {
			br.append("\t" + ee + "\n");
		}
		return br.toString();
	}

}
