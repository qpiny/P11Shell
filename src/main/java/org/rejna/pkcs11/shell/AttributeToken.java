package org.rejna.pkcs11.shell;

import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.P11Enum;
import org.rejna.pkcs11.P11EnumWrapper;

public class AttributeToken extends EnumToken<AttributeType> {
	AttributeToken(EnumCommands command) {
		super(AttributeType.P11Enum, command);
	}
	
	AttributeToken(Token ... nextTokens) {
		super(AttributeType.P11Enum, nextTokens);
	}
	
	public Token[] getNextTokens(String word) {
		P11EnumWrapper<? extends P11Enum> possible = AttributeType.valueOf(word).getPossible();
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
