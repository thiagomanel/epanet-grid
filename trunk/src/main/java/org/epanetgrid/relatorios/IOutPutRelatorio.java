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
	Quantidade de alarmes de press�o negativa - SAIDA
	Quantidade total de alarmes - SAIDA
	Press�o m�nima nos n�s em toda a simula��o - SAIDA
	Press�o m�xima por n� em toda a simula��o - SAIDA 
	Velocidade m�nima dos tubos em toda a simula��o - SAIDA
	Velocidade m�xima dos tubos em toda a simula��o - SAIDA
	*/
	
	public int getNumAlarmes(IAlarme.Tipo tipo);
	
	public int getNumTotalAlarmes();
	
	public PressaoMinimaNode pressaoMinimaNode();
	
	public PressaoMaximaNode pressaoMaximaNode();
	
}
