package com.ekenya.rnd.ethdroid.solidity.element.event;

import org.ethereum.geth.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import com.ekenya.rnd.ethdroid.EthDroid;
import com.ekenya.rnd.ethdroid.model.Filter;
import com.ekenya.rnd.ethdroid.model.FilterOptions;
import com.ekenya.rnd.ethdroid.solidity.coder.SCoder;
import com.ekenya.rnd.ethdroid.solidity.element.SolidityElement;
import com.ekenya.rnd.ethdroid.solidity.element.returns.SingleReturn;
import com.ekenya.rnd.ethdroid.solidity.types.SArray;
import com.ekenya.rnd.ethdroid.solidity.types.SType;
import io.reactivex.Observable;
import okio.ByteString;


/**
 * Created by gunicolas on 4/08/16.
 */
public class SolidityEvent<T extends SType> extends SolidityElement {

    public SolidityEvent(String address, Method method, EthDroid eth) {
        super(address, method, eth);
    }

    @Override
    protected List<AbstractMap.SimpleEntry<Type, SArray.Size>> getParametersType() {
        return returns;
    }

    private FilterOptions encode() throws Exception {
        List<String> topics = new ArrayList<>();
        topics.add("0x" + signature());
        return new FilterOptions()
            .addTopics(topics)
            .addAddress(this.address);
    }

    Observable<Log> createFilter() throws Exception{
        FilterOptions options = encode();
        return Filter.newLogFilter(eth, options);
    }

    public Observable<SingleReturn<T>> listen() throws Exception {
        Observable<Log> logObservable = createFilter();
        if( returns.size() == 0 ){
            return logObservable.map(log -> wrapDecodedLogs(null));
        } else{
            return logObservable.map(log -> {
               SType[] decodedParams = SCoder.decodeParams(ByteString.of(log.getData()).hex(), returns);
               return wrapDecodedLogs(decodedParams);
            });
        }
    }

    SingleReturn<T> wrapDecodedLogs(SType[] decodedLogs) {
        return decodedLogs != null ? new SingleReturn(decodedLogs[0]) : new SingleReturn<>(null);
    }

}
