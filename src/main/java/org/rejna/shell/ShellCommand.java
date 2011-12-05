package org.rejna.shell;

public interface ShellCommand<STATE> {
	public void execute(STATE state, Object[] args) throws Exception;
	public boolean available(STATE state);
	public Token[] getTokens();
}
