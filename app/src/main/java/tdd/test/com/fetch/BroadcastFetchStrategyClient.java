package tdd.test.com.fetch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by sujan on 1/13/2017.
 */

public class BroadcastFetchStrategyClient implements IFetchStrategyClient{

    private Context mCtx;
    private BroadcastReceiver mReceiver;
    private FetchListener mListener;
    private int mData;

    public BroadcastFetchStrategyClient(Context ctx){
        mCtx = ctx;
    }

    @Override
    public void onRequest() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(mListener != null){
                    mData = intent.getIntExtra("data", -1);
                    mListener.newData(mData);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastFetchStrategyService.ACTION_FETCH);
        mCtx.registerReceiver(mReceiver, filter);
    }

    @Override
    public void setFetchListener(FetchListener listener) {
        mListener = listener;
    }

    @Override
    public int getData() {
        return mData;
    }

    @Override
    public void destroy() {
        mCtx.unregisterReceiver(mReceiver);
    }
}
