package org.epanetgrid.perturbador;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.quantities.Dimensionless;
import javax.quantities.Length;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.epanetgrid.model.NetworkComponent;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.perturbador.PertubadorFacade.pert_types;
import org.epanetgrid.perturbador.perturbadores.IPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeLengthPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class PertubadorRunnerTest extends TestCase {

	private NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network;
	private IPipe basePipe;
	
	protected void setUp() throws Exception {
		
		super.setUp();
		network = EasyMock.createMock(NetWork.class);
		basePipe = new DefaultPipe.Builder("defPipe", network)
						.diameter(Measure.valueOf(0, Length.SI_UNIT))
						.length(Measure.valueOf(0, Length.SI_UNIT)).lossCoefficient(Measure.valueOf(0, Dimensionless.SI_UNIT))
						.roughnessCoefficient(Measure.valueOf(0, Dimensionless.SI_UNIT)).build();
		
		EasyMock.expect(network.getElemento("defPipe")).andReturn(basePipe).anyTimes();
		EasyMock.replay(network);
	}

	/*
	 * Test method for 'org.epanetgrid.perturbador.PertubadoRunner.getMalhaPerturbadas(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>, Map<String, Map<pert_types, Collection<IPerturbador>>>)'
	 */
	public void testGetMalhaPerturbadas() {
		
		IPerturbador<DefaultPipe> pert1 = new PipeLengthPerturbador(basePipe.label(), 1);
		IPerturbador<DefaultPipe> pert2 = new PipeLengthPerturbador(basePipe.label(), 2);
		IPerturbador<DefaultPipe> pert3 = new PipeLengthPerturbador(basePipe.label(), 3);
		
		Collection<IPerturbador> perturbadores  = new LinkedList<IPerturbador>();
		perturbadores.add(pert1);
		perturbadores.add(pert2);
		perturbadores.add(pert3);
		
		Map<pert_types, Collection<IPerturbador>> mapTypeToPert = new HashMap<pert_types, Collection<IPerturbador>>();
		
		mapTypeToPert.put(pert_types.PIPE_LENGTH, perturbadores);
		
		Map<String,Map<pert_types, Collection<IPerturbador>>> mapLabelToPert 
								= new HashMap<String,Map<pert_types, Collection<IPerturbador>>>();
		
		mapLabelToPert.put(basePipe.label(), mapTypeToPert);
	
		PertubadorRunner pertRunner = new PertubadorRunner();
		/**
		 * 3 Malhas geradas pela perturbacao + 1 malha base
		 */
		assertEquals(4, pertRunner.getMalhaPerturbadas(network, mapLabelToPert).size());
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void testDisturbComponent(){
		
		IPerturbador<DefaultPipe> pert1 = new PipeLengthPerturbador(basePipe.label(), 1);
		IPerturbador<DefaultPipe> pert2 = new PipeLengthPerturbador(basePipe.label(), 2);
		IPerturbador<DefaultPipe> pert3 = new PipeLengthPerturbador(basePipe.label(), 3);
		
		Collection<IPerturbador> perturbadores  = new LinkedList<IPerturbador>();
		perturbadores.add(pert1);
		perturbadores.add(pert2);
		perturbadores.add(pert3);
		
		PertubadorRunner pertRunner = new PertubadorRunner();
		Collection<NetworkComponent> pipesPerturbados = pertRunner.disturbComponent(basePipe, perturbadores);
		assertEquals(3, pipesPerturbados.size());

		List<NetworkComponent> pipesList = new LinkedList<NetworkComponent>();
		pipesList.addAll(pipesPerturbados);
		Collections.sort(pipesList,  new Comparator(){
			public int compare(Object o1, Object o2) {
				IPipe pipe1 = (IPipe)o1;
				IPipe pipe2 = (IPipe)o2;
				return pipe1.getLength().compareTo(pipe2.getLength());
			}});
		
		assertTrue(Measure.valueOf(1, Length.SI_UNIT).approximates( ((IPipe)pipesList.get(0)).getLength() ));
		assertTrue(Measure.valueOf(2, Length.SI_UNIT).approximates( ((IPipe)pipesList.get(1)).getLength() ));
		assertTrue(Measure.valueOf(3, Length.SI_UNIT).approximates( ((IPipe)pipesList.get(2)).getLength() ));
	}

}
