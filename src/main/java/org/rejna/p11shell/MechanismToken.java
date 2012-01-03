package org.rejna.p11shell;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javatuples.Pair;
import org.rejna.pkcs11.Mechanism;
import org.rejna.pkcs11.MechanismType;
import org.rejna.shell.Token;

public class MechanismToken extends Token {
	private static final Pattern startNamePattern = Pattern.compile("[\\p{Alnum}_]");
	private static final Pattern endNamePattern = Pattern.compile("[^\\p{Alnum}_]");
	
	@Override
	public Iterable<Pair<String, String>> matches(String line) {
		String mechname = "";
		String remainder = "";
		String param = "";
		Vector<Pair<String, String>> matches = new Vector<Pair<String, String>>();
		Matcher m = startNamePattern.matcher(line);
		if (m.find()) {
			line = line.substring(m.start());
			
			m = endNamePattern.matcher(line);
			if (m.find()) {
				int start = m.start();
				mechname = line.substring(0, start);
				if (line.charAt(start) == '(') {
					int end = line.indexOf(')', start);
					if (end == -1)
						param = line.substring(start);
					else
						param = line.substring(start, end + 1);
				}
			}
			else
				mechname = line;
		}
		
		for (MechanismType mech : MechanismType.values()) {
			String name = mech.name();
			if (name.startsWith(mechname))
				matches.add(new Pair<String, String>(name + param, remainder));
		}
		return matches;
	}
	
	@Override
	public void addArguments(String value, Vector<Object> args) {
		int i = value.indexOf('(');
		if (i == -1)
			args.add(new Mechanism(MechanismType.valueOf(value)));
		else
			args.add(new Mechanism(MechanismType.valueOf(value.substring(0, i)), Long.parseLong(value.substring(i + 1, value.length() - 1))));
	}
	
	@Override
	public String toString() {
		return "MechanismToken";
	}
}
