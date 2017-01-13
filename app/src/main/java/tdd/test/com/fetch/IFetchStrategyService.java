package tdd.test.com.fetch;

import android.os.IBinder;

/**
 * Created by sujan on 1/13/2017.
 */

public interface IFetchStrategyService {
    public int getData();
    public void setData(int data);
    public IBinder getBinder();
}
