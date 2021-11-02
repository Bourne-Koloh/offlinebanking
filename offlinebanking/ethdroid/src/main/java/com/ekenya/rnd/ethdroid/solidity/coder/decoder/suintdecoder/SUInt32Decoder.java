package com.ekenya.rnd.ethdroid.solidity.coder.decoder.suintdecoder;

import com.ekenya.rnd.ethdroid.solidity.SolidityUtils;
import com.ekenya.rnd.ethdroid.solidity.coder.decoder.SDecoder;
import com.ekenya.rnd.ethdroid.solidity.types.SUInt;

/**
 * Created by gunicolas on 17/03/17.
 */

public class SUInt32Decoder implements SDecoder<SUInt.SUInt32> {

    @Override
    public SUInt.SUInt32 decode(String toDecode) {
        if (!toDecode.toLowerCase().startsWith("0x")) {
            toDecode = "0x" + toDecode;
        }
        return SUInt.fromLong(SolidityUtils.hexToBigDecimal(toDecode).toBigInteger().longValue());
    }
}
