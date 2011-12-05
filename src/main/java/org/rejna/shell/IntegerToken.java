package org.rejna.shell;

import java.util.Arrays;
import java.util.Vector;

import org.javatuples.Pair;

public class IntegerToken extends Token {
	static Vector<Pair<String, String>> digits;
	static {
		digits = new Vector<Pair<String,String>>();
		for (int i = 0; i < 10; i++)
			digits.add(new Pair<String, String>(Integer.toString(i), ""));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Pair<String, String>> matches(String line) {
		Pair<String, String> split = split(line);
		String word = split.getValue0();
		if ("".equals(word))
			return digits;
		try {
			Integer.parseInt(word);
			return Arrays.asList(split);
		} catch (NumberFormatException e) {
			return Arrays.asList();  
		}
	}
	
	@Override
	public String toString() {
		return "INTEGER_TOKEN";
	}

	@Override
	public void addArguments(String value, Vector<Object> args) {
		args.add(Integer.parseInt(value));
	}
	
}
