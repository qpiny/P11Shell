package org.rejna.pkcs11.shell;

import java.util.Vector;

import org.rejna.pkcs11.P11Enum;
import org.rejna.pkcs11.P11EnumWrapper;

public class EnumToken<T extends P11Enum> extends Token {
	private P11EnumWrapper<T> enumeration;
	
	public EnumToken(P11EnumWrapper<T> enumeration, EnumCommands command) {
		super(command);
		this.enumeration = enumeration;
	}
	
	public EnumToken(P11EnumWrapper<T> enumeration, Token... nextTokens) {
		super(nextTokens);
		//this.values = enumToString(enumeration);
		this.enumeration = enumeration;
	}
	/*
	public EnumToken(String[] values, EnumCommands command) {
		super(command);
		this.values = values;
	}
	
	public EnumToken(String[] values, Token... nextTokens) {
		super(nextTokens);
		this.values = values;
	}

	private String[] enumToString(Enum<?>[] es) {
		String[] ret = new String[es.length];
		for (int i = 0; i < es.length; i++)
			ret[i] = es[i].name();
		return ret;
	}
*/
	@Override
	public Iterable<String> matches(String word) {
		Vector<String> matches = new Vector<String>();
		for (T e : enumeration.values()) {
			String name = e.name();
			if (name.startsWith(word))
				matches.add(name);
		}
		return matches;
	}	
}
