package dev.phonis.sharedwaypoints.server.commands.util;

public class Single<A> {

    protected A a;

    public Single() { }

    public Single(A a) {
        this.a = a;
    }

    public A getA() {
        return this.a;
    }

    public void setA(A a) {
        this.a = a;
    }

}
