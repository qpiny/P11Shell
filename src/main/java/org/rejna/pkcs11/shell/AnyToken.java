package org.rejna.pkcs11.shell;

import java.util.Arrays;

public class AnyToken extends Token {
	boolean can_be_empty;
	
	public AnyToken(boolean can_be_empty, EnumCommands command) {
		super(command);
		this.can_be_empty = can_be_empty;
	}

	public AnyToken(boolean can_be_empty, Token... keywords) {
		super(keywords);
		this.can_be_empty = can_be_empty;
	}
	
	public AnyToken(EnumCommands command) {
		this(false, command);
	}

	public AnyToken(Token... keywords) {
		this(false, keywords);
	}

	@Override
	public Iterable<String> matches(String word) {
		return Arrays.asList(word);
	}
	
}
