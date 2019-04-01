import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class HuffmanTest {
  
  public static void main(String[] args) throws IOException {
    long startTime = System.nanoTime();
    BufferedInputStream bufferedIn=null;
    PrintStream printer = null;

    String inputFileName;
    int excessBits;
    String codeString;
    String bracketTree;

       
    try {
      inputFileName="hello.txt";
      inputFileName=inputFileName.toUpperCase();
            bufferedIn= new BufferedInputStream(new FileInputStream(inputFileName));
      printer = new PrintStream(new BufferedOutputStream(new FileOutputStream(inputFileName+"2"+"")));

      
      System.out.println("29");
      HuffmanTree tree = new HuffmanTree(bufferedIn);
      System.out.println("31");
      bufferedIn.close();
      bufferedIn= new BufferedInputStream(new FileInputStream("hello.txt"));// go back to beginning of the file
      tree.generateCode(bufferedIn);
      excessBits=tree.excessBitCount();
      codeString=tree.getCode();
      bracketTree=tree.generateBracketTree("",tree.getRoot());

      /*
       line 1 based on the input stream
       line 2 tree.generateBracketTree("",tree.getRoot())
       line 3 tree.excessBitCount()
       line 4 tree.getCode();
       */
      System.out.println(inputFileName.toUpperCase());
      System.out.println(bracketTree);
      System.out.println(excessBits);
      //System.out.println(codeString);

      printer.println(inputFileName.toUpperCase()); //line 1
      printer.println(bracketTree); //line 2
      printer.println(excessBits); //line 3
      printer.println(codeString);//line 4
      
    } finally {
      if (bufferedIn != null) {
        bufferedIn.close();
      }
      if (printer != null) {
        printer.close();
      }
    }
    System.out.println((System.nanoTime()-startTime)/1000000);
  }

  
}