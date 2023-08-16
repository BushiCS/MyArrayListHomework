package ru.sviridov;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * реализация изменяемого (динамического) массива
 * <p>Реализует публичные методы {@code add}, {@code get}, {@code remove}, {@code removeAll}, {@code trimToSize},
 * {@code clear}, {@code contains}, {@code indexOf}, {@code size} и {@code sort}.
 * <p>Каждый экземпляр CustomArrayList имеет начальную емкость (размер) равный 10. Если в дальнейшем массив будет заполняться данными, то
 * его размер будет автоматически увеличиваться (для чего используется приватный метод {@code grow}).
 * <p>Уменьшение размера производится с помощью метода {@code trimToSize}.
 * <p><strong>Обратите внимание, что эта реализация не синхронизирована.</strong></p>
 * Если несколько потоков одновременно обращаются к экземпляру ArrayList и по крайней мере один из потоков изменяет список структурно,
 * он должен быть синхронизирован извне.
 *
 * @param <E>
 * @author Yuriy Sviridov
 * @since 0.5
 */
public class CustomArrayList<E> {
    /**
     * начальная емкость по умолчанию
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * основной массив для хранения данных
     */
    private Object[] elementData;

    /**
     * Размер списка ArrayList (количество содержащихся в нем элементов)
     */
    private int size;

