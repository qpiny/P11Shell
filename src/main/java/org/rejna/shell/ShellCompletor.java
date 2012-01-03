package org.rejna.shell;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.javatuples.Pair;
import org.rejna.tree.TreeNode;

import jline.Completor;
import jline.ConsoleReader;


class TokenNode<T> {
	Token token;
	String word;
	String remainder;
	boolean complete; // not useful any longer
	ShellCommand<T> command;
	boolean exact;
	
	public TokenNode(ShellCommand<T> command, Token token, String word, String remainder, boolean complete, boolean exact) {
		this.token = token;
		this.word = word;
		this.remainder = remainder;
		this.complete = complete;
		this.command = command;
		this.exact = exact;
	}
	public TokenNode<T> complete(boolean complete) {
		this.complete = complete;
		return this;
	}
	public TokenNode<T> exact(boolean exact) {
		this.exact = exact;
		return this;
	}
	@Override
	public String toString() {
		return "TokenNode[" + token + "," + word + "," + remainder + "," + complete + "," + command + "," + exact + "]";
	}
}

class Candidate<T> {
	private Vector<Pair<Token,String>> elements = new Vector<Pair<Token,String>>();
	private ShellCommand<T> command = null;

	public Candidate(ShellCommand<T> command) {
		this.command = command;
	}
	
	public Candidate() {
	}
	
	private Candidate(ShellCommand<T> command, Vector<Pair<Token,String>> elements) {
		this.elements = elements;
	}
	
	public void add(Token token, String word) {
		elements.add(new Pair<Token, String>(token, word));
	}
	
	@SuppressWarnings("unchecked")
	public Object clone() {
		return new Candidate<T>(command, (Vector<Pair<Token,String>>) elements.clone());
	}

	public String getLine() {
		StringBuilder sb = new StringBuilder();
		for (Pair<Token, String> e : elements) {
			sb.append(' ').append(e.getValue1());
		}
		return sb.substring(1);
	}

	public ShellCommand<T> getCommand() {
		return command;
	}
	
	public Candidate<T> setCommand(ShellCommand<T> command) {
		this.command = command;
		return this;
	}

	public void execute(T state) throws Exception {
		Vector<Object> args = new Vector<Object>();
		for (Pair<Token, String> e : elements) {
			e.getValue0().addArguments(e.getValue1(), args);
		}
		command.execute(state, args.toArray(new Object[args.size()]));
		
	}
	
}

public class ShellCompletor<STATE, CMD extends ShellCommand<STATE>> implements Completor {
	private ConsoleReader console;
	private STATE state;
	private CMD[] commands;

	public ShellCompletor(ConsoleReader console, CMD[] commands, STATE state) {
		this.console = console;
		this.state = state;
		this.commands = commands;
	}
	
	private int strcmp(String a, String b) {
		int i = 0;
		int max = Math.min(a.length(), b.length());
		while (i < max) {
			if (a.charAt(i) != b.charAt(i))
				break;
			i++;
		}
		return i;
	}

	private String getUnambiguousCompletions(
			List<Candidate<STATE>> candidates, boolean show) {
		Iterator<Candidate<STATE>> ite = candidates.iterator();
		String first;
		if (ite.hasNext()) {
			first = ite.next().getLine();
			if (show)
				System.out.println(first);
		}
		else
			first = "";
		int best = first.length();
		while (ite.hasNext()) {
			String match = ite.next().getLine();
			if (show)
				System.out.println(match);
			best = Math.min(best, strcmp(first, match));
		}
		return first.substring(0, best);
	}
	
