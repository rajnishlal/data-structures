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

import java.util.List;

import graph.error.EdgeAlreadyExistsException;
import graph.error.EdgeDoesNotExistException;
import graph.error.VertexDoesNotExistException;

/**
 * 
 */

/**
 * 
 *
 */
public class UndirectedGraph extends Graph {

	/**
	 * Undirected graph represents graphs with edges that do not have a direction,
	 * example if a graph has two vertices (A, B) then an edge traversal for A - B
	 * would be bi-directional i.e. A -> B and B -> A are both allowed.
	 */
	public UndirectedGraph() {
		super();
	}

	/**
	 * @param vList
	 * @param eList
	 */
	public UndirectedGraph(List<Vertex> vList, List<Edge> eList) {
		super(vList, eList);
	}

	/**
	 * @see graph.Graph#addEdge(graph.Edge)
	 */
	@Override
	public synchronized void addEdge(Edge e) throws VertexDoesNotExistException, EdgeAlreadyExistsException {
		if (e != null) {
			super.addEdge(e);
			Vertex ev1 = e.getV1();
			Vertex ev2 = e.getV2();
			Edge rev = new Edge(ev2, ev1, 1, ev2.getVname() + "->" + ev1.getVname());
			super.addEdge(rev);
		}
	}

	/**
	 * @see graph.Graph#removeEdge(graph.Edge)
	 */
	@Override
	public synchronized void removeEdge(Edge e) throws EdgeDoesNotExistException {
		super.removeEdge(e);
		super.removeEdge(new Edge(e.getV2(), e.getV1()));
	}

}
