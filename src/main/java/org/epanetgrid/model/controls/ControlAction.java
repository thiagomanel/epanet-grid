/**
 * 
 */
package org.epanetgrid.model.controls;

import org.epanetgrid.resultado.output.DateTimeInterval;

/**
 * @author alan
 *
 */
public class ControlAction implements Comparable<ControlAction> {
	
	private DateTimeInterval clocktime;
	private String linkID;
	private boolean state;

	/**
	 * @param clocktime
	 * @param linkID
	 * @param state
	 */
	public ControlAction(DateTimeInterval clocktime, String linkID,
			boolean state) {
		this.clocktime = clocktime;
		this.linkID = linkID;
		this.state = state;
	}

	/**
	 * @return
	 */
	public DateTimeInterval getClocktime() {
		return clocktime;
	}

	/**
	 * @return
	 */
	public String getLinkID() {
		return linkID;
	}

	/**
	 * @return
	 */
	public boolean state() {
		return state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(ControlAction action) {
		int comp = clocktime.compareTo(action.clocktime);
		if (comp == 0) {
			comp = linkID.compareTo(action.linkID);
		}
		return comp;
	}

	@Override
	public String toString() {
		return linkID + " " + clocktime + " " + state;
	}

}
