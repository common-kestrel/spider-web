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
 * SpiderWeb&lt;Integer&gt; spiderWeb = new SpiderWeb<>();
 * spiderWeb.add(1);
 * spiderWeb.add(2);
 * spiderWeb.add(3);
 * System.out.println(spiderWeb.toString());
 * </pre></blockquote>
 *
 * @param <E> the type of elements stored in the SpiderWeb
 *
 * @author Milan Savic
 * @version 1.0
 * @since November 21, 2023
 */
public class SpiderWeb<E> {
    private SpiderWebNode<E> first;
    private SpiderWebNode<E> levelPointer;
    private SpiderWebNode<E> last;
    private int level;
    private int index;
    private int tmpLevel;
    private int tmpIndex;
    private int size;
    private final int maxElementPerLevel;

    public SpiderWeb() {
        this.maxElementPerLevel = 6;
    }

    public SpiderWeb(int maxElementPerLevel) {
        this.maxElementPerLevel = maxElementPerLevel;
    }

    public SpiderWebNode<E> getFirst() {
        return first;
    }

    public SpiderWebNode<E> getLevelPointer() {
        return levelPointer;
    }

    public SpiderWebNode<E> getLast() {
        return last;
    }

    public int getLevel() {
        if (this.first == null) {
            return -1;
        }
        if (this.index == 0) {
            return level - 1;
        }
        return level;
    }

    public int getIndex() {
        if (this.first == null) {
            return -1;
        }
        if (index == 0) {
            return this.maxElementPerLevel - 1;
        }
        return index - 1;
    }

    private void resetTmpVariables(){
        this.tmpLevel = 0;
        this.tmpIndex = 0;
    }
    private void nextIndex(){
        this.tmpIndex++;
        if (this.tmpIndex == this.maxElementPerLevel){
            this.tmpLevel++;
            this.tmpIndex = 0;
        }
    }

    private void resetTmpVariablesLast(){
        this.tmpIndex = this.index - 1;
        if(this.tmpIndex < 0){
            this.tmpIndex = this.maxElementPerLevel - 1;
            this.tmpLevel = this.level - 1;
        }else{
            this.tmpLevel = this.level;
        }
    }
    private void prevIndex(){
        this.tmpIndex--;
        if(this.tmpIndex == -1){
            this.tmpLevel--;
            this.tmpIndex = this.maxElementPerLevel - 1;
        }
    }

    public int size() {
        return this.size;
    }

    public void add(E value) {
        final SpiderWebNode<E> newNode = new SpiderWebNode<>(value, this.last, this.levelPointer);
        if (this.first == null){
            this.first = newNode;
            this.last = newNode;
        } else {
            this.last.setNextNode(newNode);
            this.last = newNode;
            if (this.levelPointer != null) {
                this.levelPointer.setNextLevelNode(newNode);
                this.levelPointer = this.levelPointer.getNextNode();
            }
        }
        this.index++;
        if (this.index == (this.maxElementPerLevel)){
            if(this.level == 0) {
                this.levelPointer = this.first;
            }
            this.level++;
            this.index = 0;
        }
        this.size++;
    }

    public int getMaximumIndexForLevel(int level) throws Exception {
        if (level < 0) {
            throw new IllegalArgumentException("Invalid level: Level cannot be negative.");
        }
        if (this.first == null) {
            throw new IllegalStateException("Cannot get maximum index for level on an empty spider web");
        }
        if (level > this.getLevel()){
            throw new IllegalArgumentException(String.format("Invalid level: %d exceeds the maximum level %d.", level, this.getLevel()));
        }
        if (level < this.getLevel()){
            return this.maxElementPerLevel - 1;
        }
        return this.getIndex();
    }

    public void print(){
        SpiderWebNode<E> current = this.first;
        this.resetTmpVariables();
        while (current != null){
            System.out.println("value: " + current.getValue() + ", level: " + this.tmpLevel + ", index: " + this.tmpIndex);
            current = current.getNextNode();
            this.nextIndex();
        }
    }

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
     * Removes and returns the last element from the SpiderWeb.
     *
     * @return The last element in the SpiderWeb.
     * @throws NoSuchElementException If the SpiderWeb is empty.
     */
    public E removeLast(){
        if(this.first == null) {
            throw new NoSuchElementException("Cannot remove from an empty SpiderWeb.");
        }

        final SpiderWebNode<E> prev = this.last.getPrevNode();
        final E lastValue = this.last.getValue();

        if (prev == null) {
            this.first = null;
            this.last = null;
        } else {
            this.last.setValue(null);
            prev.setNextNode(null);
            this.last = prev;
        }

        if (this.index > 0) {
            this.index--;
        } else {
            this.index = maxElementPerLevel - 1;
            this.level--;
        }
        this.size--;

        return lastValue;
    }

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
