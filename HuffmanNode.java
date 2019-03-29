
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