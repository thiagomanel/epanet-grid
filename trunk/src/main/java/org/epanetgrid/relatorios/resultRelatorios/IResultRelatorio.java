/**
 * 
 */
package org.epanetgrid.relatorios.resultRelatorios;

import java.util.Date;

import org.jscience.economics.money.Money;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * @author Vinicius Ferraz
 * since 18/07/2007
 */
public interface IResultRelatorio {

	public Date getDataSimulacao();
	
	public Measure<Money> getCusto();
}
