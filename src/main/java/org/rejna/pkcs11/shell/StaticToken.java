package org.rejna.pkcs11.shell;

import java.util.Arrays;
import java.util.List;

public class StaticToken extends Token {
	private String name;
	public StaticToken(String name, EnumCommands command) {
		super(command);
		this.name = name;
	}

	public StaticToken(String name, Token... keywords) {
		super(keywords);
		this.name = name;
	}

	@Override
	public List<String> matches(String word) {
		if (name.startsWith(word))
			return Arrays.asList(name);
		else
			return Arrays.asList();
	}
}
