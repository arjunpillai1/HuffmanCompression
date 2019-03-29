import java.io.*;

public class HuffmanTest {

    public static void main(String[] args) throws IOException {
        HuffmanTree tree =new HuffmanTree();
        BufferedInputStream bufferedIn=null;
        BufferedOutputStream bufferedOut=null;
        FileInputStream in = null;
        FileOutputStream out = null;


        try {
            in = new FileInputStream("hello.txt");
            bufferedIn= new BufferedInputStream(in);
            out = new FileOutputStream("goodbye.txt");
            bufferedOut = new BufferedOutputStream(out);
            int c;



            while ((c = bufferedIn.read()) != -1) {
                tree.incrementCharacterFrequencies(c);
                System.out.print(c);
                System.out.println((char)c);
                out.write(c);
            }
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

        //method to get remain
    }



    public class HuffmanTree{
        HuffmanNode root;

        private int[] possibleCharacter = new int[256];
        private int[] characterFrequencies = new int[256];

        HuffmanTree(){
            root= null;
            for (int i=0;i<256;i++){
                possibleCharacter[i]=i;
            }
        }
        /*
        build tree method
        dequeue the 2 lowest frequency nodes from priority queue and create a new node that has them as children
        add the new Node back into queue
        */
        // after a list of
        public void buildTree(){
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
                // creates the parent which have the huffman nodes as its children
                HuffmanNode parent = new HuffmanNode(-1,leftChild.getFrequency()+rightChild.getFrequency(),leftChild,rightChild);
            }
            root= priorityQ.dequeue();

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

        public void setPossibleCharacter(int[] possibleCharacter) {
            this.possibleCharacter = possibleCharacter;
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

        public HuffmanNode getLeft() {
            return left;
        }

        public HuffmanNode getRight() {
            return right;
        }

        public int getFrequency() {
            return frequency;
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
            return (left==null && right==null);// true of both are null
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
            PriorityNode newNode = new PriorityNode(node);
            if (head==null){ //when adding to an empty queue
                head=newNode;
                tail=head;
            }else if (head==tail){ // if there's only one item in the queue (it's both the end and beginning)
                if (newNode.compareTo(head) == -1){// if the new node has lower priority than the head
                    PriorityNode tempNode = head;
                    newNode.setNext(tempNode);
                    tempNode.setNext(null);
                    tempNode.setPrevious(newNode);
                    tail=tempNode;
                    head=newNode;
                } else {
                    newNode.setPrevious(head);
                    newNode.setNext(null);
                    head.setPrevious(null);
                    head.setNext(newNode);
                    tail = newNode;
                }
            }else {// this is the most common case, 2+ items in queue
                PriorityNode tempNode =tail; // the moving node starting at the tail;
                if (newNode.compareTo(tempNode) != -1){
                    tempNode.setNext(newNode);
                    newNode.setPrevious(tempNode);
                    tail=newNode;
                }
                while (newNode.compareTo(tempNode)==-1){
                    tempNode=tempNode.getPrevious();// this traverses through the queue

                    if (newNode.compareTo(tempNode)!=-1){
                        newNode.setNext(tempNode.getNext());
                        newNode.setPrevious((tempNode));
                        tempNode.setNext(newNode);
                        tempNode.getNext().setPrevious(newNode);
                    }
                }
            }
        }
        public HuffmanNode dequeue(){
            if (head==null) { // when there are no items
                return null;
            }else {
                HuffmanNode temp =head.getItem(); // store the item before erasing the node
                if (head.getNext()==null){
                    head=null;
                    return temp;
                } else {
                    head=head.getNext();
                    if (head.getNext()==null){ //
                        tail=null;
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