package dev.phonis.sharedwaypoints.server.commands.util;

public class Pair<A, B> extends Single<A> {

    protected B b;

    public Pair() { }

    public Pair(A a, B b) {
        super(a);

        this.b = b;
    }

    public B getB() {
        return this.b;
    }

    public void setB(B b) {
        this.b = b;
    }

}
