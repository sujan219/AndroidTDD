package tdd.test.com.fetch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sujan on 1/13/2017.
 */

public class FetchFactory {
    public static IFetchStrategyClient getFetchClient(Context ctx, Intent intent){
        return new AIDLFetchStrategyClient(ctx, intent);
    }

    public static IFetchStrategyService getFetchService(Context ctx){
        return new AIDLFetchStrategyService();
    }
}
