/**
 * 
 */
package org.epanetgrid.perturbador;

import org.epanetgrid.perturbador.valueProvider.ValueProvider;


/**
 * @see org.epanetgrid.perturbador.IVariavelPerturbada
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 */
class DefaultVariavelPerturbada implements IVariavelPerturbada {

	private final String componentLabel;
	private final int numSamples;
	private final ValueProvider vp;
	
	/**
	 * @param label
	 * @param samples
	 */
	public DefaultVariavelPerturbada(String label, ValueProvider vp) {
		
		if(label == null) throw new IllegalArgumentException("The label must be not null");
		if(vp == null) throw new IllegalArgumentException("The ValueProvider must be not null");
		
		componentLabel = label;
		this.vp = vp;
		numSamples = vp.getValorPerturbado().length;
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.IVariavelPerturbada#getComponentLabel()
	 */
	public String getComponentLabel() {
		return this.componentLabel;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.IVariavelPerturbada#getNumSamples()
	 */
	public int getNumSamples() {
		return this.numSamples;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.IVariavelPerturbada#getValueProvider()
	 */
	public ValueProvider getValueProvider() {
		return this.vp;
	}

}
