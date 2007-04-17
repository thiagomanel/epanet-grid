/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.tank;

import javax.quantities.Volume;

import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class TankMinimunVolume extends AbstractPerturbador<DefaultTank>  {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public TankMinimunVolume(String componentLabel, double newValue) {
		super(componentLabel, newValue);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public DefaultTank disturb(DefaultTank component) {
		Measure<Volume> newMinimumVolume = Measure.valueOf(getNewValue(), Volume.SI_UNIT);
		return new DefaultTank.Builder(getComponentLabel(), null).copy(component).minimumVolume(newMinimumVolume).build();
	}

}
