/**
 * 
 */
package org.epanetgrid.resultado.output;

import javax.quantities.Length;

import org.jscience.physics.measures.Measure;

/**
 * @author
 * since 18/07/2007
 */
public class AlturaNode implements Comparable<AlturaNode>{

	private final Measure<Length> altura;
	private final String nodeName;

	/**
	 * @param altura
	 * @param nodeName
	 */
	public AlturaNode(Measure<Length> altura, String nodeName){
		this.altura = altura;
		this.nodeName = nodeName;
	}
	
	/**
	 * @return
	 */
	public Measure<Length> getAltura(){
		return this.altura;
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
	public int compareTo(AlturaNode o) {
		
		if(o == null) throw new NullPointerException();
		
		return this.altura.compareTo(o.altura);
	}

}
