package Tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
 
class TrieNode {
    char data;     
    LinkedList<TrieNode> children; 
    TrieNode parent;
    boolean isEnd;
 
    public TrieNode(char c) {
    	data = c;
        children = new LinkedList();
        isEnd = false;        
    }  
    
    public TrieNode getChild(char c) {
        if (children != null)
            for (TrieNode eachChild : children)
                if (eachChild.data == c)
                    return eachChild;
        return null;
    }
    
    protected List getWords() {
       List list = new ArrayList();      
       if (isEnd) {
    	   list.add(toString());
       }
       
       if (children != null) {
	       for (int i=0; i< children.size(); i++) {
	          if (children.get(i) != null) {
	             list.addAll(((TrieNode) children.get(i)).getWords());
	          }
	       }
       }       
       return list; 
    }
    
	public String toString() {
		if (parent == null) {
		     return "";
		} else {
		     return parent.toString() + new String(new char[] {data});
		}
	}
}
 
class Trie {
    private TrieNode root; //K�keni ba�lat�r�z
 
    public Trie() {
        root = new TrieNode(' '); 
    }
 
    public void insert(String word) { //Trie'ye bir s�zc�k eklemeye �al���yoruz.
        if (search(word) == true) 
            return;    
        
        TrieNode current = root; //ilk �nce current'i k�kle�tiririz. Bir karakterden di�erine ge�i� i�in
        TrieNode pre ;
        for (char ch : word.toCharArray()) { //kelimeyi karakterlere ay�r�r
        	pre = current;
            TrieNode child = current.getChild(ch);
            if (child != null) {  //cu
                current = child;
                child.parent = pre;
            } else {
                 current.children.add(new TrieNode(ch)); //yeni bir trie d���m� olu�tururuz
                 current = current.getChild(ch);
                 current.parent = pre;
            }
        }
        current.isEnd = true; 
    }
    
    public boolean search(String word) {
        TrieNode current = root;      //tekrar current k�kle�ir.
        for (char ch : word.toCharArray()) {  //currentin  d���m�n� ald�k. haritada olup olmad���n� kontrol ederiz
            if (current.getChild(ch) == null)  //d���m�n olup olmad���n� kontrol ederiz.
                return false;  
            else {
                current = current.getChild(ch);    
            }
        }      
        if (current.isEnd == true) {       
            return true;
        }
        return false;
    }
    
    public List autocomplete(String prefix) {     
       TrieNode lastNode = root;
       for (int i = 0; i< prefix.length(); i++) {
	       lastNode = lastNode.getChild(prefix.charAt(i));	     
	       if (lastNode == null) 
	    	   return new ArrayList();      
       }
       
       return lastNode.getWords(); //Bu node ile ili�kili b�t�n kelimeleri bul
    }
}    
 
public class AutocompleteWithTrie {
	 public static void main(String[] args) throws IOException { 
		 	boolean control=true;
		  	FileReader fileReader = new FileReader("test.txt");
		  	String line;
		  	BufferedReader br = new BufferedReader(fileReader);
		  	ArrayList<String> list = new ArrayList<String>();
		  	Trie t = new Trie();
		  	System.out.println("S�zl�k Y�kleniyor. L�tfen Bekleyin...");
		  	while ((line = br.readLine()) != null) 
		  	{              
		  		t.insert(line);              
		  	}
		  	br.close();
		  	System.out.println("S�zl�k Y�klendi.");
		  	while (control!=false)
		  	{
            Scanner scan = new Scanner(System.in);
            System.out.println("Bir Kelime Yaz�p Enter Tu�una Bas�n�z");
            String word = scan.nextLine();
            System.out.println("Olas� Kelimeler");
			List a= t.autocomplete(word);
			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i));
			}}
	  }
}