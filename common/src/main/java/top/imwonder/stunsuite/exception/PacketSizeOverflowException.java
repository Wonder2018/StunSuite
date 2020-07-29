package top.imwonder.stunsuite.exception;

public class PacketSizeOverflowException extends Exception {
    private static final long serialVersionUID = 1L;

    public PacketSizeOverflowException(String msg) {
        super(msg);
    }
}