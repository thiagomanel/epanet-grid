/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.pumps;

import javax.quantities.Energy;

import org.epanetgrid.model.link.DefaultPump;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 25/06/2007
 */
public class PumpPowerPerturbador extends AbstractPerturbador<IPump> {

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
	public IPump disturb(IPump component) {
		Measure<Energy> newPower = Measure.valueOf(getNewValue(), Energy.SI_UNIT);
		return new DefaultPump.Builder(getComponentLabel(), null).copy(component).power(newPower).build();
	}

}
