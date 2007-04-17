/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.pumps;

import javax.quantities.Energy;

import org.epanetgrid.model.link.DefaultPump;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class PumpPowerPerturbador extends AbstractPerturbador<DefaultPump> {

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	public PumpPowerPerturbador(String componentLabel, double newValue) {
		super(componentLabel, newValue);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.AbstractPerturbador#disturb(T)
	 */
	@Override
	public DefaultPump disturb(DefaultPump component) {
		Measure<Energy> newPower = Measure.valueOf(getNewValue(), Energy.SI_UNIT);
		return new DefaultPump.Builder(getComponentLabel(), null).copy(component).power(newPower).build();
	}

}
