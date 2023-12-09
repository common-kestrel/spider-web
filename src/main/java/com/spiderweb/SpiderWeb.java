package com.spiderweb;

import java.util.HashMap;
import java.util.NoSuchElementException;


/**
 * SpiderWeb is a custom data structure designed to organize elements in a hierarchical
 * and indexed manner. It is implemented as a linked structure with nodes organized in
 * levels and indices. The class provides methods for adding, querying, and removing
 * elements based on their levels and indices within the SpiderWeb.
 *
 * <p>Usage Example:
 * <blockquote><pre>
 * SpiderWeb&lt;Integer&gt; spiderWeb = new SpiderWeb&lt;&gt;();
 * spiderWeb.add(1);
 * spiderWeb.add(2);
 * spiderWeb.add(3);
 * spiderWeb.print();
 * </pre></blockquote>
 *
 * @param <E> the type of elements stored in the SpiderWeb
 *
 * @author Milan Savic
 * @version 1.0
 * @since November 24, 2023
 */
public class SpiderWeb<E> implements Cloneable{
    // Private fields for managing the spider web structure

    private SpiderWebNode<E> first;
    private SpiderWebNode<E> prevLevel;
    private SpiderWebNode<E> last;
    private int level;
    private int index;
    private int tmpLevel;
    private int tmpIndex;
    private int size;
    private final int maxElementPerLevel;

    /**
     * Constructs a SpiderWeb with a default maximum number of elements per level (6).
     */
    public SpiderWeb() {
        this.maxElementPerLevel = 6;
    }

    /**
     * Constructs a SpiderWeb with a specified maximum number of elements per level.
     *
     * @param maxElementPerLevel The maximum number of elements allowed in each level.
     */
    public SpiderWeb(int maxElementPerLevel) {
        this.maxElementPerLevel = maxElementPerLevel;
    }

    // Getter methods for accessing SpiderWeb properties

    /**
     * Gets the first node in the SpiderWeb.
     *
     * @return The first node in the SpiderWeb.
     */
    public SpiderWebNode<E> getFirstNode() {
        return first;
    }

    /**
     * Gets the previous level pointer in the SpiderWeb.
     *
     * @return The previous level pointer in the SpiderWeb.
     */
    public SpiderWebNode<E> getPrevLevel() {
        return prevLevel;
    }

    /**
     * Gets the last node in the SpiderWeb.
     *
     * @return The last node in the SpiderWeb.
     */
    public SpiderWebNode<E> getLastNode() {
        return last;
    }

    /**
     * Gets the last level of the SpiderWeb.
     *
     * @return The last level of the SpiderWeb.
     */
    public int getLevel() {
        if (this.first == null) {
            return -1;
        }
        if (this.index == 0) {
            return level - 1;
        }
        return level;
    }

    /**
     * Gets the last index of the SpiderWeb.
     *
     * @return The last index of the SpiderWeb.
     */
    public int getIndex() {
        if (this.first == null) {
            return -1;
        }
        if (index == 0) {
            return this.maxElementPerLevel - 1;
        }
        return index - 1;
    }

    // Private helper methods for managing temporary variables

    private void resetSpiderWeb() {
        this.resetPointers();
        this.level = 0;
        this.index = 0;
        this.size = 0;
    }

    private void resetPointers() {
        this.first = null;
        this.last = null;
        this.prevLevel = null;
    }

    private void resetTmpVariables() {
        this.tmpLevel = 0;
        this.tmpIndex = 0;
    }
    private void nextIndex() {
        this.tmpIndex++;
        if (this.tmpIndex == this.maxElementPerLevel) {
            this.tmpLevel++;
            this.tmpIndex = 0;
        }
    }

    private void resetTmpVariablesLast() {
        this.tmpIndex = this.index - 1;
        if(this.tmpIndex < 0) {
            this.tmpIndex = this.maxElementPerLevel - 1;
            this.tmpLevel = this.level - 1;
        }else{
            this.tmpLevel = this.level;
        }
    }
    private void prevIndex() {
        this.tmpIndex--;
        if(this.tmpIndex == -1) {
            this.tmpLevel--;
            this.tmpIndex = this.maxElementPerLevel - 1;
        }
    }

    private void incrementIndex() {
        this.index++;
        if (this.index == (this.maxElementPerLevel)){
            if(this.level == 0) {
                this.prevLevel = this.first;
            }
            this.level++;
            this.index = 0;
        }
    }

    private void decrementIndex() {
        if (this.index > 0) {
            this.index--;
        } else {
            this.index = maxElementPerLevel - 1;
            this.level--;
        }
    }

    private void incrementSize() {
        this.size++;
    }

    private void decrementSize() {
        this.size--;
    }

    private boolean isValidLevelAndIndex(int level, int index) {
        return (level >= 0 && level <= this.getLevel()) && (index >= 0 && index <= this.getMaximumIndexForLevel(level));
    }

