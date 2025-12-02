package com.example.QomuS;

public class QomusRBnode {
    String key;
    Object value;
    QomusRBnode parent;
    QomusRBnode left;
    QomusRBnode right;
    boolean isRed;

    public QomusRBnode(String key, Object value) {
        this.key = key;
        this.value = value;
        this.isRed = true;
    }
}
