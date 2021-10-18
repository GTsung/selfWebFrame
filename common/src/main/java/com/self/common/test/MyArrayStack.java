package com.self.common.test;

import java.util.Arrays;

/**
 * @author GTsung
 * @date 2021/9/28
 */
public class MyArrayStack<T> {

    private Object[] table;
    private int size;

    public MyArrayStack() {
        this.table = new Object[16];
    }

    public void push(T o) {
        capacity(size + 1);
        table[size++] = o;
    }

    private void capacity(int len) {
        if (len>=table.length) {
            int newLen = table.length * 2;
            table = Arrays.copyOf(table, newLen);
        }
    }

    public T pop() {
        if (size<=0) {
            return null;
        }
        T obj = (T)table[size-1];
        table[size--] = null;
        return obj;
    }

    public T peek() {
        if (size>0) {
            return (T)table[size-1];
        }
        return null;
    }

    public static void main(String[] args) {
        MyArrayStack<Integer> stack = new MyArrayStack<>();
        stack.push(12);
        stack.push(2);
        stack.push(32);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.peek());

        System.out.println(8>>1);
    }
}

class MyLinkStack<T> {
    private static class Node<T> {
        private Node<T> next;
        private T obj;
        public Node(Node<T> next, T obj) {
            this.next = next;
            this.obj = obj;
        }
    }

    private Node<T> head;

    public void push(T obj) {
        if (head == null) {
            head = new Node<T>(null, obj);
        } else {
            Node<T> next = new Node<T>(null, obj);
            Node<T> temp = head;
            head = next;
            head.next = temp;
        }
    }

    public T peek() {
        if(head!=null) {
            return head.obj;
        }
        return null;
    }

    public T pop() {
        if (head == null) {
            return null;
        }
        T obj = head.obj;
        head = head.next;
        return obj;
    }
}
