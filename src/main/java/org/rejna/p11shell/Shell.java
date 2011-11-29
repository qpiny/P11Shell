package org.rejna.p11shell;

import jline.ConsoleReader;

import org.rejna.pkcs11.PKCS11;
import org.rejna.shell.ShellCompletor;


public class Shell {
	private ConsoleReader cr;
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: p11shell <arch>");
			System.out.println("arch : 32 or 64 depending of your system");
			System.out.println("note : for Windows 64bits, arch = 32");
		}
		else
			new Shell().start(Integer.parseInt(args[0]));
	}
	
	
	public void start(int arch) {
		try {
			cr = new ConsoleReader();
			cr.setBellEnabled(false);
			ShellState state = new ShellState(PKCS11.getInstance(arch), cr);
			ShellCompletor<ShellState, P11Commands> p11cmds = new ShellCompletor<ShellState, P11Commands>(cr, P11Commands.values(), state);
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
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
