package com.self.bytes.input;

import java.io.*;

/**
 * @author GTsung
 * @date 2021/10/12
 */
public class InputTest01 {

    static final String PATH = "D:\\self-web-frame\\ios\\src\\main\\java\\com\\self\\bytes\\file\\data.dat";

    public static void main(String[] args) throws Exception {

        int[] ss = new int[102];
        System.out.println(ss.length);
//        return;
        File file = new File(PATH);
        OutputStream stream = new FileOutputStream(file);
        byte[] bytes = new byte[]{0x12,0x8};
        stream.write(bytes);
        stream.close();
        InputStream inputStream = new FileInputStream(PATH);
        while(inputStream.read()!=-1) {
            char r = (char) inputStream.read();
            System.out.println(r);
        }

        BufferedInputStream bin = new BufferedInputStream(inputStream);

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[1024]);

    }
}
