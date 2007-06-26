/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.tank;

import javax.quantities.Volume;

import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class TankMinimunVolume extends AbstractPerturbador<ITank>  {

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
	public ITank disturb(ITank component) {
		Measure<Volume> newMinimumVolume = Measure.valueOf(getNewValue(), Volume.SI_UNIT);
		return new DefaultTank.Builder(getComponentLabel(), null).copy(component).minimumVolume(newMinimumVolume).build();
	}

}
