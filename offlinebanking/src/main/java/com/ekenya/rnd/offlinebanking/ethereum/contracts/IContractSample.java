package com.ekenya.rnd.offlinebanking.ethereum.contracts;

import android.accounts.Account;

import com.ekenya.rnd.ethdroid.solidity.ContractType;
import com.ekenya.rnd.ethdroid.solidity.element.SolidityElement;
import com.ekenya.rnd.ethdroid.solidity.element.event.SolidityEvent;
import com.ekenya.rnd.ethdroid.solidity.element.event.SolidityEvent3;
import com.ekenya.rnd.ethdroid.solidity.element.function.SolidityFunction;
import com.ekenya.rnd.ethdroid.solidity.element.function.SolidityFunction2;
import com.ekenya.rnd.ethdroid.solidity.types.SArray;
import com.ekenya.rnd.ethdroid.solidity.types.SBool;
import com.ekenya.rnd.ethdroid.solidity.types.SInt;
import com.ekenya.rnd.ethdroid.solidity.types.SUInt;

public interface IContractSample extends ContractType {

    SolidityEvent<SBool> balancesReceived();

    SolidityEvent3<SBool,SBool,SBool> multipleEvent();

    SolidityFunction getFloatBalance();

    SolidityFunction getUserBalance(Account userAccount);

    SolidityFunction buyAirtime(Account receiverAccount, SInt.SInt256 a);


    @SolidityElement.ReturnParameters({@SArray.Size({3,3})})
    SolidityEvent<SArray<SArray<SInt.SInt256>>> eventOutputMatrix();

    @SolidityElement.ReturnParameters({@SArray.Size({2,8})})
    SolidityFunction<SArray<SArray<SUInt.SUInt8>>> functionOutputMatrix();

    SolidityFunction functionInputMatrix(@SArray.Size({3}) SArray<SUInt.SUInt8> a);

    @SolidityElement.ReturnParameters({@SArray.Size(),@SArray.Size({2,3})})
    SolidityFunction2<SBool,SArray<SArray<SUInt.SUInt8>>> functionOutputMultiple();

}

