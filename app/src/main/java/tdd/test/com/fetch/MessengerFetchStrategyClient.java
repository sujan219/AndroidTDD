package tdd.test.com.fetch;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sujan on 1/13/2017.
 */

public class MessengerFetchStrategyClient implements IFetchStrategyClient {

    private ServiceConnection mServiceConnection;
    private Messenger mMessenger;
    private int data;
    private int prev;
    private Activity activity;
    private FetchListener listener;

    public MessengerFetchStrategyClient(Activity activity, Intent intent) {
        this.activity = activity;
        mServiceConnection = new MyServiceConnection();
        activity.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onRequest() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                data = msg.getData().getInt("data");
                if(listener != null){
                    listener.newData(data);
                }
            }
        };
        final Messenger responseMessenger = new Messenger(handler);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mMessenger != null) {
                    Message msg = Message.obtain(null, FetchService.MSG_TYPE_COUNTER);
                    msg.replyTo = responseMessenger;
                    Bundle bundle = new Bundle();
                    bundle.putInt("data", data);
                    msg.setData(bundle);
                    try {
                        mMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000, 1000);
    }

    @Override
    public void setFetchListener(FetchListener listener) {
        this.listener = listener;
    }

    @Override
    public int getData() {
        return data;
    }

    @Override
    public void destroy() {
        if (mMessenger != null) {
            activity.unbindService(mServiceConnection);
        }
    }

    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
        }
    }
}