	public TreeNode<TokenNode<STATE>> buildCommandTree(String line) {
		TreeNode<TokenNode<STATE>> tree = TreeNode.newTree(new TokenNode<STATE>(null, null, "", line, false, false));   //new HashMap<ShellCommand<STATE>, TreeNode<TokenNode>>();
		
		Vector<TreeNode<TokenNode<STATE>>> currentDepth = new Vector<TreeNode<TokenNode<STATE>>>();
		
		for (ShellCommand<STATE> command : commands) {
			if (!command.available(state))
				continue;
			Token token = command.getTokens()[0]; // empty array should be tested ? => no sense
			for (Pair<String, String> p : token.matches(line)) {
				String word = p.getValue0();
				String remainder = p.getValue1();
				currentDepth.add(tree.addChild(new TokenNode<STATE>(command, token, word, remainder, "".equals(remainder), line.startsWith(word))));
			}
		}
		
		while (tree.getNleaf() < 20 && !currentDepth.isEmpty()) {
			Vector<TreeNode<TokenNode<STATE>>> nextDepth = new Vector<TreeNode<TokenNode<STATE>>>();
			for (TreeNode<TokenNode<STATE>> node : currentDepth) {
				TokenNode<STATE> tn = node.getData();
				Token[] tokens = tn.command.getTokens();
				int ntoken = node.getDepth();
				if (ntoken < tokens.length) {
					Token token = tokens[ntoken];
					boolean empty = true;
					for (Pair<String, String> p : token.matches(tn.remainder)) {
						String word = p.getValue0();
						String remainder = p.getValue1();
						nextDepth.add(node.addChild(new TokenNode<STATE>(tn.command, token, word, remainder, "".equals(remainder), tn.remainder.startsWith(word))));
						empty = false;
					} 
					if (empty) {
						/* if no match then prune tree */
						while (node != null && node.getDepth() > 0 && node.getNchildren() == 0) {
							TreeNode<TokenNode<STATE>> parent = node.getParent();
							parent.removeChild(node);
							node = parent;
						}
					}
				}
			}
			currentDepth = nextDepth;
		}
		return tree;
	}
 
	@SuppressWarnings("unchecked")
	public Vector<Candidate<STATE>> getCandidates(
			TreeNode<TokenNode<STATE>> tree,
			Candidate<STATE> candidate,
			Vector<Candidate<STATE>> candidates) {
		
		Vector<Candidate<STATE>> result = new Vector<Candidate<STATE>>();
		boolean exactFound = false;
		for (TreeNode<TokenNode<STATE>> node : tree) {
			Candidate<STATE> c = (Candidate<STATE>) candidate.clone();
			if (exactFound && !node.getData().exact)
				continue;
			if (node.getData().exact && !exactFound) {
				exactFound = true;
				result.clear();
			}
			TokenNode<STATE> data = node.getData();
			c.setCommand(data.command);
			c.add(data.token, data.word);
			if (node.getNchildren() == 0) {
				result.add(c);
			} else {
				getCandidates(node, c, result);
			}
		}
		candidates.addAll(result);
		return candidates;
	}
	
	@SuppressWarnings("rawtypes")
	public int complete(String buffer, int cursor, List _candidates) {
		TreeNode<TokenNode<STATE>> tree = buildCommandTree(console.getCursorBuffer().toString().trim());
		Vector<Candidate<STATE>> candidates = getCandidates(tree, new Candidate<STATE>(null), new Vector<Candidate<STATE>>());
		//System.out.println("\n" + tree);
		//System.out.println();
		String line = getUnambiguousCompletions(candidates, true);
		try {
			System.out.println();
			console.getCursorBuffer().clearBuffer();
			console.drawLine();
			console.putString(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void execute(String line) throws Exception {
		int comment = line.indexOf('#');
		if (comment != -1)
			line = line.substring(0, comment).trim();
		if ("".equals(line))
			return;
		TreeNode<TokenNode<STATE>> tree = buildCommandTree(line);
		Vector<Candidate<STATE>> candidates = getCandidates(tree, new Candidate<STATE>(), new Vector<Candidate<STATE>>());
		
		if (candidates.size() == 1)
			candidates.firstElement().execute(state);
		else {
			System.out.println("ERROR : ");
			for (Candidate<STATE> candidate : candidates)
				System.out.println(candidate);
		}
	}
}
