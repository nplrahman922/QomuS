package com.example.QomuS;

public class QomusRBTree {
    private QomusRBnode root;
    private final QomusRBnode TNULL;

    public QomusRBTree() {
        TNULL = new QomusRBnode("", null, null);
        TNULL.isRed = false;
        root = TNULL;
    }

    public void insert(String key, Object value, Object gimmick) {
        QomusRBnode node = new QomusRBnode(key, value, gimmick);
        node.left = TNULL;
        node.right = TNULL;

        QomusRBnode y = null;
        QomusRBnode x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.key.compareToIgnoreCase(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.key.compareToIgnoreCase(y.key) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.isRed = false;
            return;
        }
        if (node.parent.parent == null) {
            return;
        }
        fixInsert(node);
    }

    public QomusRBnode searchNode(String key) {
        return searchHelper(this.root, key);
    }

    private QomusRBnode searchHelper(QomusRBnode node, String key) {
        if (node == TNULL || key == null) {
            return null;
        }
        if (key.equalsIgnoreCase(node.key)) {
            return node;
        }
        if (key.compareToIgnoreCase(node.key) < 0) {
            return searchHelper(node.left, key);
        } else {
            return searchHelper(node.right, key);
        }
    }

    // --- Rotasi & FixUp
    private void fixInsert(QomusRBnode k) {
        QomusRBnode u;
        while (k.parent.isRed) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.isRed) {
                    u.isRed = false;
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u.isRed) {
                    u.isRed = false;
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) break;
        }
        root.isRed = false;
    }

    private void leftRotate(QomusRBnode x) {
        QomusRBnode y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(QomusRBnode x) {
        QomusRBnode y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }
}