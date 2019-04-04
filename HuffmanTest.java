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
    String initCodeString;
    String convertedCode;
    String bracketTree;

       
    try {
      inputFileName="hello.txt";
      inputFileName=inputFileName.toUpperCase();
      bufferedIn= new BufferedInputStream(new FileInputStream(inputFileName));
      printer = new PrintStream(new BufferedOutputStream(new FileOutputStream("goodbye.txt")));

      HuffmanTree tree = new HuffmanTree(bufferedIn);
      bufferedIn.close();
      bufferedIn= new BufferedInputStream(new FileInputStream("hello.txt"));// go back to beginning of the file
      tree.generateCode(bufferedIn);
      excessBits=tree.excessBitCount();
      bracketTree=tree.generateBracketTree("",tree.getRoot());
      initCodeString=tree.getCode();
      convertedCode=convertCode(initCodeString);


      /*
       line 1 based on the input stream
       line 2 tree.generateBracketTree("",tree.getRoot())
       line 3 tree.excessBitCount()
       line 4 tree.getCode();
       */
      System.out.println(inputFileName.toUpperCase());
      System.out.println(bracketTree);
      System.out.println(excessBits);
      //System.out.println(initCodeString);

      printer.println(inputFileName.toUpperCase()); //line 1
      printer.println(bracketTree); //line 2
      printer.println(excessBits); //line 3
      printer.println(convertedCode);//line 4
      
    } finally {
      if (bufferedIn != null) {
        bufferedIn.close();
      }
      if (printer != null) {
        printer.close();
      }
    }
    System.out.println("runtime: "+(System.nanoTime()-startTime)/1000000);
  }
  public static String convertCode(String initialCode){
    String convertedCode="";
    for (int i=0;i<(initialCode.length()/8);i++){
        int tempByte = Integer.parseInt((initialCode.substring(8*i,8*i+8)),2);
        convertedCode=convertedCode+((char)tempByte);
    }
    return convertedCode;
  }

  
}