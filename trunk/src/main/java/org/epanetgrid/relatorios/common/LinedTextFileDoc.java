/**
 * 
 */
package org.epanetgrid.relatorios.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	private final File file;

	/**
	 * @param matchers
	 */
	private LinedTextFileDoc(File file, Set<IMatcher> matchers){
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
		while ((lineMatched = rd.readLine()) != null) {
			for (IMatcher matcher : matchers) {
				if(matcher.match(lineMatched)) {
 					addDocItem(result, matcher, new DefaultDocItem(lineMatched.trim()));
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
		
		private Set<IMatcher> matchers;
		private File file;
		
		public Builder(File file) {
			
			if(file == null) throw new IllegalArgumentException("The file must not be null");
			
			this.file = file;
			this.matchers = new HashSet<IMatcher>();
		}

		/**
		 * @param matcher
		 * @return
		 */
		public Builder addMatcher(IMatcher matcher){
			
			if(matcher == null) throw new IllegalArgumentException("The matcher must not be null");
			
			if(matchers.contains(matcher)) throw new IllegalArgumentException("Builder already contains this matcher.");
			
			matchers.add(matcher);
			return this;
		}
		
		/**
		 * @return
		 */
		public LinedTextFileDoc build() throws FileNotFoundException{
			
			if(! file.exists()) throw new FileNotFoundException("File: "+file.getName()+" does not exist.");
			
			return new LinedTextFileDoc(file, matchers);
		}
		
	}
}
