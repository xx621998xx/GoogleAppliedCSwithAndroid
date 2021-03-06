/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TrieNode {
    char data;
    boolean is_end_of_string;
    Map<Character, TrieNode> nodes;
    String actualWord;

    public TrieNode(char data) {
        this.data = data;
        is_end_of_string = false;
        nodes = new HashMap<Character, TrieNode>();
    }

    public TrieNode children(char data) {
        return nodes.get(data);
    }
    private Map<Character, TrieNode> getAllchildren() {
        return nodes;
    }

    public boolean isChildExist(char c) {
        return children(c) != null;
    }

    public void add(String s) {
        if (s == null || s.trim().length() == 0) {
            return;
        }
        TrieNode current = this;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!current.isChildExist(c)) {
                TrieNode node = new TrieNode(c);
                current.nodes.put(c, node);
            }
            current = current.children(c);
        }
        current.actualWord=s;
        current.is_end_of_string = true;
    }

    public boolean isWord(String s) {
        TrieNode current = getLastNode(s);
        return current!=null?current.is_end_of_string:false;
    }

    private TrieNode getLastNode(String s) {
        TrieNode current = this;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!current.isChildExist(c)) {
                return null;
            }
            current = current.children(c);
        }
        return current;
    }

    public String getAnyWordStartingWith(String s) {
        List<String> retval=new ArrayList<String>();
        TrieNode current = getLastNode(s);
        if(current!=null){
            if(current.is_end_of_string){
                retval.add(s);
            }
            current.getAllChildrens(retval);

        }
        if(!retval.isEmpty())
        {
            Random rand = new Random();
            int x = rand.nextInt(retval.size());
            while((retval.get(x).length()-s.length())%2!=0)
            {
                 x = rand.nextInt(retval.size());
            }
            return retval.get(x);
        }
        return null;
    }
    private void getAllChildrens(List<String> retval){
        TrieNode current=this;
        Map<Character, TrieNode> nodes=current.getAllchildren();
        Collection< TrieNode> sets=nodes.values();
        for (TrieNode trieNode : sets) {
            if(trieNode.is_end_of_string){
                retval.add(trieNode.actualWord);
            }
            trieNode.getAllChildrens(retval);
        }
    }

}

/*class TrieNode1 {
    char c;
    HashMap<Character, TrieNode1> children = new HashMap<Character, TrieNode1>();
    boolean isLeaf;

    public TrieNode1() {}

    public TrieNode1(char c){
        this.c = c;
    }
}

 class TrieNode {
    private TrieNode1 root;

    public TrieNode() {
        root = new TrieNode1();
    }

    // Inserts a word into the trie.
    public void add(String word) {
        HashMap<Character, TrieNode1> children = root.children;

        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);

            TrieNode1 t;
            if(children.containsKey(c)){
                t = children.get(c);
            }else{
                t = new TrieNode1(c);
                children.put(c, t);
            }

            children = t.children;

            //set leaf node
            if(i==word.length()-1)
                t.isLeaf = true;
        }
    }

    // Returns if the word is in the trie.
    public boolean isWord(String word) {
        TrieNode1 t = searchNode1(word);

        if(t != null && t.isLeaf)
            return true;
        else
            return false;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public String getAnyWordStartingWith(String prefix) {
        if(searchNode2(prefix) == null)
            return null;
        else
            return searchNode2(prefix);
    }

    public String searchNode2(String str){
        String s=new String();
        Map<Character, TrieNode1> children = root.children;
        TrieNode1 t = null;
        for(int i=0; i<str.length(); i++)
        {
            char c = str.charAt(i);
            if(children.containsKey(c))
            {
                t = children.get(c);
                s=s+c;
                children = t.children;
            }
            else
            {
                return null;
            }
        }
        children = t.children;
        while(!t.isLeaf)
        {
            s=s+children;
            children = t.children;
        }
        return s;
    }
     public TrieNode1 searchNode1(String str){

         Map<Character, TrieNode1> children = root.children;
         TrieNode1 t = null;
         for(int i=0; i<str.length(); i++){
             char c = str.charAt(i);
             if(children.containsKey(c)){
                 t = children.get(c);

                 children = t.children;
             }else{
                 return null;
             }
         }

         return t;
     }
}
public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
    }

    public boolean isWord(String s) {
      return false;
    }

    public String getAnyWordStartingWith(String s) {
        return null;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
*/