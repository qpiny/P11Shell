package org.rejna.shell;

import java.util.EnumSet;
import java.util.Vector;

import org.javatuples.Pair;

public class EnumToken<T extends Enum<T>> extends Token {
	private Class<T> enumeration;
	
	public EnumToken(Class<T> enumeration) {
		this.enumeration = enumeration;
	}
	
	@Override
	public Iterable<Pair<String, String>> matches(String line) {
		Vector<Pair<String, String>> matches = new Vector<Pair<String, String>>();
		Pair<String, String> split = split(line);
		String word = split.getValue0();
		String remaining = split.getValue1();
		for (T e : EnumSet.allOf(enumeration)) {
			String name = e.name();
			if (name.startsWith(word))
				matches.add(new Pair<String, String>(name, remaining));
		}
		return matches;
	}	
	
	@Override
	public String toString() {
		return enumeration.toString();
	}

	@Override
	public void addArguments(String value, Vector<Object> args) {
		args.add(Enum.valueOf(enumeration, value));
	}
}
