package org.rejna.pkcs11.shell;

import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.P11Enum;

public class AttributeToken extends EnumToken<AttributeType> {
	AttributeToken(EnumCommands command) {
		super(AttributeType.values(), command);
	}
	
	AttributeToken(Token ... nextTokens) {
		super(AttributeType.values(), nextTokens);
	}
	
	public Token[] getNextTokens(String word) {
		P11Enum possible = AttributeType.valueOf(word).getPossible();
		new EnumToken()
		if (possible == null) {
			if (isFinal())
				return new Token[] { new AnyToken(getCommand()) };
			else
				return new Token[] { new AnyToken(super.getNextTokens(word)) };
		}
		else {
			if (isFinal())
				return new Token[] { new EnumToken(possible, getCommand()) };
			else
				return new Token[] { new EnumToken(possible, super.getNextTokens(word)) };
		}
	}

}
