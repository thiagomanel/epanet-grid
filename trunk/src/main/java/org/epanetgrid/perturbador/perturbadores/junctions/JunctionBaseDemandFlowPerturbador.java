/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores.junctions;

import javax.quantities.VolumetricFlowRate;

import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.perturbador.perturbadores.AbstractPerturbador;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 */
public class JunctionBaseDemandFlowPerturbador extends AbstractPerturbador<IJunction>{

	/**
	 * @param label
	 * @param value
	 */
	public JunctionBaseDemandFlowPerturbador(String label, double value) {
		super(label, value);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.IPerturbador#disturb(T)
	 */
	public DefaultJuntion disturb(IJunction component) {
		Measure<VolumetricFlowRate> newBaseDemandFlow = Measure.valueOf(getNewValue(), VolumetricFlowRate.SI_UNIT);
		return new DefaultJuntion.Builder(getComponentLabel(), null).copy(component).baseDemandFlow(newBaseDemandFlow).build();
	}
	
}
