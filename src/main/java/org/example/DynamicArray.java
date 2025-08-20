package org.example;


import java.util.Comparator;


public class DynamicArray<T> {
    private T[] arr;
    private int size;
    private int capacity;


    @SuppressWarnings("unchecked")
    public DynamicArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be > 0");
        }
        this.capacity = initialCapacity;
        this.arr = (T[]) new Object[initialCapacity];
        this.size = 0;
    }


    @SuppressWarnings("unchecked")
    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= capacity) return;
        int newCapacity = Math.max(minCapacity, capacity * 2);
        T[] newArr = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
        capacity = newCapacity;
    }


    public void add(T element) {
        if (element == null) throw new IllegalArgumentException("Element cannot be null");
        ensureCapacity(size + 1);
        arr[size++] = element;
    }


    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return arr[index];
    }


    public int size() {
        return size;
    }


    public void sortBy(Comparator<T> comparator) {
        if (comparator == null) throw new IllegalArgumentException("Comparator cannot be null");
        for (int i = 1; i < size; i++) {
            T key = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}

