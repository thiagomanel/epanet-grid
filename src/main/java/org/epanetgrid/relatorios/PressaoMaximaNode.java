/**
 * 
 */
package org.epanetgrid.relatorios;

import javax.quantities.Pressure;

import org.jscience.physics.measures.Measure;

/**
 * @author
 * since 18/07/2007
 */
public class PressaoMaximaNode {

	private final Measure<Pressure> pressaoMaxima;
	private final String nodeName;

	/**
	 * @param pressaoMaxima
	 * @param nodeName
	 */
	public PressaoMaximaNode(Measure<Pressure> pressaoMaxima, String nodeName){
		this.pressaoMaxima = pressaoMaxima;
		this.nodeName = nodeName;
	}
	
	/**
	 * @return
	 */
	public Measure<Pressure> getPressaoMaxima(){
		return this.pressaoMaxima;
	}
	
	/**
	 * @return
	 */
	public String getNodeName(){
		return this.nodeName;
	}

}
