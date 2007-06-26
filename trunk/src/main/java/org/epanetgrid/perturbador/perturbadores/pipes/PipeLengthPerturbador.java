/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.pipes;

import javax.quantities.Length;

import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class PipeLengthPerturbador extends AbstractPerturbador<IPipe> {

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
	public IPipe disturb(IPipe component) {
		Measure<Length> newLenght = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultPipe.Builder(getComponentLabel(), null).copy(component).length(newLenght).build();
	}

}
