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
	
	private final Map<INode<?>, Set<ILink<?>>> elosAJusante = new HashMap<INode<?>, Set<ILink<?>>>();
	private final Map<INode<?>, Set<ILink<?>>> elosAMontante = new HashMap<INode<?>, Set<ILink<?>>>();

	private final Set<String> energy = new HashSet<String>();
	private final Set<String> options = new HashSet<String>();
	private final Set<String> patterns = new HashSet<String>();
	private final Set<String> reports = new HashSet<String>();
	private final Set<String> times = new HashSet<String>();

	/**
	 * @param builder
	 */
	private DefaultNetWork(Builder builder){ 
		NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork = builder.baseNetWork;
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
		elosAJusante.get(noMontante).add(pipe);
		elosAMontante.get(noJusante).add(pipe);
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
		elosAJusante.get(noMontante).add(pump);
		elosAMontante.get(noJusante).add(pump);
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
		elosAJusante.get(noMontante).add(valve);
		elosAMontante.get(noJusante).add(valve);
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
		elosAMontante.put(juncao, new HashSet<ILink<?>>());
		elosAJusante.put(juncao, new HashSet<ILink<?>>());
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
		elosAMontante.put(reservoir, new HashSet<ILink<?>>());
		elosAJusante.put(reservoir, new HashSet<ILink<?>>());
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
		elosAMontante.put(tank, new HashSet<ILink<?>>());
		elosAJusante.put(tank, new HashSet<ILink<?>>());
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

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#replaceComponent(java.lang.String, org.epanetgrid.model.NetworkComponent)
	 */
	public void replaceComponent(String oldComponentLabel, NetworkComponent newComponent) {
		//<ugly>
		if(isNodeComponent(oldComponentLabel)){
			
			INode oldComponent = (INode)getElemento(oldComponentLabel);
			Set<ILink<?>> elosMontante = getAnteriores(oldComponent);
			elosMontante.remove(oldComponent);
			
			Set<ILink<?>> elosJusante = getProximos(oldComponent);
			elosJusante.remove(oldComponent);
			
			//ugly!!
			if(getTanks().contains(oldComponent)){
				removeNode(oldComponent);
				addTanks((ITank<?>) newComponent);
			}else if(getReservoirs().contains(oldComponent)){
				removeNode(oldComponent);
				addReservoir((IReservoir) newComponent);
			}else if(getJunctions().contains(oldComponent)){
				removeNode(oldComponent);
				addJuncao((IJunction) newComponent);
			}
			
			for (ILink<?> link : elosJusante) {
				nosAMontante.remove(link);
				nosAMontante.put(link, (INode<?>) newComponent);
			}
			
			for (ILink<?> link : elosMontante) {
				nosAJusante.remove(link);
				nosAJusante.put(link, (INode<?>) newComponent);
			}
			
		}else if(isLinkComponent(oldComponentLabel)){
			
			ILink oldComponent = (ILink)getElemento(oldComponentLabel);
			INode noMontante = getAnterior(oldComponent);
			INode noJusante = getProximo(oldComponent);

			//ugly!!
			if(getPipes().contains(oldComponent)){
				removeLink(oldComponent);
				addPipe((IPipe) newComponent, noMontante, noJusante);
			}else if(getPumps().contains(oldComponent)){
				removeLink(oldComponent);
				addPump((IPump) newComponent, noMontante, noJusante);
			}else if(getValves().contains(oldComponent)){
				removeLink(oldComponent);
				addValve((IValve) newComponent, noMontante, noJusante);
			}
		}
		//</ugly>
	}

	private void removeLink(ILink link){
		Set<ILink<?>> set = elosAMontante.get(nosAJusante.get(link));
		if (null != set) {
			set.remove(link);
		}
		set = elosAJusante.get(nosAMontante.get(link));
		if (null != set) {
			set.remove(link);
		}
		
		nosAJusante.remove(link);
		nosAMontante.remove(link);
		
		pipes.remove(link);
		valves.remove(link);
		pumps.remove(link);
		
		component.remove(link.label());
	}
	
	private void removeNode(INode node){
		junctions.remove(node);
		tanks.remove(node);
		reservoirs.remove(node);
		
		for (ILink link : elosAJusante.get(node)) {
			removeLink(link);
		}
		
		for (ILink link : elosAMontante.get(node)) {
			removeLink(link);
		}
		
		component.remove(node.label());
	}
	
	//FIXME: DEVERIA LANÇAR UMA EXCEPTION SE O ELEMENTO NAUM EXISTIR
	private boolean isLinkComponent(String label){
		NetworkComponent component = getElemento(label); 
		return ( component == null) ? false : ( getPipes().contains(component) || getPumps().contains(component)
													|| getValves().contains(component)); 
	}

	private boolean isNodeComponent(String label){
		NetworkComponent component = getElemento(label); 
		return ( component == null) ? false : ( getTanks().contains(component) || getReservoirs().contains(component)
													|| getJunctions().contains(component));
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getAnteriores(org.epanetgrid.model.INode)
	 */
	public Set<ILink<?>> getAnteriores(INode node) {
		return this.elosAMontante.get(node);
	}

	public Set<ILink<?>> getProximos(INode node) {
		return this.elosAJusante.get(node);
	}

	public void addEnergy(String energy){
		this.energy.add(energy);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getEnergy()
	 */
	public Set<String> getEnergy() {
		return this.energy;
	}

	public void addOption(String option){
		this.options.add(option);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getOptions()
	 */
	public Set<String> getOptions() {
		return this.options;
	}
	
	public void addPattern(String pattern){
		this.patterns.add(pattern);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getPattern()
	 */
	public Set<String> getPattern() {
		return this.patterns;
	}

	public void addReport(String report){
		this.reports.add(report);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getReports()
	 */
	public Set<String> getReports() {
		return this.reports;
	}

	public void addTime(String time){
		this.times.add(time);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getTimes()
	 */
	public Set<String> getTimes() {
		return this.times;
	}
}
