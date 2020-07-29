package top.imwonder.stunsuite.util;

public class ByteUtil {
    private static final byte[] hexdigits = { 
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' 
    };

    public static byte[] parseToSize(byte[] src, int size) {
        if (src.length == size) {
            return src;
        } else if (src.length < size) {
            byte res[] = new byte[size];
            System.arraycopy(src, 0, res, size - src.length, src.length);
            return res;
        } else {
            throw new IndexOutOfBoundsException(
                    String.format("This array is too large! Its length is %d and bigger than %d!", src.length, size));
        }
    }

    public String convertToHexString(byte[] bytes) {
        int pos = 0;
        int len = bytes.length;
        int val;
        byte[] result = new byte[(((len * 2) + 0xf) & ~(0xf))];

        while (pos < len) {
            val = (bytes[pos] & 0xff);
            result[pos * 2] = hexdigits[val >> 4];
            result[pos * 2 + 1] = hexdigits[val & 0x0f];
            pos++;
        }

        String hexStr = new String(result);
        return hexStr;
    }
}