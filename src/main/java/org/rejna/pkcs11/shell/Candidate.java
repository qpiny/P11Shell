package org.rejna.pkcs11.shell;

import java.util.Vector;

public class Candidate {
	private Token token;
	private Vector<String> matches = new Vector<String>();
	
	public Candidate(Token token) {
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
	
	public boolean match(String word) {
		for (String s : token.matches(word))
			matches.add(s);
		return !matches.isEmpty();
	}
	
	public boolean isSingle() {
		return matches.size() == 1;
	}
	
	public Vector<String> getMatches() {
		return matches;
	}
}
