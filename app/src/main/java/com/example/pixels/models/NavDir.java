package com.example.pixels.models;

import javax.annotation.Nullable;

public class NavDir {
    private NavDest destination;
    private Object data = null;

    public NavDir(NavDest destination, @Nullable Object data) {
        this.destination = destination;
        this.data = data;
    }

    public NavDest getDestination() {
        return destination;
    }

    public void setDestination(NavDest destination) {
        this.destination = destination;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static enum NavDest {
        POST
    }
}
