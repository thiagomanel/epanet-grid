/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.pumps;

import javax.quantities.Dimensionless;

import org.epanetgrid.model.link.DefaultPump;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class PumpRelativeSpeedPerturbador extends AbstractPerturbador<DefaultPump> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public PumpRelativeSpeedPerturbador(String componentLabel, double newValue) {
		super(componentLabel, newValue);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public DefaultPump disturb(DefaultPump component) {
		Measure<Dimensionless> newRelativeSpeed = Measure.valueOf(getNewValue(), Dimensionless.SI_UNIT);
		return new DefaultPump.Builder(getComponentLabel(), null).copy(component).relativeSped(newRelativeSpeed).build();
	}

}
