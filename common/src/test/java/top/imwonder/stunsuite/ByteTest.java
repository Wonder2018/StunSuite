package top.imwonder.stunsuite;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.junit.Test;

public class ByteTest {
    
    public static final int INT = 0x0101;
    @Test
    public void byteBufferTest() {
        ByteBuffer buf = ByteBuffer.allocate(30);
        buf.putInt(INT);
        buf.putShort((short)5);
        buf.putChar('a');

        short length = 8;
        int port = 0xA903;

        buf.putInt(port);
        buf.position(buf.position()-4);
        buf.putShort(length);
        buf.position(buf.position()+2);
        buf.putChar('m');


        int pp = 0x97898978;
        short p = (short)pp;
        buf.putInt(pp);
        buf.putShort(p);


        try(FileOutputStream fos = new FileOutputStream("testByteBuffer.txt")){
            fos.write(buf.array());
        }catch(IOException e){
            e.printStackTrace();
        }
        buf.position(4);
        System.out.println(buf.getShort());
        System.out.println(buf.getChar());
        System.out.println(buf.position());
        System.out.println(buf.getShort(buf.position()));
        System.out.println(buf.getShort());
        System.out.println(buf.position());
    }
    @Test
    public void readPacketTest() {
        byte rawData[] = new byte[256];
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream("raw_data.txt"))){
            bis.read(rawData);
        }catch(IOException e){
            e.printStackTrace();
        }
        ByteBuffer buf = ByteBuffer.wrap(rawData);
        buf.position(20);
        System.out.println(buf.getShort());
        System.out.println(buf.getShort());
        System.out.println(buf.getShort());
        System.out.println(buf.getShort() & 0xffff);
    }

    @Test
    public void outOfRange() {
        int port = 0x97898978;
        short p = (short)port;
        System.out.println(port);
        System.out.println(p);
    }

    @Test
    public void foreachByteTest(){
        byte a[] = new byte[5];
        Arrays.fill(a, (byte)0);
        for (@SuppressWarnings("all") byte b : a) {
            b=(byte)1;
        }
        for (byte b : a) {
            System.out.print(b);
            System.out.print(",");
        }
    }
}