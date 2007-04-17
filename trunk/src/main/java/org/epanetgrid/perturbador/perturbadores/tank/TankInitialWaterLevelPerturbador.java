/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.tank;

import javax.quantities.Length;

import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class TankInitialWaterLevelPerturbador extends AbstractPerturbador<DefaultTank> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public TankInitialWaterLevelPerturbador(String componentLabel, double newValue) {
		super(componentLabel, newValue);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public DefaultTank disturb(DefaultTank component) {
		Measure<Length> newInitialWaterLevel = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultTank.Builder(getComponentLabel(), null).copy(component).initialWaterLevel(newInitialWaterLevel).build();
	}

}
