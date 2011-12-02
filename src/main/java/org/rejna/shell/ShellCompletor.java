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
	@Override
	public String toString() {
		return "TokenNode[" + token + "," + word + "," + remainder + "," + complete + "," + command + "," + exact + "]";
	}
}

class Candidate<T> {

	public void add(Token token, String word) {
		// TODO Auto-generated method stub
		
	}
	
	public Object clone() {
		return null;
	}

	public String getLine() {
		// TODO Auto-generated method stub
		return null;
	}

	public ShellCommand<T> getCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	public void execute() {
		// TODO Auto-generated method stub
		
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
		
		//currentDepth.add(tree);

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
		
		while (tree.getMaxDepth() < 20 && !currentDepth.isEmpty()) {
			Vector<TreeNode<TokenNode<STATE>>> nextDepth = new Vector<TreeNode<TokenNode<STATE>>>();
			for (TreeNode<TokenNode<STATE>> node : currentDepth) {
				TokenNode<STATE> tn = node.getData();
				Token[] tokens = tn.command.getTokens();
				int depth = node.getDepth();
				if (depth < tokens.length) {
					Token token = tokens[depth];
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
	
	@SuppressWarnings("unchecked")
	public Vector<Candidate<STATE>> getCandidates(
			TreeNode<TokenNode<STATE>> tree,
			Candidate<STATE> candidate,
			Vector<Candidate<STATE>> candidates) {
		
		Vector<Candidate<STATE>> result = new Vector<Candidate<STATE>>();
		boolean exactFound = false;
		for (TreeNode<TokenNode<STATE>> node : tree) {
			if (exactFound && !node.getData().exact)
				continue;
			if (node.getData().exact && !exactFound) {
				exactFound = true;
				result.clear();
			}
			TokenNode<STATE> data = node.getData();
			candidate.add(data.token, data.word);
			if (node.getNchildren() == 0) {
				result.add(candidate);
			} else {
				getCandidates(node, (Candidate<STATE>) candidate.clone(), result);
			}
		}
		candidates.addAll(result);
		return candidates;
	}
	
	@SuppressWarnings("rawtypes")
	public int complete(String buffer, int cursor, List _candidates) {
		TreeNode<TokenNode<STATE>> tree = buildCommandTree(console.getCursorBuffer().toString().trim());
		Vector<Candidate<STATE>> candidates = getCandidates(tree, new Candidate<STATE>(), new Vector<Candidate<STATE>>());
		//if (candidates.size() > 1)
		//	for (String candidate : candidates)
		//		System.out.println(candidate);
		System.out.println();
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
	
	@Deprecated
	private void linearize(TreeNode<TokenNode<STATE>> tree, Vector<String> line) {
		Iterator<TreeNode<TokenNode<STATE>>> ite = tree.iterator();
		if (ite.hasNext()) {
			TreeNode<TokenNode<STATE>> child = ite.next();
			if (ite.hasNext())
				throw new RuntimeException("nleaf != 1");
			String s = child.getData().word;
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
		TreeNode<TokenNode<STATE>> tree = buildCommandTree(line);
		Vector<Candidate<STATE>> candidates = getCandidates(tree, new Candidate<STATE>(), new Vector<Candidate<STATE>>());
		
		if (candidates.size() == 1) {
			candidates.firstElement().execute();
			System.out.println("EXECUTE : " + candidates.firstElement());
		} else {
			System.out.println("ERROR : ");
			for (Candidate<STATE> candidate : candidates)
				System.out.println(candidate);
		}

			//command.execute(state, args.toArray(new String[args.size()]));
	}
}
