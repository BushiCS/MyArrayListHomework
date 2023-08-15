import ru.sviridov.CustomArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomArrayListTest {

    CustomArrayList<String> testList = new CustomArrayList<>();

    public void addList() {
        testList.add("Michael");
        testList.add("Mary");
        testList.add("Joan");
        testList.add("Bill");
        testList.add("Jack");
    }

    @Test
    @DisplayName("Проверка стандартной емкости коллекции")
    void defaultSizeTest() {
        testList = new CustomArrayList<>();
        Assertions.assertEquals(10, testList.getCapacity());
    }

    @Test
    @DisplayName("Проверка добавления некорректного значения емкости")
    void incorrectCapacityTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CustomArrayList<>(-10));
    }

    @Test
    @DisplayName("Проверка добавления корректного значения емкости")
    void correctCapacityTest() {
        CustomArrayList<String> list = new CustomArrayList<>(15);
        Assertions.assertEquals(15, list.getCapacity());
    }

    @Test
    @DisplayName("Проверка добавления элемента в список (и проверка некорректного индекса")
    void addTest() {
        addList();
        testList.add(1, "John");
        Assertions.assertEquals("Bill", testList.get(4));
        Assertions.assertEquals("Jack", testList.get(5));
        Assertions.assertEquals("John", testList.get(1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> testList.add(-1, "Kyle"));
    }

    @Test
    @DisplayName("Проверка получения элемента по индексу и некорректного индекса")
    void getTest() {
        addList();
        Assertions.assertEquals("Mary", testList.get(1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> testList.get(-1));
    }

    @Test
    @DisplayName("Проверка удаления элемента")
    void removeTest() {
        addList();
        CustomArrayList<String> testListForRemove = new CustomArrayList<>();
        CustomArrayList<String> wrongTestRemovingList = new CustomArrayList<>();
        wrongTestRemovingList.add("213213");
        testListForRemove.add("Jack");
        testListForRemove.add("Bill");
        Assertions.assertTrue(testList.remove("Michael"));
        Assertions.assertTrue(testList.removeAll((testListForRemove)));
        Assertions.assertFalse(testList.removeAll(wrongTestRemovingList));
    }

    @Test
    @DisplayName("Проверка нахождения объекта в коллекции")
    void containsTest() {
        addList();
        Assertions.assertTrue(testList.contains("Jack"));
        Assertions.assertFalse(testList.contains("2"));
    }

    @Test
    @DisplayName("проверка размера коллекции")
    void sizeTest() {
        addList();
        Assertions.assertEquals(5, testList.size());
    }

    @Test
    @DisplayName("проверка очистки всей коллекции")
    void clearTest() {
        addList();
        testList.clear();
        Assertions.assertEquals(0, testList.size());
    }

    @Test
    @DisplayName("проверка получения индекса по объекту")
    void indexOfTest() {
        addList();
        Assertions.assertEquals(2, testList.indexOf("Joan"));
    }

    @Test
    @DisplayName("проверка увеличения размера и фактической емкости коллекции")
    void growTest() {
        addList();
        Assertions.assertEquals(5, testList.size());
        Assertions.assertEquals(10, testList.getCapacity());
        addList();
        addList();
        addList();
        Assertions.assertEquals(20, testList.size());
        Assertions.assertEquals(29, testList.getCapacity());
    }

    @Test
    @DisplayName("проверка trimToSize")
    void trimToSizeTest() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        Assertions.assertEquals(29, list.getCapacity());
        list.trimToSize();
        Assertions.assertEquals(20, list.getCapacity());
    }


    @Test
    @DisplayName("проверка сортировки")
    void sortTest() {
        addList();
        testList.sort();
        Assertions.assertEquals("[Bill, Jack, Joan, Mary, Michael]", testList.toString());
        CustomArrayList<Integer> testlist2 = new CustomArrayList<>();
        testlist2.add(4);
        testlist2.add(2);
        testlist2.add(1);
        testlist2.add(3);
        testlist2.sort();
        Assertions.assertEquals("[1, 2, 3, 4]", testlist2.toString());
    }
}
