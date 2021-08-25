package dev.phonis.sharedwaypoints.server.commands.util;

public class Triple<A, B, C> extends Pair<A, B> {

    protected C c;

    public Triple() { }

    public Triple(A a, B b, C c) {
        super(a, b);

        this.c = c;
    }

    public C getC() {
        return this.c;
    }

    public void setC(C c) {
        this.c = c;
    }

}