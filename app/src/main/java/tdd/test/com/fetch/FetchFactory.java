package tdd.test.com.fetch;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by sujan on 1/13/2017.
 */

public class FetchFactory {
    public static IFetchStrategyClient getFetchClient(Activity act, Intent intent){
        return new MessengerFetchStrategyClient(act, intent);
    }

    public static IFetchStrategyService getFetchService(){
        return new MessengerFetchStrategyService();
    }
}
