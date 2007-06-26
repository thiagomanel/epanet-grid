/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.tank;

import javax.quantities.Length;

import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class TankMaximumWaterLevel extends AbstractPerturbador<ITank> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public TankMaximumWaterLevel(String componentLabel, double newValue) {
		super(componentLabel, newValue);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public ITank disturb(ITank component) {
		Measure<Length> newMaximumWaterLevel = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultTank.Builder(getComponentLabel(), null).copy(component).maximumWaterLevel(newMaximumWaterLevel).build();
	}

}
