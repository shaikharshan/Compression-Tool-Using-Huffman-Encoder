package CompressionTool;

import java.io.File;
import java.io.FileInputStream;
import java.time.temporal.ChronoField;
import java.util.*;

/*The Java BitSet class implements a vector of bits. The BitSet grows automatically as more bits are needed.
 The BitSet class comes under java.util package.
 The BitSet class extends the Object class and provides the implementation of Serializable and Cloneable interfaces.*/

class Node{
    public Character ch;
    public Integer freq;
    public Node left;
    public Node right;

    public Node(Character ch,Integer freq){
        this.ch = ch;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }
    public Node(Integer sum){
        this.freq = sum;
        this.ch = null;
        this.left = null;
        this.right = null;
    }

}

class NodeComparator implements Comparator<Node>{

    @Override
    public int compare(Node o1, Node o2) {
        if(o1.freq < o2.freq){
            return -1;
        } else if (o1.freq > o2.freq) {
            return 1;
        }
        else{
            return 0;
        }
    }
}

public class HuffmanTree {

    private File file;
    private HashMap<Character,Integer> freqMap;
    private Node huffmanRoot;

    public HashMap<Character,Integer> countChar(File f){
        HashMap<Character,Integer> mp = new HashMap<>();
        try{
            FileInputStream fs = new FileInputStream(f);
            int c;
            while((c=fs.read()) != -1){
                Character ch = (char) c;
                mp.put(ch,mp.getOrDefault(ch,0) + 1);
            }
            fs.close();
            return mp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void levelOrder(Node root){
            Queue<Node> q = new ArrayDeque<>();
            q.add(root);
            while(!q.isEmpty()){
                int size = q.size();
                for(int i=0;i<size;i++){
                    Node front = q.poll();
                    System.out.print(front.ch+"/"+front.freq+" ");
                    if(front.left != null){
                        q.add(front.left);
                    }
                    if(front.right != null){
                        q.add(front.right);
                    }
                }
                System.out.print("\n----------------------------------------------------\n");
            }
    }

    private Node createTree(HashMap<Character,Integer> mp){
        Queue<Node> q = new PriorityQueue<>(new NodeComparator());
        for(var i:mp.entrySet()){
            Node leaf = new Node(i.getKey(),i.getValue());
            q.add(leaf);
        }
        while(q.size() != 1 ){
            Node n1  = q.poll();
            Node n2 = q.poll();
            int sum = n1.freq + n2.freq;
            Node newNode = new Node(sum);
            newNode.left = n1;

            newNode.right = n2;
            q.add(newNode);
        }
        Node finalTree = q.poll();
        return finalTree;

    }

    private void DFS(HashMap<Character,BitSet> mp,ArrayList<Boolean> arr,Node root,int ind){
        if(root==null){
            return;
        }
        if(root.ch != null){
            BitSet b = new BitSet();
            for(int i=arr.size()-1;i>=0;i--){
                b.set(i,arr.get(i));
            }
            mp.put(root.ch,b);
            return;
        }

        if(root.left != null){

            arr.add(false);
            DFS(mp,arr,root.left,ind+1);
            arr.remove(ind);

        }
        if(root.right != null){
            arr.add(true);
            DFS(mp,arr,root.right,ind+1);
            arr.remove(ind);
        }
    }

    private void preorder(StringBuilder str,Node head){
        if(head == null){
            return;
        }
        //NLR
        if(head.ch==null) {str.append('0');}
        else {str.append(head.ch+head.freq);}
        preorder(str,head.left);
        preorder(str,head.right);
    }

    private HashMap<Character,BitSet> createEncoding(Node root){
        HashMap<Character,BitSet> mp = new HashMap<>();
        DFS(mp,new ArrayList<>(),root,0);
        return mp;

    }

    public void printHuffmanTree(){
        levelOrder(huffmanRoot);

    }

    public String getTreePreorder(){
        StringBuilder str = new StringBuilder();
        preorder(str,huffmanRoot);
        int n = str.length();
        System.out.println(n);
        str = new StringBuilder(String.valueOf(n) + str);
        System.out.println("String:"+str);
        return str.toString();
    }

    public HashMap<Character,BitSet> getEncodingTable(){

        HashMap<Character,BitSet> table = createEncoding(huffmanRoot);
        for(var i:table.entrySet()){
            System.out.println(i.getKey() + " " + i.getValue());
        }
        return table;
    }


    //Constructor
    public HuffmanTree(File f){
        this.file = f;
        this.freqMap = countChar(f);
        this.huffmanRoot = createTree(freqMap);
    }


}
