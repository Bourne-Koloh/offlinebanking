package com.ekenya.rnd.ethdroid.solidity.coder.decoder.suintdecoder;

import com.ekenya.rnd.ethdroid.solidity.SolidityUtils;
import com.ekenya.rnd.ethdroid.solidity.coder.decoder.SDecoder;
import com.ekenya.rnd.ethdroid.solidity.types.SUInt;

/**
 * Created by gunicolas on 17/03/17.
 */

public class SUInt16Decoder implements SDecoder<SUInt.SUInt16> {

    @Override
    public SUInt.SUInt16 decode(String toDecode) {
        if (!toDecode.toLowerCase().startsWith("0x")) {
            toDecode = "0x" + toDecode;
        }
        return SUInt.fromInteger(SolidityUtils.hexToBigDecimal(toDecode).toBigInteger().intValue());
    }
}
