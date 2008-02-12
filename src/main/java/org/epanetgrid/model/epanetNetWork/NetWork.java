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
import org.joda.time.DateTime;
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
	
	public List<String> getTitle();
	
	public List<String> getCurves();
	
	public List<String> getEmitters();
	
	public List<String> getQuality();
	
	public List<String> getPattern();
	
	public List<String> getEnergy();
	
	public List<String> getStatus();
	
	public List<String> getCoordinates();
	
	public List<String> getVertices();
	
	public List<String> getBackdrop();
	
	public Map<Integer, Map<String, Boolean>> getControls();
	
	public List<String> getRules();
	
	public List<String> getDemands();
	
	public List<String> getOptions();
	
	public List<String> getReactions();
	
	public List<String> getSources();
	
	public List<String> getMixing();
	
	public List<String> getTimes();
	
	public IReport getReports();

	public Duration getDuration();
	
	public Duration getHydraulicTimestep();
	
	public DateTime getStartClockTime();

}
