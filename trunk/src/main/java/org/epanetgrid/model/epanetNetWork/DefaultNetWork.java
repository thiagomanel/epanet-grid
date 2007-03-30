/**
 * 
 */
package org.epanetgrid.model.epanetNetWork;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

/**
 * @author thiago
 *
 */
public class DefaultNetWork <L extends ILink> implements NetWork{
	
	private final Map<String, NetworkComponent> component = new HashMap<String, NetworkComponent>();
	
	private final Set<IPipe> pipes = new HashSet<IPipe>();
	private final Set<IPump> pumps = new HashSet<IPump>();
	private final Set<IValve> valves = new HashSet<IValve>();
	
	private final Set<IJunction> junctions = new HashSet<IJunction>();
	private final Set<IReservoir> reservoirs = new HashSet<IReservoir>();
	private final Set<ITank> tanks = new HashSet<ITank>();
	
	private final Map<L, String> nosAMontante = new HashMap<L, String>();
	private final Map<L, String> nosAJusante = new HashMap<L, String>();

	
	public void removeLink(String label)
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getPipes()
	 */
	public Set<IPipe> getPipes() {
		return Collections.unmodifiableSet(pipes);
	}

	public void addPipe(IPipe pipe, INode noMontante, INode noJusante) {
		if (contains(pipe.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + pipe.label() + ">.");
		}
		pipes.add(pipe);
		component.put(pipe.label(), pipe);
		nosAMontante.put((L) pipe, noMontante.label());
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getPumps()
	 */
	public Set<IPump> getPumps() {
		return Collections.unmodifiableSet(pumps);
	}
	
	public void addPump(IPump pump, INode noMontante, INode noJusante) {
		if (contains(pump.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + pump.label() + ">.");
		}
		pumps.add(pump);
		component.put(pump.label(), pump);
		nosAMontante.put((L) pump, noMontante.label());
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getValves()
	 */
	public Set<IValve> getValves() {
		return Collections.unmodifiableSet(valves);
	}
	
	public void addValve(IValve valve, INode noMontante, INode noJusante) {
		if (contains(valve.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + valve.label() + ">.");
		}
		valves.add(valve);
		component.put(valve.label(), valve);
		nosAMontante.put((L) valve, noMontante.label());
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getJunctions()
	 */
	public Set<IJunction> getJunctions() {
		return Collections.unmodifiableSet(junctions);
	}
	
	/**
	 * Adiciona uma juno a malha.
	 *
	 * @param juncao A juno a ser adicionada.
	 */
	public void addJuncao(IJunction juncao) {
		if (contains(juncao.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + juncao.label() + ">.");
		}
		junctions.add(juncao);
		component.put(juncao.label(), juncao);
	}

	public boolean contains(NetworkComponent component) {
		return contains(component.label());
	}
	
	public boolean contains(String label) {
		return component.containsKey(label);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getReservoirs()
	 */
	public Set<IReservoir> getReservoirs() {
		return Collections.unmodifiableSet(reservoirs);
	}
	
	public void addReservoir(IReservoir reservoir) {
		if (contains(reservoir.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + reservoir.label() + ">.");
		}
		reservoirs.add(reservoir);
		component.put(reservoir.label(), reservoir);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getTanks()
	 */
	public Set<ITank> getTanks() {
		return Collections.unmodifiableSet(tanks);
	}
	
	public void addTanks(ITank tank) {
		if (contains(tank.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + tank.label() + ">.");
		}
		tanks.add(tank);
		component.put(tank.label(), tank);
	}

	public INode getAnterior(ILink link) {
		// TODO Auto-generated method stub
		return null;
	}

	public INode getProximo(ILink link) {
		// TODO Auto-generated method stub
		return null;
	}

}
