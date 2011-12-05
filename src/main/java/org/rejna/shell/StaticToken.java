package org.rejna.shell;

import java.util.Arrays;
import java.util.Vector;

import org.javatuples.Pair;

public class StaticToken extends Token {
	private String name;
	
	public StaticToken(String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Pair<String, String>> matches(String line) {
		Pair<String, String> split = split(line);
		if (name.startsWith(split.getValue0()))
			return Arrays.asList(split.setAt0(name));
		else
			return Arrays.asList();
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public void addArguments(String value, Vector<Object> args) {
	}
}
