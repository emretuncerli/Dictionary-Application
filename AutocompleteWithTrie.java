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
    private TrieNode root; //Kökeni baþlatýrýz
 
    public Trie() {
        root = new TrieNode(' '); 
    }
 
    public void insert(String word) { //Trie'ye bir sözcük eklemeye çalýþýyoruz.
        if (search(word) == true) 
            return;    
        
        TrieNode current = root; //ilk önce current'i kökleþtiririz. Bir karakterden diðerine geçiþ için
        TrieNode pre ;
        for (char ch : word.toCharArray()) { //kelimeyi karakterlere ayýrýr
        	pre = current;
            TrieNode child = current.getChild(ch);
            if (child != null) {  //cu
                current = child;
                child.parent = pre;
            } else {
                 current.children.add(new TrieNode(ch)); //yeni bir trie düðümü oluþtururuz
                 current = current.getChild(ch);
                 current.parent = pre;
            }
        }
        current.isEnd = true; 
    }
    
    public boolean search(String word) {
        TrieNode current = root;      //tekrar current kökleþir.
        for (char ch : word.toCharArray()) {  //currentin  düðümünü aldýk. haritada olup olmadýðýný kontrol ederiz
            if (current.getChild(ch) == null)  //düðümün olup olmadýðýný kontrol ederiz.
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
       
       return lastNode.getWords(); //Bu node ile iliþkili bütün kelimeleri bul
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
		  	System.out.println("Sözlük Yükleniyor. Lütfen Bekleyin...");
		  	while ((line = br.readLine()) != null) 
		  	{              
		  		t.insert(line);              
		  	}
		  	br.close();
		  	System.out.println("Sözlük Yüklendi.");
		  	while (control!=false)
		  	{
            Scanner scan = new Scanner(System.in);
            System.out.println("Bir Kelime Yazýp Enter Tuþuna Basýnýz");
            String word = scan.nextLine();
            System.out.println("Olasý Kelimeler");
			List a= t.autocomplete(word);
			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i));
			}}
	  }
}