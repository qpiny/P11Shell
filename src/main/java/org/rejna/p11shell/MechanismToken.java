package org.rejna.p11shell;

import java.util.Vector;

import org.javatuples.Pair;
import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.MechanismType;
import org.rejna.shell.EnumToken;
import org.rejna.shell.Token;

public class MechanismToken extends EnumToken<MechanismType> {
	MechanismToken() {
		super(MechanismType.class);
	}
	/*
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Iterable<Pair<String, String>> matches(String line) {
		Vector<Pair<String, String>> matches = new Vector<Pair<String, String>>();
		for (Pair<String, String> mech : super.matches(line)) {
			String mechanismName = mech.getValue0();
			Class<? extends Enum<?>> possible = AttributeType.valueOf(attributeName).getPossible();
			if (possible != null) {
				Token nextToken = new EnumToken(possible);
				for (Pair<String, String> value : nextToken.matches(attr.getValue1()))
					matches.add(new Pair<String, String>(attributeName + " " + value.getValue0(), value.getValue1()));
			}
		}
		return matches;
	}
	*/
	
	@Override
	public void addArguments(String value, Vector<Object> args) {
		Pair<String, String> s = split(value);
		AttributeType attrType = AttributeType.valueOf(s.getValue0());
		args.add(attrType);
		args.add(attrType.getAttribute(s.getValue1()));
	}
}
