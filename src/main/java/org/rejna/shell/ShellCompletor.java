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

	public boolean match(int index, String line, Token[] tokens, TreeNode<Pair<String, Boolean>> possible) {
		if (index >= tokens.length)
			return "".equals(line);
		boolean completed = false;
		for (Pair<String, String> p : tokens[index].matches(line)) {
			TreeNode<Pair<String, Boolean>> currentNode = possible.addChild(new Pair<String, Boolean>(p.getValue0(), false));
			if (match(index + 1, p.getValue1(), tokens, currentNode)) {
				currentNode.setData(currentNode.getData().setAt1(true));
				completed = true;
			}
		}
		return completed;
	}
	
	public HashMap<ShellCommand<STATE>, TreeNode<Pair<String, Boolean>>> buildCommandTree(String line) {
		HashMap<ShellCommand<STATE>, TreeNode<Pair<String, Boolean>>> commandTree = new HashMap<ShellCommand<STATE>, TreeNode<Pair<String,Boolean>>>();
		
		for (ShellCommand<STATE> command : commands) {
			if (command.available(state)) {
				TreeNode<Pair<String, Boolean>> tree = TreeNode.newTree(new Pair<String, Boolean>("", false));
				if (match(0, line, command.getTokens(), tree))
					tree.setData(tree.getData().setAt1(true));
				commandTree.put(command, tree);
			}
		}
		return commandTree;
	}
	
	public void showTree(String prefix, TreeNode<Pair<String, Boolean>> tree, Vector<String> lines) {
		boolean isEmpty = true;
		if (!"".equals(prefix))
			prefix = prefix + " ";
		for (TreeNode<Pair<String, Boolean>> node : tree) {
			isEmpty = false;
			showTree(prefix + node.getData().getValue0(), node, lines);
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
		HashMap<ShellCommand<STATE>, TreeNode<Pair<String, Boolean>>> commandTree = buildCommandTree(line);
		
		int depth = 0;
		for (Entry<ShellCommand<STATE>, TreeNode<Pair<String, Boolean>>> ct : commandTree.entrySet()) {
			int d = ct.getValue().getMaxDepth();
			if (d > depth)
				depth = d;
		}
		
		System.out.println();
		Vector<String> lines = new Vector<String>();
		for (Entry<ShellCommand<STATE>, TreeNode<Pair<String, Boolean>>> ct : commandTree.entrySet()) {
			TreeNode<Pair<String, Boolean>> tree = ct.getValue();
			//System.out.println("Tree:" + ct.getKey().toString() + " -> " + tree);
			if (depth == tree.getMaxDepth())
				showTree("", ct.getValue(), lines);
		}
		
		//for (String l : lines)
		//	System.out.println("line=" + l);
		return  getUnambiguousCompletions(lines, true);
	}
	
	public void linearize(TreeNode<Pair<String, Boolean>> tree, Vector<String> line) {
		Iterator<TreeNode<Pair<String, Boolean>>> ite = tree.iterator();
		if (ite.hasNext()) {
			TreeNode<Pair<String, Boolean>> child = ite.next();
			if (ite.hasNext())
				throw new RuntimeException("nleaf != 1");
			String s = child.getData().getValue0();
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
		
		HashMap<ShellCommand<STATE>, TreeNode<Pair<String, Boolean>>> commandTree = buildCommandTree(line);
		ShellCommand<STATE> command = null;
		boolean unique = true;
		for (Entry<ShellCommand<STATE>, TreeNode<Pair<String, Boolean>>> ct : commandTree.entrySet()) {
			TreeNode<Pair<String, Boolean>> tree = ct.getValue();
			if (tree.getData().getValue1() && tree.getNleaf() == 1) {
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
			TreeNode<Pair<String, Boolean>> tree = commandTree.get(command);
			Vector<String> args = new Vector<String>();
			linearize(tree, args);
			//System.out.println("execute command " + command + " with arg :");
			//for (String a : args)
			//	System.out.println(" - " + a);
			command.execute(state, args.toArray(new String[args.size()]));
		}
			

		/*
		Vector<Token> currentTokens = grammar;
		Pattern pattern = Pattern.compile("\\s+");
		String[] words = pattern.split(line);
		EnumCommands command = null;
		
		for (String word : words) {
			if (currentTokens == null) {
				System.out.println("Unexpected token " + word);
				return;
			}
			
			HashMap<String, Vector<Token>> candidates = new HashMap<String, Vector<Token>>();
			for (Token token : currentTokens) {
				for (String match : token.matches(word)) {
					if (candidates.containsKey(match)) {
						candidates.get(match).add(token);
					}
					else {
						Vector<Token> tokens = new Vector<Token>();
						tokens.add(token);
						candidates.put(match, tokens);
					}
				}
			}
			
			if (!candidates.isEmpty()) {
				Set<String> possible_words = candidates.keySet();
				if (possible_words.size() == 1) {
					currentTokens = new Vector<Token>();
					Vector<Token> tokens = candidates.get(word);
					if (tokens.size() == 1)
						command = tokens.firstElement().getCommand();
					for (Token token : tokens)
						currentTokens.addAll(Arrays.asList(token.getNextTokens(word)));
				}
				else {
					System.out.println("Syntax error at token \"" + word + "\"");
					System.out.print("Expected :");
					for (String candidate : possible_words)
						System.out.print(" " + candidate);
					System.out.println();
					return;
				}
			}
			else {
				System.out.println("Unexpected token " + word);
				return;
			}
		}
		if (command == null) {
			System.out.println("Unfished command, expected :");
			for (Token token : currentTokens)
				System.out.print(" " + token);
			System.out.println();
		}
		else {
			command.execute(state, words);
		}
		*/
	}
}
