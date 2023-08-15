package ru.sviridov;

import java.util.Arrays;

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
 * наследование от Comparable использовано для встроенной сортировки {@code sort}
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
    private Object[] element_data;

    /**
     * Размер списка ArrayList (количество содержащихся в нем элементов)
     */
    private int size;

    /**
     * Создает пустой список с начальной емкостью по умолчанию
     */
    public CustomArrayList() {
        element_data = new Object[DEFAULT_CAPACITY];
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
            element_data = new Object[capacity];
        }
    }

    /**
     * @return настоящую емкость коллекции (фактический размер с учетом пустых ячеек)
     */
    public int getCapacity() {
        return element_data.length;
    }

    /**
     * Добавляет элемент в список.
     * При необходимости список увеличивается, используя метод {@code grow}
     *
     * @param e входной элемент для добавления
     */
    public void add(E e) {
        if (size == element_data.length - 1) {
            element_data = grow();
        }
        element_data[size++] = e;
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
        if (size == (element_data.length - 1)) {
            element_data = grow();
        }
        System.arraycopy(element_data, index, element_data, index + 1, (size++) - index);
        element_data[index] = e;
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
        return (E) element_data[index];
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
            System.arraycopy(element_data, pos + 1, element_data, pos, size - pos);
            size--;
            return true;
        }
        return false;
    }

    /**
     * Очищает пустые ячейки массива до фактического размера коллекции, для экономии памяти.
     */
    public void trimToSize() {
        if (size < element_data.length) {
            element_data = Arrays.copyOf(element_data, size);
        }
    }

    /**
     * Очищает всю коллекцию.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            element_data[i] = null;
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
        int pos = -1;
        for (int i = 0; i < size; i++) {
            if (element_data[i] == null) {
                break;
            }
            if (element_data[i].equals(e)) {
                pos = i;
                break;
            }
        }
        return pos;
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
            if (c.contains(element_data[i])) {
                remove((E) element_data[i]);
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
        int growFactor = element_data.length + (size / 2);
        return Arrays.copyOf(element_data, growFactor);
    }

    /**
     * Публичный вспомогательный метод для сортировки.
     * (при необходимости можно указать другой вид сортировки).
     */
    @SuppressWarnings("unchecked")
    public void sort() {
        quickSort((E[]) element_data, 0, size - 1);
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
        int i = first, j = last;
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


    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(element_data, size));
    }
}
