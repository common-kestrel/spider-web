package com.spiderweb;

/**
 * The {@code SpiderWebNode} class represents a node in the SpiderWeb data structure, where each node
 * stores a value and maintains references to its next and previous nodes, as well
 * as references to nodes on the next and previous levels.
 *
 * <p>Example Usage:
 * <blockquote><pre>
 * SpiderWebNode&lt;Integer&gt; node = new SpiderWebNode&lt;&gt;(42, null, null);
 * </pre></blockquote>
 *
 * @param <E> the type of elements stored in the SpiderWebNode
 *
 * @author Milan Savic
 * @version 1.0
 * @since November 21, 2023
 */
public class SpiderWebNode<E> {

    /**
     * The value stored in the node.
     */
    private E value;

    /**
     * Reference to the next node.
     */
    private SpiderWebNode<E> nextNode;

    /**
     * Reference to the previous node.
     */
    private SpiderWebNode<E> prevNode;

    /**
     * Reference to the node on the next level.
     */
    private SpiderWebNode<E> nextLevelNode;

    /**
     * Reference to the node on the previous level.
     */
    private SpiderWebNode<E> prevLevelNode;

    /**
     * Constructs a SpiderWebNode with the specified value, previous node, and previous level node.
     *
     * @param value         The value to be stored in the node.
     * @param prevNode      Reference to the previous node.
     * @param prevLevelNode Reference to the node on the previous level.
     */
    public SpiderWebNode(E value, SpiderWebNode<E> prevNode, SpiderWebNode<E> prevLevelNode){
        this.value = value;
        this.prevNode = prevNode;
        this.prevLevelNode = prevLevelNode;
    }

    /**
     * Gets the value stored in the node.
     *
     * @return The value stored in the node.
     */
    public E getValue() {
        return value;
    }

    /**
     * Sets the value of the node.
     *
     * @param value The new value to be stored in the node.
     */
    public void setValue(E value) {
        this.value = value;
    }

    /**
     * Gets the reference to the next node.
     *
     * @return The next node.
     */
    public SpiderWebNode<E> getNextNode() {
        return nextNode;
    }

    /**
     * Sets the reference to the next node.
     *
     * @param nextNode The next node.
     */
    public void setNextNode(SpiderWebNode<E> nextNode) {
        this.nextNode = nextNode;
    }

    /**
     * Gets the reference to the previous node.
     *
     * @return The previous node.
     */
    public SpiderWebNode<E> getPrevNode() {
        return prevNode;
    }

    /**
     * Sets the reference to the previous node.
     *
     * @param prevNode The previous node.
     */
    public void setPrevNode(SpiderWebNode<E> prevNode) {
        this.prevNode = prevNode;
    }

    /**
     * Gets the reference to the node on the next level.
     *
     * @return The node on the next level.
     */
    public SpiderWebNode<E> getNextLevelNode() {
        return nextLevelNode;
    }

    /**
     * Sets the reference to the node on the next level.
     *
     * @param nextLevelNode The node on the next level.
     */
    public void setNextLevelNode(SpiderWebNode<E> nextLevelNode) {
        this.nextLevelNode = nextLevelNode;
    }

    /**
     * Gets the reference to the node on the previous level.
     *
     * @return The node on the previous level.
     */
    public SpiderWebNode<E> getPrevLevelNode() {
        return prevLevelNode;
    }

    /**
     * Sets the reference to the node on the previous level.
     *
     * @param prevLevelNode The node on the previous level.
     */
    public void setPrevLevelNode(SpiderWebNode<E> prevLevelNode) {
        this.prevLevelNode = prevLevelNode;
    }
}
