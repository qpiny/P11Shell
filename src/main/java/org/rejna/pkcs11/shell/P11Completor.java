package org.rejna.pkcs11.shell;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.P11Enum;

import jline.Completor;
import jline.ConsoleReader;

public class P11Completor implements Completor {
	private Token[] grammar;
	private ConsoleReader console;
	private ShellState state;

	public P11Completor(ConsoleReader console, ShellState state) {
		this.console = console;
		this.state = state;
		grammar = new Token[] {
			new StaticToken("info",
				new StaticToken("library",		EnumCommands.GET_INFO),
				new StaticToken("slot",			EnumCommands.GET_SLOT_INFO),
				new StaticToken("token",		EnumCommands.GET_TOKEN_INFO)),
			new StaticToken("select",
				new StaticToken("slot",			new AnyToken(EnumCommands.SELECT_SLOT))),
			new StaticToken("list",
				new StaticToken("slot",			EnumCommands.LIST_SLOT),
				new StaticToken("mechanism",	EnumCommands.LIST_MECHANISM)),
			new StaticToken("template",
				new StaticToken("add",			new EnumToken<AttributeType>(new  P11Enum<AttributeType>() {
					@Override public AttributeType valueOf(int v) { return AttributeType.valueOf(v); }
					@Override public AttributeType[] values() { return AttributeType.values(); }
				},
						new AttributeToken(EnumCommands.TEMPLATE_ADD))))
		};
	}

	
	/*
	GET_SLOT_INFO,
	GET_TOKEN_INFO,
	LIST_MECHANISM,
	OPEN_SESSION,
	LOGIN,
	LGOUT,
	CLOSE_SESSION,
	CLOSE_ALL_SESSION,
	FIND_OBJECTS,
	GET_ATTRIBUTE,
	SET_ATTRIBUTE,
	WRAP,
	GENERATE_KEY;	
	 * 
	 */

	public int strcmp(String a, String b) {
		int i = 0;
		int max = Math.min(a.length(), b.length());
		while (i < max) {
			if (a.charAt(i) != b.charAt(i))
				break;
			i++;
		}
		return i;
	}

	public String getUnambiguousCompletions(
			Vector<Candidate> candidates, boolean show) {
		String first = candidates.firstElement().getMatches().firstElement();
		int best = first.length();
		for (Candidate candidate : candidates)
			for (String match : candidate.getMatches()) {
				if (show)
					System.out.println(match);
				best = Math.min(best, strcmp(first, match));
			}
		return first.substring(0, best);
	}

	public String getUnambiguousCompletionsString(Vector<String> candidates) {
		if (candidates.size() == 0)
			return "";
		else if (candidates.size() == 1)
			return candidates.firstElement();
		else {
			Iterator<String> t = candidates.iterator();
			String first = t.next();
			int best = first.length();
			while (t.hasNext())
				best = Math.min(best, strcmp(first, t.next()));
			return first.substring(0, best);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int complete(String buffer, int cursor, List _candidates) {
		String line = "";
		String remaining_line = console.getCursorBuffer().toString().trim();

		Token[] currentTokens = grammar;
		boolean canContinue = true;
		while (canContinue) {
			Pattern pattern = Pattern.compile("\\s+");
			String[] words = pattern.split(remaining_line, 2);
			if (words.length == 1)
				remaining_line = "";
			else
				remaining_line = words[1];
			
			Vector<Candidate> candidates = new Vector<Candidate>();
			for (Token token : currentTokens) {
				Candidate candidate = new Candidate(token);
				if (candidate.match(words[0]))
					candidates.add(candidate);
			}
			
			if (!candidates.isEmpty()) {
				Candidate candidate = candidates.firstElement();
				String nextWord;

				if (candidates.size() == 1 && candidate.isSingle()) {
					nextWord = candidate.getMatches().firstElement();
					currentTokens = candidate.getToken().getNextTokens(nextWord);
				}
				else {
					System.out.println();
					nextWord = getUnambiguousCompletions(candidates, true);
					canContinue = false;
				}
				
				if (line.length() > 0)
					line += " " + nextWord;
				else
					line = nextWord;
				
			}
			else
				canContinue = false;
		}
			

		try {
			System.out.println();
			console.getCursorBuffer().clearBuffer();
			// console.moveCursor(0);
			// console.printNewline();
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

		
		Token[] currentTokens = grammar;
		Pattern pattern = Pattern.compile("\\s+");
		String[] words = pattern.split(line);
		EnumCommands command = null;
		
		for (String word : words) {
			if (currentTokens == null) {
				System.out.println("Unexpected token " + word);
				return;
			}
			
			Vector<Candidate> candidates = new Vector<Candidate>();
			for (Token token : currentTokens) {
				Candidate candidate = new Candidate(token);
				if (candidate.match(word))
					candidates.add(candidate);
			}
			if (candidates.size() != 1) {
				System.out.println(">Syntax error at token \"" + word + "\"");
				System.out.print("Expected :");
				for (Candidate candidate : candidates)
					System.out.print(" " + candidate.getToken());
				System.out.println();
				return;
			}
			
			Candidate candidate = candidates.firstElement();
			if (!candidate.isSingle()) {
				System.out.println("Syntax error at token \"" + word + "\"");
				System.out.print("Expected :");
				for (String s : candidate.getMatches())
					System.out.print(" " + s);
				System.out.println();
				return;
			}
			
			currentTokens = candidate.getToken().getNextTokens(word);
			if (currentTokens.length == 0)
				command = candidate.getToken().getCommand();
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
	}
}
