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
	String remainder;
	boolean complete;
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
	
	public TreeNode<TokenNode<STATE>> buildCommandTree(String line) {
		TreeNode<TokenNode<STATE>> tree = TreeNode.newTree(new TokenNode<STATE>(null, null, "", line, false, false));   //new HashMap<ShellCommand<STATE>, TreeNode<TokenNode>>();
		
		Vector<TreeNode<TokenNode<STATE>>> currentDepth = new Vector<TreeNode<TokenNode<STATE>>>();
		Vector<TreeNode<TokenNode<STATE>>> nextDepth = new Vector<TreeNode<TokenNode<STATE>>>();
		currentDepth.add(tree);

		for (ShellCommand<STATE> command : commands) {
			Token token = command.getTokens()[0]; // empty array should be tested ? => no sense
			for (Pair<String, String> p : token.matches(line)) {
				String word = p.getValue0();
				String remainder = p.getValue1();
				currentDepth.add(tree.addChild(new TokenNode<STATE>(command, token, word, remainder, "".equals(remainder), line.startsWith(word))));
			}
		}
		
		while (tree.getMaxDepth() < 20 && !currentDepth.isEmpty()) {
			for (TreeNode<TokenNode<STATE>> node : currentDepth) {
				TokenNode<STATE> tn = node.getData();
				Token[] tokens = tn.command.getTokens();
				int depth = node.getDepth();
				if (depth <= tokens.length) {
					Token token = tokens[depth - 1];
					boolean empty = true;
					for (Pair<String, String> p : token.matches(tn.remainder)) {
						String word = p.getValue0();
						String remainder = p.getValue1();
						nextDepth.add(node.addChild(new TokenNode<STATE>(tn.command, token, word, remainder, "".equals(remainder), tn.remainder.startsWith(word))));
						empty = false;
					} 
					if (empty) {
						/* if no match then prune tree */
						while (node != null && node.getMaxDepth() == 1) {
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
 
	@Deprecated
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
		TreeNode<TokenNode<STATE>> tree = buildCommandTree(console.getCursorBuffer().toString().trim());
		
		
		
		
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
