import com.spiderweb.SpiderWeb;
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
     * Test case for adding an element to an empty SpiderWeb.
     */
    @Test
    public void testAddToEmptySpiderWeb() {
        SpiderWeb<String> spiderWeb = new SpiderWeb<>();
        spiderWeb.add("A");

        assertNotNull(spiderWeb.getFirst());
        assertNotNull(spiderWeb.getLast());
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
        assertEquals('C', spiderWeb.getFirst().getNextLevelNode().getValue());
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
        assertEquals(42, spiderWeb.getLast().getValue());
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
