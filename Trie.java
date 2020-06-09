

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Trie {

	private TrieNode root;
	private int size;

	// We Create our Trie with size 0
	public Trie() {
		root = new TrieNode();
		size = 0;
	}

	// This method adds to our Trie a single word
	public boolean add(String word) {
		TrieNode curr = root;
		// if the word is null the word is not adding
		if (curr == null || word == null) {
			return false;
		}
		// we create our array based on our word length
		int i = 0;
		char[] chars = word.toCharArray();

		// Loop through all letters in the word.
		while (i < chars.length) {
			LinkedList<TrieNode> children = curr.getChildren();
			// If the children nodes does not contain the letter at i.

			if (!childContain(children, String.valueOf(chars[i]))) {
				// Insert a new node
				insertNode(curr, chars[i]);
				if (i == chars.length - 1) {
					// Get the child and set word to true if we have found it.
					getChild(curr, chars[i]).setWord(true);
					size++;
					return true;
				}

			}
			// get the current child.
			curr = getChild(curr, chars[i]);
			// If the current children text is equal the word or not the word.
			if (curr.getText().equals(word) && !curr.isWord()) {
				// set the current word to true.
				curr.setWord(true);
				size++;
				return true;
			}
			i++;
		}
		return false;
	}


	//Returns true if the given string is found.
	public boolean find(String str) {

		LinkedList<TrieNode> children = root.getChildren();
		// start the node at the root
		TrieNode node = root;
		char[] chars = str.toCharArray();
		// Loop over all letters.
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			// If child contains c.
			if (childContain(children, String.valueOf(c))) {
				// get the child of the node, not root and it's children.
				node = getChild(node, c);

				if (node == null) {
					// we have reached a node that does not have children
					if (i == chars.length - 1) {
						// we are at the end of the word - it is found
						return true;
					} else {
						// we are in the middle of the word - it is not present
						return false;
					}
				}
				children = node.getChildren();

			} else {
				return false;
			}
		}
		// finally if the word's field is true return true
		if (node.isWord())
			return true;
		else
			return false;
	}

	//Inserts a new Node with a char
	private TrieNode insertNode(TrieNode node, Character c) {
		if (childContain(node.getChildren(), String.valueOf(c))) {
			return null;
		}

		TrieNode next = new TrieNode(c.toString());
		node.getChildren().add(next);
		return next;
	}


	 //Retrieves a given node's child with the character c
	private TrieNode getChild(TrieNode node, Character c) {
		LinkedList<TrieNode> list = node.getChildren();
		Iterator<TrieNode> iter = list.iterator();
		while (iter.hasNext()) {
			TrieNode n = iter.next();
			if (n.getText().equals(String.valueOf(c))) {
				return n;
			}

		}
		return null;
	}

	//Checks if any child contains the char c.
	private boolean childContain(LinkedList<TrieNode> list, String c) {
		Iterator<TrieNode> iter = list.iterator();

		while (iter.hasNext()) {
			TrieNode n = iter.next();

			if (n.getText().equals(c)) {
				return true;
			}
		}
		return false;
	}
	//this method transform a word with another with one different char
	private static ArrayList<String> tranformOneLetter(Trie trie, String word) {
		ArrayList<String> results = new ArrayList<String>();
		for (int i = 0; i < word.length(); ++i) {
			// I used the StringBuilder which is easy to to build Strings with this
			StringBuilder sb = new StringBuilder(word);
			// Going throw every letter and change it
			for (char letter = 'a'; letter <= 'z'; ++letter) {
				if (sb.charAt(i) != letter) {
					sb.setCharAt(i, letter);
					// each time we build a String with a change of one letter
					String newWord = sb.toString();
					// we check if the word we build is in our trie
					if (trie.find(newWord)) {
						// if it is we add it to our arraylist
						results.add(newWord);
					}
				}
			}
		}
		return results;
	}
	//this method transform a word with another that has one more char and one less
	private static ArrayList<String> tranformOneLength(Trie trie, String word) {
		ArrayList<String> results = new ArrayList<String>();
		for (int i = 0; i < word.length(); ++i) {
			
			// I used the StringBuilder which is easy to to build Strings with this
			StringBuilder sb = new StringBuilder(word);
			// we remove every char starting from the first
			String newWord = removeCharAt(word, i);
			// we check if the word we build is in our trie
			if (trie.find(newWord)) {
				// if it is we add it to our arraylist
				results.add(newWord);
			}

		}
		// if there are not words with less char then we check for a word with one more
		// char
		for (int i = 0; i < word.length() + 1; ++i) {
			// I used the StringBuilder which is easy to to build Strings with this
			StringBuilder sb = new StringBuilder(word);
			// Going throw every letter and change it
			for (char letter = 'a'; letter <= 'z'; ++letter) {
				// each time we insert a new char to our word
				sb.insert(i, letter);
				String newWord = sb.toString();
				// we check if the word we build is in our trie
				if (trie.find(newWord)) {
					// if it is we add it to our arraylist
					results.add(newWord);
				}
				// we delete the char that we insert to try other chars
				sb.deleteCharAt(i);
			}
		}

		return results;

	}

	// a method that remove a char from a specific position
	public static String removeCharAt(String s, int pos) {
		return s.substring(0, pos) + s.substring(pos + 1);

	}

	//Returns the trie's size.
	public int getSize() {
		return size;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		Trie trie = new Trie();
		File f = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String text;

		// Fill the array with the Strings of the txt file
		while ((text = br.readLine()) != null) {
			text = text.toLowerCase();
			trie.add(text);
		}
		File f2 = new File(args[1]);
		BufferedReader br2 = new BufferedReader(new FileReader(f2));
		text = "";
		System.out.println("Choose which of the followings you want:(1,2,3) \n" + "1: Find the wrong words \n"
				+ "2: Find the wrong words with alternatives \n"
				+ "3: Find the wrong words with alternatives which are different by one letter \n" + "4: Exit ");
		// give which option you want
		int answer = sc.nextInt();
		// Give the correct option
		while (answer <= 0 || answer > 4) {
			System.out.println("Wrong output please enter one of the followings:(1,2,3,4)");
			answer = sc.nextInt();
		}
		switch (answer) {
		case 1:
			System.out.println("The words that are not valid are:");
			while ((text = br2.readLine()) != null) {
				text = text.toLowerCase();
				if (!trie.find(text)) {
					System.out.println(text);
				}
			}
			break;
		case 2:
			System.out.println("The words that are not valid are:");
			while ((text = br2.readLine()) != null) {
				text = text.toLowerCase();
				if (!trie.find(text)) {

					if (tranformOneLetter(trie, text).isEmpty()) {
						System.out.print(text+"\t");
						System.out.println("There is not alternative for this word");
					} else {
						System.out.print(text+ "\t");
						System.out.print("The alternative of this is : ");
						System.out.println(tranformOneLetter(trie, text));
					}

				}
			}
			break;
		case 3:
			System.out.println("The words that are not valid are:");
			while ((text = br2.readLine()) != null) {
				text = text.toLowerCase();
				if (!trie.find(text)) {

					if (tranformOneLength(trie, text).isEmpty()) {
						System.out.print(text+"\t");
						System.out.println("There is not alternative for this word");
					} else {
						System.out.print(text+"\t");
						System.out.print("The alternative of this is : ");
						System.out.println(tranformOneLength(trie, text));
					}

				}
			}
			break;
		case 4:
			System.out.println("You have ended the program");

		}

	}

}
