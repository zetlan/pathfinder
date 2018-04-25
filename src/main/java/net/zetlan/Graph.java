package net.zetlan;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    private Map<String, Node> nodeList;

    public Graph() {
        this.nodeList = new HashMap<>();
    }

    public Graph addEdge(String fromNodeName, String toNodeName) {
        Node fromNode = this.nodeList.containsKey(fromNodeName) ? this.nodeList.get(fromNodeName) : new Node(fromNodeName);
        Node toNode = this.nodeList.containsKey(toNodeName) ? this.nodeList.get(toNodeName) : new Node(toNodeName);

        fromNode.addEdge(toNode);
        this.nodeList.putIfAbsent(fromNodeName, fromNode);
        this.nodeList.putIfAbsent(toNodeName, toNode);

        return this;
    }

    public List<Node> getPath(String fromNodeName, String toNodeName) {
        return getPath(fromNodeName, toNodeName, Arrays.asList(this.nodeList.get(fromNodeName)));
    }

    private List<Node> getPath(String fromNodeName, String toNodeName, List<Node> currentPath) {

        // Easy case: one of the nodes isn't in the graph
        if (!this.hasNode(fromNodeName) || !this.hasNode(toNodeName)) {
            throw new RuntimeException(String.format("Can't get path from %s to %s: node missing from graph", fromNodeName, toNodeName));
        }

        // Another easy case: from one node to itself
        Node startNode = this.getNodeList().get(fromNodeName);
        if (fromNodeName.equals(toNodeName)) {
            return Arrays.asList(startNode);
        }

        // The next candidates can only contain nodes we haven't visited yet; this prevents circular paths
        List<Node> candidateNodes = startNode.getNextNodes().entrySet()
                .stream()
                .filter(x -> !currentPath.contains(x.getValue()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        // Examine the next nodes. One of these must be true:
        // 1. There are no further nodes; give up and return an empty list.
        // 2. The target node is in the list of next nodes; add it to the path and return.
        // 3. The target node isn't in the list, but we have remaining nodes to search; return the first one that has a match (recurse)
        if (startNode.getNextNodes() == null) {
            // Give up and return null
            return Collections.emptyList();
        }
        if (startNode.getNextNodes().containsKey(toNodeName)) {
            // We found it! Add it and return.
            currentPath.add(startNode.getNextNodes().get(toNodeName));
            return currentPath;
        } else {
            List<List<Node>> candidatePaths = new ArrayList<>();

            for (Node candidate : candidateNodes) {
                List<Node> candidatePath = new ArrayList<>();
                for (Node node : currentPath) {
                    candidatePath.add(node);
                }
                candidatePath.add(candidate);
                candidatePaths.add(getPath(candidate.getName(), toNodeName, candidatePath));
            }

            List<List<Node>> validPaths = candidatePaths.stream()
                    .filter(x -> x != null && x.size() > 0)
                    .collect(Collectors.toList());

            if (validPaths == null || validPaths.size() == 0) {
                return Collections.emptyList();
            }

            // Now find the shortest path, and return it
            int maxSize = validPaths.get(0).size();
            List<Node> returnPath = validPaths.get(0);
            for (List<Node> path : validPaths) {
                if (path.size() < maxSize ) {
                    returnPath = path;
                    maxSize = path.size();
                }
            }
            return returnPath;
        }


    }

    public boolean hasNode(String nodeName) {
        return this.nodeList.containsKey(nodeName);
    }

    /* Getters and Setters */

    public Map<String, Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(Map<String, Node> nodeList) {
        this.nodeList = nodeList;
    }
}