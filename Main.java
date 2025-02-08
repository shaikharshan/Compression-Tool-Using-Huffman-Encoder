package CompressionTool;

import java.io.*;
import java.util.HashMap;

/*ASCII coding actually uses 8 bits per character. Seven bits are used to represent the 128 codes of the ASCII character set.
The eigth bit as a parity bit, that can be used to check if there is a transmission error for the character. */

/*A set of codes is said to meet the prefix property if no code in the set is the prefix of another.
The prefix property guarantees that there will be no ambiguity in how a bit string is decoded.
In other words, once we reach the last bit of a code during the decoding process, we know which letter it is the code for.
Huffman codes certainly have the prefix property because any prefix for a code would correspond to an internal node, while all codes correspond to leaf nodes.*/

public class Main {

    public static File getFile(){
        String path = "src/files/compressionData.txt";
        File f = new File(path);
        if(!f.exists()){
            System.out.println("Error, No such file exists");
            return null;
        }
        return f;
    }

    public static void main(String args[]) throws FileNotFoundException {
        File f= getFile();
        EncodeFile ef = new EncodeFile(f);
        try {
            ef.encode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //creates new compressed file in same folder as actual file

    }

}
