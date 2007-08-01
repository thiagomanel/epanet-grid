/**
 * 
 */
package org.epanetgrid.relatorios.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 31/07/2007
 */
public class LinedTextFileDoc{
	
	private final Set<IMatcher> matchers;
	private Iterable<String> source;

	/**
	 * @param source
	 * @param matchers
	 */
	private LinedTextFileDoc(Iterable<String> source, Set<IMatcher> matchers){
		this.source = source;
		this.matchers = matchers;
	}
	
	/**
	 * @return
	 */
	public Map<IMatcher, Collection<IDocItem>> getDocItems(){
		
		Map<IMatcher, Collection<IDocItem>> result = new HashMap<IMatcher, Collection<IDocItem>>();
		
		for (String source : this.source) {
			for (IMatcher matcher : matchers) {
				if(matcher.match(source)) {
					addDocItem(result, matcher, new DefaultDocItem(source));
				}
			}
		}
		
		return result;
	}
	
	/**
	 * @param result
	 * @param matcher
	 * @param docItem
	 */
	private void addDocItem(Map<IMatcher, Collection<IDocItem>> result, IMatcher matcher, IDocItem docItem) {
		
		if(! result.containsKey(matcher)){
			result.put(matcher, new LinkedList<IDocItem>());
		}
		
		result.get(matcher).add(docItem);
	}

	//static factory
	public static class Builder{
		
		private Iterable<String> source;
		private Set<IMatcher> matchers;
		
		public Builder(Iterable<String> source) {
			
			if(source == null) throw new IllegalArgumentException("The source must not be null");
			
			this.source = source;
			this.matchers = new HashSet<IMatcher>();
		}

		/**
		 * @param matcher
		 * @return
		 */
		public Builder addMatcher(IMatcher matcher){
			
			if(matcher == null) throw new IllegalArgumentException("The matcher must not be null");
			
			matchers.add(matcher);
			return this;
		}
		
		/**
		 * @return
		 */
		public LinedTextFileDoc build() {
			return new LinedTextFileDoc(source, matchers);
		}
		
	}
}
