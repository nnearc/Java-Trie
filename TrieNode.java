
import java.util.LinkedList;

public class TrieNode {
	//The TrieNode has 3 charachteristics
	// a boolean if the word finished
	// the text the string
	//and finally a way to connect them children
	private boolean isWord;
	private String text;
	private LinkedList<TrieNode> children;
	//create every possible constructor
	public TrieNode() {
		children = new LinkedList<TrieNode>();
		text = "";
		isWord = false;
	}

	public TrieNode(String text) {
		this();
		this.text = text;
	}
	// Putting getters and Setters 
	public LinkedList<TrieNode> getChildren() {
		return children;
	}

	public boolean isWord() {
		return isWord;
	}

	public void setWord(boolean isWord) {
		this.isWord = isWord;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
