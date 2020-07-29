package top.imwonder.stunsuite.util;

import java.security.SecureRandom;
import java.util.UUID;

public class IdGen {
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void transcationId(byte[]tid){
		SecureRandom random = new SecureRandom();
        random.nextBytes(tid);
	}
}
