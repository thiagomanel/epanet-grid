/**
 * 
 */
package org.epanetgrid.model;

import javax.quantities.Dimensionless;

import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public interface IEmitters extends NetworkComponent {

	public Measure<Dimensionless> getFlowCoefficient();
}
