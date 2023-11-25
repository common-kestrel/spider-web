import com.spiderweb.SpiderWeb;
import com.spiderweb.SpiderWebNode;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SpiderWeb class.
 */
public class SpiderWebTest {

    /**
     * Test case for the size of an empty SpiderWeb.
     */
    @Test
    public void testSizeEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();

        assertEquals(0, spiderWeb.size());
    }

    /**
     * Test case for the size of a SpiderWeb with multiple elements.
     */
    @Test
    public void testSizeSpiderWebMultipleElements() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>();
        spiderWeb.add('A');
        spiderWeb.add('B');
        spiderWeb.add('C');

        assertEquals(3, spiderWeb.size());
    }

    /**
     * Test case for getting the first element from an empty SpiderWeb, expecting a {@code NoSuchElementException}.
     */
    @Test
    public void testGetFirstOnEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        assertThrows(NoSuchElementException.class, spiderWeb::getFirst);
    }

    /**
     * Test case for getting the first element from a non-empty SpiderWeb, expecting the first element.
     */
    @Test
    public void testGetFirstOnNonEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);
        spiderWeb.add(2);
        spiderWeb.add(3);

        assertEquals(1, spiderWeb.getFirst());
    }

    /**
     * Test case for getting the last element from an empty SpiderWeb, expecting a {@code NoSuchElementException}.
     */
    @Test
    public void testGetLastOnEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        assertThrows(NoSuchElementException.class, spiderWeb::getLast);
    }

    /**
     * Test case for getting the last element from a non-empty SpiderWeb, expecting the last element.
     */
    @Test
    public void testGetLastOnNonEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);
        spiderWeb.add(2);
        spiderWeb.add(3);

        assertEquals(3, spiderWeb.getLast());
    }

    /**
     * Test case for adding an element to an empty SpiderWeb.
     */
    @Test
    public void testAddToEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("A");

        assertNotNull(spiderWeb.getFirstNode());
        assertNotNull(spiderWeb.getLastNode());
        assertNull(spiderWeb.getLevelPointer());
        assertEquals(0, spiderWeb.getLevel());
        assertEquals(0, spiderWeb.getIndex());
        assertEquals(1, spiderWeb.size());
    }

    /**
     * Test case for adding multiple elements to the SpiderWeb.
     */
    @Test
    public void testAddMultipleElements() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);
        spiderWeb.add(2);
        spiderWeb.add(3);

        assertEquals(0, spiderWeb.getLevel());
        assertEquals(2, spiderWeb.getIndex());
        assertEquals(3, spiderWeb.size());
    }

    /**
     * Test case for adding elements to exceed the maximum index for a level.
     */
    @Test
    public void testAddExceedingMaxIndex() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>(2);
        spiderWeb.add('A');
        spiderWeb.add('B');
        spiderWeb.add('C');

        assertEquals(1, spiderWeb.getLevel());
        assertEquals(0, spiderWeb.getIndex());
        assertEquals(3, spiderWeb.size());
        assertNotNull(spiderWeb.getLevelPointer());
        assertEquals('C', spiderWeb.getFirstNode().getNextLevelNode().getValue());
    }

    /**
     * Test case for adding a new node to an empty SpiderWeb.
     */
    @Test
    public void testAddNodeToEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        SpiderWebNode<String> node = new SpiderWebNode<>("A", null, null);
        spiderWeb.add(node);

        assertNotNull(spiderWeb.getFirstNode());
        assertNotNull(spiderWeb.getLastNode());
        assertNull(spiderWeb.getLevelPointer());
        assertEquals(0, spiderWeb.getLevel());
        assertEquals(0, spiderWeb.getIndex());
        assertEquals(1, spiderWeb.size());
    }

    /**
     * Test case for adding multiple nodes to the SpiderWeb.
     */
    @Test
    public void testAddMultipleNodes() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        SpiderWebNode<Integer> node1 = new SpiderWebNode<>(1, null, null);
        SpiderWebNode<Integer> node2 = new SpiderWebNode<>(2, null, null);
        SpiderWebNode<Integer> node3 = new SpiderWebNode<>(3, null, null);
        spiderWeb.add(node1);
        spiderWeb.add(node2);
        spiderWeb.add(node3);

        assertEquals(0, spiderWeb.getLevel());
        assertEquals(2, spiderWeb.getIndex());
        assertEquals(3, spiderWeb.size());
    }

    /**
     * Test case for adding a new node to exceed the maximum index for a level.
     */
    @Test
    public void testAddNodeExceedingMaxIndex() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>(2);
        SpiderWebNode<Character> node1 = new SpiderWebNode<>('A', null, null);
        SpiderWebNode<Character> node2 = new SpiderWebNode<>('B', null, null);
        SpiderWebNode<Character> node3 = new SpiderWebNode<>('C', null, null);
        spiderWeb.add(node1);
        spiderWeb.add(node2);
        spiderWeb.add(node3);

        assertEquals(1, spiderWeb.getLevel());
        assertEquals(0, spiderWeb.getIndex());
        assertEquals(3, spiderWeb.size());
        assertNotNull(spiderWeb.getLevelPointer());
        assertEquals('C', spiderWeb.getFirstNode().getNextLevelNode().getValue());
    }

    /**
     * Test case for adding elements using the addFirst method.
     */
    @Test
    public void testAddFirst() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.addFirst("C");
        spiderWeb.addFirst("B");
        spiderWeb.addFirst("A");

        assertEquals(3, spiderWeb.size());
        assertEquals("A", spiderWeb.getFirstNode().getValue());
        assertEquals("C", spiderWeb.getLastNode().getValue());
        assertNull(spiderWeb.getFirstNode().getPrevNode());
        assertNotNull(spiderWeb.getFirstNode().getNextNode());
        assertNull(spiderWeb.getFirstNode().getPrevLevelNode());
        assertNull(spiderWeb.getFirstNode().getNextLevelNode());
        assertNotNull(spiderWeb.getLastNode().getPrevNode());
        assertNull(spiderWeb.getLastNode().getNextNode());
        assertNull(spiderWeb.getLastNode().getPrevLevelNode());
        assertNull(spiderWeb.getLastNode().getNextLevelNode());
    }

    /**
     * Test case for adding an element to an empty SpiderWeb using the addFirst method.
     */
    @Test
    public void testAddFirstOnEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.addFirst(42);

        assertEquals(1, spiderWeb.size());
        assertEquals(42, spiderWeb.getFirstNode().getValue());
        assertEquals(42, spiderWeb.getLastNode().getValue());
    }

    /**
     * Test case for adding elements to exceed the maximum index for a level using the addFirst method.
     */
    @Test
    public void testAddFirstOnSpiderWebWithMaxElements() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>(2);
        spiderWeb.addFirst("C");
        spiderWeb.addFirst("B");
        spiderWeb.addFirst("A");

        spiderWeb.addFirst("D");
        assertEquals(4, spiderWeb.size());
        assertEquals("D", spiderWeb.getFirstNode().getValue());
        assertEquals("C", spiderWeb.getLastNode().getValue());
        assertEquals(1, spiderWeb.getLevel());
        assertNull(spiderWeb.getFirstNode().getPrevNode());
        assertNotNull(spiderWeb.getFirstNode().getNextNode());
        assertNull(spiderWeb.getFirstNode().getPrevLevelNode());
        assertNotNull(spiderWeb.getFirstNode().getNextLevelNode());
        assertNotNull(spiderWeb.getLastNode().getPrevNode());
        assertNull(spiderWeb.getLastNode().getNextNode());
        assertNotNull(spiderWeb.getLastNode().getPrevLevelNode());
        assertNull(spiderWeb.getLastNode().getNextLevelNode());
    }

    /**
     * Test case for adding a new node using the addFirst method.
     */
    @Test
    public void testAddFirstNode() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        SpiderWebNode<String> node1 = new SpiderWebNode<>("C", null, null);
        SpiderWebNode<String> node2 = new SpiderWebNode<>("B", null, null);
        SpiderWebNode<String> node3 = new SpiderWebNode<>("A", null, null);
        spiderWeb.addFirst(node1);
        spiderWeb.addFirst(node2);
        spiderWeb.addFirst(node3);

        assertEquals(3, spiderWeb.size());
        assertEquals("A", spiderWeb.getFirstNode().getValue());
        assertEquals("C", spiderWeb.getLastNode().getValue());
        assertNull(spiderWeb.getFirstNode().getPrevNode());
        assertNotNull(spiderWeb.getFirstNode().getNextNode());
        assertNull(spiderWeb.getFirstNode().getPrevLevelNode());
        assertNull(spiderWeb.getFirstNode().getNextLevelNode());
        assertNotNull(spiderWeb.getLastNode().getPrevNode());
        assertNull(spiderWeb.getLastNode().getNextNode());
        assertNull(spiderWeb.getLastNode().getPrevLevelNode());
        assertNull(spiderWeb.getLastNode().getNextLevelNode());
    }

    /**
     * Test case for adding a new node to an empty SpiderWeb using the addFirst method.
     */
    @Test
    public void testAddFirstNodeOnEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        SpiderWebNode<Integer> node = new SpiderWebNode<>(42, null, null);
        spiderWeb.addFirst(node);

        assertEquals(1, spiderWeb.size());
        assertEquals(42, spiderWeb.getFirstNode().getValue());
        assertEquals(42, spiderWeb.getLastNode().getValue());
    }

    /**
     * Test case for adding a new node to exceed the maximum index for a level using the addFirst method.
     */
    @Test
    public void testAddFirstNodeOnSpiderWebWithMaxElements() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>(3);
        SpiderWebNode<String> node1 = new SpiderWebNode<>("C", null, null);
        SpiderWebNode<String> node2 = new SpiderWebNode<>("B", null, null);
        SpiderWebNode<String> node3 = new SpiderWebNode<>("A", null, null);
        spiderWeb.addFirst(node1);
        spiderWeb.addFirst(node2);
        spiderWeb.addFirst(node3);

        SpiderWebNode<String> node4 = new SpiderWebNode<>("D", null, null);
        spiderWeb.addFirst(node4);

        assertEquals(4, spiderWeb.size());
        assertEquals("D", spiderWeb.getFirstNode().getValue());
        assertEquals("C", spiderWeb.getLastNode().getValue());
        assertEquals(1, spiderWeb.getLevel());
        assertNull(spiderWeb.getFirstNode().getPrevNode());
        assertNotNull(spiderWeb.getFirstNode().getNextNode());
        assertNull(spiderWeb.getFirstNode().getPrevLevelNode());
        assertNotNull(spiderWeb.getFirstNode().getNextLevelNode());
        assertNotNull(spiderWeb.getLastNode().getPrevNode());
        assertNull(spiderWeb.getLastNode().getNextNode());
        assertNotNull(spiderWeb.getLastNode().getPrevLevelNode());
        assertNull(spiderWeb.getLastNode().getNextLevelNode());
    }

    /**
     * Test case for the level and index of an empty SpiderWeb.
     */
    @Test
    public void testLevelIndexEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();

        assertEquals(-1, spiderWeb.getLevel());
        assertEquals(-1, spiderWeb.getIndex());
    }

    /**
     * Test case for the level and index after adding one element to the SpiderWeb.
     */
    @Test
    public void testLevelIndexAfterAddOneElement() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);

        assertEquals(0, spiderWeb.getLevel());
        assertEquals(0, spiderWeb.getIndex());
    }

    /**
     * Test case for the level and index after adding elements to exceed the maximum index for a level.
     */
    @Test
    public void testLevelIndexAfterExceedingMaxIndex() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>(2);
        spiderWeb.add("A");
        spiderWeb.add("B");
        spiderWeb.add("C");

        assertEquals(1, spiderWeb.getLevel());
        assertEquals(0, spiderWeb.getIndex());
    }


    /**
     * Test case for printing an empty SpiderWeb.
     */
    @Test
    public void testPrintEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        spiderWeb.print();
        System.setOut(System.out);

        assertEquals("", outContent.toString());
    }

    /**
     * Test case for printing a SpiderWeb with one element.
     */
    @Test
    public void testPrintSpiderWebOneElement() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("A");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        spiderWeb.print();
        System.setOut(System.out);

        assertEquals("value: A, level: 0, index: 0\n", outContent.toString());
    }

    /**
     * Test case for printing a SpiderWeb with multiple elements.
     */
    @Test
    public void testPrintSpiderWebMultipleElements() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>(2);
        spiderWeb.add('A');
        spiderWeb.add('B');
        spiderWeb.add('C');

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        spiderWeb.print();
        System.setOut(System.out);

        String expectedOutput = "value: A, level: 0, index: 0\n" +
                "value: B, level: 0, index: 1\n" +
                "value: C, level: 1, index: 0\n";
        assertEquals(expectedOutput, outContent.toString());
    }


    /**
     * Test case for a valid level where the maximum index is within the current level.
     */
    @Test
    public void testGetMaximumIndexForLevel_ValidLevel() throws Exception {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("A");

        int maxIndex = spiderWeb.getMaximumIndexForLevel(0);
        assertEquals(0, maxIndex);
    }

    /**
     * Test case for a level equal to the current level.
     */
    @Test
    public void testGetMaximumIndexForLevel_LevelEqualsCurrent() throws Exception {
        SpiderWeb<Double> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1.0);
        spiderWeb.add(2.0);
        spiderWeb.add(3.0);

        int maxIndex = spiderWeb.getMaximumIndexForLevel(0);
        assertEquals(2, maxIndex);
    }

    /**
     * Test case for a level above the current level.
     */
    @Test
    public void testGetMaximumIndexForLevel_LevelAboveCurrent() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>();
        spiderWeb.add('A');
        spiderWeb.add('B');

        assertThrows(IllegalArgumentException.class, () -> {
            spiderWeb.getMaximumIndexForLevel(1);
        });
    }

    /**
     * Test case for a negative level.
     */
    @Test
    public void testGetMaximumIndexForLevel_NegativeLevel() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();

        assertThrows(IllegalArgumentException.class, () -> {
            spiderWeb.getMaximumIndexForLevel(-1);
        });
    }

    /**
     * Test case for an empty spider web.
     */
    @Test
    public void testGetMaximumIndexForLevel_EmptySpiderWeb() {
        SpiderWeb<Boolean> spiderWeb = new SpiderWeb<>();

        assertThrows(IllegalStateException.class, () -> {
            spiderWeb.getMaximumIndexForLevel(0);
        });
    }


    /**
     * Test case for finding the index of an element in an empty SpiderWeb.
     */
    @Test
    public void testIndexOfEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();

        HashMap<String, Integer> result = spiderWeb.indexOf("A");
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for finding the index of an element that exists in the SpiderWeb.
     */
    @Test
    public void testIndexOfElementExists() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);
        spiderWeb.add(123);
        spiderWeb.add(999);

        HashMap<String, Integer> result = spiderWeb.indexOf(123);
        assertEquals(0, result.get("level"));
        assertEquals(1, result.get("index"));
    }

    /**
     * Test case for finding the index of an element that does not exist in the SpiderWeb.
     */
    @Test
    public void testIndexOfElementNotExists() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>();
        spiderWeb.add('A');
        spiderWeb.add('B');
        spiderWeb.add('C');

        HashMap<String, Integer> result = spiderWeb.indexOf('X');
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for finding the index of an element in a SpiderWeb with multiple occurrences.
     */
    @Test
    public void testIndexOfElementMultipleOccurrences() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("apple");
        spiderWeb.add("banana");
        spiderWeb.add("apple");

        HashMap<String, Integer> result = spiderWeb.indexOf("apple");
        assertEquals(0, result.get("level"));
        assertEquals(0, result.get("index"));
    }

    /**
     * Test case for finding the last index of an element in an empty SpiderWeb.
     */
    @Test
    public void testLastIndexOfEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();

        HashMap<String, Integer> result = spiderWeb.lastIndexOf("A");
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for finding the last index of an element that exists in the SpiderWeb.
     */
    @Test
    public void testLastIndexOfElementExists() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);
        spiderWeb.add(123);
        spiderWeb.add(999);

        HashMap<String, Integer> result = spiderWeb.lastIndexOf(123);
        assertEquals(0, result.get("level"));
        assertEquals(1, result.get("index"));
    }

    /**
     * Test case for finding the last index of an element that does not exist in the SpiderWeb.
     */
    @Test
    public void testLastIndexOfElementNotExists() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>();
        spiderWeb.add('A');
        spiderWeb.add('B');
        spiderWeb.add('C');

        HashMap<String, Integer> result = spiderWeb.lastIndexOf('X');
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for finding the last index of an element in a SpiderWeb with multiple occurrences.
     */
    @Test
    public void testLastIndexOfElementMultipleOccurrences() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>(2);
        spiderWeb.add("apple");
        spiderWeb.add("banana");
        spiderWeb.add("apple");

        HashMap<String, Integer> result = spiderWeb.lastIndexOf("apple");
        assertEquals(1, result.get("level"));
        assertEquals(0, result.get("index"));
    }

    /**
     * Test case for finding the index of an object in an empty SpiderWeb.
     */
    @Test
    public void testIndexOfObjectEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        SpiderWebNode<Integer> spiderWebNode = new SpiderWebNode<>(2, null, null);

        HashMap<String, Integer> result = spiderWeb.indexOf(spiderWebNode);
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for finding the index of an object that exists in the SpiderWeb.
     */
    @Test
    public void testIndexOfObjectExists() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);
        spiderWeb.add(123);
        spiderWeb.add(999);
        SpiderWebNode<Integer> spiderWebNode = spiderWeb.getFirstNode();

        HashMap<String, Integer> result = spiderWeb.indexOf(spiderWebNode);
        assertEquals(0, result.get("level"));
        assertEquals(0, result.get("index"));

    }

    /**
     * Test case for finding the index of an object that does not exist in the SpiderWeb.
     */
    @Test
    public void testIndexOfObjectNotExists() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);
        spiderWeb.add(123);
        spiderWeb.add(999);
        SpiderWebNode<Integer> spiderWebNode = new SpiderWebNode<>(11, null, null);

        HashMap<String, Integer> result = spiderWeb.indexOf(spiderWebNode);
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for finding the last index of an object in an empty SpiderWeb.
     */
    @Test
    public void testLastIndexOfObjectEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        SpiderWebNode<Integer> spiderWebNode = new SpiderWebNode<>(2, null, null);

        HashMap<String, Integer> result = spiderWeb.lastIndexOf(spiderWebNode);
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for finding the last index of an object that exists in the SpiderWeb.
     */
    @Test
    public void testLastIndexOfObjectExists() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);
        spiderWeb.add(123);
        spiderWeb.add(999);
        SpiderWebNode<Integer> spiderWebNode = spiderWeb.getFirstNode();

        HashMap<String, Integer> result = spiderWeb.lastIndexOf(spiderWebNode);
        assertEquals(0, result.get("level"));
        assertEquals(0, result.get("index"));

    }

    /**
     * Test case for finding the last index of an object that does not exist in the SpiderWeb.
     */
    @Test
    public void testLastIndexOfObjectNotExists() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);
        spiderWeb.add(123);
        spiderWeb.add(999);
        SpiderWebNode<Integer> spiderWebNode = new SpiderWebNode<>(11, null, null);

        HashMap<String, Integer> result = spiderWeb.lastIndexOf(spiderWebNode);
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for getting the element at a valid level and index.
     */
    @Test
    public void testGetValidLevelAndIndex() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);
        spiderWeb.add(2);
        spiderWeb.add(3);

        int result = spiderWeb.get(0, 1);

        assertEquals(2, result);
    }

    /**
     * Test case for getting the element at an invalid level.
     */
    @Test
    public void testGetInvalidLevel() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();

        int invalidLevel = -1;
        int validIndex = 1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> spiderWeb.get(invalidLevel, validIndex));

        assertEquals("Invalid level or index. Level: " + invalidLevel + ", Index: " + validIndex,
                exception.getMessage(), "Expected IllegalArgumentException for invalid level");
    }

    /**
     * Test case for getting the element at an invalid index.
     */
    @Test
    public void testGetInvalidIndex() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);

        int validLevel = 0;
        int invalidIndex = 1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> spiderWeb.get(validLevel, invalidIndex));

        assertEquals("Invalid level or index. Level: " + validLevel + ", Index: " + invalidIndex,
                exception.getMessage(), "Expected IllegalArgumentException for invalid index");
    }

    /**
     * Test case for getting the element when the SpiderWeb is empty.
     */
    @Test
    public void testGetEmptySpiderWeb() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>();

        int level = 0;
        int index = 0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> spiderWeb.get(level, index));

        assertEquals("Invalid level or index. Level: " + level + ", Index: " + index,
                exception.getMessage(), "Expected IllegalArgumentException for invalid index");
    }

    /**
     * Test case for setting an element at a valid level and index.
     */
    @Test
    void testSetValidLevelAndIndex() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("A");
        spiderWeb.add("B");

        String oldValue = spiderWeb.set(0, 1, "X");

        assertEquals("B", oldValue);
        assertEquals("X", spiderWeb.getFirstNode().getNextNode().getValue());
    }

    /**
     * Test case for setting an element with an invalid level.
     */
    @Test
    void testSetInvalidLevel() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);

        assertThrows(IllegalArgumentException.class, () -> spiderWeb.set(-1, 0, 42));
        assertThrows(IllegalArgumentException.class, () -> spiderWeb.set(1, 0, 42));
    }

    /**
     * Test case for setting an element with an invalid index.
     */
    @Test
    void testSetInvalidIndex() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);

        assertThrows(IllegalArgumentException.class, () -> spiderWeb.set(0, -1, 42));
        assertThrows(IllegalArgumentException.class, () -> spiderWeb.set(0, 1, 42));
    }

    /**
     * Test case for setting an element with both an invalid level and index.
     */
    @Test
    void testSetInvalidLevelAndIndex() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);

        assertThrows(IllegalArgumentException.class, () -> spiderWeb.set(-1, -1, 42));
        assertThrows(IllegalArgumentException.class, () -> spiderWeb.set(1, 1, 42));
    }


    /**
     * Test case for removing the first element from an empty SpiderWeb.
     */
    @Test
    void testRemoveFirstFromEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, spiderWeb::removeFirst);
        assertEquals("Cannot remove from an empty SpiderWeb.", exception.getMessage());
        assertEquals(0, spiderWeb.size());
        assertNull(spiderWeb.getFirstNode());
        assertNull(spiderWeb.getLastNode());
        assertNull(spiderWeb.getLevelPointer());
    }

    /**
     * Test case for removing the first element from a non-empty SpiderWeb.
     */
    @Test
    void testRemoveFirstFromNonEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("Element1");
        spiderWeb.add("Element2");

        String removedElement = spiderWeb.removeFirst();

        assertEquals("Element1", removedElement);
        assertEquals("Element2", spiderWeb.getFirst());
        assertEquals(1, spiderWeb.size());
    }

    /**
     * Test case for removing the first element from a SpiderWeb with a single element.
     */
    @Test
    void testRemoveFirstFromSingleElementSpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("SingleElement");

        String removedElement = spiderWeb.removeFirst();

        assertEquals("SingleElement", removedElement);
        assertNull(spiderWeb.getFirstNode());
        assertNull(spiderWeb.getLastNode());
        assertNull(spiderWeb.getLevelPointer());
    }

    /**
     * Test case for removing the first two elements from a SpiderWeb.
     */
    @Test
    void testRemoveFirstTwiceFromSpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("Element1");
        spiderWeb.add("Element2");

        String removedElement1 = spiderWeb.removeFirst();
        String removedElement2 = spiderWeb.removeFirst();

        assertEquals("Element1", removedElement1);
        assertEquals("Element2", removedElement2);
        assertNull(spiderWeb.getFirstNode());
        assertNull(spiderWeb.getLastNode());
        assertNull(spiderWeb.getLevelPointer());
    }

    /**
     * Test case removing the first element from a SpiderWeb with next-level nodes.
     */
    @Test
    void testRemoveFirstFromSpiderWebWithNextLevel() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>(2);
        spiderWeb.add("Element1");
        spiderWeb.add("Element2");
        spiderWeb.add("Element3");

        String removedElement = spiderWeb.removeFirst();

        assertEquals("Element1", removedElement);
        assertEquals("Element2", spiderWeb.getFirst());
        assertEquals(2, spiderWeb.size());
        assertNull(spiderWeb.getFirstNode().getPrevNode());
        assertNull(spiderWeb.getLastNode().getPrevLevelNode());
    }

    /**
     * Test case for removing the last element from an empty SpiderWeb.
     */
    @Test
    public void testRemoveLastFromEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();

        assertThrows(NoSuchElementException.class, spiderWeb::removeLast);
        assertEquals(0, spiderWeb.size());
    }

    /**
     * Test case for removing the last element from a non-empty SpiderWeb.
     */
    @Test
    public void testRemoveLastFromNonEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);
        spiderWeb.add(123);

        Integer removedValue = spiderWeb.removeLast();

        assertEquals(123, removedValue);
        assertEquals(1, spiderWeb.size());
        assertEquals(42, spiderWeb.getLastNode().getValue());
    }

    /**
     * Test case for removing the last element from a SpiderWeb with a single element.
     */
    @Test
    public void testRemoveLastFromSingleElementSpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("apple");

        String removedValue = spiderWeb.removeLast();
        assertEquals("apple", removedValue);
        assertEquals(0, spiderWeb.size());
    }

    /**
     * Test case for clearing an empty SpiderWeb.
     */
    @Test
    public void testClearOnEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.clear();

        assertEquals(0, spiderWeb.size());
        assertNull(spiderWeb.getFirstNode());
        assertNull(spiderWeb.getLastNode());
        assertNull(spiderWeb.getLevelPointer());
        assertEquals(-1, spiderWeb.getIndex());
        assertEquals(-1, spiderWeb.getLevel());
    }

    /**
     * Test case for clearing a non-empty SpiderWeb.
     */
    @Test
    public void testClearOnNonEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(1);
        spiderWeb.add(2);
        spiderWeb.add(3);

        spiderWeb.clear();

        assertEquals(0, spiderWeb.size());
        assertNull(spiderWeb.getFirstNode());
        assertNull(spiderWeb.getLastNode());
        assertNull(spiderWeb.getLevelPointer());
        assertEquals(-1, spiderWeb.getIndex());
        assertEquals(-1, spiderWeb.getLevel());
    }


    /**
     * Test case for cloning an empty SpiderWeb.
     */
    @Test
    public void testCloneEmptySpiderWeb() {
        SpiderWeb<Integer> originalSpiderWeb = new SpiderWeb<>();

        SpiderWeb<Integer> clonedSpiderWeb = (SpiderWeb<Integer>) originalSpiderWeb.clone();

        assertNotSame(originalSpiderWeb, clonedSpiderWeb);
        assertEquals(originalSpiderWeb.toString(), clonedSpiderWeb.toString());
    }

    /**
     * Test case for cloning a SpiderWeb with elements.
     */
    @Test
    public void testCloneNonEmptySpiderWeb() {
        SpiderWeb<String> originalSpiderWeb = new SpiderWeb<>();
        originalSpiderWeb.add("A");
        originalSpiderWeb.add("B");
        originalSpiderWeb.add("C");

        SpiderWeb<String> clonedSpiderWeb = (SpiderWeb<String>) originalSpiderWeb.clone();

        assertNotSame(originalSpiderWeb, clonedSpiderWeb);

        assertEquals(originalSpiderWeb.toString(), clonedSpiderWeb.toString());

        originalSpiderWeb.add("D");
        assertNotEquals(originalSpiderWeb.toString(), clonedSpiderWeb.toString());
    }

    /**
     * Test case for the toString method when the SpiderWeb is empty.
     */
    @Test
    public void testToStringEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();

        String result = spiderWeb.toString();
        assertEquals("SpiderWeb{level=-1, index=-1, size=0, maxElementPerLevel=6}", result);
    }

    /**
     * Test case for the toString method when the SpiderWeb has elements.
     */
    @Test
    public void testToStringNonEmptySpiderWeb() {
        SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();
        spiderWeb.add(42);
        spiderWeb.add(123);

        String result = spiderWeb.toString();
        assertEquals("SpiderWeb{level=0, index=1, size=2, maxElementPerLevel=6}", result);
    }

    /**
     * Test case for the toString method when the SpiderWeb has a custom maxElementPerLevel value.
     */
    @Test
    public void testToStringWithCustomMaxElementPerLevel() {
        SpiderWeb<Character> spiderWeb = new SpiderWeb<>(2);
        spiderWeb.add('A');
        spiderWeb.add('B');
        spiderWeb.add('C');

        String result = spiderWeb.toString();
        assertEquals("SpiderWeb{level=1, index=0, size=3, maxElementPerLevel=2}", result);
    }
}
