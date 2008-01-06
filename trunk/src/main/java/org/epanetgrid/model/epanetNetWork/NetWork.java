/**
 * 
 */
package org.epanetgrid.model.epanetNetWork;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.epanetgrid.model.ILink;
import org.epanetgrid.model.INode;
import org.epanetgrid.model.NetworkComponent;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.model.report.IReport;
import org.joda.time.Duration;

/**
 * @author thiago
 */
public interface NetWork <B extends IPump, P extends IPipe, T extends ITank, J extends IJunction, V extends IValve,
								R extends IReservoir> extends Cloneable {

	
	public Set<P> getPipes();
	
	public Set<B> getPumps();
	
	public Set<V> getValves();
	
	public Set<J> getJunctions();
	
	public Set<R> getReservoirs();
	
	public Set<T> getTanks();
	
	public INode getAnterior(ILink link);
	
	public INode getProximo(ILink link);
	
	public Set<ILink<?>> getAnteriores(INode node);
	
	public Set<ILink<?>> getProximos(INode node);
	
	public NetworkComponent getElemento(String label);
	
	public boolean contains(String label);
	
	public void replaceComponent(String oldComponentLabel, NetworkComponent newComponent);
	
	public Set<String> getPattern();
	
	public Set<String> getEnergy();
	
	public Set<String> getOptions();
	
	public Set<String> getTimes();
	
	public List<String> getCurves();
	
	public IReport getReports();

	public Map<String, Map<Integer, Boolean>> getControls();

	public Duration getDuration();
	
	public Duration getHydraulicTimestep();

}
