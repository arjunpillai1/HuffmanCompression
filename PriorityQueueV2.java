public class PriorityQueueV2 {
    PriorityNode head;
    int size;

    PriorityQueueV2(){
        head=null;
        size=0;
    }

    /**
     * enqueue
     * adds a new node according to priority into the queue
     * @param node the item to add to the new node
     */
    public void enqueue(HuffmanNode node){
        PriorityNode newNode = new PriorityNode(node);
        size++;
        PriorityNode traversingNode = head;
        if (head==null){ // adding to empty queue
            //System.out.println("empty queue problem");
            head=newNode;
        } else if (newNode.compareTo(head) == 1){ // if the added node has lower frequency than head
            //System.out.println("node at head");
            newNode.setPrevious(head);
            head= newNode;
        }else{
            /*
            There are two ways to exit this loop
            1 the if statement, which is triggered when at the end of the queue, it adds new node to the end
            2 the while loop which stops when the new node has
             */
            while (newNode.compareTo(traversingNode) !=1) { // new node has higher freq than traversing node
                if (traversingNode.getPrevious() == null) { // if at the end of the queue
                    traversingNode.setPrevious(newNode); // tack the new node at the end
                    return;
                } else if (newNode.compareTo(traversingNode.getPrevious())==1){// if new node has less freq than the one behind traversing node
                    newNode.setPrevious(traversingNode.getPrevious()); // connect new node in between
                    traversingNode.setPrevious(newNode);
                    return;
                }else {
                    traversingNode=traversingNode.getPrevious(); // traverses the queue
                }
            }


        }

    }

    /**
     * Dequeue
     * removes the lowest frequency item from queue
     * @return HuffmanNode the item of the node removed
     */
    public HuffmanNode dequeue(){
        size--;
        if (head==null){ //no items in queue then return nothing
            return null;
        } else {
            HuffmanNode temp = head.getItem(); // store the item so it doesn't get lost
            head=head.getPrevious();// null if taking the only item otherwise shift head over one node
            return temp;
        }
    }

    /**
     * getSize
     * @return the size of queue
     */
    public int getSize(){
        return this.size;
    }


    private class PriorityNode implements Comparable<PriorityNode> {
        private HuffmanNode item;
        private PriorityNode previous;
        private int priority;

        /**
         * @param node the Huffman node to set as item
         */
        public PriorityNode(HuffmanNode node) {
            this.item = node;
            this.priority = node.getFrequency();
            this.previous = null;
        }

        /**
         * getPrevious
         * @return PriorityNode the node behind this one
         */
        public PriorityNode getPrevious() {
            return previous;
        }

        /**
         * getItem
         * @return HuffmanNode the item of this node
         */
        public HuffmanNode getItem() {
            return item;
        }

        /**
         * getPriority
         * @return int the priority of this node
         */
        public int getPriority() {
            return priority;
        }

        /**
         * setPrevious
         * @param previous the node to set behind this one
         */
        public void setPrevious(PriorityNode previous) {
            this.previous = previous;
        }

        /**
         * compareTo
         * @param node node to compare to
         * @return int how this nodes freq compares to the other
         */
        public int compareTo(PriorityNode node) {
            if (this.priority > node.getPriority()) {
                return -1;
            } else if (this.priority < node.getPriority()) {
                return 1;
            } else {
                return 0;
            }

        }
    }
}
