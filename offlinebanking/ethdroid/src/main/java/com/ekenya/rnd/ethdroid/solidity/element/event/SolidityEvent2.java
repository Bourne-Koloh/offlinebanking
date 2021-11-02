package com.ekenya.rnd.ethdroid.solidity.element.event;

import java.lang.reflect.Method;

import com.ekenya.rnd.ethdroid.EthDroid;
import com.ekenya.rnd.ethdroid.solidity.element.returns.PairReturn;
import com.ekenya.rnd.ethdroid.solidity.types.SType;


/**
 * Created by gunicolas on 22/03/17.
 */

public class SolidityEvent2<T1 extends SType, T2 extends SType> extends SolidityEvent {


    public SolidityEvent2(String address, Method method, EthDroid eth) {
        super(address, method, eth);
    }

    @Override
    PairReturn<T1, T2> wrapDecodedLogs(SType[] decodedLogs) {
        return new PairReturn(decodedLogs[0], decodedLogs[1]);
    }
}
