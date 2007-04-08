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
import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.model.link.DefaultPump;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.model.nodes.DefaultReservoir;
import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;

/**
 * @author thiago
 *
 */
public class DefaultNetWork implements NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>{
	
	private final Map<String, NetworkComponent> component = new HashMap<String, NetworkComponent>();
	
	private final Set<IPipe<?>> pipes = new HashSet<IPipe<?>>();
	private final Set<IPump<?>> pumps = new HashSet<IPump<?>>();
	private final Set<IValve<?>> valves = new HashSet<IValve<?>>();
	
	private final Set<IJunction<?>> junctions = new HashSet<IJunction<?>>();
	private final Set<IReservoir<?>> reservoirs = new HashSet<IReservoir<?>>();
	private final Set<ITank<?>> tanks = new HashSet<ITank<?>>();
	
	private final Map<ILink<?>, INode<?>> nosAMontante = new HashMap<ILink<?>, INode<?>>();
	private final Map<ILink<?>, INode<?>> nosAJusante = new HashMap<ILink<?>, INode<?>>();

	private DefaultNetWork(Builder builder){ 
		NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork = builder.baseNetWork;
		//tirar
		if(baseNetWork != null) {
			copyComponents(baseNetWork);
		}
	}
		
	/**
	 * @param baseNetWork
	 * @param work
	 */
	private void copyComponents(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		copyNodes(baseNetWork);
		copyLinks(baseNetWork);
	}

	private void copyLinks(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		
		for (IPipe<?> basePipe : baseNetWork.getPipes()) {
			INode montanteNode = (INode) getElemento(baseNetWork.getAnterior(basePipe).label());
			INode jusanteNode = (INode) getElemento(baseNetWork.getProximo(basePipe).label());
			addPipe(new DefaultPipe.Builder(basePipe.label(), this).copy(basePipe).build(), montanteNode, jusanteNode);
		}
		
		for (IPump<?> basePump : baseNetWork.getPumps()) {
			INode montanteNode = (INode) getElemento(baseNetWork.getAnterior(basePump).label());
			INode jusanteNode = (INode) getElemento(baseNetWork.getProximo(basePump).label());
			addPump(new DefaultPump.Builder(basePump.label(), this).copy(basePump).build(), montanteNode, jusanteNode);
		}
	}

	private void copyNodes(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		//podia subir o copy
		for (IJunction<?> baseJunction : baseNetWork.getJunctions()) {
			addJuncao(new DefaultJuntion.Builder(baseJunction.label(), this).copy(baseJunction).build());
		}
		
		for (IReservoir<?> baseReservoir : baseNetWork.getReservoirs()) {
			addReservoir(new DefaultReservoir.Builder(baseReservoir.label(), this).copy(baseReservoir).build());
		}
		
		for (ITank<?> baseTank : baseNetWork.getTanks()) {
			addTanks(new DefaultTank.Builder(baseTank.label(), this).copy(baseTank).build());
		}
	}

	public static class Builder{
		
		private NetWork baseNetWork;
		
		public Builder(){ }
		
		public Builder copy(NetWork netWork){
			this.baseNetWork = netWork;
			return this;
		}
		
		public DefaultNetWork build(){
			return new DefaultNetWork(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getPipes()
	 */
	public Set<IPipe<?>> getPipes() {
		return Collections.unmodifiableSet(pipes);
	}

	private void addComponent(NetworkComponent component){
		this.component.put(component.label(), component);
	}
	
	private void removeComponent(String label){
		this.component.remove(label);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getElemento(java.lang.String)
	 */
	public NetworkComponent getElemento(String label){
		return this.component.get(label);
	}
	
	public void addPipe(IPipe pipe, INode noMontante, INode noJusante) {
		if (contains(pipe.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + pipe.label() + ">.");
		}
		pipes.add(pipe);
		component.put(pipe.label(), pipe);
		nosAMontante.put(pipe, noMontante);
		nosAJusante.put(pipe, noJusante);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getPumps()
	 */
	public Set<IPump<?>> getPumps() {
		return Collections.unmodifiableSet(pumps);
	}
	
	public void addPump(IPump pump, INode noMontante, INode noJusante) {
		if (contains(pump.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + pump.label() + ">.");
		}
		pumps.add(pump);
		component.put(pump.label(), pump);
		nosAMontante.put(pump, noMontante);
		nosAJusante.put(pump, noJusante);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getValves()
	 */
	public Set<IValve<?>> getValves() {
		return Collections.unmodifiableSet(valves);
	}
	
	public void addValve(IValve valve, INode noMontante, INode noJusante) {
		if (contains(valve.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + valve.label() + ">.");
		}
		valves.add(valve);
		component.put(valve.label(), valve);
		nosAMontante.put(valve, noMontante);
		nosAJusante.put(valve, noJusante);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getJunctions()
	 */
	public Set<IJunction<?>> getJunctions() {
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
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#contains(java.lang.String)
	 */
	public boolean contains(String label) {
		return component.containsKey(label);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getReservoirs()
	 */
	public Set<IReservoir<?>> getReservoirs() {
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
	public Set<ITank<?>> getTanks() {
		return Collections.unmodifiableSet(tanks);
	}
	
	public void addTanks(ITank<?> tank) {
		if (contains(tank.label())) {
			throw new IllegalArgumentException("J existe um elemento com a descrio dessa juno <" + tank.label() + ">.");
		}
		tanks.add(tank);
		component.put(tank.label(), tank);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getAnterior(org.epanetgrid.model.ILink)
	 */
	public INode getAnterior(ILink link) {
		return nosAMontante.get(link);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getProximo(org.epanetgrid.model.ILink)
	 */
	public INode getProximo(ILink link) {
		return nosAJusante.get(link);
	}

}
