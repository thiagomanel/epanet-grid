/**
 * 
 */
package org.epanetgrid.model;


/**
 * @author thiago
 *
 */
public interface ILink<L extends ILink> extends NetworkComponent {

	public INode getStarNode();
	
	public INode getEndNode();
}
