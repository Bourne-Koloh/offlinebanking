package com.ekenya.rnd.ethdroid.solidity.element.event;

import java.lang.reflect.Method;

import com.ekenya.rnd.ethdroid.EthDroid;
import com.ekenya.rnd.ethdroid.solidity.element.returns.TripleReturn;
import com.ekenya.rnd.ethdroid.solidity.types.SType;


/**
 * Created by gunicolas on 22/03/17.
 */

public class SolidityEvent3<T1 extends SType, T2 extends SType, T3 extends SType> extends
    SolidityEvent2 {

    public SolidityEvent3(String address, Method method, EthDroid eth) {
        super(address, method, eth);
    }

    @Override
    TripleReturn<T1, T2, T3> wrapDecodedLogs(SType[] decodedLogs) {
        return new TripleReturn(decodedLogs[0], decodedLogs[1], decodedLogs[2]);
    }
}
