package org.rejna.p11shell;

import org.rejna.pkcs11.Defs;
import org.rejna.pkcs11.P11Exception;
import org.rejna.pkcs11.PKCS11;
import org.rejna.shell.ShellCompletor;

public class TestShell {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShellState state = new ShellState(PKCS11.getInstance(), null);
		ShellCompletor<ShellState, P11Commands> p11cmds = new ShellCompletor<ShellState, P11Commands>(null, P11Commands.values(), state);
		try {
			p11cmds.execute("info library");
			p11cmds.execute("select slot 1");
			p11cmds.execute("open session TRUE");
			try {
				state.getPKCS11().login("11915");
			} catch (P11Exception e) {
				if (e.getCode() != Defs.CKR_USER_ALREADY_LOGGED_IN)
					throw e;
			}
			state.setLogged(true);
			p11cmds.execute("find objects");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
