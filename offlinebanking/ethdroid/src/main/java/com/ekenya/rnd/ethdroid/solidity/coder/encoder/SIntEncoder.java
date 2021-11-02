package com.ekenya.rnd.ethdroid.solidity.coder.encoder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.ekenya.rnd.ethdroid.solidity.SolidityUtils;
import com.ekenya.rnd.ethdroid.solidity.types.SInt;

/**
 * Created by gunicolas on 08/09/16.
 */
public class SIntEncoder implements SEncoder<SInt> {


    @Override
    public String encode(SInt toEncode) {
        BigDecimal twoCompDec = SolidityUtils.toTwosComplement(toEncode.asString());
        MathContext mc = new MathContext(0, RoundingMode.DOWN);
        BigDecimal twoCompDecRounded = twoCompDec.round(mc);
        String twoCompDecRoundedHexa = SolidityUtils.bigDecimalToHexString(twoCompDecRounded);
        String result = SolidityUtils.padLeftWithZeros(twoCompDecRoundedHexa, 64);
        return result;
    }

}
