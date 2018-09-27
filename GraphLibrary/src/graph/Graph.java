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
 */

/**
 * 
 * Graph library class to represent a graph. Making this abstract as it could be
 * directed or undirected.
 * 
 * 
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

	Graph(List<Vertex> vList, List<Edge> eList) {
		synchronized (this) {
			vertices = vList;
			edges = eList;
		}
	}

	/**
	 * @param v
	 * @throws VertexDoesNotExistException
	 */
	protected synchronized void validateVertexInGraph(Vertex v) throws VertexDoesNotExistException {
		if (v == null || !vertices.contains(v)) {
			throw new VertexDoesNotExistException(v);
		}
	}

	/**
	 * @param v
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
		ArrayList<Edge> rem = findEdgesContainingVertex(v);
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
	 * @param v
	 * @return
	 * @throws VertexDoesNotExistException
	 */
	public synchronized ArrayList<Edge> findEdgesContainingVertex(Vertex v) throws VertexDoesNotExistException {
		validateVertexInGraph(v);
		// Find all edges that contain this vertex
		ArrayList<Edge> rem = new ArrayList<Edge>();
		for (Edge ee : edges) {
			if (ee.getV1().equals(v) || ee.getV2().equals(v)) {
				rem.add(ee);
			}
		}
		return rem;
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
		br.append("Edges:\n");
		for (Edge ee : edges) {
			br.append("\t" + ee + "\n");
		}
		return br.toString();
	}

}
