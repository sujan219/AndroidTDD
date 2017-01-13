package tdd.test.com.tdd;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sujan on 1/11/2017.
 */

public class FetchService extends Service {

    private int counter;
    public static final int MSG_TYPE_COUNTER = 1;
    private Messenger mMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter++;
            }
        }, 3000, 3000);

        mMessenger = new Messenger(new FetchHandler());
    }

    class FetchHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            int msgType = msg.what;
            switch (msgType){
                case MSG_TYPE_COUNTER:
                    try {
                        int data = msg.getData().getInt("current");
                        if(getCounter() != data) {
                            Log.d("Current counter", data + "");
                            Message response = Message.obtain(null, MSG_TYPE_COUNTER);
                            Bundle responseBundle = new Bundle();
                            responseBundle.putInt("counter", getCounter());
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

    public int getCounter() {
        return counter;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
