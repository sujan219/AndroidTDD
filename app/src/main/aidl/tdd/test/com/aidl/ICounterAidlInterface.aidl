// ICounterAidlInterface.aidl
package tdd.test.com.aidl;

// Declare any non-default types here with import statements
import tdd.test.com.aidl.CounterListener;

interface ICounterAidlInterface {
    int getCounter();
    void setCounterListener(CounterListener listener);
}