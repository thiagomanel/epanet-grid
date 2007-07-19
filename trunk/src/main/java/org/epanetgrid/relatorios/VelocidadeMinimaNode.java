/**
 * 
 */
package org.epanetgrid.relatorios;

import javax.quantities.Velocity;

import org.jscience.physics.measures.Measure;

/**
 * @author
 * since 18/07/2007
 */
public class VelocidadeMinimaNode {

	private final Measure<Velocity> velocidadeMinima;
	private final String nodeName;

	/**
	 * @param pressaoMaxima
	 * @param nodeName
	 */
	public VelocidadeMinimaNode(Measure<Velocity> velocidadeMinima, String nodeName){
		this.velocidadeMinima = velocidadeMinima;
		this.nodeName = nodeName;
	}
	
	
	
	/**
	 * @return
	 */
	public String getNodeName(){
		return this.nodeName;
	}


	/**
	 * @return Returns the velocidadeMaxima.
	 */
	public Measure<Velocity> getVelocidadeMinima() {
		return velocidadeMinima;
	}

}
