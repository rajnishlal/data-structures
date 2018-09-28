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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import graph.error.EdgeAlreadyExistsException;
import graph.error.EdgeDoesNotExistException;
import graph.error.VertexAlreadyExistsException;
import graph.error.VertexDoesNotExistException;

@XmlRootElement(name = "graph")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 * Graph library class to represent a graph. This is a directed graph, edge
 * direction is from vertex v1 to vertex v2.
 */
public class Graph {
	@XmlElement(name = "vertex")
	private final List<Vertex> vertices;
	@XmlElement(name = "edge")
	private final List<Edge> edges;
	private List<Path> paths = null;

	/**
	 * Default constructor
	 */
	public Graph() {
		synchronized (this) {
			vertices = new ArrayList<Vertex>();
			edges = new ArrayList<Edge>();
		}
	}

	/**
	 * Custom constructor that takes list of vertices and edges
	 * 
	 * @param vList list of vertices
	 * @param eList list of edges
	 */
	public Graph(List<Vertex> vList, List<Edge> eList) {
		synchronized (this) {
			vertices = vList;
			edges = eList;
		}
	}

	/**
	 * @return the vertices
	 */
	protected synchronized List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * @return the edges
	 */
	protected synchronized List<Edge> getEdges() {
		return edges;
	}

	private synchronized void resetPaths() {
		paths = null;
	}

	private synchronized void buildPaths() {
		for (Vertex v : vertices) {
			List<Vertex> processedVL = new ArrayList<Vertex>();
			buildPathForVertex(v, processedVL);
		}
	}

	private synchronized void buildPathForVertex(Vertex v, List<Vertex> processedVL) {
		if (!processedVL.contains(v)) {
			processedVL.add(v);
			List<Edge> vel = findEdgesForValidVertex(v);
			for (Edge e : vel) {
				final Vertex ev1 = e.getV1();
				final Vertex ev2 = e.getV2();
				boolean newEdge = true;
				ArrayList<Path> ap = new ArrayList<Path>();
				for (Path p : paths) {
					final Vertex pStart = p.getStart();
					final Vertex pEnd = p.getEnd();
					if (pStart.equals(ev1) && p.getEnd().equals(ev2)) {
						newEdge = false;
					} else if (pEnd.equals(ev1)) {
						Path np = new Path(p, e);
						if (np.isValid() && !paths.contains(np)) {
							ap.add(np);
						}
					}
				}
				paths.addAll(ap);
				if (newEdge) {
					Path np = new Path(e);
					if (np.isValid()) {
						paths.add(np);
					}
				}
				buildPathForVertex(ev2, processedVL);
			}
		}
	}

	/**
	 * This method finds all the paths in graph
	 * 
	 * @return all the paths
	 */
	public synchronized List<Path> getPaths() {
		if (paths == null) {
			paths = new ArrayList<Path>();
			buildPaths();
		}
		return paths;
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
	 * @throws VertexAlreadyExistsException error if vertex is already in graph
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
	 * @throws VertexDoesNotExistException error if vertex does not exist
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
		resetPaths();
	}

	/**
	 * Find a list of edges that have vertex v as edge vertex v1
	 * 
	 * @param v vertex
	 * @return list of edges with v1 equal to v
	 * @throws VertexDoesNotExistException error if vertex does not exist
	 */
	public List<Edge> findEdgesForVertex(Vertex v) throws VertexDoesNotExistException {
		validateVertexInGraph(v);
		return findEdgesForValidVertex(v);
	}

