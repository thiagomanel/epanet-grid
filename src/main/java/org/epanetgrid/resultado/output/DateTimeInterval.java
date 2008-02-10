package org.epanetgrid.resultado.output;

public class DateTimeInterval {

	private int hour;
	private int minutes;
	private int seconds;

	public DateTimeInterval(int hour, int minutes, int seconds) {
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}
	
	@Override
	public String toString() {
		return hour + ":" + minutes + ":" + seconds;
	}

}
