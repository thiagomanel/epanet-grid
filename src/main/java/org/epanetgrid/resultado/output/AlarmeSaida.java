package org.epanetgrid.resultado.output;

import javax.units.Dimension;

public class AlarmeSaida implements IAlarme {

	private DateTimeInterval dateTimeInterval;
	private String descricao;
	private Tipo tipoDeRestricao;
	private Dimension tipoDeAlarme;

	public AlarmeSaida(DateTimeInterval dateTimeInterval, String descricao, Dimension tipoDeAlarme, Tipo tipoDeRestricao) {
		this.dateTimeInterval = dateTimeInterval;
		this.descricao = descricao;
		this.tipoDeAlarme = tipoDeAlarme;
		this.tipoDeRestricao = tipoDeRestricao;
	}
	
	@Override
	public DateTimeInterval getDate() {
		return this.dateTimeInterval;
	}

	@Override
	public String getDescricao() {
		return this.descricao;
	}

	@Override
	public Dimension getTipoDeAlarme() {
		return this.tipoDeAlarme;
	}

	@Override
	public Tipo getTipoDeRestricao() {
		return this.tipoDeRestricao;
	}

}
