/*******************************************************************************
 * Copyright (C) 2018 Rajnish R Lal <rajnishlal@gmail.com>
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
/**
 * 
 */
package graph.junit;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import graph.Edge;
import graph.UndirectedGraph;
import graph.Vertex;
import graph.error.VertexAlreadyExistsException;

/**
 * Tests for a graph containing edges with no direction
 */
class UndirectedGraphCreate {

	@Test
	void test() {
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

		System.out.println("Ran undirected graph create test successfully.");
	}

}
