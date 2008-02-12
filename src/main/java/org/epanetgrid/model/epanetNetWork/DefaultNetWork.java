/**
 * 
 */
package org.epanetgrid.model.epanetNetWork;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

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
import org.epanetgrid.model.report.IReport;
import org.epanetgrid.model.report.Report;
import org.epanetgrid.util.NetWorkComponentStringComparator;
import org.joda.time.DateTime;
import org.joda.time.Duration;

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

	private final Map<Integer, Map<String, Boolean>> controls = new TreeMap<Integer, Map<String,Boolean>>();
	
	private final List<String> energy = new LinkedList<String>();
	private final List<String> options = new LinkedList<String>();
	private final List<String> patterns = new LinkedList<String>();
	private final IReport reports = new Report();
	private final List<String> times = new LinkedList<String>();

	private final List<String> curves = new LinkedList<String>();

	private Duration duration;
	private Duration hydraulicTimestep;
	private DateTime startClockTime;

	private List<String> backdrop = new LinkedList<String>();
	private List<String> coordinates = new LinkedList<String>();
	private List<String> demmands = new LinkedList<String>();
	private List<String> emitters = new LinkedList<String>();
	private List<String> mixing = new LinkedList<String>();
	private List<String> quality = new LinkedList<String>();
	private List<String> reactions = new LinkedList<String>();
	private List<String> rules = new LinkedList<String>();
	private List<String> sources = new LinkedList<String>();
	private List<String> status = new LinkedList<String>();
	private List<String> title = new LinkedList<String>();
	private List<String> vertices = new LinkedList<String>();

	/**
	 * @param builder
	 */
	private DefaultNetWork(Builder builder){ 
		NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork = builder.baseNetWork;
		if(baseNetWork != null) {
			copyComponents(baseNetWork);
			copyCurves(baseNetWork);
			copyPatterns(baseNetWork);
			copyEnergy(baseNetWork);
			copyOptions(baseNetWork);
			copyReports(baseNetWork);
			copyTime(baseNetWork);
			copyControls(baseNetWork);
			copyBackdrop(baseNetWork);
			copyCoordinates(baseNetWork);
			copyDemands(baseNetWork);
			copyEmitters(baseNetWork);
			copyMixing(baseNetWork);
			copyQuality(baseNetWork);
			copyReactions(baseNetWork);
			copyRules(baseNetWork);
			copySources(baseNetWork);
			copyStatus(baseNetWork);
			copyTitle(baseNetWork);
			copyVertice(baseNetWork);
			this.duration = baseNetWork.getDuration();
			this.hydraulicTimestep = baseNetWork.getHydraulicTimestep();
			this.startClockTime = baseNetWork.getStartClockTime();
		}
	}
		
	private void copyControls(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (Map.Entry<Integer, Map<String, Boolean>> linkControl : baseNetWork.getControls().entrySet()) {
			for (Map.Entry<String, Boolean> control : linkControl.getValue().entrySet()) {
				addControl(linkControl.getKey(), control.getKey(), control.getValue());
			}
		}
	}

	/**
	 * @param baseNetWork
	 */
	private void copyTime(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		
		for (String time : baseNetWork.getTimes()) {
			addTime(time);
		}
	}

	/**
	 * @param baseNetWork
	 */
	private void copyReports(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {

		IReport report = baseNetWork.getReports();
		for (Entry<String, String> reportValue : report.getValues().entrySet()) {
			addReport(reportValue.getKey(), reportValue.getValue());
		}
	}

	/**
	 * @param baseNetWork
	 */
	private void copyPatterns(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		
		for (String pattern : baseNetWork.getPattern()) {
			addPattern(pattern);
		}
	}
	
	/**
	 * @param baseNetWork
	 */
	private void copyEnergy(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		
		for (String baseEnergy : baseNetWork.getEnergy()) {
			addEnergy(baseEnergy);
		}
	}
	
	/**
	 * @param baseNetWork
	 */
	private void copyOptions(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		
		for (String option : baseNetWork.getOptions()) {
			addOption(option);
		}
	}

	/**
	 * @param baseNetWork
	 */
	private void copyCurves(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		
		for (String curve : baseNetWork.getCurves()) {
			addCurve(curve);
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

	private void copyBackdrop(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String backdrop : baseNetWork.getBackdrop()) {
			addBackdrop(backdrop);
		}
	}
	
	private void copyCoordinates(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String coordinates : baseNetWork.getCoordinates()) {
			addCoordinates(coordinates);
		}
	}

	private void copyDemands(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String demands : baseNetWork.getDemands()) {
			addDemands(demands);
		}
	}
	
	private void copyEmitters(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String emitters : baseNetWork.getEmitters()) {
			addEmitters(emitters);
		}
	}
	
	private void copyMixing(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String mixing : baseNetWork.getMixing()) {
			addMixing(mixing);
		}
	}
	
	private void copyQuality(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String quality : baseNetWork.getQuality()) {
			addQuality(quality);
		}
	}
	
	private void copyReactions(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String reaction : baseNetWork.getReactions()) {
			addReaction(reaction);
		}
	}
	
	private void copyRules(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String rules : baseNetWork.getRules()) {
			addRules(rules);
		}
	}
	
	private void copySources(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String source : baseNetWork.getSources()) {
			addSource(source);
		}
	}
	
	private void copyStatus(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String status : baseNetWork.getStatus()) {
			addStatus(status);
		}
	}
	
	private void copyTitle(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String title : baseNetWork.getTitle()) {
			addTitle(title);
		}
	}
	
	private void copyVertice(
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> baseNetWork) {
		for (String vertice : baseNetWork.getVertices()) {
			addVertice(vertice);
		}
	}
	
	
	@Override
	public DefaultNetWork clone() throws CloneNotSupportedException {
		return new Builder().copy(this).build();
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
		return (Set<IPipe<?>>) sortSet(pipes);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getElemento(java.lang.String)
	 */
	public NetworkComponent getElemento(String label){
		return this.component.get(label);
	}
	
	public void addPipe(IPipe pipe, INode noMontante, INode noJusante) {
		if (contains(pipe.label())) {
			throw new IllegalArgumentException("J� existe um elemento com a descri��o desse duto <" + pipe.label() + ">.");
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
		return (Set<IPump<?>>) sortSet(pumps);
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
		return (Set<IValve<?>>) sortSet(valves);
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
		return (Set<IJunction<?>>) sortSet(junctions);
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
		return (Set<IReservoir<?>>) sortSet(reservoirs);
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
		return (Set<ITank<?>>) sortSet(tanks);
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

	/* 
	 * TODO: REFATORAR ESTE MÉTODO!
	 * 
	 * (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#replaceComponent(java.lang.String, org.epanetgrid.model.NetworkComponent)
	 */
	public void replaceComponent(String oldComponentLabel, NetworkComponent newComponent) {
		//<ugly>
		if(isNodeComponent(oldComponentLabel)){
			
			INode oldComponent = (INode)getElemento(oldComponentLabel);
			Set<ILink<?>> elosMontante = new HashSet<ILink<?>>(getAnteriores(oldComponent));
			Set<ILink<?>> elosJusante = new HashSet<ILink<?>>(getProximos(oldComponent));
			
			elosAJusante.remove(oldComponent);
			elosAMontante.remove(oldComponent);			
			component.remove(oldComponentLabel);
			
			//ugly!!
			if(getTanks().contains(oldComponent)){
				tanks.remove(oldComponent);
				addTanks((ITank<?>) newComponent);
			}else if(getReservoirs().contains(oldComponent)){
				reservoirs.remove(oldComponent);
				addReservoir((IReservoir) newComponent);
			}else if(getJunctions().contains(oldComponent)){
				junctions.remove(oldComponent);
				addJuncao((IJunction) newComponent);
			}
			
			for (ILink<?> link : elosJusante) {
				nosAMontante.put(link, (INode<?>) newComponent);
				elosAJusante.get((INode<?>) newComponent).add(link);
			}
			
			for (ILink<?> link : elosMontante) {
				nosAJusante.put(link, (INode<?>) newComponent);
				elosAMontante.get((INode<?>) newComponent).add(link);
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
	public List<String> getEnergy() {
		return this.energy;
	}

	public void addOption(String option){
		this.options.add(option);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getOptions()
	 */
	public List<String> getOptions() {
		return this.options;
	}
	
	public void addPattern(String pattern){
		this.patterns.add(pattern);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getPattern()
	 */
	public List<String> getPattern() {
		return this.patterns;
	}

	public void addReport(String key, String value){
		this.reports.setValue(key, value);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getCurves()
	 */
	public List<String> getCurves() {
		return this.curves;
	}
	
	public void addCurve(String curve){
		this.curves.add(curve);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getReports()
	 */
	public IReport getReports() {
		return this.reports;
	}

	public void addTime(String time){
		this.times.add(time);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.epanetNetWork.NetWork#getTimes()
	 */
	public List<String> getTimes() {
		return this.times;
	}
	
	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Duration getHydraulicTimestep() {
		return hydraulicTimestep;
	}

	public void setHydraulicTimestep(Duration hydraulicTimestep) {
		this.hydraulicTimestep = hydraulicTimestep;
	}
	
	public DateTime getStartClockTime() {
		return startClockTime;
	}

	public void setStartClockTime(DateTime startClockTime) {
		this.startClockTime = startClockTime;
	}

	private  Set<? extends NetworkComponent> sortSet(Set<? extends NetworkComponent> components){
		TreeSet<NetworkComponent> sortedSet = new TreeSet(new NetWorkComponentStringComparator());
		sortedSet.addAll(components);
		return sortedSet;
	}

	public void setControls(Map<Integer, Map<String, Boolean>> controls) {
		this.controls.clear();
		for (Map.Entry<Integer, Map<String, Boolean>> entry : controls.entrySet()) {
			for (Map.Entry<String, Boolean> control : entry.getValue().entrySet()) {
				addControl(entry.getKey(), control.getKey(), control.getValue());
			}
		}
	}
	
	public void addControl(Integer interval, String linkID, boolean state) {
		if ( controls.get(interval) == null ) {
			controls.put(interval, new TreeMap<String, Boolean>());
		}
		controls.get(interval).put(linkID, state);
	}

	public Map<Integer, Map<String, Boolean>> getControls() {
		return controls;
	}
	
	public Map<Integer, Map<String, Boolean>> getFullControls() {
		
		Map<Integer, Map<String, Boolean>> full = new HashMap<Integer, Map<String,Boolean>>(this.controls);
		
		int numIntervals = (int) (duration.getMillis() / hydraulicTimestep.getMillis());
		
		for (int i = 0; i <= numIntervals; i++) {
			Map<String, Boolean> intervalControl = new TreeMap<String,Boolean>();
			for (IPump<?> pump : this.pumps) {
				Boolean state = ( full.get(i) != null ? full.get(i).get(pump.label()) : null );
				if (state == null) {
					state = (i == 0 ? true : full.get(i-1).get(pump.label()));
				}
				intervalControl.put(pump.label(), state);
			}
			full.put(i, intervalControl);
		}

		return full;
	}

	public List<String> getBackdrop() {
		return this.backdrop;
	}

	public void addBackdrop(String backdrop){
		this.backdrop.add(backdrop);
	}
	
	public List<String> getCoordinates() {
		return this.coordinates;
	}
	
	public void addCoordinates(String coordinates){
		this.coordinates.add(coordinates);
	}

	public List<String> getDemands() {
		return this.demmands;
	}

	public void addDemands(String demands){
		this.demmands.add(demands);
	}
	
	public List<String> getEmitters() {
		return this.emitters;
	}

	public void addEmitters(String emitters){
		this.emitters.add(emitters);
	}
	
	public List<String> getMixing() {
		return this.mixing;
	}

	public void addMixing(String mixing){
		this.mixing.add(mixing);
	}
	
	public List<String> getQuality() {
		return this.quality;
	}

	public void addQuality(String quality){
		this.quality.add(quality);
	}
	
	public List<String> getReactions() {
		return this.reactions;
	}

	public void addReaction(String reaction){
		this.reactions.add(reaction);
	}
	
	public List<String> getRules() {
		return this.rules;
	}

	public void addRules(String rules){
		this.rules.add(rules);
	}
	
	public List<String> getSources() {
		return this.sources;
	}

	public void addSource(String source){
		this.sources.add(source);
	}
	
	public List<String> getStatus() {
		return this.status;
	}

	public void addStatus(String status){
		this.status.add(status);
	}
	
	public List<String> getTitle() {
		return this.title;
	}

	public void addTitle(String title){
		this.title.add(title);
	}
	
	public List<String> getVertices() {
		return this.vertices;
	}

	public void addVertice(String vertice){
		this.vertices.add(vertice);
	}
	
}