	/**
	 * Find a list of edges that have vertex v as edge vertex v1
	 * 
	 * @param v vertex
	 * @return list of edges with v1 equal to v
	 */
	private List<Edge> findEdgesForValidVertex(Vertex v) {
		// Find all edges for vertex
		ArrayList<Edge> ret = new ArrayList<Edge>();
		for (Edge ee : getEdges()) {
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
	 */
	protected synchronized List<Edge> findEdgesContainingValidVertex(Vertex v) {
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
	 * Find a list of edges that have vertex v as edge vertex v1 or v2
	 * 
	 * @param v vertex
	 * @return list of edges with v1 or v2 equal to v
	 * @throws VertexDoesNotExistException
	 */
	protected synchronized List<Edge> findEdgesContainingVertex(Vertex v) throws VertexDoesNotExistException {
		validateVertexInGraph(v);
		return findEdgesContainingValidVertex(v);
	}

	/**
	 * Add an edge
	 * 
	 * @param e edge to be added
	 * @throws EdgeAlreadyExistsException  error if edge does not exist
	 * @throws VertexDoesNotExistException error if vertex does not exist
	 */
	public synchronized void addEdge(Edge e) throws VertexDoesNotExistException, EdgeAlreadyExistsException {
		if (e != null) {
			validateVertexInGraph(e.getV1());
			validateVertexInGraph(e.getV2());
			if (edges.contains(e)) {
				throw new EdgeAlreadyExistsException(e);
			}
			edges.add(e);
			resetPaths();
		}
	}

	/**
	 * Remove an edge
	 * 
	 * @param e edge to be removed
	 * @throws EdgeDoesNotExistException error if edge does not exist
	 */
	public synchronized void removeEdge(Edge e) throws EdgeDoesNotExistException {
		validateEdgeInGraph(e);
		edges.remove(e);
		resetPaths();
	}

	/**
	 * Find a list of vertices in graph that are connected. A connected vertex is
	 * one that has a path to all other vertices in the graph.
	 * 
	 * @return list of connected vertices
	 */
	public List<Vertex> getConnectedVertices() {
		ArrayList<Vertex> ret = new ArrayList<Vertex>();
		List<Vertex> vl = getVertices();
		for (Vertex cv : vl) {
			ArrayList<Vertex> ovl = new ArrayList<Vertex>();
			ovl.addAll(vl);
			ovl.remove(cv);
			boolean ivc = true;
			if (ovl.isEmpty()) {
				ivc = false;
			} else {
				for (Vertex cve : ovl) {
					List<Path> cvepl = findPaths(cv, cve);
					if (cvepl.isEmpty()) {
						ivc = false;
						break;
					}
				}
			}
			if (ivc) {
				ret.add(cv);
			}
		}
		return ret;
	}

	private List<Path> findPaths(Vertex cv, Vertex cve) {
		List<Path> ret = new ArrayList<Path>();
		List<Path> cgpl = getPaths();
		for (Path cp : cgpl) {
			if (cp.getStart().equals(cv) && cp.getEnd().equals(cve)) {
				ret.add(cp);
			}
		}
		return ret;
	}

	/**
	 * Calculates the percentage connectivity of graph as: (Number of connected
	 * vertices * 100) / (Number of total vertices)
	 * 
	 * @return percentage connectivity
	 */
	public int calculateConnectivity() {
		return (getConnectedVertices().size() * 100) / getVertices().size();
	}

	/**
	 * Finds the shortest path between vertex v1 and v2
	 * 
	 * @param v1 starting vertex
	 * @param v2 ending vertex
	 * @return shortest path between v1 and v2
	 * @throws VertexDoesNotExistException error if vertex does not exist
	 */
	public Path findShortestPath(Vertex v1, Vertex v2) throws VertexDoesNotExistException {
		Path ret = null;
		validateVertexInGraph(v1);
		validateVertexInGraph(v2);
		List<Path> cgpvl = findPaths(v1, v2);
		if (!cgpvl.isEmpty()) {
			int sl = 0;
			for (Path cp : cgpvl) {
				final int cpl = cp.getLength();
				if (ret == null || cpl < sl) {
					ret = cp;
					sl = cpl;
				}
			}
		}
		return ret;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean ret = (this == obj);
		if (!ret && obj != null && obj instanceof Graph) {
			Graph ce = (Graph) obj;
			List<Vertex> cvl = getVertices();
			List<Edge> cel = getEdges();
			List<Vertex> ovl = ce.getVertices();
			List<Edge> oel = ce.getEdges();
			ret = ovl.equals(cvl) && oel.equals(cel);
		}
		return ret;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getVertices(), getEdges());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder br = new StringBuilder(this.getClass().getSimpleName() + ":\n\tVertices:\n");
		for (Vertex ev : getVertices()) {
			br.append("\t" + ev + "\n");
		}
		br.append("\tEdges:\n");
		for (Edge ee : getEdges()) {
			br.append("\t" + ee + "\n");
		}
		return br.toString();
	}

	/**
	 * Create XML for this graph
	 * 
	 * @return string XML for graph
	 * @throws JAXBException error during XML conversion
	 */
	public String convertToXML() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Graph.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		QName qName = new QName(Graph.class.getSimpleName().toLowerCase());
		JAXBElement<Graph> root = new JAXBElement<Graph>(qName, Graph.class, this);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(root, sw);
		return sw.toString();
	}

	/**
	 * Create instance of this graph from XML
	 * 
	 * @param xml string XML for this graph
	 * @return instance of graph
	 * @throws JAXBException error during XML conversion
	 */
	public static Graph createFromXML(String xml) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Graph.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		InputStream targetStream = new ByteArrayInputStream(xml.getBytes());
		Graph dg = (Graph) jaxbUnmarshaller.unmarshal(targetStream);
		dg.resetPaths();
		return dg;
	}

}
