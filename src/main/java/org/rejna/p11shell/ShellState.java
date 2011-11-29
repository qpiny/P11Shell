package org.rejna.p11shell;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import jline.ConsoleReader;

import org.rejna.pkcs11.Attribute;
import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.PKCS11;

public class ShellState {
	private PKCS11 p11;
	private int slot = -1;
	private ConsoleReader consoleReader;
	private boolean logged = false;
	private boolean inSession = false;
	private Vector<Attribute> attributes = new Vector<Attribute>();
	
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
		return consoleReader.readLine("Enter your PIN : ", '*');
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

	public Attribute[] getAttributes() {
		return attributes.toArray(new Attribute[attributes.size()]);
	}
	
	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public void clearAttribute() {
		attributes.clear();
	}
	
	public void removeAttribute(int index) {
		attributes.remove(index);
	}
	
	public void removeAttribute(AttributeType attribute) {
		Iterator<Attribute> attr = attributes.iterator();
		while (attr.hasNext())
			if (attr.next().attributeType().equals(attribute)) {
				attr.remove();
				break;
			}
	}
}
