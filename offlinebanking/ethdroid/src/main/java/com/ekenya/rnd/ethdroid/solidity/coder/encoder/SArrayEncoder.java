package com.ekenya.rnd.ethdroid.solidity.coder.encoder;

import java.math.BigInteger;

import com.ekenya.rnd.ethdroid.solidity.coder.SCoder;
import com.ekenya.rnd.ethdroid.solidity.types.SArray;
import com.ekenya.rnd.ethdroid.solidity.types.SInt;
import com.ekenya.rnd.ethdroid.solidity.types.SType;

/**
 * Created by gunicolas on 07/02/17.
 */

public class SArrayEncoder<T extends SType> implements SEncoder<SArray<T>> {

    @Override
    public String encode(SArray<T> toEncode) {

        StringBuilder stringBuilder = new StringBuilder();

        if (toEncode.isDynamic()) {
            int length = toEncode.get().length;
            SInt.SInt256 lengthSolidity = SInt.SInt256.fromBigInteger256(
                BigInteger.valueOf(length));
            stringBuilder.append(SCoder.encodeParam(lengthSolidity));
        }

        for (T item : toEncode.get()) {
            stringBuilder.append(SCoder.encodeParam(item));
        }

        return stringBuilder.toString();
    }


}
