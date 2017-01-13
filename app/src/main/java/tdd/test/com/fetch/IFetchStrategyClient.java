package tdd.test.com.fetch;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by sujan on 1/13/2017.
 */

public interface IFetchStrategyClient {
    public void onRequest();
    public void setFetchListener(FetchListener listener);
    public int getData();
    public void destroy();
}
