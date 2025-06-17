package com.mvp.customerprocessor.util;

import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class BinaryToUuidConverter implements Converter<Binary, UUID> {

    @Override
    public UUID convert(Binary source) {
        byte[] data = source.getData();
        long mostSigBits = bytesToLong(data, 0);
        long leastSigBits = bytesToLong(data, 8);
        return new UUID(mostSigBits, leastSigBits);
    }

    private long bytesToLong(byte[] bytes, int offset) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result = (result << 8) | (bytes[offset + i] & 0xff);
        }
        return result;
    }
}
