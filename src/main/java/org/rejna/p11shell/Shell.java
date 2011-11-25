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
			ShellState state = new ShellState(PKCS11.getInstance(64), cr);
			P11Completor p11cmds = new P11Completor(cr, state);
			cr.addCompletor(p11cmds);
			while (true) {
				int slot = state.getSlot();
				String line;
				if (slot != -1)
					line = cr.readLine("[" + slot + "] >");
				else 
					line = cr.readLine(">");
				if (line == null)
					return;
				else if ("exit".equals(line))
					return;
				else if (!line.trim().equals("")) {
					p11cmds.execute(line);
					//System.out.println(" => " + line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
