package org.rejna.pkcs11;

public class InvalidMechanismException extends Exception {
	private static final long serialVersionUID = 8595941167331118723L;
	
	public InvalidMechanismException(String message) {
		super(message);
	}
}
