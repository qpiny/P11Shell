package org.rejna.p11shell;

import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javatuples.Pair;
import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.MechanismType;
import org.rejna.shell.Token;

public class MechanismToken extends Token {
	static final Pattern pattern = Pattern.compile("\\s*([\\S&&[^\\(]]*)((?:\\([^\\)]*)\\))?(?:\\s+(\\S+))?");

	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Pair<String, String>> matches(String line) {
		Vector<Pair<String, String>> matches = new Vector<Pair<String, String>>();
		Matcher matcher = pattern.matcher(line);
		if (!matcher.find()) {
			System.out.println("no match");
			return Arrays.asList();
		}
		
		String word = matcher.group(1);
		String param = matcher.group(2);
		if (param == null)
			param = "";
		String remainder = matcher.group(3);
		if (remainder == null)
			remainder = "";

		for (MechanismType mech : MechanismType.values()) {
			String name = mech.name();
			if (name.startsWith(word))
				matches.add(new Pair<String, String>(name + param, remainder));
		}
		return matches;
	}
	
	@Override
	public void addArguments(String value, Vector<Object> args) {
		Matcher matcher = pattern.matcher(value);
		if (!matcher.find())
			return;
		args.add(MechanismType.valueOf(matcher.group(1)));
		args.add(matcher.group(2));
	}
}
