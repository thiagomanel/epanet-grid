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
public class PipeRoughnessCoefPerturbador extends AbstractPerturbador<DefaultPipe> {

	/**
	 * @param label
	 * @param value
	 */
	public PipeRoughnessCoefPerturbador(String label, double value) {
		super(label, value);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.IPerturbador#disturb(T)
	 */
	public DefaultPipe disturb(DefaultPipe component) {
		Measure<Dimensionless> newRoughnessCoef = Measure.valueOf(getNewValue(), Dimensionless.SI_UNIT);
		return new DefaultPipe.Builder(getComponentLabel(), null).copy(component).roughnessCoefficient(newRoughnessCoef).build();
	}

}