    private void addFirstNode(SpiderWebNode<E> newNode) {
        if (this.first == null) {
            this.first = newNode;
            this.last = newNode;
        } else {
            this.first.setPrevNode(newNode);
            newNode.setNextNode(this.first);
            this.first = newNode;
            if (this.size >= this.maxElementPerLevel) {
                SpiderWebNode<E> tmpPointer = this.first;
                for (int i = 0; i < this.maxElementPerLevel; i++) {
                    tmpPointer = tmpPointer.getNextNode();
                }
                this.first.setNextLevelNode(tmpPointer);
                tmpPointer.setPrevLevelNode(this.first);
            }
        }

        this.incrementIndex();
        this.incrementSize();
    }

    private void addLastNode(SpiderWebNode<E> newNode) {
        if (this.first == null) {
            this.first = newNode;
            this.last = newNode;
        } else {
            this.last.setNextNode(newNode);
            this.last = newNode;
            if (this.prevLevel != null) {
                this.prevLevel.setNextLevelNode(newNode);
                this.prevLevel = this.prevLevel.getNextNode();
            }
        }

        this.incrementIndex();
        this.incrementSize();
    }

    @SuppressWarnings("unchecked")
    private SpiderWeb<E> superClone() {
        try {
            return (SpiderWeb<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    // Other public methods...

    /**
     * Gets the maximum index for a specified level in the SpiderWeb.
     *
     * @param level The level for which to retrieve the maximum index.
     * @return The maximum index for the specified level.
     * @throws IllegalArgumentException If the specified level is negative or exceeds the maximum level in the SpiderWeb.
     * @throws IllegalStateException If the SpiderWeb is empty, and the maximum index cannot be determined.
     */
    public int getMaximumIndexForLevel(int level) throws IllegalArgumentException, IllegalStateException {
        if (level < 0) {
            throw new IllegalArgumentException("Invalid level: Level cannot be negative.");
        }
        if (this.first == null) {
            throw new IllegalStateException("Cannot get maximum index for level on an empty SpiderWeb");
        }
        if (level > this.getLevel()){
            throw new IllegalArgumentException(String.format("Invalid level: %d exceeds the maximum level %d.", level, this.getLevel()));
        }
        if (level < this.getLevel()){
            return this.maxElementPerLevel - 1;
        }
        return this.getIndex();
    }

    /**
     * Returns the size of the SpiderWeb, indicating the total number of elements stored.
     *
     * @return The size of the SpiderWeb.
     */
    public int size() {
        return this.size;
    }

    /**
     * Prints the elements of the SpiderWeb along with their levels and indices.
     */
    public void print(){
        SpiderWebNode<E> current = this.first;
        this.resetTmpVariables();
        while (current != null){
            System.out.println("value: " + current.getValue() + ", level: " + this.tmpLevel + ", index: " + this.tmpIndex);
            current = current.getNextNode();
            this.nextIndex();
        }
    }

    /**
     * Returns the value of the first element in the SpiderWeb.
     *
     * @return The value of the first element.
     * @throws NoSuchElementException If the SpiderWeb is empty and there is no first element to return.
     */
    public E getFirst() {
        if (this.first == null) {
            throw new NoSuchElementException("SpiderWeb is empty, no first element available.");
        }
        return this.first.getValue();
    }

    /**
     * Returns the value of the last element in the SpiderWeb.
     *
     * @return The value of the last element.
     * @throws NoSuchElementException If the SpiderWeb is empty and there is no last element to return.
     */
    public E getLast() {
        if (this.last == null) {
            throw new NoSuchElementException("SpiderWeb is empty, no last element available.");
        }
        return this.last.getValue();
    }

    /**
     * Adds the specified element to the end of the SpiderWeb.
     *
     * @param value The value to be added to the end of the SpiderWeb.
     */
    public void add(E value) {
        final SpiderWebNode<E> newNode = new SpiderWebNode<>(value, this.last, this.prevLevel);
        this.addLastNode(newNode);
    }

    /**
     * Adds a new SpiderWebNode to the end of the SpiderWeb.
     *
     * @param newNode The SpiderWebNode to be added to the SpiderWeb.
     */
    public void add(SpiderWebNode<E> newNode) {
        newNode.resetPointers();
        newNode.setPrevNode(this.last);
        newNode.setPrevLevelNode(this.prevLevel);
        this.addLastNode(newNode);
    }

    /**
     *Adds the specified element to the beginning of the SpiderWeb.
     *
     * @param value The value to be added to the beginning of the SpiderWeb.
     */
    public void addFirst(E value) {
        final SpiderWebNode<E> newNode = new SpiderWebNode<>(value, null, null);
        this.addFirstNode(newNode);
    }

    /**
     * Adds a new SpiderWebNode to the beginning of the SpiderWeb.
     *
     * @param newNode The SpiderWebNode to be added to the SpiderWeb.
     */
    public void addFirst(SpiderWebNode<E> newNode) {
        newNode.resetPointers();
        this.addFirstNode(newNode);
    }

    /**
     *Adds the specified element to the end of the SpiderWeb.
     *
     * @param value The value to be added to the end of the SpiderWeb.
     */
    public void addLast(E value) {
        this.add(value);
    }

    /**
     * Adds a new SpiderWebNode to the end of the SpiderWeb.
     *
     * @param node The SpiderWebNode to be added to the SpiderWeb.
     */
    public void addLast(SpiderWebNode<E> node) {
        this.add(node);
    }

    /**
     * Searches for the specified element and returns its level and index in the SpiderWeb.
     *
     * @param e The element to search for in the SpiderWeb.
     * @return A HashMap containing the level and index of the specified element.
     */
    public HashMap<String, Integer> indexOf(E e){
        SpiderWebNode<E> current = this.first;
        HashMap<String, Integer> hashMap = new HashMap<>();
        this.resetTmpVariables();

        while (current != null){
            if (current.getValue().equals(e)){
                hashMap.put("level", this.tmpLevel);
                hashMap.put("index", this.tmpIndex);
                return hashMap;
            }
            this.nextIndex();
            current = current.getNextNode();
        }

        return hashMap;
    }

    /**
     * Searches for the last occurrence of the specified element and returns its level and index in the SpiderWeb.
     *
     * @param e The element to search for in the SpiderWeb.
     * @return A HashMap containing the level and index of the last occurrence of the specified element.
     */
    public HashMap<String, Integer> lastIndexOf(E e) {
        SpiderWebNode<E> current = this.last;
        HashMap<String, Integer> hashMap = new HashMap<>();
        this.resetTmpVariablesLast();

        while (current != null){
            if (current.getValue().equals(e)){
                hashMap.put("level", this.tmpLevel);
                hashMap.put("index", this.tmpIndex);
                return hashMap;
            }
            this.prevIndex();
            current = current.getPrevNode();
        }

        return hashMap;
    }

    /**
     * Searches for the specified object in the SpiderWeb and returns its level and index.
     *
     * @param o The object to search for in the SpiderWeb.
     * @return A HashMap containing the level and index of the specified object.
     *         If the object is not found, an empty HashMap is returned.
     */
    public HashMap<String, Integer> indexOf(SpiderWebNode<E> o) {
        SpiderWebNode<E> current = this.first;
        HashMap<String, Integer> hashMap = new HashMap<>();
        this.resetTmpVariables();

        while (current != null){
            if (current.equals(o)){
                hashMap.put("level", this.tmpLevel);
                hashMap.put("index", this.tmpIndex);
                return hashMap;
            }
            this.nextIndex();
            current = current.getNextNode();
        }

        return hashMap;
    }

    /**
     * Searches for the last occurrence of the specified object in the SpiderWeb and returns its level and index.
     *
     * @param o The object to search for in the SpiderWeb.
     * @return A HashMap containing the level and index of the last occurrence of the specified object.
     *         If the object is not found, an empty HashMap is returned.
     */
    public HashMap<String, Integer> lastIndexOf(SpiderWebNode<E> o) {
        SpiderWebNode<E> current = this.last;
        HashMap<String, Integer> hashMap = new HashMap<>();
        this.resetTmpVariablesLast();

        while (current != null){
            if (current.equals(o)){
                hashMap.put("level", this.tmpLevel);
                hashMap.put("index", this.tmpIndex);
                return hashMap;
            }
            this.prevIndex();
            current = current.getPrevNode();
        }

        return hashMap;
    }

    /**
     * Returns the element at the specified level and index in the SpiderWeb.
     *
     * @param level The level of the desired element (non-negative).
     * @param index The index of the desired element (non-negative).
     * @return The element at the specified level and index in the SpiderWeb.
     *
     * @throws IllegalArgumentException If the provided level or index is invalid.
     * @throws IllegalStateException If the operation fails to get the element, which should not occur under normal conditions.
     */
    public E get(int level, int index) {
        if (!this.isValidLevelAndIndex(level, index)) {
            throw new IllegalArgumentException("Invalid level or index. Level: " + level + ", Index: " + index);
        }
        this.resetTmpVariables();
        for (SpiderWebNode<E> node = this.first; node != null; node = node.getNextNode()) {
            if (this.tmpLevel == level && this.tmpIndex == index) {
                return node.getValue();
            }
            this.nextIndex();
        }
        throw new IllegalStateException("Failed to get element. Level: " + level + ", Index: " + index);
    }

    /**
     * Returns the SpiderWebNode at the specified level and index in the SpiderWeb.
     *
     * @param level The level of the desired SpiderWebNode (non-negative).
     * @param index The index of the desired SpiderWebNode (non-negative).
     * @return The SpiderWebNode at the specified level and index in the SpiderWeb.
     *
     * @throws IllegalArgumentException If the provided level or index is invalid.
     * @throws IllegalStateException If the operation fails to get the SpiderWebNode, which should not occur under normal conditions.
     */
    public SpiderWebNode<E> getNode(int level, int index) {
        if (!this.isValidLevelAndIndex(level, index)) {
            throw new IllegalArgumentException("Invalid level or index. Level: " + level + ", Index: " + index);
        }
        this.resetTmpVariables();
        for (SpiderWebNode<E> node = this.first; node != null; node = node.getNextNode()) {
            if (this.tmpLevel == level && this.tmpIndex == index) {
                return node;
            }
            this.nextIndex();
        }
        throw new IllegalStateException("Failed to get element. Level: " + level + ", Index: " + index);
    }

    /**
     * Sets the element at the specified level and index in the SpiderWeb, replacing any existing element.
     * Returns the previous value at the specified position.
     *
     * @param level   The level at which to set the element.
     * @param index   The index within the specified level to set the element.
     * @param element The new element to be set at the specified level and index.
     * @return The previous value at the specified level and index.
     * @throws IllegalArgumentException If the provided level or index is invalid.
     * @throws IllegalStateException    If the operation fails to set the element, which should not occur under normal conditions.
     */
    public E set(int level, int index, E element) {
        if (!this.isValidLevelAndIndex(level, index)) {
            throw new IllegalArgumentException("Invalid level or index. Level: " + level + ", Index: " + index);
        }
        this.resetTmpVariables();
        for (SpiderWebNode<E> node = this.first; node != null; node = node.getNextNode()) {
            if (this.tmpLevel == level && this.tmpIndex == index) {
                E oldValue = node.getValue();
                node.setValue(element);
                return oldValue;
            }
            this.nextIndex();
        }
        throw new IllegalStateException("Failed to set element. Level: " + level + ", Index: " + index);
    }

    /**
     * Removes and returns the first element from the SpiderWeb.
     *
     * @return The first element in the SpiderWeb.
     * @throws NoSuchElementException If the SpiderWeb is empty.
     */
    public E removeFirst() {
        if (this.first == null) {
            throw new NoSuchElementException("Cannot remove from an empty SpiderWeb.");
        }

        final SpiderWebNode<E> next = this.first.getNextNode();
        final SpiderWebNode<E> nextLevel = this.first.getNextLevelNode();
        final E firstValue = this.first.getValue();

        if (next != null) {
            next.setPrevNode(null);
            this.first.setNextNode(null);
            if (nextLevel != null) {
                nextLevel.setPrevLevelNode(null);
                this.first.setNextLevelNode(null);
            }
            this.first = next;
        } else {
            this.resetPointers();
        }

        this.decrementIndex();
        this.decrementSize();

        return firstValue;
    }

    /**
     * Removes and returns the last element from the SpiderWeb.
     *
     * @return The last element in the SpiderWeb.
     * @throws NoSuchElementException If the SpiderWeb is empty.
     */
    public E removeLast() {
        if(this.first == null) {
            throw new NoSuchElementException("Cannot remove from an empty SpiderWeb.");
        }

        final SpiderWebNode<E> prev = this.last.getPrevNode();
        final E lastValue = this.last.getValue();

        if (prev == null) {
            this.resetPointers();
        } else {
            this.last.setValue(null);
            prev.setNextNode(null);
            this.last = prev;
        }

        this.decrementIndex();
        this.decrementSize();

        return lastValue;
    }

    /**
     * Removes all elements from the SpiderWeb.
     * After calling this method, the SpiderWeb will have no elements.
     */
    public void clear() {
        for(SpiderWebNode<E> node = this.first; node != null; ) {
            SpiderWebNode<E> next = node.getNextNode();
            node.resetSpiderWebNode();
            node = next;
        }

        this.resetSpiderWeb();
    }


    /**
     * Returns a shallow copy of this SpiderWeb instance.
     *
     * @return A shallow copy of this SpiderWeb instance.
     */
    @Override
    public Object clone() {
        SpiderWeb<E> clone = superClone();
        clone.resetSpiderWeb();

        for (SpiderWebNode<E> x = first; x != null; x = x.getNextNode())
            clone.add(x.getValue());
        return clone;
    }

    /**
     * Returns a string representation of the SpiderWeb, including its current level, index, size, and maximum elements per level.
     *
     * @return A string representation of the SpiderWeb.
     */
    @Override
    public String toString() {
        return "SpiderWeb{" +
                "level=" + getLevel() +
                ", index=" + getIndex() +
                ", size=" + size +
                ", maxElementPerLevel=" + maxElementPerLevel +
                '}';
    }
}
