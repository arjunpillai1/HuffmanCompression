/**
 *
 */
public class HuffmanNode {
    int value;
    int frequency;
    HuffmanNode left=null;
    HuffmanNode right=null;

    /**
     * @param value character to store
     * @param frequency freq of character
     * @param leftChild left node
     * @param rightChild right node
     */
    HuffmanNode(int value, int frequency,HuffmanNode leftChild,HuffmanNode rightChild){
        this.value=value;
        this.frequency=frequency;
        left=leftChild;
        right=rightChild;
    }

    /**
     * getValue
     * @return int value stored
     */
    public int getValue() {
        return value;
    }

    /**
     * getFrequency
     * @return int freq of value
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * getLeft
     * @return HuffmanNode left child
     */
    public HuffmanNode getLeft() {
        return left;
    }

    /**
     * getRight
     * @return HuffmanNode right child
     */
    public HuffmanNode getRight() {
        return right;
    }


    /**
     * isLeaf
     * @return boolean if it has children
     */
    public boolean isLeaf(){
        return ((left==null) && (right==null)); // true if it has no children
    }
}