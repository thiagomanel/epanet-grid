/*
 * Copyright (c) 2002-2007 Universidade Federal de Campina Grande This program
 * is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.epanetgrid.perturbador;

import junit.framework.TestCase;

import org.epanetgrid.perturbador.perturbadores.junctions.JunctionBaseDemandFlowPerturbador;
import org.epanetgrid.perturbador.perturbadores.junctions.JunctionElevationPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeDiameterPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeLengthPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeLossCoefPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeRoughnessCoefPerturbador;
import org.epanetgrid.perturbador.perturbadores.pumps.PumpPowerPerturbador;
import org.epanetgrid.perturbador.perturbadores.pumps.PumpRelativeSpeedPerturbador;
import org.epanetgrid.perturbador.perturbadores.reservoir.ReservoirHeadPerturbador;
import org.epanetgrid.perturbador.perturbadores.tank.TankElevationPerturbador;
import org.epanetgrid.perturbador.perturbadores.tank.TankInitialWaterLevelPerturbador;
import org.epanetgrid.perturbador.perturbadores.tank.TankMaximumWaterLevel;
import org.epanetgrid.perturbador.perturbadores.tank.TankMinimunVolume;
import org.epanetgrid.perturbador.perturbadores.tank.TankMinimunWaterLevel;
import org.epanetgrid.perturbador.perturbadores.tank.TankNominalDiameter;
import org.epanetgrid.perturbador.perturbadores.valves.ValveDiameterPerturbador;
import org.epanetgrid.perturbador.perturbadores.valves.ValveLossCoefPerturbador;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 01/09/2007
 */
public class PerturbationTypeTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link org.epanetgrid.perturbador.PerturbationType#getPerturbador(java.lang.String, double, org.epanetgrid.perturbador.PerturbationType)}.
	 */
	public final void testGetPerturbador() {
		
		String label = "label";
		int value = 2;
		
		assertEquals(new JunctionBaseDemandFlowPerturbador(label, value),
						PerturbationType.getPerturbador(label, value, PerturbationType.JUNCTION_BASE_DEMAND_FLOW));
		
		assertEquals(new JunctionElevationPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.JUNCTION_ELEVATION));
		
		assertEquals(new PipeDiameterPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.PIPE_DIAMETER));
		
		assertEquals(new PipeLengthPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.PIPE_LENGTH));
		
		assertEquals(new PipeLossCoefPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.PIPE_LOSS_COEF));
		
		assertEquals(new PipeRoughnessCoefPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.PIPE_ROUGHNESS_COEF));
		
		assertEquals(new PumpPowerPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.PUMP_POWER));
		
		assertEquals(new PumpRelativeSpeedPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.PUMP_RELATIVE_SPEED));
		
		assertEquals(new ReservoirHeadPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.RESERVOIR_HEAD));
		
		assertEquals(new TankElevationPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.TANK_ELEVATION));
		
		assertEquals(new TankInitialWaterLevelPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.TANK_INIT_W_LEV));
		
		assertEquals(new TankMaximumWaterLevel(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.TANK_MAX_W_LEV));
		
		assertEquals(new TankMinimunVolume(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.TANK_MIN_VOL_LEV));
		
		assertEquals(new TankMinimunWaterLevel(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.TANK_MIN_W_LEV));
		
		assertEquals(new TankNominalDiameter(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.TANK_NOM_DIAM));
		
		assertEquals(new ValveDiameterPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.VALVE_DIAMETER));
		
		assertEquals(new ValveLossCoefPerturbador(label, value),
				PerturbationType.getPerturbador(label, value, PerturbationType.VALVE_LOSS_COEF));
	}

}
