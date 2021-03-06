package ru.job4j.collection;

import java.util.*;

/**
 * В данном классе создана упрощённая реализация ArrayList. В качестве поля класса определён
 * массив типа Object, размер которого определяется аргументом, переданным в конструктор
 * или равен 10, если вызван конструктор по умолчанию. Он будет хранить в себе значения списка.
 * Вторым полем является int modCount, это счётчик. Операция добавления элементов,
 * которая структурно модифицирует коллекцию, увеличивает этот счетчик, а итератор запоминает
 * значение этого счетчика на момент своего создания. Если коллекция модифицирована, итератор будет
 * кидать ConcurrentModificationException при вызове методов итератора.
 * @param <T> данный параметр описывает тип объектов, которые содержит список.
 */
public class SimpleArray<T> implements Iterable<T> {
    private Object[] container;
    private int modCount = 0;
    private int size = 0;

    public SimpleArray() {
        container = new Object[10];
    }

    public SimpleArray(int n) {
        container = new Object[n];
    }

    /**
     * Метод для получения элемента по индексу.
     * Если элемента с таким индексом не существует, бросается исключение
     * IndexOutOfBoundsException
     * @param index Индекс получаемого элемента.
     * @return Значение по данному индексу.
     */
    public T get(int index) {
        Objects.checkIndex(index, size);
        return (T) container[index];
    }

    /**
     * Метод для добавления элемента в конец списка. Если все поля массива container
     * заполнены, вызывается метод arrayExpansion, который расширяет данный массив.
     * @param model Элемент, который мы добавляем.
     */
    public void add(T model) {
        if (size == container.length) {
            arrayExpansion();
        }
        modCount++;
        container[size++] = model;
    }

    /**
     * Данный метод создаёт новый массив с такими же значениями, как в container,
     * но количеством ячеек в 2 раза больше исходного и присваивает ссылку на него
     * массиву container.
     */
    public void arrayExpansion() {
        container = Arrays.copyOf(container, size * 2);
    }

    /**
     * Данный метод создаёт итератор для перебора элементов списка.
     * Если список был изменён после создания итератора, любой из его методов
     * вызовет ConcurrentModificationException.
     * Если метод hasNext возвращает false(), но при этом вызван метод next(),
     * будет выброшено NoSuchElementException.
     * @return Итератор для перебора элементов списка.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
           private int expectedModCount = modCount;
           private int cursor = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) container[cursor++];
            }
        };
    }
}