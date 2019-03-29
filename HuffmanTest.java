import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HuffmanTest {
  
  public static void main(String[] args) throws IOException {
    BufferedInputStream bufferedIn=null;
    BufferedOutputStream bufferedOut=null;
       
    try {
      String inputFileName="HELLO.TXT";
      bufferedIn= new BufferedInputStream(new FileInputStream(inputFileName));
      bufferedOut = new BufferedOutputStream(new FileOutputStream("goodbye.txt"));
      
      
      HuffmanTree tree = new HuffmanTree(bufferedIn);
      bufferedIn.close();
      bufferedIn= new BufferedInputStream(new FileInputStream("hello.txt"));// go back to beginning of the file
      tree.generateCode(bufferedIn);
      /*
       line 1 based on the input stream
       line 2 tree.generateBracketTree("",tree.getRoot())
       line 3 tree.excessBitCount()
       line 4 tree.getCode();
       */
      System.out.println(inputFileName);
      System.out.println(tree.generateBracketTree("",tree.getRoot()));
      System.out.println(tree.excessBitCount());
      System.out.println(tree.getCode());
      
      
    } finally {
      if (bufferedIn != null) {
        bufferedIn.close();
      }
      if (bufferedOut != null) {
        bufferedOut.close();
      }
    }
  }
  
  
}