package org.rejna.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import org.javatuples.Pair;
import org.rejna.tree.TreeNode;

import jline.Completor;
import jline.ConsoleReader;


class TokenNode<T> {
	Token token;
	String word;
	boolean complete;
	ShellCommand<T> Command;
	boolean exact;
	
	public TokenNode(ShellCommand<T> command, Token token, String word, boolean complete, boolean exact) {
		this.token = token;
		this.word = word;
		this.complete = complete;
		this.Command = command;
		this.exact = exact;
	}
	public TokenNode<T> complete(boolean complete) {
		this.complete = complete;
		return this;
	}
	public boolean complete() {
		return complete;
	}
	public String word() {
		return word;
	}
	public TokenNode<T> exact(boolean exact) {
		this.exact = exact;
		return this;
	}
	public boolean exact() {
		return exact;
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
			List<String> candidates, boolean show) {
		Iterator<String> ite = candidates.iterator();
		String first;
		if (ite.hasNext()) {
			first = ite.next();
			if (show)
				System.out.println(first);
		}
		else
			first = "";
		int best = first.length();
		while (ite.hasNext()) {
			String match = ite.next();
			if (show)
				System.out.println(match);
			best = Math.min(best, strcmp(first, match));
		}
		return first.substring(0, best);
	}

	public boolean match(int index, String line, ShellCommand<STATE> command, Token[] tokens, TreeNode<TokenNode<STATE>> tree) {
		boolean completed = false;
		if (index < tokens.length) {
			for (Pair<String, String> p : tokens[index].matches(line)) {
				TreeNode<TokenNode<STATE>> node = tree.addChild(
						new TokenNode<STATE>(command, tokens[index], p.getValue0(), false, line.startsWith(p.getValue0())));
				if (match(index + 1, p.getValue1(), command, tokens, node)) {
					node.getData().complete(true);
					completed = true;
				}
			}
		}
		else 
			completed = "".equals(line);
		return completed;
	}
	
	public TreeNode<TokenNode<STATE>> buildCommandTree(String line) {
		TreeNode<TokenNode<STATE>> tree = TreeNode.newTree(new TokenNode<STATE>(null, null, "", false, false));   //new HashMap<ShellCommand<STATE>, TreeNode<TokenNode>>();
		
		for (ShellCommand<STATE> command : commands) {
			if (command.available(state))
				match(0, line, command, command.getTokens(), tree);
		}
		return tree;
	}
	
	public void showTree(String prefix, TreeNode<TokenNode<STATE>> tree, Vector<String> lines) {
		boolean isEmpty = true;
		if (!"".equals(prefix) && !prefix.endsWith(" "))
			prefix = prefix + " ";
		for (TreeNode<TokenNode<STATE>> node : tree) {
			isEmpty = false;
			showTree(prefix + node.getData().word, node, lines);
		}
		if (isEmpty && !"".equals(prefix))
			lines.add(prefix);
	}
	
	@SuppressWarnings("rawtypes")
	public int complete(String buffer, int cursor, List _candidates) {
		String line = showCompletion(console.getCursorBuffer().toString().trim());
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
	
	public String showCompletion(String line) {
		TreeNode<TokenNode<STATE>> commandTree = buildCommandTree(line);

		System.out.println();
		Vector<String> lines = new Vector<String>();
		for (Entry<ShellCommand<STATE>, TreeNode<TokenNode<STATE>>> ct : commandTree.entrySet()) {
			TreeNode<TokenNode<STATE>> tree = ct.getValue();
			if (tree.getMaxDepth() > 0)
				showTree("", ct.getValue(), lines);
		}

		return  getUnambiguousCompletions(lines, true);
	}
	
	private void linearize(TreeNode<TokenNode<STATE>> tree, Vector<String> line) {
		Iterator<TreeNode<TokenNode<STATE>>> ite = tree.iterator();
		if (ite.hasNext()) {
			TreeNode<TokenNode<STATE>> child = ite.next();
			if (ite.hasNext())
				throw new RuntimeException("nleaf != 1");
			String s = child.getData().word();
			line.add(s);
			linearize(child, line);
		}
	}
	
	public void execute(String line) throws Exception {
		int comment = line.indexOf('#');
		if (comment != -1)
			line = line.substring(0, comment).trim();
		if ("".equals(line))
			return;
		
		HashMap<ShellCommand<STATE>, TreeNode<TokenNode>> commandTree = buildCommandTree(line);
		ShellCommand<STATE> command = null;
		boolean unique = true;
		for (Entry<ShellCommand<STATE>, TreeNode<TokenNode>> ct : commandTree.entrySet()) {
			TreeNode<TokenNode> tree = ct.getValue();
			if (tree.getData().complete() && tree.getNleaf() == 1) {
				if (command == null) {
					command = ct.getKey();
				}
				else {
					unique = false;
					break;
				}
			}
		}
		
		if (unique == false || command == null)
			showCompletion(line);
		else {
			TreeNode<TokenNode> tree = commandTree.get(command);
			Vector<String> args = new Vector<String>();
			linearize(tree, args);

			command.execute(state, args.toArray(new String[args.size()]));
		}
	}
}