    /**
     * Создает пустой список с начальной емкостью по умолчанию
     */
    public CustomArrayList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Конструктор, который создает пустой список с указанной начальной емкостью.
     *
     * @param capacity начальная емкость списка
     * @throws IllegalArgumentException если укажут отрицательную емкость
     */
    public CustomArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("incorrect capacity");
        } else {
            elementData = new Object[capacity];
        }
    }

    /**
     * @return настоящую емкость коллекции (фактический размер с учетом пустых ячеек)
     */
    public int getCapacity() {
        return elementData.length;
    }

    /**
     * Добавляет элемент в список.
     * При необходимости список увеличивается, используя метод {@code grow}
     *
     * @param e входной элемент для добавления
     */
    public void add(E e) {
        if (size == elementData.length - 1) {
            elementData = grow();
        }
        elementData[size++] = e;
    }

    /**
     * Добавляет элемент по указанному индексу, а также сдвигает соседние вышестоящие элементы вправо.
     * При необходимости список увеличивается, используя метод {@code grow}.
     *
     * @param index индекс, по которому нужно добавить элемент
     * @param e     элемент для добавления
     * @throws ArrayIndexOutOfBoundsException если указывается некорректный индекс
     */
    public void add(int index, E e) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index " + index + " out of bound " + size);
        }
        if (size == (elementData.length - 1)) {
            elementData = grow();
        }
        System.arraycopy(elementData, index, elementData, index + 1, (size++) - index);
        elementData[index] = e;
    }

    /**
     * Достает элемент по указанному индексу.
     *
     * @param index указанный индекс для поиска элемента
     * @return {@code E} нужный элемент по индексу
     * @throws IndexOutOfBoundsException если указан некорректный индекс
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        return (E) elementData[index];
    }

    /**
     * Находит и удаляет указаный объект из коллекции, а также сдвигает последуюище элементы списка влево.
     *
     * @param e элемент для удаления
     * @return {@code true} если элемент успешно удален
     */
    public boolean remove(E e) {
        int pos = indexOf(e);
        if (pos >= 0) {
            System.arraycopy(elementData, pos + 1, elementData, pos, size - pos);
            size--;
            return true;
        }
        return false;
    }

    /**
     * Очищает пустые ячейки массива до фактического размера коллекции, для экономии памяти.
     */
    public void trimToSize() {
        if (size < elementData.length) {
            elementData = Arrays.copyOf(elementData, size);
        }
    }

    /**
     * Очищает всю коллекцию.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * Возвращает индекс по найденному элементу или -1, если элемент не найден
     *
     * @param e элемент для поиска
     * @return {@code int} индекс элемента
     */
    public int indexOf(Object e) {
        return IntStream.range(0, size).takeWhile(i -> elementData[i] != null).filter(i -> elementData[i].equals(e)).findFirst().orElse(-1);
    }

    /**
     * Удаляет из этого списка все его элементы, содержащиеся в указанной коллекции.
     *
     * @param c коллекция, содержащая элементы, которые нужно удалить из этого списка
     * @return {@code false} если ни один элемент из переданной коллекции не был найден и удален в искомой
     */

    @SuppressWarnings("unchecked")
    public boolean removeAll(CustomArrayList<?> c) {
        int removeCount = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(elementData[i])) {
                remove((E) elementData[i]);
                i--;
                removeCount++;
            }
        }
        return removeCount > 0;
    }

    /**
     * Возвращает true если эта коллекция содержит указанный элемент.
     *
     * @param o элемент для поиска
     * @return {@code true}, если элемент найден в коллекции
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Возвращает размер коллекции
     *
     * @return {@code int} размер коллекции.
     */
    public int size() {
        return size;
    }

    /**
     * Приватный метод для увеличения размера коллекции по достижении лимита.
     *
     * @return {@code Object[]} увеличенный массив (коллекцию)
     */
    private Object[] grow() {
        int growFactor = elementData.length + (size / 2);
        return Arrays.copyOf(elementData, growFactor);
    }

    /**
     * Публичный вспомогательный метод для сортировки.
     * (при необходимости можно указать другой вид сортировки).
     */
    @SuppressWarnings("unchecked")
    public void sort() {
        quickSort((E[]) elementData, 0, size - 1);
    }

    /**
     * Публичный вспомогательный метод для сортировки с компаратором.
     * (при необходимости можно указать другой вид сортировки).
     * @param c входной компаратор для конкретной сортировки нужных данных
     */
    @SuppressWarnings("unchecked")
    public void sort(Comparator<E> c) {
        quickSort(c, (E[]) elementData, 0, size - 1);
    }

    /**
     * Метод сортировки посредством алгоритма быстрой сортировки.
     * Достигается путем выбора опорного элемента и сортировки соседних, а также с помощью рекурсии.
     * В теле метода описаны важные этапы
     * Сложность алгоритма составляет: худшая = O(n^2), средняя = O(n log(n))
     *
     * @param sortArr сортируемый массив
     * @param first   первый элемент коллекции
     * @param last    крайний элемент
     */
    private void quickSort(E[] sortArr, int first, int last) {
        // завершаем, если массив пуст или уже нечего делить
        if (sortArr.length == 0 || first >= last) return;

        // выбираем опорный элемент
        int middle = first + (last - first) / 2;
        E border = sortArr[middle];

        // разделяем на подмассивы и меняем местами
        int i = first;
        int j = last;
        while (i <= j) {
            while (((Comparable<E>) sortArr[i]).compareTo(border) < 0) i++;
            while (((Comparable<E>) sortArr[j]).compareTo(border) > 0) j--;
            if (i <= j) { // меняем местами
                E swap = sortArr[i];
                sortArr[i] = sortArr[j];
                sortArr[j] = swap;
                i++;
                j--;
            }
        }
        // рекурсия для сортировки левой и правой частей
        if (first < j) quickSort(sortArr, first, j);
        if (last > i) quickSort(sortArr, i, last);
    }
    /**
     * Метод сортировки посредством алгоритма быстрой сортировки
     * Достигается путем выбора опорного элемента и сортировки соседних, а также с помощью рекурсии.
     * В теле метода описаны важные этапы
     * Сложность алгоритма составляет: худшая = O(n^2), средняя = O(n log(n)).
     * <p>Компаратор определяет - по каким параметрам нужно сортировать.
     * @param c входной компаратор
     * @param sortArr сортируемый массив
     * @param first   первый элемент коллекции
     * @param last    крайний элемент
     */
    private void quickSort(Comparator<E> c, E[] sortArr, int first, int last) {
        // завершаем, если массив пуст или уже нечего делить
        if (sortArr.length == 0 || first >= last) return;

        // выбираем опорный элемент
        int middle = first + (last - first) / 2;
        E border = sortArr[middle];

        // разделяем на подмассивы и меняем местами
        int i = first;
        int j = last;
        while (i <= j) {
            while (c.compare(sortArr[i], border) < 0) i++;
            while (c.compare(sortArr[j], border) > 0) j--;
            if (i <= j) { // меняем местами
                E swap = sortArr[i];
                sortArr[i] = sortArr[j];
                sortArr[j] = swap;
                i++;
                j--;
            }
        }
        // рекурсия для сортировки левой и правой частей
        if (first < j) quickSort(c, sortArr, first, j);
        if (last > i) quickSort(c, sortArr, i, last);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elementData, size));
    }
}
