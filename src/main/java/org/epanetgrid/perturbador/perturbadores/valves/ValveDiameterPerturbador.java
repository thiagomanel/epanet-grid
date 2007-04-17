/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.valves;

import javax.quantities.Length;

import org.epanetgrid.model.link.DefaultValve;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class ValveDiameterPerturbador extends AbstractPerturbador<DefaultValve> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public ValveDiameterPerturbador(String componentLabel, double newValue) {
		super(componentLabel, newValue);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public DefaultValve disturb(DefaultValve component) {
		Measure<Length> newDiameter = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultValve.Builder(getComponentLabel(), null).copy(component).diameter(newDiameter).build();
	}

}
