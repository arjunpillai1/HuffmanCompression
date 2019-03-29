import java.io.BufferedInputStream;
import java.io.IOException;
//import HuffmanNode;

public class HuffmanTree{
    HuffmanNode root;

    private int[] possibleCharacter = new int[256];
    private int[] characterFrequencies = new int[256];
    private String[] codeTable = new String[256];
    private String code;


    HuffmanTree(BufferedInputStream input) throws IOException {
        root=null;
        for (int i=0;i<256;i++){
            possibleCharacter[i]=i;
        }

        int c;
        while ((c = input.read()) != -1) {
            incrementCharacterFrequencies(c);
        }
        assembleTree();


    }
    /*
    build tree method
    dequeue the 2 lowest frequency nodes from priority queue and create a new node that has them as children
    add the new Node back into queue
    */
    public void assembleTree(){
        PriorityQueue priorityQ= new PriorityQueue();

        //initialise priority queue
        for (int i=0;i<256;i++){
            if (getCharacterFrequency(i)>=1){ // only create the node if the character exists
                priorityQ.enqueue(new HuffmanNode(getCharacter(i),getCharacterFrequency(i),null,null));
            }
        }

        while (priorityQ.getSize()>1){
            HuffmanNode leftChild =priorityQ.dequeue(); // take off the two lowest frequency nodes
            HuffmanNode rightChild=priorityQ.dequeue();
            HuffmanNode parent = new HuffmanNode(-1,leftChild.getFrequency()+rightChild.getFrequency(),leftChild,rightChild);
            // creates the parent which has the lowest frequency nodes as children
            priorityQ.enqueue(parent); //adds parent back into queue
        }

        root= priorityQ.dequeue(); // set root equal to highest node
        generateCodeTable(codeTable, root,"");//

    }


    /*
    recursive call traverse through tree to create the table of values
    if the path to a leaf takes it left then it adds 0 to the compression key for that character
    if the path takes it right then it adds a 1
     */
    public void generateCodeTable(String[] table, HuffmanNode node, String code){
        if (node.isLeaf()){
            table[node.getValue()] = code; // assigns the final code to the corresponding index as the character its for
        } else{
            generateCodeTable(table,node.getLeft(),code+"0");
            generateCodeTable(table,node.getRight(),code+"1");
        }
    }

    public void generateCode(BufferedInputStream input) throws IOException{
        int c;
        code="";
        while ((c = input.read()) != -1) {
            code=code+codeTable[c];
        }
    }

    public int excessBitCount(){
        return (code.length()%8);
    }

    public String generateBracketTree(String bracketTree, HuffmanNode node){
        if (!node.isLeaf()){
            bracketTree= "("+generateBracketTree(bracketTree,node.getLeft())+" "+ generateBracketTree(bracketTree,node.getRight())+")";
        } else {
            return node.getValue()+"";
        }

        return bracketTree;
    }

    public int getCharacterFrequency(int index) {
        return characterFrequencies[index];
    }

    public void incrementCharacterFrequencies(int index) {
        characterFrequencies[index]++;
    }

    public int getCharacter(int index) {
        return possibleCharacter[index];
    }

    public String getCode() {
        return code;
    }

    public HuffmanNode getRoot() {
        return root;
    }
}

