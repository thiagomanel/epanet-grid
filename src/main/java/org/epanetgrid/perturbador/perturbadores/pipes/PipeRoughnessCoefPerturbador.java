/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.pipes;

import javax.quantities.Dimensionless;

import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class PipeRoughnessCoefPerturbador extends AbstractPerturbador<IPipe> {

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
	public IPipe disturb(IPipe component) {
		Measure<Dimensionless> newRoughnessCoef = Measure.valueOf(getNewValue(), Dimensionless.SI_UNIT);
		return new DefaultPipe.Builder(getComponentLabel(), null).copy(component).roughnessCoefficient(newRoughnessCoef).build();
	}

}
