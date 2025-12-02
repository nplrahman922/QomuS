package com.example.QomuS;

public class QomusRBnode {
    String key;
    Object value;   // Arti
    Object gimmick; // String atau Function (Supplier)

    QomusRBnode parent;
    QomusRBnode left;
    QomusRBnode right;
    boolean isRed;

    public QomusRBnode(String key, Object value, Object gimmick) {
        this.key = key;
        this.value = value;
        this.gimmick = gimmick;
        this.isRed = true;
    }
}