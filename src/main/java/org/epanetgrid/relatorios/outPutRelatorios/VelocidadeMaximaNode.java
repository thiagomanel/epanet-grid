/**
 * 
 */
package org.epanetgrid.relatorios.outPutRelatorios;

import javax.quantities.Velocity;

import org.jscience.physics.measures.Measure;

/**
 * @author
 * since 18/07/2007
 */
public class VelocidadeMaximaNode {

	private final Measure<Velocity> velocidadeMaxima;
	private final String nodeName;

	/**
	 * @param pressaoMaxima
	 * @param nodeName
	 */
	public VelocidadeMaximaNode(Measure<Velocity> velocidadeMaxima, String nodeName){
		this.velocidadeMaxima = velocidadeMaxima;
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
	public Measure<Velocity> getVelocidadeMaxima() {
		return velocidadeMaxima;
	}

}
