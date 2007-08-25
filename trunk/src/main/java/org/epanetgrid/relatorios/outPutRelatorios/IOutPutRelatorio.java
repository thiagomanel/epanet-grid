/**
 * 
 */
package org.epanetgrid.relatorios.outPutRelatorios;




/**
 * @author
 * since 18/07/2007
 */
public interface IOutPutRelatorio {

	/**
	Quantidade de alarmes de pressao negativa - SAIDA
	Quantidade total de alarmes - SAIDA
	Presso minima nos nos em toda a simulacao - SAIDA
	Pressao maxima por no em toda a simulacao - SAIDA 
	Velocidade minima dos tubos em toda a simulacao - SAIDA
	Velocidade maxima dos tubos em toda a simulacao - SAIDA
	*/
	
	public int getNumAlarmes(IAlarme.Tipo tipo);
	
	public int getNumTotalAlarmes();
	
	public PressaoNode pressaoMinimaNode();
	
	public PressaoNode pressaoMaximaNode();

	public VelocidadeNode velocidadeMaximaNode();
	
	public VelocidadeNode velocidadeMinimaNode();
	
}
