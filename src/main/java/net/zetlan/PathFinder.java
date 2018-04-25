package net.zetlan;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class PathFinder {

    private static Graph buildGraph(String filename) {
        Graph graph;

        File nodelist = new File(filename);
        BufferedReader nodelistReader = null;
        try {
            nodelistReader = new BufferedReader(new FileReader(nodelist));
            String line = null;
            graph = new Graph();
            while ((line = nodelistReader.readLine()) != null) {
                System.out.println(line);
                String[] nodeNames = line.split(" ");
                String fromNodeName = nodeNames[0];
                String toNodeName = nodeNames[1];
                graph.addEdge(fromNodeName, toNodeName);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't open file for reading", e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        } finally {
            try {
                if (nodelistReader != null) {
                    nodelistReader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Couldn't close file", e);
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println(String.format("%d args: %s", args.length, String.join(", ", args)));
        Graph graph = buildGraph(args[0]);

        System.out.println(
                String.format("Graph has %d nodes: %s",
                        graph.getNodeList().size(),
                        String.join(", ", graph.getNodeList().keySet())));

        List<Node> path = graph.getPath(args[1], args[2]);
        List<String> pathNames = path.stream().map(x -> x.getName()).collect(Collectors.toList());
        if (path == null || path.size() == 0) {
            System.out.println(String.format("No route from %s to %s", args[1], args[2]));
        } else {
            System.out.println(String.format("Route from %s to %s:", args[1], args[2]));

            System.out.println(String.format("    %s", String.join(" -> ", pathNames)));
        }
    }
}
