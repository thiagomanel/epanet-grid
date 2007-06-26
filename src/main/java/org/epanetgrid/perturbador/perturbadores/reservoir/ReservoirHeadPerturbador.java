/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.reservoir;

import javax.quantities.Length;

import org.epanetgrid.model.nodes.DefaultReservoir;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class ReservoirHeadPerturbador extends AbstractPerturbador<IReservoir> {

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
	public IReservoir disturb(IReservoir component) {
		Measure<Length> newHead = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultReservoir.Builder(getComponentLabel(), null).copy(component).head(newHead).build();
	}

}
