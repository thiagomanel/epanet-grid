/**
 * 
 */
package org.epanetgrid.relatorios.outPutRelatorios;

import javax.quantities.Pressure;

import org.jscience.physics.measures.Measure;

/**
 * @author
 * since 18/07/2007
 */
public class PressaoMinimaNode {

	private final Measure<Pressure> pressaoMinima;
	private final String nodeName;

	/**
	 * @param pressaoMinima
	 * @param nodeName
	 */
	public PressaoMinimaNode(Measure<Pressure> pressaoMinima, String nodeName){
		this.pressaoMinima = pressaoMinima;
		this.nodeName = nodeName;
	}
	
	/**
	 * @return
	 */
	public Measure<Pressure> getPressaoMinima(){
		return this.pressaoMinima;
	}
	
	/**
	 * @return
	 */
	public String getNodeName(){
		return this.nodeName;
	}

}
