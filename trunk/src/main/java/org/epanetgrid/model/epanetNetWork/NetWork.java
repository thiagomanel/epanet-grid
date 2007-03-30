/**
 * 
 */
package org.epanetgrid.model.epanetNetWork;

import java.util.Set;

import org.epanetgrid.model.ILink;
import org.epanetgrid.model.INode;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;

/**
 * @author thiago
 */
public interface NetWork <B extends IPump, P extends IPipe, T extends ITank, J extends IJunction, V extends IValve,
								R extends IReservoir> {

	
	public Set<P> getPipes();
	
	public Set<B> getPumps();
	
	public Set<V> getValves();
	
	public Set<J> getJunctions();
	
	public Set<R> getReservoirs();
	
	public Set<T> getTanks();
	
	public <L extends ILink,N extends INode> N getAnterior(L link);
	
	public <L extends ILink,N extends INode> N getProximo(L link);
}
