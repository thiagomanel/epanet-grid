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
public class PressaoNode implements Comparable<PressaoNode>{

	private final Measure<Pressure> pressao;
	private final String nodeName;

	/**
	 * @param pressao
	 * @param nodeName
	 */
	public PressaoNode(Measure<Pressure> pressao, String nodeName){
		this.pressao = pressao;
		this.nodeName = nodeName;
	}
	
	/**
	 * @return
	 */
	public Measure<Pressure> getPressao(){
		return this.pressao;
	}
	
	/**
	 * @return
	 */
	public String getNodeName(){
		return this.nodeName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(PressaoNode o) {
		
		if(o == null) throw new NullPointerException();
		
		return this.pressao.compareTo(o.pressao);
	}

}
