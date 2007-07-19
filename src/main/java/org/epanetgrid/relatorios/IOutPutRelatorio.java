/**
 * 
 */
package org.epanetgrid.relatorios;



/**
 * @author
 * since 18/07/2007
 */
public interface IOutPutRelatorio {

	/**
	Quantidade de alarmes de pressão negativa - SAIDA
	Quantidade total de alarmes - SAIDA
	Pressão mínima nos nós em toda a simulação - SAIDA
	Pressão máxima por nó em toda a simulação - SAIDA 
	Velocidade mínima dos tubos em toda a simulação - SAIDA
	Velocidade máxima dos tubos em toda a simulação - SAIDA
	*/
	
	public int getNumAlarmes(IAlarme.Tipo tipo);
	
	public int getNumTotalAlarmes();
	
	public PressaoMinimaNode pressaoMinimaNode();
	
	public PressaoMaximaNode pressaoMaximaNode();
	
}
