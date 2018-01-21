package com.webservices.payment.logic;

import com.webservices.payment.model.Token;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Class used for generating a barcode from a UUID
 *
 * @author James, Kasper, Aleksandar
 * @version 1.0
 */
public class TokenGenerator {

    private static Token generateToken(UUID owner, UUID uuid) {
        String barcodePath = BarcodeGenerator.generate(toBase36(uuid));
        long timestamp = System.currentTimeMillis();

		return new Token(uuid,owner,barcodePath,timestamp);
    }

	/**
	 * Used from generating a barcode from a randomly generated UUID
	 *
	 * @return a token object that has the path to the barcode
	 */
    public static Token generateToken(UUID owner) {
        UUID uuid = UUID.randomUUID();
        return generateToken(owner,uuid);
    }

    private static String toBase36(UUID uuid){
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());

        return (new BigInteger(byteBuffer.array())).toString(36);
    }
}
