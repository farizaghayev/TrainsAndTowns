package com.farizaghayev.app;

import com.farizaghayev.Properties;

import java.util.*;


public class Graph {

   private Map<Node, GraphNode> graph;

    public Graph() {
        graph = new HashMap<>();
    }


    public void addNode(Node node) {

        GraphNode graphNode = new GraphNode(node);

        if (!graph.containsKey(node)) {

            graph.put(node, graphNode);

        }
    }


    public boolean contains(Node node) {
        return graph.containsKey(node);
    }


    public void addEdge(Node start, Node dest, int weight) {

        validateInputNodes(start, dest);

        if (weight < 0) {
            throw new IllegalArgumentException("weight >= 0");
        }

        graph.get(start).getNeighbours().put(dest, weight);
    }


    public void removeEdge(Node start, Node dest) {
        validateInputNodes(start, dest);

        graph.get(start).getNeighbours().remove(dest);
    }


    public Set<Node> getNeighbours(Node node) {
        validateInputNodes(node);

        return graph.get(node).getNeighbours().keySet();
    }


    public boolean edgeExists(Node start, Node dest) {
        validateInputNodes(start, dest);

        return graph.get(start).getNeighbours().containsKey(dest);
    }


    private int weightForEdge(Node start, Node dest) {
        return graph.get(start).getNeighbours().get(dest);
    }


    public Set<Node> getNodes() {
        return graph.keySet();
    }



    private void dijkstra(Node start, Node dest) {
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();




        Set<Node> nodes = getNodes();
        for (Node node : nodes) {
            GraphNode graphNode = graph.get(node);
            graphNode.setDistance(Integer.MAX_VALUE);
            if (start.equals(node)) {
                graphNode.setDistance(0);
                graphNode.setPrevious(graphNode);
            }


            queue.add(graphNode);
        }


        while (!queue.isEmpty()) {
            GraphNode currentGraphNode = queue.poll();

            if (currentGraphNode.getData().equals(dest) && !currentGraphNode.equals(currentGraphNode.getPrevious())) {
                break;
            }

            for (Node neighbour : getNeighbours(currentGraphNode.getData())) {
                GraphNode neighbourGraphNode = graph.get(neighbour);

                final int alternateDist = currentGraphNode.getDistance() + weightForEdge(currentGraphNode.getData(), neighbour);
                if (alternateDist < neighbourGraphNode.getDistance() || neighbourGraphNode.equals(neighbourGraphNode.getPrevious())) {
                    queue.remove(neighbourGraphNode);
                    neighbourGraphNode.setDistance(alternateDist);
                    neighbourGraphNode.setPrevious(currentGraphNode);
                    queue.add(neighbourGraphNode);
                }
            }
        }
    }



    private int countPaths(Node start, DFSCondition stop, DFSCondition filter) {
        int total = 0;

        for (Node neighbour : getNeighbours(start)) {
            GraphPath path = new GraphPath();
            path.appendNode(start, 0);
            path.appendNode(neighbour, weightForEdge(start, neighbour));
            total += dfs(neighbour, stop, filter, path);
        }

        return total;
    }


    private int dfs(Node currentNode, DFSCondition stop, DFSCondition filter, GraphPath path) {
        int total = 0;

        if (filter.evaluate(path)) {

            total++;
        }

        for (Node neighbour : getNeighbours(currentNode)) {

            path.appendNode(neighbour, weightForEdge(currentNode, neighbour));

            if (stop.evaluate(path)) {
                path.removeLastNode(weightForEdge(currentNode, neighbour));
                continue;
            } else {
                total += dfs(neighbour, stop, filter, path);
            }

            path.removeLastNode(weightForEdge(currentNode, neighbour));
        }
        return total;
    }



    private void validateInputNodes(Node... nodes) {
        for (Node node : nodes) {
            if (!contains(node)) {
                throw new NoSuchElementException(Properties.getProperties().get("message.no_such_element"));
            }
        }
    }





    public GraphPath distanceShortestPath(Node start, Node dest) throws NodeNotReachableException {
        validateInputNodes(start, dest);

        dijkstra(start, dest);

        GraphNode destNode = graph.get(dest);
        GraphPath path = destNode.path(graph.get(start));

        if (path.getNodes().isEmpty()) {
            throw new NodeNotReachableException(Properties.getProperties().get("message.not_reachable"));
        } else {
            return path;
        }
    }


