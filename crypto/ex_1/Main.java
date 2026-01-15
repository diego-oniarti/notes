/**
 * Authors: Diego Oniarti, Alessandro Marostica
 */

import java.nio.ByteBuffer;

public class Main {
    /**
     * Read 4 byes from a stream and return them in an Integer, arranged according
     * to the specified endianness
     */
    private static int read_4_bytes(ByteBuffer b, boolean big_endian) {
        int b1 = b.get() & 0xFF;
        int b2 = b.get() & 0xFF;
        int b3 = b.get() & 0xFF;
        int b4 = b.get() & 0xFF;

        int ret;
        if (big_endian) {
            ret = (b1 << 24) | (b2 << 16) | (b3 << 8) | b4;
        } else {
            ret = (b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
        }

        return ret;
    }

    private static String breakCurseOfHex(String input) {
        // Read the input two characters at a time, combine them, and put the
        // result into a ByteBuffer
        ByteBuffer b = ByteBuffer.allocate(input.length()/2);
        for (int i=0; i<input.length(); i+=2) {
            char c1 = input.charAt(i  ) ;
            char c2 = input.charAt(i+1) ;

            int a1 = Character.digit(c1, 16);
            int a2 = Character.digit(c2, 16);

            int z = (a1 & 0xF)<<4 | (a2 & 0xF);
            b.put((byte)z);
        }
        b.position(0);

        // Read the first byte and extract the flags
        byte flags = b.get();
        boolean is_big_endian    = (flags & 0b01) == 0;
        boolean is_left_rotation = (flags & 0b10) == 0;

        // Rotation is in [0..31] so it can be stored in a byte without worrying
        // about sign
        byte rot = b.get();

        // M16 is two bytes so it can be read as a short
        short M16 = b.getShort();
        int M32 = (int)M16<<16 | (M16&0xffff);

        // Read the value as unsigned by fitting it into a long
        long LEN = ((long)read_4_bytes(b, is_big_endian)) & 0xFFFFFFFF;

        // Read LEN bytes in groups of 4
        StringBuilder key = new StringBuilder();
        for (long i=0; i<LEN; i+=4) {
            Integer E = read_4_bytes(b, is_big_endian);

            // Rotate in the opposite direction of the encoding
            E = is_left_rotation
                ? Integer.rotateRight(E, (int)rot)
                : Integer.rotateLeft(E, (int)rot);

            // XOR with the mask
            E ^= M32;

            // Collect the bytes in the order dictated by the endianness
            byte[] chars;
            if (is_big_endian) {
                chars = new byte[] {
                    (byte)( (E & 0xff000000) >>> 24),
                    (byte)( (E & 0x00ff0000) >>> 16),
                    (byte)( (E & 0x0000ff00) >>> 8 ),
                    (byte)( (E & 0x000000ff) >>> 0 ) 
                };
            }else{
                chars = new byte[] {
                    (byte)( (E & 0x000000ff) >>> 0 ),
                    (byte)( (E & 0x0000ff00) >>> 8 ),
                    (byte)( (E & 0x00ff0000) >>> 16),
                    (byte)( (E & 0xff000000) >>> 24)
                };
            }

            try{
                // Add the 4 characters to the result
                String chars_string = new String(chars, "ASCII");
                for (int j=0; j<4; j++) {
                    if (i+j>=LEN) break;
                    key.append(chars_string.charAt(j));
                }
            }catch(Exception e) {
                System.out.println(e.getStackTrace());
            }
        }

        return key.toString();
    }

    public static void main(String[] args) {
        String curse = "0305A1B2460000006F9F37A7AED6264E173E9E6E266E770EF6374EA6F6D726BEF6DF96561F06766E26E6F6CF4EBEF63776EE9EB677A6F66F4EBEF6379E5E8E8EF6DF96367FEEB66E7716266EE6960DE5";
        String key = breakCurseOfHex(curse);
        System.out.println(key);
        // FLAG{the_curse_of_the_hex_is_broken_the_door_of_the_crypt_is_now_open}
    }
}

