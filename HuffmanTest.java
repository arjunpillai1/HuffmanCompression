import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HuffmanTest {

    public static void main(String[] args) throws IOException {
        BufferedInputStream bufferedIn=null;
        BufferedOutputStream bufferedOut=null;
        FileInputStream in = null;
        FileOutputStream out = null;


        try {
            in = new FileInputStream("hello.txt");
            bufferedIn= new BufferedInputStream(in);
            out = new FileOutputStream("goodbye.txt");
            bufferedOut = new BufferedOutputStream(out);

            HuffmanTree tree = new HuffmanTree(bufferedIn);
            bufferedIn.reset(); // go back to beginning of the file
            tree.generateCode(bufferedIn);
            /*
            line 1 based on the input stream
            line 2 tree.generateBracketTree("",tree.getRoot())
            line 3 tree.excessBitCount()
            line 4 tree.getCode();
             */


        } finally {
            if (in != null) {
                in.close();
                bufferedIn.close();
            }
            if (out != null) {
                out.close();
                bufferedOut.close();
            }
        }
    }





    public class HuffmanTree{
        HuffmanNode root;

        private int[] possibleCharacter = new int[256];
        private int[] characterFrequencies = new int[256];
        private String[] codeTable = new String[256];
        private String code;


        HuffmanTree(BufferedInputStream input) throws IOException{
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

    public class HuffmanNode implements Comparable<HuffmanNode>{
        int value;
        int frequency;
        HuffmanNode left=null;
        HuffmanNode right=null;

        HuffmanNode(int value, int frequency,HuffmanNode leftChild,HuffmanNode rightChild){
            this.value=value;
            this.frequency=frequency;
            left=leftChild;
            right=rightChild;
        }

        public int getValue() {
            return value;
        }

        public int getFrequency() {
            return frequency;
        }

        public HuffmanNode getLeft() {
            return left;
        }

        public HuffmanNode getRight() {
            return right;
        }


        public void setLeft(HuffmanNode left) {
            this.left = left;
        }

        public void setRight(HuffmanNode right) {
            this.right = right;
        }

        public int compareTo(HuffmanNode other){
            return this.frequency-other.frequency;
        }

        public boolean isLeaf(){
            return ((left==null) && (right==null)); // true if it has no children
        }
    }

    public class PriorityQueue {
        PriorityNode head;
        PriorityNode tail;
        int size;
        //int frequency;

        PriorityQueue(){
            head=null;
            tail=null;
        }

        public void enqueue(HuffmanNode node){
            size++;
            PriorityNode newNode = new PriorityNode(node);
            if (head==null){ //when adding to an empty queue
                head=newNode;
                tail=head;
            }else if (head==tail){ // if there's only one item in the queue (it's both the end and beginning)
                if (newNode.compareTo(head) == 1){// if the new node has lower frequency than the head
                    PriorityNode tempNode = head;
                    newNode.setNext(tempNode);
                    newNode.setPrevious(null);
                    tempNode.setNext(null);
                    tempNode.setPrevious(newNode);
                    tail=tempNode;
                    head=newNode;
                } else { // if the new node has a higher frequency than the head
                    newNode.setPrevious(head);
                    newNode.setNext(null);
                    head.setPrevious(null);
                    head.setNext(newNode);
                    tail = newNode;
                }
            }else {// this is the most common case, 2+ items in queue
                PriorityNode tempNode =tail; // the moving node starting at the tail;
                if (newNode.compareTo(tempNode) != 1){
                    tempNode.setNext(newNode);
                    newNode.setPrevious(tempNode);
                    tail=newNode;
                }
                while (newNode.compareTo(tempNode)== 1){
                    tempNode=tempNode.getPrevious();// this traverses through the queue

                    if (newNode.compareTo(tempNode)!= 1){
                        newNode.setNext(tempNode.getNext());
                        newNode.setPrevious((tempNode));
                        tempNode.getNext().setPrevious(newNode);
                        tempNode.setNext(newNode);

                    }
                }
            }
        }
        public HuffmanNode dequeue(){
            if (head==null) { // when there are no items
                return null;
            }else {
                size--;
                HuffmanNode temp =head.getItem(); // store the item before erasing the node
                if (head.getNext()==null){ // if there's only one item;
                    head=null;
                    return temp;
                } else {
                    head=head.getNext();
                    if (head.getNext()==null){ //
                        tail=head;
                    }
                    return temp;
                }
            }
        }

        public int getSize() {
            return size;
        }

        private class PriorityNode implements Comparable<PriorityNode>{
            HuffmanNode item;
            PriorityNode next;
            PriorityNode previous;
            int priority;

            public PriorityNode(HuffmanNode node) {
                this.item = node;
                this.priority = node.getFrequency();
                this.next = null;
                this.previous = null;
            }

            public PriorityNode getNext() {
                return next;
            }

            public PriorityNode getPrevious() {
                return previous;
            }

            public HuffmanNode getItem() {
                return item;
            }

            public int getPriority() {
                return priority;
            }

            public void setNext(PriorityNode next) {
                this.next = next;
            }

            public void setPrevious(PriorityNode previous) {
                this.previous = previous;
            }
            public int compareTo (PriorityNode node){
                if (this.priority> node.getPriority()){
                    return -1;
                } else if (this.priority< node.getPriority()){
                    return 1;
                } else{
                    return 0;
                }

            }
        }
    }



}
