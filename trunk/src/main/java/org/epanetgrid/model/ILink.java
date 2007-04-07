/**
 * 
 */
package org.epanetgrid.model;


/**
 * @author thiago
 *
 */
public interface ILink<L extends ILink> extends NetworkComponent<ILink> {

	public INode getStarNode();
	
	public INode getEndNode();
}
