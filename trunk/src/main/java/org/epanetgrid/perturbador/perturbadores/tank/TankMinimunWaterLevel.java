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
public class TankMinimunWaterLevel extends AbstractPerturbador<DefaultTank> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public TankMinimunWaterLevel(String componentLabel, double newValue) {
		super(componentLabel, newValue);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public DefaultTank disturb(DefaultTank component) {
		Measure<Length> newMinimumWaterLevel = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultTank.Builder(getComponentLabel(), null).copy(component).minimumWaterLevel(newMinimumWaterLevel).build();
	}

}
