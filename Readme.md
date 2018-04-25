## Synopsis

Find a path through a non-weighted, directional graph.

This development problem will deal with finding a path from one node to another in a non-weighted, directional graph.

The application takes a single file name, and two node ID strings as parameters (<filename> <node1> <node2>). The file consists of
graph edge data, one edge per line. A line that contains "AA DD" describes an edge that connects node AA to node DD,
in that direction only.

It will calculate all possible paths, then choose the shortest of those. If there are multiple shortest paths (ties),
the first shortest path found wins.

## Quickstart

    git clone https://github.com/zetlan/pathfinder.git ; cd pathfinder
    ./gradlew build jar
    java -jar build/libs/pathfinder-1.0.jar nodelist.txt AA TT

Your node list file should consist of lines like:

    AA BB
    BB CC
    CC AA

and so on; each line represents an "edge" connecting a two nodes, in order (order matters).  In the above node list, to
get from AA to CC, the path is AA -> BB -> CC. From CC to AA, the path is CC -> AA.

## Tests

Ordinarily I'd write the tests concurrently with the code, or before the code. But I slapped this together quickly, and
in something of a rush, after a long workday.

## License

This really doesn't need a license. Enjoy it? Use it. I'd love some credit.