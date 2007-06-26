/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.valves;

import javax.quantities.Dimensionless;

import org.epanetgrid.model.link.DefaultValve;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class ValveLossCoefPerturbador extends AbstractPerturbador<IValve> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public ValveLossCoefPerturbador(String componentLabel, double newValue) {
		super(componentLabel, newValue);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public IValve disturb(IValve component) {
		Measure<Dimensionless> newLossCoef = Measure.valueOf(getNewValue(), Dimensionless.SI_UNIT);
		return new DefaultValve.Builder(getComponentLabel(), null).copy(component).lossCoefficient(newLossCoef).build();
	}

}
