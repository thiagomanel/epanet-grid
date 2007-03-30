/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.pipes;

import javax.quantities.Dimensionless;

import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiagoepdc
 *
 */
public class PipeLossCoefPerturbador extends AbstractPerturbador<DefaultPipe> {

	/**
	 * @param label
	 * @param value
	 */
	public PipeLossCoefPerturbador(String label, double value) {
		super(label, value);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.IPerturbador#disturb(T)
	 */
	public DefaultPipe disturb(DefaultPipe component) {
		Measure<Dimensionless> newLossCoef = Measure.valueOf(getNewValue(), Dimensionless.SI_UNIT);
		return new DefaultPipe.Builder(getComponentLabel(), null).copy(component).lossCoefficient(newLossCoef).build();
	}

}
