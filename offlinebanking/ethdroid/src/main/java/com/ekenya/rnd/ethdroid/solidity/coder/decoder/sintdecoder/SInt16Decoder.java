package com.ekenya.rnd.ethdroid.solidity.coder.decoder.sintdecoder;

import com.ekenya.rnd.ethdroid.solidity.SolidityUtils;
import com.ekenya.rnd.ethdroid.solidity.coder.decoder.SDecoder;
import com.ekenya.rnd.ethdroid.solidity.types.SInt;

/**
 * Created by gunicolas on 17/03/17.
 */

public class SInt16Decoder implements SDecoder<SInt.SInt16> {

    @Override
    public SInt.SInt16 decode(String toDecode) {
        if (!toDecode.toLowerCase().startsWith("0x")) {
            toDecode = "0x" + toDecode;
        }
        return SInt.fromShort(SolidityUtils.hexToBigDecimal(toDecode).toBigInteger().shortValue());
    }
}
