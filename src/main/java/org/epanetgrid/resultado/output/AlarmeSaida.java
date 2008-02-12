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
	
	public DateTimeInterval getDate() {
		return this.dateTimeInterval;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Dimension getTipoDeAlarme() {
		return this.tipoDeAlarme;
	}

	public Tipo getTipoDeRestricao() {
		return this.tipoDeRestricao;
	}

}
