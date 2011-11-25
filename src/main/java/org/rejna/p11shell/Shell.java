package org.rejna.p11shell;

import jline.ConsoleReader;

import org.rejna.pkcs11.PKCS11;
import org.rejna.pkcs11.shell.P11Completor;
import org.rejna.pkcs11.shell.ShellState;


public class Shell {
	private ConsoleReader cr;
	
	public static void main(String[] args) {
		new Shell().start();
	}
	
	
	public void start() {
		try {
			cr = new ConsoleReader();
			ShellState state = new ShellState(PKCS11.getInstance(32), cr);
			P11Completor p11cmds = new P11Completor(cr, state);
			cr.addCompletor(p11cmds);
			while (true) {
				int slot = state.getSlot();
				StringBuffer prompt = new StringBuffer();
				if (slot != -1)
					prompt.append("[" + slot + "]");
				if (state.isLogged())
					prompt.append('#');
				else if (state.isInSession())
					prompt.append('$');
				prompt.append("> ");
				String line = cr.readLine(prompt.toString());
				if (line == null)
					return;
				else if ("exit".equals(line) || "quit".equals(line))
					return;
				else if (!line.trim().equals("")) {
					try {
						p11cmds.execute(line);
					} catch (Exception e) {
						System.out.println("ERROR : " + e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
