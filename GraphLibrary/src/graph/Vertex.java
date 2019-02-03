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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vertex")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 * Graph library class to represent a vertex.
 */
public class Vertex {

	private final int vid;
	private final String vname;

	@SuppressWarnings("unused")
	private Vertex() {
		vid = -1;
		vname = "";
	}

	/**
	 * Custom constructor that takes vertex ID
	 * 
	 * @param vertexID vertex ID
	 */
	public Vertex(int vertexID) {
		vid = vertexID;
		vname = "" + vid;
	}

	/**
	 * Custom constructor that takes vertex ID and vertex Name
	 * 
	 * @param vertexID   vertex ID
	 * @param vertexName vertex Name
	 */
	public Vertex(int vertexID, String vertexName) {
		vid = vertexID;
		vname = vertexName;
	}

	/**
	 * @return the vertex ID
	 */
	public int getVid() {
		return vid;
	}

	/**
	 * @return the vertex name
	 */
	public String getVname() {
		return vname;
	}

	/**
	 * Override of default method to return custom string vertex.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ID=" + vid + ", Name='" + vname + "'";
	}

	/**
	 * Override of default method to check for vertex ID.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean ret = (this == obj);
		if (!ret && obj != null && obj instanceof Vertex) {
			Vertex cv = (Vertex) obj;
			ret = (cv.getVid() == getVid());
		}
		return ret;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(vid);
	}

}
