package org.rejna.pkcs11.shell;

import java.io.IOException;

import jline.ConsoleReader;

import org.rejna.pkcs11.PKCS11;

public class ShellState {
	private PKCS11 p11;
	private int slot = -1;
	private ConsoleReader consoleReader;
	private boolean logged = false;
	private boolean inSession = false;
	
	public ShellState(PKCS11 p11, ConsoleReader consoleReader) {
		this.p11 = p11;
		this.consoleReader = consoleReader;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public void unsetSlot() {
		this.slot = -1;
	}
	
	public PKCS11 getPKCS11() {
		return p11;
	}
	
	public String getPIN() throws IOException {
		return consoleReader.readLine("Enter your PIN", '*');
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public boolean isInSession() {
		return inSession;
	}

	public void setInSession(boolean inSession) {
		this.inSession = inSession;
	}
}
