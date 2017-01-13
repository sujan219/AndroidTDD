package tdd.test.com.fetch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by sujan on 1/13/2017.
 */

public class BroadCastFetchStrategyService implements IFetchStrategyService {

    public static final String ACTION_FETCH = "tdd.test.com.action.FETCH";
    private int mData;
    private Context mCtx;

    public BroadCastFetchStrategyService(Context ctx){
        mCtx = ctx;
    }

    @Override
    public int getData() {
        return mData;
    }

    @Override
    public void setData(int data) {
        mData = data;
        broadcast();
    }

    private void broadcast(){
        Intent intent = new Intent();
        intent.putExtra("data", mData);
        intent.setAction(ACTION_FETCH);
        mCtx.sendBroadcast(intent);
    }

    @Override
    public IBinder getBinder() {
        return null;
    }
}