    public int distance(List<Node> nodes) throws NodeNotReachableException {
        Node [] nodeArrays={};
        nodeArrays=nodes.toArray(nodeArrays);
        validateInputNodes(nodeArrays);


        int distance = 0;
        for (int i = 0; i < nodes.size() - 1; i++) {
            Node start = nodes.get(i);
            Node dest = nodes.get(i + 1);

            if (edgeExists(start, dest)) {
                distance += weightForEdge(start, dest);
            } else {
                throw new NodeNotReachableException(Properties.getProperties().get("message.invalid_edge"));
            }

        }

        return distance;
    }


    public int countEdgesMaxStop(Node start, Node dest, int maxStops) {
        validateInputNodes(start, dest);

        return countPaths(start, (p) -> {
            return p.stopCount() > maxStops;
        }, (p) -> {
            return dest.equals(p.last());
        });
    }


    public int countEdgesWithStop(Node start, Node dest, int stops) {
        validateInputNodes(start, dest);

        return countPaths(start, (p) -> {
            return p.stopCount() > stops;
        }, (p) -> {
            return dest.equals(p.last()) && p.stopCount() == stops;
        });
    }


    public int countEdgesMaxDistance(Node start, Node dest, int distance) {

        validateInputNodes(start, dest);

        return countPaths(start, (p) -> {
            return p.distance() > distance;
        }, (p) -> {
            return dest.equals(p.last()) && p.distance() < distance;
        });
    }


    public static class NodeNotReachableException extends Exception {
        public NodeNotReachableException(String message) {
            super(message);
        }
    }


    private interface DFSCondition {
        boolean evaluate(Graph.GraphPath path);
    }


    private class GraphNode implements Comparable<GraphNode> {

        private Node data;
        private Integer distance;
        private GraphNode previous;

        protected Map<Node, Integer> neighbours;

        public GraphNode(Node data) {
            this.data = data;
            this.neighbours = new HashMap<Node, Integer>();
        }

        public Node getData() {
            return data;
        }

        public Map<Node, Integer> getNeighbours() {
            return neighbours;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public GraphNode getPrevious() {
            return previous;
        }

        public void setPrevious(GraphNode previous) {
            this.previous = previous;
        }

        public GraphPath path(GraphNode startNode) {
            List<Node> nodes = new LinkedList<Node>();
            int distance = 0;
            GraphNode currentNode = this;

            if (currentNode.getPrevious() == null) {
                return new GraphPath(nodes, distance);
            } else {
                nodes.add(currentNode.getData());

                while (!currentNode.equals(currentNode.getPrevious())) {
                    distance += weightForEdge(currentNode.getPrevious().getData(), currentNode.getData());

                    currentNode = currentNode.getPrevious();
                    nodes.add(0, currentNode.getData());

                    if (currentNode.equals(startNode)) {
                        break;
                    }
                }

                return new GraphPath(nodes, distance);
            }
        }

        public int compareTo(GraphNode other) {
            return distance.compareTo(other.getDistance());
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }


    public class GraphPath {

        private final String PATH_SEPARATOR = "->";
        private List<Node> nodes;
        private int distance;


        public GraphPath() {
            this.nodes = new ArrayList<Node>();
            this.distance = 0;
        }


        public GraphPath(List<Node> nodes, int length) {
            this.nodes = nodes;
            this.distance = length;
        }


        public boolean removeLastNode(int weight) {
            if (!this.nodes.isEmpty()) {
                distance -= weight;
                this.nodes.remove(this.nodes.size() - 1);
                return true;
            } else {
                return false;
            }
        }


        public boolean appendNode(Node node, int weight) {
            distance += weight;
            return this.nodes.add(node);
        }


        public List<Node> getNodes() {
            return nodes;
        }


        public Node last() {
            if (nodes.isEmpty()) {
                return null;
            } else {
                return nodes.get(nodes.size() - 1);
            }
        }


        public int distance() {
            return distance;
        }


        public int stopCount() {
            return Math.max(0, this.nodes.size() - 1);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodes.size() - 1; i++) {
                sb.append(nodes.get(i) + PATH_SEPARATOR);
            }
            sb.append(nodes.get(nodes.size() - 1));
            return sb.toString();
        }
    }
}
