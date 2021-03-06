package ru.job4j.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ForwardLinked<T> implements Iterable<T> {
    private Node<T> head;

    public void add(T value) {
        Node<T> node = new Node<T>(value, null);
        if (isEmpty()) {
            head = node;
            return;
        }
        Node<T> tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = node;
    }

    public void addFirst(T value) {
        Node<T> node = new Node<T>(value, head);
        head = node;
    }

    public T deleteFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T rsl = head.value;
        head = head.next;
        return rsl;
    }

    public boolean revert() {
        if (isEmpty() || head.next == null) {
            return false;
        }
        Node<T> temp;
        Node<T> prev = null;
        while (!isEmpty()) {
            temp = head;
            head = head.next;
            temp.next = prev;
            prev = temp;
        }
        head = prev;
        return true;
    }

    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}