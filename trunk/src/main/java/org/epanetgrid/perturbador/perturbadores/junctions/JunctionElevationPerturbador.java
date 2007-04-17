/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.junctions;

import javax.quantities.Length;

import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiagoepdc
 *
 */
public class JunctionElevationPerturbador extends AbstractPerturbador<DefaultJuntion> {

	/**
	 * @param label
	 * @param value
	 */
	public JunctionElevationPerturbador(String label, double value) {
		super(label, value);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.IPerturbador#disturb(T)
	 */
	public DefaultJuntion disturb(DefaultJuntion component) {
		Measure<Length> newElevation = Measure.valueOf(getNewValue(), Length.SI_UNIT);
		return new DefaultJuntion.Builder(getComponentLabel(), null).copy(component).elevation(newElevation).build();
	}

}
