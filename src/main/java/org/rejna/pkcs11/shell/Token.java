package org.rejna.pkcs11.shell;

public abstract class Token {
	private EnumCommands command = null;
	private Token[] nextTokens = new Token[0];

	public Token(EnumCommands command) {
		this.command = command;
	}

	public Token(Token... nextTokens) {
		this.nextTokens = nextTokens;
	}

	public Token[] getNextTokens(String word) {
		return nextTokens;
	}
	
	public EnumCommands getCommand() {
		return command;
	}
	
	public boolean isFinal() {
		return command != null;
	}
	
	public abstract Iterable<String> matches(String word);	
}
