package org.rejna.p11shell;

import org.rejna.pkcs11.PKCS11;
import org.rejna.pkcs11.shell.P11Completor;
import org.rejna.pkcs11.shell.ShellState;

public class TestShell {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShellState state = new ShellState(PKCS11.getInstance(32), null);
		P11Completor p11cmds = new P11Completor(null, state);
		try {
			p11cmds.execute("info library");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
