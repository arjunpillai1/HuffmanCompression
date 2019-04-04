import java.io.BufferedInputStream;
import java.io.IOException;

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

    /**
     * assembleTree
     *  build tree method
     *  dequeue the 2 lowest frequency nodes from priority queue and create a new node that has them as children
     *  add the new Node back into queue
     */

    public void assembleTree(){
        PriorityQueueV2 priorityQ= new PriorityQueueV2();

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


    /**
     * generateCodeTable
     * recursive call traverse through tree to create the table of values
     *  if the path to a leaf takes it left then it adds 0 to the compression key for that character
     *  if the path takes it right then it adds a 1
     * @param table the table with the compression keys
     * @param node the movement through the tree
     * @param code the individual compression keys
     */
    
    public void generateCodeTable(String[] table, HuffmanNode node, String code){
        if (node.isLeaf()){
            table[node.getValue()] = code; // assigns the final code to the corresponding index as the character its for
        } else{
            generateCodeTable(table,node.getLeft(),code+"0");
            generateCodeTable(table,node.getRight(),code+"1");
        }
    }

    /**
     * generateCode
     * @param input the file to create the code from
     * @throws IOException
     */
    public void generateCode(BufferedInputStream input) throws IOException{
        int c;
        code="";
        while ((c = input.read()) != -1) {
            code=code+codeTable[c];
        }
    }

    /**
     * excessBitCount
     * @return int number of extra bits at the end of the code
     */
    public int excessBitCount(){
        int count =8-(code.length()%8);
        if (count==8){
          count=0;
        }else{
          for (int i=0;i<count;i++){
              code=code+"0";
          }
        }
        return count;
    }

    /**
     * generateBracketTree
     * @param bracketTree String to concatenate
     * @param node node to translate
     * @return String tree in string form
     */
    public String generateBracketTree(String bracketTree, HuffmanNode node){
        if (!node.isLeaf()){
            bracketTree= "("+generateBracketTree(bracketTree,node.getLeft())+" "+ generateBracketTree(bracketTree,node.getRight())+")";
        } else {
            return node.getValue()+"";
        }

        return bracketTree;
    }

    /**
     * getCharacterFrequency
     * @param index the index of the character to examine
     * @return int the frequency of the specific character
     */
    public int getCharacterFrequency(int index) {
        return characterFrequencies[index];
    }

    /**
     * incrementCharacterFrequencies
     * @param index index of the character whose frequency to increment
     */
    public void incrementCharacterFrequencies(int index) {
        characterFrequencies[index]++;
    }

    /**
     * getCharacter
     * @param index index of the character to get
     * @return int the decimal value of the character
     */
    public int getCharacter(int index) {
        return possibleCharacter[index];
    }

    /**
     * getCode
     * @return String the code
     */
    public String getCode() {
        return code;
    }

    /**
     * getRoot
     * @return HuffmanNode the highest node of the tree
     */
    public HuffmanNode getRoot() {
        return root;
    }
}

