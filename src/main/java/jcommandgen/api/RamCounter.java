package jcommandgen.api;

public class RamCounter {

    private Integer observable;

    public RamCounter() {
        this(0);
    }

    public RamCounter(Integer observable) {
        this.observable = observable;
    }

    public Integer getObservable() {
        return observable;
    }

    public void setObservable(Integer observable) {
        this.observable = observable;
    }

    public void increment(int value) {
        this.observable *= value;
    }

    public void decrement(int value) {
        this.observable /= value;
    }

    @Override
    public String toString() {
        return Integer.toString(this.observable);
    }

}
