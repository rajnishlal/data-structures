/*******************************************************************************
 * Copyright (C) 2020 Rajnish R Lal <rajnishlal@gmail.com>
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package graph.error;

import graph.Edge;

/**
 * 
 */

/**
 * 
 *
 */
public class EdgeAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String message = "Edge already exists.";

	public EdgeAlreadyExistsException() {
		super(message);
	}

	public EdgeAlreadyExistsException(Edge e) {
		super("Edge already exists for the two vertices " + e.getV1() + " and " + e.getV2() + ".");
	}

}
