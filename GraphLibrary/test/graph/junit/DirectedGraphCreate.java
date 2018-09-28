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

import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Test;

import graph.Graph;
import graph.Edge;
import graph.Vertex;
import graph.error.EdgeAlreadyExistsException;
import graph.error.VertexAlreadyExistsException;
import graph.error.VertexDoesNotExistException;

/**
 * Tests for a graph containing edges with direction
 */
class DirectedGraphCreate {

	Graph createGraph1() {
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
	void testNormalCase() {
		createGraph1();
	}

	@Test
	void testXML() {
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

		String dgxml = "";
		try {
			dgxml = dg.convertToXML();
		} catch (JAXBException e) {
			fail(e.getMessage());
		}

		Graph dg1 = null;
		try {
			dg1 = Graph.createFromXML(dgxml);
		} catch (JAXBException e) {
			fail(e.getMessage());
		}
		assertTrue(dg.equals(dg1), "XML conversion did not create graph");
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
	}

	@Test
	void testFindEdgesForVertexCase() {
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
			fail(ex.getMessage());
		}

		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);
		Edge e3 = new Edge(v2, v4);

		try {
			dg.addEdge(e1);
			dg.addEdge(e2);
			dg.addEdge(e3);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			List<Edge> efv1 = dg.findEdgesForVertex(v1);
			assertTrue(efv1.contains(e1), "Edge e1 for vertex v1");
			List<Edge> efv2 = dg.findEdgesForVertex(v2);
			assertTrue(efv2.contains(e2), "Edge e2 for vertex v2");
			assertTrue(efv2.contains(e3), "Edge e3 for vertex v2");
			List<Edge> efv3 = dg.findEdgesForVertex(v3);
			assertTrue(efv3.isEmpty(), "No edge for vertex v3");
			List<Edge> efv4 = dg.findEdgesForVertex(v4);
			assertTrue(efv4.isEmpty(), "No edge for vertex v4");
		} catch (VertexDoesNotExistException ex) {
			fail(ex.getMessage());
		}
	}

}
