package org.epanetgrid.util;


public class StringUtils {

		/**
		 * Retira os números a direita de uma string.
		 *
		 * @param str
		 * @return
		 */
		public static String extraiOrdem(String str) {
			int inicio = str.length();
			for (int i = inicio-1; i >= 0; i--) {
				char c = str.charAt(i);
				if (!(c >= '0' && c <= '9')) {
					inicio = i+1;
					break;
				}
			}
			return str.substring(inicio);
		}
		
		public static String extraiAlpha(String str) {
			int fim = 0;
			for (int i = 0; i <= str.length() - 1; i++) {
				char c = str.charAt(i);
				if (!(c >= '0' && c <= '9')) {
					fim = i;
				}else {
					break;
				}
			}
			return str.substring(0, fim+1);
		}

	
}
