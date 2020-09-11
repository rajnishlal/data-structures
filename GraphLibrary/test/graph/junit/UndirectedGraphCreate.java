/*******************************************************************************
 * Copyright (C) 2020 Rajnish R Lal <rajnishlal@gmail.com>
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import jakarta.xml.bind.JAXBException;

import org.junit.jupiter.api.Test;

import graph.Edge;
import graph.Graph;
import graph.UndirectedGraph;
import graph.Vertex;
import graph.error.VertexAlreadyExistsException;

/**
 * Tests for a graph containing edges with no direction
 */
class UndirectedGraphCreate {

	@Test
	void testCreateAndXML() {
		UndirectedGraph ug = new UndirectedGraph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		try {
			ug.addVertex(v1);
			ug.addVertex(v2);
			ug.addVertex(v3);
			ug.addVertex(v4);
		} catch (VertexAlreadyExistsException ex) {
			fail(ex.getMessage());
		}

		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);
		Edge e3 = new Edge(v3, v4);

		try {
			ug.addEdge(e1);
			ug.addEdge(e2);
			ug.addEdge(e3);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		String ugxml = "";
		try {
			ugxml = ug.convertToXML();
		} catch (JAXBException e) {
			fail(e.getMessage());
		}

		Graph ug1 = null;
		try {
			ug1 = Graph.createFromXML(ugxml);
		} catch (JAXBException e) {
			fail(e.getMessage());
		}
		assertTrue(ug.equals(ug1), "XML conversion did not create graph");
	}

}
