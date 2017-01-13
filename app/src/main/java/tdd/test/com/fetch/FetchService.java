package tdd.test.com.fetch;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sujan on 1/11/2017.
 */

public class FetchService extends Service {

    public static final int MSG_TYPE_COUNTER = 1;
    private IFetchStrategyService fetchStrategy;

    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fetchStrategy.setData(fetchStrategy.getData()+1);
            }
        }, 3000, 3000);
        fetchStrategy = FetchFactory.getFetchService();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return fetchStrategy.getBinder();
    }
}
