package net.zetlan;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private String name;

    private Map<String, Node> nextNodes = new HashMap<>();

    public Node(String name) {
        this.name = name;
    }

    public Node addEdge(Node newNode) {
        if (newNode == null) {
            throw new RuntimeException("Attempted to add a null node");
        }
        if (!nextNodes.containsKey(newNode.getName())) {
            nextNodes.putIfAbsent(newNode.getName(), newNode);
        }
        return this;
    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Node> getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(Map<String, Node> nextNodes) {
        this.nextNodes = nextNodes;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Node) || this.getName() == null) {
            return false;
        }
        return this.getName().equals(((Node) obj).getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
