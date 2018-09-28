/*******************************************************************************
 * Copyright (C) 2018 Rajnish R Lal <rajnishlal@gmail.com>
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package graph.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import graph.Edge;
import graph.Graph;
import graph.Path;
import graph.UndirectedGraph;
import graph.Vertex;
import graph.error.VertexAlreadyExistsException;
import graph.error.VertexDoesNotExistException;

/**
 * Tests for undirected graph traversal
 */
class UndirectedGraphTraversal {

	Graph createGraph1() {
		UndirectedGraph dg = new UndirectedGraph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		try {
			dg.addVertex(v1);
			dg.addVertex(v2);
			dg.addVertex(v3);
			dg.addVertex(v4);
		} catch (VertexAlreadyExistsException ex) {
			fail(ex.getMessage());
		}

		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);
		Edge e3 = new Edge(v3, v4);

		try {
			dg.addEdge(e1);
			dg.addEdge(e2);
			dg.addEdge(e3);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		return dg;
	}

	@Test
	void testConnectivity() {
		Graph dg = createGraph1();
		List<Path> gp = dg.getPaths();
		assertTrue(gp.size() == 12, "Got " + gp.size() + " instead of 12 paths");
		final int con = dg.calculateConnectivity();
		assertTrue(con == 100, "Got " + con + " instead of 100");
	}

	@Test
	void testShortestPath() {
		UndirectedGraph dg = new UndirectedGraph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		try {
			dg.addVertex(v1);
			dg.addVertex(v2);
			dg.addVertex(v3);
			dg.addVertex(v4);
		} catch (VertexAlreadyExistsException ex) {
			fail(ex.getMessage());
		}

		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);
		Edge e3 = new Edge(v3, v4);
		Edge e4 = new Edge(v2, v4);

		try {
			dg.addEdge(e1);
			dg.addEdge(e2);
			dg.addEdge(e3);
			dg.addEdge(e4);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		Path sp;
		try {
			sp = dg.findShortestPath(v1, v4);
			assertTrue(sp.getLength() == 2, "Got " + sp.getLength() + " instead of 2");
		} catch (VertexDoesNotExistException e) {
			fail(e.getMessage());
		}

	}

	@Test
	void testWeightedShortestPath() {
		UndirectedGraph dg = new UndirectedGraph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		try {
			dg.addVertex(v1);
			dg.addVertex(v2);
			dg.addVertex(v3);
			dg.addVertex(v4);
		} catch (VertexAlreadyExistsException ex) {
			fail(ex.getMessage());
		}

		Edge e1 = new Edge(v1, v2, 10, "1->2");
		Edge e2 = new Edge(v2, v3, 20, "2->3");
		Edge e3 = new Edge(v3, v4, 20, "3->4");
		Edge e4 = new Edge(v2, v4, 50, "2->4");

		try {
			dg.addEdge(e1);
			dg.addEdge(e2);
			dg.addEdge(e3);
			dg.addEdge(e4);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		Path sp;
		try {
			sp = dg.findShortestPath(v1, v4);
			assertTrue(sp.getLength() == 50, "Got " + sp.getLength() + " instead of 50");
		} catch (VertexDoesNotExistException e) {
			fail(e.getMessage());
		}

	}

}
