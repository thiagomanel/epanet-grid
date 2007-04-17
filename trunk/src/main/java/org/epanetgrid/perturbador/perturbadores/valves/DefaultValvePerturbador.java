/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.valves;

import javax.quantities.Dimensionless;

import org.epanetgrid.model.link.DefaultValve;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class DefaultValvePerturbador extends AbstractPerturbador<DefaultValve> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public DefaultValvePerturbador(String componentLabel, double newValue) {
		super(componentLabel, newValue);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public DefaultValve disturb(DefaultValve component) {
		Measure<Dimensionless> newLossCoef = Measure.valueOf(getNewValue(), Dimensionless.SI_UNIT);
		return new DefaultValve.Builder(getComponentLabel(), null).copy(component).lossCoefficient(newLossCoef).build();
	}

}
