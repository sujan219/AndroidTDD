package tdd.test.com.fetch;

import android.os.IBinder;
import android.os.RemoteException;

import tdd.test.com.aidl.CounterListener;
import tdd.test.com.aidl.ICounterAidlInterface;

/**
 * Created by sujan on 1/14/2017.
 */

public class AIDLFetchStrategyService implements IFetchStrategyService {

    private int mData;
    private CounterListener mConterListener;

    @Override
    public int getData() {
        return mData;
    }

    @Override
    public void setData(int data) {
        mData = data;
        try {
            if(mConterListener != null) {
                mConterListener.newData(mData);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder getBinder() {
        return new ICounterAidlInterface.Stub() {
            @Override
            public int getCounter() throws RemoteException {
                return mData;
            }

            @Override
            public void setCounterListener(CounterListener listener) throws RemoteException {
                mConterListener = listener;
            }
        };
    }
}