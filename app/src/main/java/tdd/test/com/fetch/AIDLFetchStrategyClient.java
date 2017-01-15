package tdd.test.com.fetch;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.Timer;
import java.util.TimerTask;

import tdd.test.com.aidl.CounterListener;
import tdd.test.com.aidl.ICounterAidlInterface;

/**
 * Created by sujan on 1/14/2017.
 */

public class AIDLFetchStrategyClient implements IFetchStrategyClient {

    private ICounterAidlInterface mAidlInterface;
    private Context mCtx;
    private CounterServiceConnection mConnection;
    private int data;
    private FetchListener mListener;

    public AIDLFetchStrategyClient(Context ctx, Intent intent){
        mCtx = ctx;
        mConnection = new CounterServiceConnection();
        ctx.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onRequest() {
    }

    @Override
    public void setFetchListener(FetchListener listener) {
        mListener = listener;
    }

    @Override
    public int getData() {
        return data;
    }

    @Override
    public void destroy() {
        if(mConnection != null) {
            mCtx.unbindService(mConnection);
        }
    }

    private class CounterServiceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAidlInterface = ICounterAidlInterface.Stub.asInterface(service);
            try {
                mAidlInterface.setCounterListener(new CounterListener.Stub(){
                    @Override
                    public void newData(int data) throws RemoteException {
                        if(mListener != null) {
                            mListener.newData(data);
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAidlInterface = null;
        }
    }
}
