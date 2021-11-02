package com.ekenya.rnd.ethdroid.solidity.coder.decoder.suintdecoder;


import com.ekenya.rnd.ethdroid.solidity.SolidityUtils;
import com.ekenya.rnd.ethdroid.solidity.coder.decoder.SDecoder;
import com.ekenya.rnd.ethdroid.solidity.types.SUInt;

/**
 * Created by gunicolas on 17/03/17.
 */

public class SUInt8Decoder implements SDecoder<SUInt.SUInt8> {

    @Override
    public SUInt.SUInt8 decode(String toDecode) {
        if (!toDecode.toLowerCase().startsWith("0x")) {
            toDecode = "0x" + toDecode;
        }
        return SUInt.fromShort(SolidityUtils.hexToBigDecimal(toDecode).toBigInteger().shortValue());
    }

}
