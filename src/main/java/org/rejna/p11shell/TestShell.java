package org.rejna.p11shell;

import org.rejna.pkcs11.PKCS11;
import org.rejna.shell.ShellCompletor;

public class TestShell {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShellState state = new ShellState(PKCS11.getInstance(32), null);
		ShellCompletor<ShellState, P11Commands> p11cmds = new ShellCompletor<ShellState, P11Commands>(null, P11Commands.values(), state);
		try {
			p11cmds.execute("info library");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
