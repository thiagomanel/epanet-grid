package org.epanetgrid.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.quantities.Dimensionless;
import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import junit.framework.TestCase;

import org.epanetgrid.model.epanetNetWork.DefaultNetWork;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.model.link.DefaultPump;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.model.nodes.DefaultReservoir;
import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class EpaFileWriterTest extends TestCase {

	private NetWork netWork;
	private String filePath = "/tmp/epaOutPUt.inp"; 
	
	protected void setUp() throws Exception {
		super.setUp();
		netWork = createNetWork();
	}

	/*
	 * Test method for 'org.epanetgrid.data.EpaFileWriter.write(NetWork, String)'
	 */
	public final void testWrite() throws IOException {
		String inputfilePath = "resources/accept/MalhaTeste1-1.inp";
		NetWork netWork = new EpaFileReader().read(inputfilePath);
		
		EpaFileWriter fileWriter = new EpaFileWriter();
		fileWriter.write(netWork, filePath);
		
		netWork = new EpaFileReader().read(filePath);
		
		Set<IJunction<?>> junctions  = netWork.getJunctions();
		assertEquals(17, junctions.size());	
		
		Set<IReservoir> reservoirs = netWork.getReservoirs();
		assertEquals(1, reservoirs.size());
		
		Set<ITank<?>> tanks = netWork.getTanks();
		assertEquals(3, tanks.size());
		
		Set<IPipe> pipes = netWork.getPipes();
		assertEquals(17, pipes.size());
		
		Set<ITank<?>> pumps = netWork.getPumps();
		assertEquals(3, pumps.size());
		
		Set<String> options = netWork.getOptions();
		assertEquals(2, options.size());
		
		Set<String> patterns = netWork.getPattern();
		assertEquals(2, patterns.size());
		
		/*
		GLOBAL PRICE     0.10294
		GLOBAL PATTERN   preco
		DEMAND CHARGE    9.52
		*/
		Set<String> energy = netWork.getEnergy();
		assertEquals(3, energy.size());
		assertTrue(energy.contains("GLOBAL PRICE     0.10294"));
		assertTrue(energy.contains("GLOBAL PATTERN   preco"));
		assertTrue(energy.contains("DEMAND CHARGE    9.52"));
		
		Set<String> times = netWork.getTimes();
		assertEquals(4, times.size());
		
		Map<String, String> reports = netWork.getReports().getValues();
		assertEquals(7, reports.size());
		
		assertTrue(reports.containsKey("FILE"));
		assertEquals("Relatorio-1.txt", reports.get("FILE").trim());
	}
	
	/**
	 * @return Malha do teste de aceitação
	 */
	private NetWork createNetWork() {
		
		DefaultNetWork network = new DefaultNetWork.Builder().build();
		
		/*
		[JUNCTIONS]
		 ;ID     Elevation    Demand     Pattern
		 ;-------------------------------------------
		 N1       621.75
		 N2       633          2.12          demanda
		 N3       625          2.12         demanda
		 N4       618          2.12          demanda
		 N5       633          2.12          demanda
		 N6       640          2.12          demanda
		 N7       622          2.12          demanda
		 N8       628
		 N9       631
		 N10      631
		 N11      689          8.08            demanda
		 N12      640
		 N13      575
		 N14      575
		 N15      661
		 N16      635          4.04            demanda
		 N17      653          12.22         demanda
		 */
		IJunction n1 = new DefaultJuntion.Builder("N1", network).elevation(Measure.valueOf(621.75, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(2.12, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n2 = new DefaultJuntion.Builder("N2", network).elevation(Measure.valueOf(633, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(2.12, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n3 = new DefaultJuntion.Builder("N3", network).elevation(Measure.valueOf(625, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(2.12, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n4 = new DefaultJuntion.Builder("N4", network).elevation(Measure.valueOf(618, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(2.12, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n5 = new DefaultJuntion.Builder("N5", network).elevation(Measure.valueOf(633, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(2.12, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n6 = new DefaultJuntion.Builder("N6", network).elevation(Measure.valueOf(640, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(2.12, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n7 = new DefaultJuntion.Builder("N7", network).elevation(Measure.valueOf(622, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(2.12, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n8 = new DefaultJuntion.Builder("N8", network).elevation(Measure.valueOf(628, Length.SI_UNIT)).build();
		
		IJunction n9 = new DefaultJuntion.Builder("N9", network).elevation(Measure.valueOf(631, Length.SI_UNIT)).build();
		
		IJunction n10 = new DefaultJuntion.Builder("N10", network).elevation(Measure.valueOf(631, Length.SI_UNIT)).build();

		IJunction n11 = new DefaultJuntion.Builder("N11", network).elevation(Measure.valueOf(689, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(8.08, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n12 = new DefaultJuntion.Builder("N12", network).elevation(Measure.valueOf(640, Length.SI_UNIT)).build();
		
		IJunction n13 = new DefaultJuntion.Builder("N13", network).elevation(Measure.valueOf(575, Length.SI_UNIT)).build();
		
		IJunction n14 = new DefaultJuntion.Builder("N14", network).elevation(Measure.valueOf(575, Length.SI_UNIT)).build();
		
		IJunction n15 = new DefaultJuntion.Builder("N15", network).elevation(Measure.valueOf(661, Length.SI_UNIT)).build();
		
		IJunction n16 = new DefaultJuntion.Builder("N16", network).elevation(Measure.valueOf(635, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(4.04, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		IJunction n17 = new DefaultJuntion.Builder("N17", network).elevation(Measure.valueOf(653, Length.SI_UNIT))
								.baseDemandFlow(Measure.valueOf(12.22, VolumetricFlowRate.SI_UNIT)).demandPatternID("demanda")
								.build();
		
		network.addJuncao(n1);
		network.addJuncao(n2);
		network.addJuncao(n3);
		network.addJuncao(n4);
		network.addJuncao(n5);
		network.addJuncao(n6);
		network.addJuncao(n7);
		network.addJuncao(n8);
		network.addJuncao(n9);
		network.addJuncao(n10);
		network.addJuncao(n11);
		network.addJuncao(n12);
		network.addJuncao(n13);
		network.addJuncao(n14);
		network.addJuncao(n15);
		network.addJuncao(n16);
		network.addJuncao(n17);
		
		/*
		[RESERVOIRS]
		 ;ID     Head
		 ;---------------
		 M1      621.75
		 */
		IReservoir m1 = new DefaultReservoir.Builder("M1", network).head(Measure.valueOf(621.75, Length.SI_UNIT)).build();
		network.addReservoir(m1);
		
		/*
		[TANKS]
		 ;ID     Elev.     InitLvl     MinLvl     MaxLvl     Diam.
		 ;----------------------------------------------------------
		 R1       643       20         18.4        21.4       10.3
		 R2       689       10         7.2         12.7       11.3
		 R3       669       10         8.3         12.2       9.2
		 */
		ITank R1 = new DefaultTank.Builder("R1", network).elevation(Measure.valueOf(643, Length.SI_UNIT))
								.initialWaterLevel(Measure.valueOf(20, Length.SI_UNIT))
								.minimumWaterLevel(Measure.valueOf(18.4, Length.SI_UNIT))
								.maximumWaterLevel(Measure.valueOf(21.4, Length.SI_UNIT))
								.nominalDiameter(Measure.valueOf(10.3, Length.SI_UNIT))
								.build();
		
		ITank R2 = new DefaultTank.Builder("R2", network).elevation(Measure.valueOf(689, Length.SI_UNIT))
								.initialWaterLevel(Measure.valueOf(10, Length.SI_UNIT))
								.minimumWaterLevel(Measure.valueOf(7.2, Length.SI_UNIT))
								.maximumWaterLevel(Measure.valueOf(12.7, Length.SI_UNIT))
								.nominalDiameter(Measure.valueOf(11.3, Length.SI_UNIT))
								.build();
		
		ITank R3 = new DefaultTank.Builder("R3", network).elevation(Measure.valueOf(669, Length.SI_UNIT))
								.initialWaterLevel(Measure.valueOf(10, Length.SI_UNIT))
								.minimumWaterLevel(Measure.valueOf(8.3, Length.SI_UNIT))
								.maximumWaterLevel(Measure.valueOf(12.2, Length.SI_UNIT))
								.nominalDiameter(Measure.valueOf(9.2, Length.SI_UNIT))
								.build();
		
		network.addTanks(R1);
		network.addTanks(R2);
		network.addTanks(R3);
		
		/*
		[PIPES]
		 ;ID     Node1     Node2     Length     Diam.     Roughness
		 ;-----------------------------------------------------------
		 T1      N1         R1        2365       200        100
		 T2      R1         N2        181        250        100
		 T3      N2         N3        465        250        100
		 T4      N3         N4        122        250        100
		 T5      N4         N5        335        250        100
		 T6      N5         N6        164        250        100
		 T7      N6         N7        1768       250        100
		 T8      N7         N8        485        250        100
		 T9      N8         N9        280        200        150
		 T10     N10        R2        1441       200        150
		 T11     R2         N11       1          200        150
		 T12     N11        N12       3354       150        150
		 T13     N12        N13       6340       150        150
		 T14     N14        R3        5290       150        150
		 T15     R3         N15       172        100        150
		 T16     N15        N16       511        75         150
		 T17     N15        N17       1450       150        150
		 */
		
		IPipe p1 = new DefaultPipe.Builder("P1", network).length(Measure.valueOf(2365, Length.SI_UNIT))
									.diameter(Measure.valueOf(200, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();

		IPipe p2 = new DefaultPipe.Builder("P2", network).length(Measure.valueOf(181, Length.SI_UNIT))
									.diameter(Measure.valueOf(250, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p3 = new DefaultPipe.Builder("P3", network).length(Measure.valueOf(465, Length.SI_UNIT))
									.diameter(Measure.valueOf(250, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();
		
		
		IPipe p4 = new DefaultPipe.Builder("P4", network).length(Measure.valueOf(122, Length.SI_UNIT))
									.diameter(Measure.valueOf(250, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p5 = new DefaultPipe.Builder("P5", network).length(Measure.valueOf(335, Length.SI_UNIT))
									.diameter(Measure.valueOf(250, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p6 = new DefaultPipe.Builder("P6", network).length(Measure.valueOf(164, Length.SI_UNIT))
									.diameter(Measure.valueOf(250, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p7 = new DefaultPipe.Builder("P7", network).length(Measure.valueOf(1768, Length.SI_UNIT))
									.diameter(Measure.valueOf(250, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p8 = new DefaultPipe.Builder("P8", network).length(Measure.valueOf(485, Length.SI_UNIT))
									.diameter(Measure.valueOf(250, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p9 = new DefaultPipe.Builder("P9", network).length(Measure.valueOf(280, Length.SI_UNIT))
									.diameter(Measure.valueOf(200, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(150, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p10 = new DefaultPipe.Builder("P10", network).length(Measure.valueOf(1441, Length.SI_UNIT))
									.diameter(Measure.valueOf(200, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(100, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p11 = new DefaultPipe.Builder("P11", network).length(Measure.valueOf(1, Length.SI_UNIT))
									.diameter(Measure.valueOf(200, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(150, Dimensionless.SI_UNIT))
									.build();
		 
		IPipe p12 = new DefaultPipe.Builder("P12", network).length(Measure.valueOf(3354, Length.SI_UNIT))
									.diameter(Measure.valueOf(150, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(150, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p13 = new DefaultPipe.Builder("P13", network).length(Measure.valueOf(6340, Length.SI_UNIT))
									.diameter(Measure.valueOf(150, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(150, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p14 = new DefaultPipe.Builder("P14", network).length(Measure.valueOf(5290, Length.SI_UNIT))
									.diameter(Measure.valueOf(150, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(150, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p15 = new DefaultPipe.Builder("P15", network).length(Measure.valueOf(172, Length.SI_UNIT))
									.diameter(Measure.valueOf(100, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(150, Dimensionless.SI_UNIT))
									.build();

		IPipe p16 = new DefaultPipe.Builder("P16", network).length(Measure.valueOf(511, Length.SI_UNIT))
									.diameter(Measure.valueOf(75, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(150, Dimensionless.SI_UNIT))
									.build();
		
		IPipe p17 = new DefaultPipe.Builder("P17", network).length(Measure.valueOf(1450, Length.SI_UNIT))
									.diameter(Measure.valueOf(150, Length.SI_UNIT))
									.roughnessCoefficient(Measure.valueOf(150, Dimensionless.SI_UNIT))
									.build();
		
		 network.addPipe(p1, n1, R1);
		 network.addPipe(p2, n1, n2);
		 network.addPipe(p3, n2, n3);
		 network.addPipe(p4, n3, n4);
		 network.addPipe(p5, n4, n5);
		 network.addPipe(p6, n5, n6);
		 network.addPipe(p7, n6, n7);
		 network.addPipe(p8, n7, n8);
		 network.addPipe(p9, n8, n9);
		 network.addPipe(p10, n10, R2);
		 network.addPipe(p11, R2, n11);
		 network.addPipe(p12, n11, n12);
		 network.addPipe(p13, n12, n13);
		 network.addPipe(p14, n14, R3);
		 network.addPipe(p15, R3, n15);
		 network.addPipe(p16, n15, n16);
		 network.addPipe(p17, n15, n17);
		
		/*
		[PUMPS]
		 ;ID     Node1     Node2     Properties
		 ;---------------------------------------------
		 B1        M1       N1        HEAD B1
		 B2        N9       N10       HEAD B2
		 B3        N13      N14       HEAD B
		 */
		 IPump b1 = new DefaultPump.Builder("B1", network).headCurveID("B1").build();
		 IPump b2 = new DefaultPump.Builder("B2", network).headCurveID("B2").build();
		 IPump b3 = new DefaultPump.Builder("B3", network).headCurveID("B3").build();
		 
		 network.addPump(b1, m1, n1);
		 network.addPump(b2, n9, n10);
		 network.addPump(b3, n13, n14);
		
		/*
		[CURVES]
		 ;ID     Flow     Head
		 ;Curvas das bombas
		 ;-------------------------
		 B1       0        102.6
		 B1       10       102.06
		 B1       20       98.41
		 B1       30       91.67
		 B1       40       81.83
		 B1       50       68.69
		 B1       60       52.84
		 ;---------------------------
		 B2       0        102.42
		 B2       10       100.28
		 B2       20       95.90
		 B2       30       89.28
		 B2       40       80.42
		 B2       50       69.32
		 B2       60       55.97
		 ;---------------------------
		 B3       0        124.71
		 B3       10       122.05
		 B3       20       108.76
		 B3       30       80.88
		 B3       40       50.33
		 B3       50       5.19
		 */

		return network;
	}
}

