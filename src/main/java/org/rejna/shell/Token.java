package org.rejna.shell;

import java.util.regex.Pattern;

import org.javatuples.Pair;

public abstract class Token {
	private static Pattern pattern = Pattern.compile("\\s+");
	
	protected Pair<String, String> split(String line) {
		String[] a = pattern.split(line, 2);
		if (a.length == 1)
			return new Pair<String, String>(a[0], "");
		else
			return Pair.fromArray(a);
	}
	
	public abstract Iterable<Pair<String, String>> matches(String line);	
}
