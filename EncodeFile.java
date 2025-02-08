package CompressionTool;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.HashMap;

public class EncodeFile {

    private File fnew;
    private File f;

    private File createOutputFile(File f) throws IOException {
        String path = f.getCanonicalPath();
//        System.out.println(path);
//        TODO: Output file path is hardcoded. Change it to dynamic path.
        String newFilePath = "src/files/CompressedFile.txt";
        File fnew = new File(newFilePath);
        try {
            if (fnew.createNewFile()) {
                System.out.println("Created Compressed File(Empty)");
                return fnew;
            } else {
                System.out.println("Could not create Compressed File !! ERROR");
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public File getCompressedFile() {
        return fnew;
    }

    public void addHeader(HuffmanTree tree, File fnew) throws IOException {
        FileOutputStream fo = new FileOutputStream(fnew, true);
        String header = tree.getTreePreorder();
        // TODO: Header uses String input of preorder making it inefficient, use ByteArray instead, like (1ASCII(E) + 0) : 1 leaf node, 0 middle node
        fo.write(header.getBytes(StandardCharsets.UTF_8));
    }

    public void encode() throws IOException {
        HuffmanTree tree = new HuffmanTree(f);
        HashMap<Character, BitSet> table = tree.getEncodingTable();
        addHeader(tree, fnew);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8);
        FileOutputStream stream = new FileOutputStream(fnew, true);

        int c;
        try {
            while ((c = reader.read()) != -1) {
                Character ch = (char) c;
//                System.out.print(ch);
                BitSet b = table.get(ch);
                if (b != null) {
                    stream.write(b.toByteArray());
//                    TODO: ByteArray is still not efficient as [1] get written as [00000001].
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    //Constructor
    public EncodeFile(File f) {
        this.f = f;
        try {
            this.fnew = createOutputFile(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
