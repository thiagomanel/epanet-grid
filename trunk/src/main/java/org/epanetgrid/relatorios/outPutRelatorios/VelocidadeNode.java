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
public class VelocidadeNode implements Comparable<VelocidadeNode>{

	private final Measure<Velocity> velocidade;
	private final String nodeName;

	/**
	 * @param velocidade
	 * @param nodeName
	 */
	public VelocidadeNode(Measure<Velocity> velocidade, String nodeName){
		this.velocidade = velocidade;
		this.nodeName = nodeName;
	}
	
	/**
	 * @return
	 */
	public String getNodeName(){
		return this.nodeName;
	}

	/**
	 * @return Returns the velocidade.
	 */
	public Measure<Velocity> getVelocidade() {
		return velocidade;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(VelocidadeNode o) {
		
		if(o == null) throw new NullPointerException();
		
		return this.velocidade.compareTo(o.velocidade);
	}

}
