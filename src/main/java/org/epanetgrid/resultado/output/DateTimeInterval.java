package org.epanetgrid.resultado.output;

public class DateTimeInterval implements Comparable<DateTimeInterval> {

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

	public DateTimeInterval plus(long milli) {
		int seconds = (int) (milli / 1000);
		int minutes = seconds / 60;
		seconds = seconds % 60;
		int hours = minutes / 60;
		minutes = minutes % 60;
		return new DateTimeInterval(hours, minutes, seconds);
	}
	
	public int compareTo(DateTimeInterval dateTime) {
		int sec1 = (hour * 60 * 60) + (minutes * 60) + seconds;
		int sec2 = (dateTime.hour * 60 * 60) + (dateTime.minutes * 60) + dateTime.seconds;
		return sec1 - sec2;
	}
	
	@Override
	public String toString() {
		return hour + ":" + minutes + ":" + seconds;
	}

	public String toAmPmString() {
		int hour = Math.min(this.hour, Math.abs(this.hour - 12));
		String unit = "AM";
		if (this.hour > 12) {
			unit = "PM";
		} else if (this.hour == 0) {
			hour = 12;
		}
		return hour + ":" + minutes + " " + unit;
	}

	public static void main(String[] args) {
		System.out.println(90 / 60);
	}

}
