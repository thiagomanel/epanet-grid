/**
 * 
 */
package org.epanetgrid.perturbador;

import org.epanetgrid.perturbador.perturbadores.IPerturbador;
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
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 */
public enum PerturbationType {
	
	PIPE_LENGTH, PIPE_DIAMETER, PIPE_ROUGHNESS_COEF, PIPE_LOSS_COEF,
	PUMP_POWER, PUMP_RELATIVE_SPEED,
	VALVE_LOSS_COEF, VALVE_DIAMETER,
	
	RESERVOIR_HEAD, 
	JUNCTION_ELEVATION, JUNCTION_BASE_DEMAND_FLOW, 
	TANK_ELEVATION, TANK_INIT_W_LEV, TANK_MIN_W_LEV, TANK_MIN_VOL_LEV, TANK_MAX_W_LEV, TANK_NOM_DIAM;
	
	/**
	 * 
	 * @param componentLabel
	 * @param value
	 * @param type
	 * @return
	 */
	public static IPerturbador getPerturbador(String componentLabel, double value, PerturbationType type){
		switch (type) {
		
		//LINKS
		case PIPE_LENGTH:
			return new PipeLengthPerturbador(componentLabel, value);
		case PIPE_DIAMETER:
			return new PipeDiameterPerturbador(componentLabel, value);
		case PIPE_ROUGHNESS_COEF:
			return new PipeRoughnessCoefPerturbador(componentLabel, value);
		case PIPE_LOSS_COEF:
			return new PipeLossCoefPerturbador(componentLabel, value);

		case PUMP_POWER:
			return new PumpPowerPerturbador(componentLabel, value);
		case PUMP_RELATIVE_SPEED:
			return new PumpRelativeSpeedPerturbador(componentLabel, value);
			
		case VALVE_DIAMETER:
			return new ValveDiameterPerturbador(componentLabel, value);
		case VALVE_LOSS_COEF:
			return new ValveLossCoefPerturbador(componentLabel, value);
			
		//NODES
		case RESERVOIR_HEAD:
			return new ReservoirHeadPerturbador(componentLabel, value);
			
		case JUNCTION_BASE_DEMAND_FLOW:
			return new JunctionBaseDemandFlowPerturbador(componentLabel, value);
		case JUNCTION_ELEVATION:
			return new JunctionElevationPerturbador(componentLabel, value);
			
		case TANK_ELEVATION:
			return new TankElevationPerturbador(componentLabel, value);
		case TANK_INIT_W_LEV:
			return new TankInitialWaterLevelPerturbador(componentLabel, value);
		case TANK_MAX_W_LEV:
			return new TankMaximumWaterLevel(componentLabel, value);
		case TANK_MIN_VOL_LEV:
			return new TankMinimunVolume(componentLabel, value);
		case TANK_NOM_DIAM:
			return new TankNominalDiameter(componentLabel, value);
		case TANK_MIN_W_LEV:
			return new TankMinimunWaterLevel(componentLabel, value);
			
		default:
			break;
		}
		
		return null;
	}
}
