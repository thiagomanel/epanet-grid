/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.pipes;

import javax.quantities.Length;

import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiagoepdc
 *
 */
public class PipeLengthPerturbador extends AbstractPerturbador<DefaultPipe> {

	/**
	 * @param label
	 * @param value
	 */
	public PipeLengthPerturbador(String label, double value) {
		super(label, value);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.IPerturbador#disturb(T)
	 */
	public DefaultPipe disturb(DefaultPipe component) {
		Measure<Length> newLenght = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultPipe.Builder(getComponentLabel(), null).copy(component).length(newLenght).build();
	}

}
