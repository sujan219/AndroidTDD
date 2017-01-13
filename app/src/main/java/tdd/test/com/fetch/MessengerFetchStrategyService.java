package tdd.test.com.fetch;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import static tdd.test.com.fetch.FetchService.MSG_TYPE_COUNTER;

/**
 * Created by sujan on 1/13/2017.
 */

public class MessengerFetchStrategyService implements IFetchStrategyService {

    private Messenger mMessenger;
    private int mData;

    public MessengerFetchStrategyService(){
        mMessenger = new Messenger(new FetchHandler());
    }

    @Override
    public int getData() {
        return mData;
    }

    @Override
    public void setData(int data) {
        mData = data;
    }

    @Override
    public IBinder getBinder() {
        return mMessenger.getBinder();
    }

    class FetchHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int msgType = msg.what;
            switch (msgType){
                case MSG_TYPE_COUNTER:
                    try {
                        int data = msg.getData().getInt("current", -1);
                        if(mData != data) {
                            Log.d("Current counter", data + "");
                            Message response = Message.obtain(null, MSG_TYPE_COUNTER);
                            Bundle responseBundle = new Bundle();
                            responseBundle.putInt("data", mData);
                            response.setData(responseBundle);
                            msg.replyTo.send(response);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}