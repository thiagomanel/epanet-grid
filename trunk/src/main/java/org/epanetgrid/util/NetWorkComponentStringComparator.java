package org.epanetgrid.util;

import java.util.Comparator;

import org.epanetgrid.model.NetworkComponent;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 11/06/2007
 */
public class NetWorkComponentStringComparator implements Comparator<NetworkComponent<?>> {

	/**
	 * Compares <code>NetworkComponent</code> using their label properties. This method
	 * uses the alphanumeric schema. So <code>"a".compare("a1") < 0 </code> and <code>"b1".compare("a5") > 0 </code>
	 * @param o1
	 * @param o2
	 * @return
	 */
	public int compare(NetworkComponent<?> o1, NetworkComponent<?> o2) {
		String nome1 = o1.label();
		String nome2 = o2.label();
		String ord1 = StringUtils.extraiOrdem(nome1.toString());
		String ord2 = StringUtils.extraiOrdem(nome2.toString());
		
		String alpha1 = StringUtils.extraiAlpha(nome1);
		String alpha2 = StringUtils.extraiAlpha(nome2);
		
		int alphaComparation = alpha1.compareTo(alpha2);
		
		if(alphaComparation == 0) {
			if (ord1.length() > 0 && ord2.length() > 0) {
				return new Integer(ord1).intValue() - new Integer(ord2).intValue();
			}
			if (ord1.length() > 0 || ord2.length() > 0) {
				return ord1.length() - ord2.length();
			}
		}
		return alphaComparation;
	}
}
