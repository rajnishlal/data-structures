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
package graph.error;

import graph.Edge;

/**
 * 
 *
 */
public class EdgeDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String message = "Edge does not exist in graph.";

	public EdgeDoesNotExistException() {
		super(message);
	}

	public EdgeDoesNotExistException(Edge e) {
		super("Edge does not exist in graph for the two vertices " + e.getV1() + " and " + e.getV2() + ".");
	}

}
