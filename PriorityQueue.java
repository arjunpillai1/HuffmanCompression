public class PriorityQueue {
    PriorityNode head;
    PriorityNode tail;
    int size;
    //int frequency;

    PriorityQueue() {
        head = null;
        tail = null;
    }

    public void enqueue(HuffmanNode node) {
        size++;
        PriorityNode newNode = new PriorityNode(node);
        if (head == null) { //when adding to an empty queue
            head = newNode;
            tail = head;
        } else if (head == tail) { // if there's only one item in the queue (it's both the end and beginning)
            if (newNode.compareTo(head) == 1) {// if the new node has lower frequency than the head
                PriorityNode tempNode = head;
                newNode.setNext(tempNode);
                newNode.setPrevious(null);
                tempNode.setNext(null);
                tempNode.setPrevious(newNode);
                tail = tempNode;
                head = newNode;
            } else { // if the new node has a higher frequency than the head
                newNode.setPrevious(head);
                newNode.setNext(null);
                head.setPrevious(null);
                head.setNext(newNode);
                tail = newNode;
            }
        } else {// this is the most common case, 2+ items in queue
            PriorityNode tempNode = tail; // the moving node starting at the tail;
            if (newNode.compareTo(tempNode) != 1) {
                tempNode.setNext(newNode);
                newNode.setPrevious(tempNode);
                tail = newNode;
            }
            while (newNode.compareTo(tempNode) == 1) {
                tempNode = tempNode.getPrevious();// this traverses through the queue

                if (newNode.compareTo(tempNode) != 1) {
                    newNode.setNext(tempNode.getNext());
                    newNode.setPrevious((tempNode));
                    tempNode.getNext().setPrevious(newNode);
                    tempNode.setNext(newNode);

                }
            }
        }
    }

    public HuffmanNode dequeue() {
        if (head == null) { // when there are no items
            return null;
        } else {
            size--;
            HuffmanNode temp = head.getItem(); // store the item before erasing the node
            if (head.getNext() == null) { // if there's only one item;
                head = null;
                return temp;
            } else {
                head = head.getNext();
                if (head.getNext() == null) { //
                    tail = head;
                }
                return temp;
            }
        }
    }

    public int getSize() {
        return size;
    }

    private class PriorityNode implements Comparable<PriorityNode> {
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