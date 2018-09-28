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
import java.util.Objects;

/**
 * Path between two vertices
 */
public class Path {

	private final Vertex start, end;
	private final int length;
	private final List<Vertex> vertices = new ArrayList<Vertex>();
	private final List<Edge> edges = new ArrayList<Edge>();

	@SuppressWarnings("unused")
	private Path() {
		start = null;
		end = null;
		length = 0;
	}

	public Path(Edge e) {
		if (e != null && !e.getV1().equals(e.getV2())) {
			start = e.getV1();
			end = e.getV2();
			length = e.getWeight();
			vertices.add(start);
			vertices.add(end);
			edges.add(e);
		} else {
			start = null;
			end = null;
			length = 0;
		}
	}

	public Path(Path p, Edge e) {
		if (p != null && e != null && p.getEnd().equals(e.getV1()) && !p.getVertices().contains(e.getV2())) {
			start = p.getStart();
			end = e.getV2();
			length = p.getLength() + e.getWeight();
			vertices.addAll(p.getVertices());
			vertices.add(e.getV2());
			edges.addAll(p.getEdges());
			edges.add(e);
		} else {
			start = null;
			end = null;
			length = 0;
		}
	}

	/**
	 * Is this a valid path
	 * 
	 * @return true if path is valid
	 */
	public boolean isValid() {
		return length > 0;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean ret = (this == obj);
		if (!ret && obj != null && obj instanceof Path) {
			Path pe = (Path) obj;
			ret = pe.getStart().equals(start) && pe.getEnd().equals(end) && pe.getLength() == length
					&& pe.getVertices().equals(vertices) && pe.getEdges().equals(edges);
		}
		return ret;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(start, end, length, vertices, edges);
	}

	/**
	 * @return the start
	 */
	public Vertex getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Vertex getEnd() {
		return end;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the vertices
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder br = new StringBuilder(
				"Path: From " + getStart() + " To " + getEnd() + " Length = " + length + "\n\tVertices:\n");
		for (Vertex ev : getVertices()) {
			br.append("\t" + ev + "\n");
		}
		br.append("\tEdges:\n");
		for (Edge ee : getEdges()) {
			br.append("\t" + ee + "\n");
		}
		return br.toString();
	}

}
