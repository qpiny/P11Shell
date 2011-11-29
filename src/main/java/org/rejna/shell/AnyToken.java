package org.rejna.shell;

import java.util.Arrays;

import org.javatuples.Pair;

public class AnyToken extends Token {
	boolean can_be_empty;
	
	public AnyToken(boolean can_be_empty) {
		this.can_be_empty = can_be_empty;
	}
	
	public AnyToken() {
		this(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Pair<String, String>> matches(String line) {
		Pair<String, String> split = split(line);
		if (can_be_empty)
			return Arrays.asList(split, new Pair<String, String>("", line));
		else
			return Arrays.asList(split);
	}
	
	@Override
	public String toString() {
		return "ANY_TOKEN";
	}
	
}
