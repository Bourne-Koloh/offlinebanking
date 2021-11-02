package com.ekenya.rnd.ethdroid.solidity.coder.encoder;


import com.ekenya.rnd.ethdroid.solidity.SolidityUtils;
import com.ekenya.rnd.ethdroid.solidity.types.SBytes;

/**
 * Created by gunicolas on 05/10/16.
 */

public class SBytesEncoder implements SEncoder<SBytes> {

    @Override
    public String encode(SBytes toEncode) {
        return SolidityUtils.padRightWithZeros(toEncode.asString(), 64);
    }
}
