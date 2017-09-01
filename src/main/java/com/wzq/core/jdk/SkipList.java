package com.wzq.core.jdk;

import java.util.Random;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2017-9-1 19:26:32
 * Description: 跳跃表
 * http://www.mathcs.emory.edu/~cheung/Courses/323/Syllabus/Map/skip-list-impl.html
 */
public class SkipList {
    public SkipListEntry head;
    public SkipListEntry tail;

    public int n;
    public int h;
    public Random r;

    public SkipList() {
        SkipListEntry p1, p2;
        p1 = new SkipListEntry(SkipListEntry.negInf, null);
        p2 = new SkipListEntry(SkipListEntry.posInf, null);

        p1.right = p2;
        p2.left = p1;

        this.head = p1;
        this.tail = p2;

        this.n = 0;
        this.h = 0;
        this.r = new Random();
    }

    public Integer get(String key) {
        SkipListEntry p = findEntry(key);
        if (p.key.compareTo(key) == 0) {
            return p.value;
        }
        return null;
    }

    public Integer put(String key, Integer value) {
        SkipListEntry p = findEntry(key);
        if (p.key.equals(key)) {
            Integer old = p.value;
            p.value = value;
            return old;
        }

        SkipListEntry q = new SkipListEntry(key, value);
        q.left = p;
        q.right = p.right;
        p.right.left = q;
        p.right = q;

        int i = 0; //current level
        while (r.nextDouble() < 0.5) { //conin toss
            if (i >= h) {
                addEmptyLevel();
            }

            while (p.up == null) {
                p = p.left;
            }

            p = p.up;

            SkipListEntry e = new SkipListEntry(key, null);
            e.left = p;
            e.right = p.right;
            e.down = q;

            p.right.left = e;
            p.right = e;
            q.up = e;

            q = e;

            i += 1;
        }
        n += 1;
        return null;
    }

    public Integer remove(String key) {
        SkipListEntry p = findEntry(key);
        if (!p.key.equals(key)) {
            return null;
        }
        Integer old = p.value;
        SkipListEntry q;
        while (p != null) {
            q = p.up;
            p.left.right = p.right;
            p.right.left = p.left;
            p = q;
        }
        return old;
    }

    public SkipListEntry findEntry(String key) {
        SkipListEntry p;
        p = head;
        while (true) {
            while (!p.right.key.equals(SkipListEntry.posInf)
                    && p.right.key.compareTo(key) <= 0) {
                p = p.right;
            }
            if (p.down != null) {
                p = p.down;
            } else {
                break;
            }
        }
        return p;
    }


    private void addEmptyLevel() {
        SkipListEntry p1, p2;
        p1 = new SkipListEntry(SkipListEntry.negInf, null);
        p2 = new SkipListEntry(SkipListEntry.posInf, null);
        p1.right = p2;
        p1.down = head;

        p2.left = p1;
        p2.down = tail;

        head.up = p1;
        tail.up = p2;

        head = p1;
        tail = p2;

        h += 1;
    }

    public void printHorizontal() {
        String s = "";
        SkipListEntry p = head;
        while (p.down != null) {
            p = p.down;
        }
        int i = 0;
        while (p != null) {
            p.pos = i++;
            p = p.right;
        }

        p = head;
        while (p != null) {
            s = getOneRow(p);
            System.out.println(s);
            p = p.down;
        }
    }

    public String getOneRow(SkipListEntry p) {
        String s = "" + p.key;
        int a, b, i;
        a = 0;
        p = p.right;
        while (p != null) {
            SkipListEntry q = p;
            while (q.down != null){
                q = q.down;
            }
            b = q.pos;
            s = s + " <-";
            for (i = a + 1; i < b; i++)
                s = s + "--------";
            s = s + "> " + p.key;
            a = b;
            p = p.right;
        }
        return (s);
    }

    public static void main(String[] args) {

        SkipList S = new SkipList();

        S.printHorizontal();
        System.out.println("------");

        S.put("ABC", 123);
        S.printHorizontal();
        System.out.println("------");

        S.put("DEF", 123);
        S.printHorizontal();
        System.out.println("------");

        S.put("KLM", 123);
        S.printHorizontal();
        System.out.println("------");

        S.put("HIJ", 123);
        S.printHorizontal();
        System.out.println("------");

        S.put("GHJ", 123);
        S.printHorizontal();
        System.out.println("------");

        S.put("AAA", 123);
        S.printHorizontal();
        System.out.println("------");

        System.out.println(S.get("AAA"));
        System.out.println(S.remove("GHJ"));
        System.out.println(S.get("GHJ"));
        System.out.println(S.remove("a"));
        System.out.println(S.get("HIJ"));
    }
}


class SkipListEntry {
    public String key;
    public Integer value;

    public SkipListEntry left;
    public SkipListEntry right;
    public SkipListEntry up;
    public SkipListEntry down;

    public static String negInf = "-oo";
    public static String posInf = "+oo";

    public int pos;

    public SkipListEntry(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
