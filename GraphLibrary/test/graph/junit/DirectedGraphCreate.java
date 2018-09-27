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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import graph.Graph;
import graph.Edge;
import graph.Vertex;
import graph.error.EdgeAlreadyExistsException;
import graph.error.VertexAlreadyExistsException;
import graph.error.VertexDoesNotExistException;

/**
 * 
 */

/**
 * 
 *
 */
class DirectedGraphCreate {

	@Test
	void testNormalCase() {
		Graph dg = new Graph();
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
			fail("Error occured - " + ex.getMessage());
		}

		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);
		Edge e3 = new Edge(v3, v4);

		try {
			dg.addEdge(e1);
			dg.addEdge(e2);
			dg.addEdge(e3);
		} catch (Exception ex) {
			fail("Error occured - " + ex.getMessage());
		}

		System.out.println("Ran directed graph create test successfully.");
	}

	@Test
	void testDuplicate() {
		Graph dg = new Graph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);

		try {
			dg.addVertex(v1);
			dg.addVertex(v3);
			dg.addVertex(v2);
			dg.addVertex(v2);
		} catch (VertexAlreadyExistsException ex) {
			assertEquals("Vertex already exists in graph for ID=2, Name='2'.", ex.getMessage());
		}

		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);

		try {
			dg.addEdge(e1);
			dg.addEdge(e2);
			dg.addEdge(e2);
		} catch (EdgeAlreadyExistsException ex) {
			assertEquals("Edge already exists for the two vertices ID=2, Name='2' and ID=3, Name='3'.",
					ex.getMessage());
		} catch (VertexDoesNotExistException ex1) {
			fail(ex1.getMessage());
		}
		System.out.println("Ran directed graph duplicate vertex/edge test successfully.");
	}

	@Test
	void testRemove() {
		Graph dg = new Graph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);

		try {
			dg.addVertex(v1);
			dg.addVertex(v2);
			dg.removeVertex(v2);
			dg.addVertex(v2);
			dg.addVertex(v3);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);

		try {
			dg.addEdge(e1);
			dg.removeEdge(e1);
			dg.addEdge(e1);
			dg.addEdge(e2);
			dg.removeVertex(v3);
			dg.addVertex(v3);
			dg.addEdge(e2);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
		System.out.println("Ran directed graph remove vertex/edge test successfully.");
	}

}
