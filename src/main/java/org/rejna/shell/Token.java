package org.rejna.shell;

import org.javatuples.Pair;

public abstract class Token {
	
	protected Pair<String, String> split(String line) {
		int index = line.indexOf(' ');
		if (index == -1)
			return new Pair<String, String>(line, "");
		else
			return new Pair<String, String>(line.substring(0, index), line.substring(index + 1));
	}
	
	public abstract Iterable<Pair<String, String>> matches(String line);	
}
