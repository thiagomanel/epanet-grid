package org.epanetgrid.resultado.output;

public class IInputError {

	private String description;
	private String code;

	public IInputError(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

}
