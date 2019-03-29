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
            System.out.println(tree.generateBracketTree("",tree.getRoot()));
            System.out.println(tree.excessBitCount());
            System.out.println(tree.getCode());


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


}