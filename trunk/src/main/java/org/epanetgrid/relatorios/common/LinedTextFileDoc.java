/**
 * 
 */
package org.epanetgrid.relatorios.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.epanetgrid.util.RegexReader;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 31/07/2007
 */
public class LinedTextFileDoc{
	
	private final Set<IMatcher> matchers;
	private RegexReader source;
	private final File file;

	/**
	 * @param matchers
	 */
	private LinedTextFileDoc(File file, Set<IMatcher> matchers){
		this.source = source;
		this.matchers = matchers;
		this.file = file;
	}
	
	/**
	 * @return
	 * @throws IOException 
	 */
	public Map<IMatcher, Collection<IDocItem>> getDocItems() throws IOException{
		
		Map<IMatcher, Collection<IDocItem>> result = new HashMap<IMatcher, Collection<IDocItem>>();
		
		BufferedReader rd = new BufferedReader(new FileReader(file));
		
		String lineMatched;
		while ((lineMatched = rd.readLine()) == null) {
			
			for (IMatcher matcher : matchers) {
				if(matcher.match(lineMatched)) {
					addDocItem(result, matcher, new DefaultDocItem(lineMatched));
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
		private File file;
		
		public Builder(File file) {
			
			if(source == null) throw new IllegalArgumentException("The source must not be null");
			
			this.source = source;
			this.file = file;
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
			return new LinedTextFileDoc(file, matchers);
		}
		
	}
}
