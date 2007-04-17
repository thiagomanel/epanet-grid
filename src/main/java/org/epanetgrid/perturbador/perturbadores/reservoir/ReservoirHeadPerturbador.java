/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.reservoir;

import javax.quantities.Length;

import org.epanetgrid.model.nodes.DefaultReservoir;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class ReservoirHeadPerturbador extends AbstractPerturbador<DefaultReservoir> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public ReservoirHeadPerturbador(String componentLabel, double newValue) {
		super(componentLabel, newValue);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public DefaultReservoir disturb(DefaultReservoir component) {
		Measure<Length> newHead = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultReservoir.Builder(getComponentLabel(), null).copy(component).head(newHead).build();
	}

}
