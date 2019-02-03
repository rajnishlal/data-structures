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

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "edge")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 * Graph library class to represent an edge, all edges need two vertices. Edges
 * could have weights and/or labels, both of these are optional.
 */
public class Edge {

	private final Vertex v1, v2;

	private int weight = 1;
	private String label = "";

	@SuppressWarnings("unused")
	private Edge() {
		v1 = null;
		v2 = null;
	}

	/**
	 * Custom constructor that takes two vertices. Default weight will be 1 and
	 * label vertex1 to vertex2.
	 * 
	 * @param vertex1 vertex1
	 * @param vertex2 vertex2
	 */
	public Edge(Vertex vertex1, Vertex vertex2) {
		v1 = vertex1;
		v2 = vertex2;
		label = v1.getVname() + "->" + v2.getVname();
	}

	/**
	 * Custom constructor that takes two vertices, weight and label
	 * 
	 * @param vertex1 vertex1
	 * @param vertex2 vertex2
	 * @param weight  weight assigned to this edge (default 1)
	 * @param label   label string for this edge
	 */
	public Edge(Vertex vertex1, Vertex vertex2, int weight, String label) {
		v1 = vertex1;
		v2 = vertex2;
		this.weight = weight;
		this.label = label;
	}

	/**
	 * @return the vertex v1
	 */
	public final Vertex getV1() {
		return v1;
	}

	/**
	 * @return the vertex v2
	 */
	public final Vertex getV2() {
		return v2;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean ret = (this == obj);
		if (!ret && obj != null && obj instanceof Edge) {
			Edge ce = (Edge) obj;
			ret = ce.getV1().equals(v1) && ce.getV2().equals(v2);
		}
		return ret;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(v1, v2);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + v1 + " -> " + v2 + ") Label='" + label + "' Weight=" + weight;
	}

}
