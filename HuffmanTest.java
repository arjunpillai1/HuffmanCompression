import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class HuffmanTest {

    public static void main(String[] args) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("ZeroImageSampleImage69.JPG");
            out = new FileOutputStream("goodbye.txt");
            int c;



            while ((c = in.read()) != -1) {
                System.out.print(c);
                System.out.println((char)c);
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    public class HuffmanTree{
        HuffmanNode root;

        /*
        build tree method
        dequeue the 2 lowest frequency nodes from priority queue and create a new node that has them as children
        add the new Node back into queue
        */


    }
    public class HuffmanNode implements Comparable<HuffmanNode>{
        int frequency;
        HuffmanNode left=null;
        HuffmanNode right=null;

        HuffmanNode(int frequency,HuffmanNode leftChild,HuffmanNode rightChild){
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
            if (left==null && right==null){
                return true;
            }else {
                return false;
            }
        }
    }
    public class PriorityQueue<HuffmanNode> {
        HuffmanTest.HuffmanNode
        int size;
        int frequency;

        public void enqueue(HuffmanNode node){
            frequency= node.getFrequency();
        }
        public HuffmanNode dequeue(){
            return  null; // return the item with the lowest frequency
        }
    }


}
