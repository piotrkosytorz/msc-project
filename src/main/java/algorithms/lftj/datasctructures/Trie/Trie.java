package algorithms.lftj.datasctructures.Trie;

import java.util.ArrayList;
import java.util.List;

public abstract class Trie<T> {

    public class Node {
        private T key;
        private List<Node> children;
        private Node parent;

        public Node(T value) {
            children = new ArrayList<>();
            parent = null;
            this.key = value;
        }

        public T getKey() {
            return key;
        }

        public boolean hasChildren() {
            return !children.isEmpty();
        }

        public List<Node> getChildren() {
            return children;
        }

        public Node getFirstChild() throws Exception {
            if (!this.hasChildren()) {
                throw new Exception("Cannot open a leaf;");
            }
            return children.get(0);
        }

        public int getNumberOfChildren() {
            return children.size();
        }

        public boolean hasParent() {
            return parent != null;
        }

        public Node getParent() {
            return parent;
        }

        public Node findChildByValue(T searchValue) {
            for (Node child : children) {
                if (child.key.equals(searchValue))
                    return child;
            }
            return null;
        }

        public Node getNextSibling(int i) {
            return getParent().getChildByIndex(i + 1);
        }

        public Node getChildByIndex(int i) {
            return children.get(i);
        }

        public Node getLastChild() {
            return children.get(children.size() - 1);
        }

        public void print() {
            print("", true);
        }

        private void print(String prefix, boolean isTail) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + key);
            for (int i = 0; i < children.size() - 1; i++) {
                children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
            }
            if (children.size() > 0) {
                children.get(children.size() - 1)
                        .print(prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    protected final Node root;

    protected Trie() {
        this.root = new Node(null);
    }

    /**
     * Inserts a new relation to the trie.
     *
     * @param tuplesArray
     */
    protected void insert(T[] tuplesArray) {
        Node current = root;
        for (T element : tuplesArray) {
            Node node = current.findChildByValue(element);
            if (node == null) {
                node = new Node(element);
                node.parent = current;
                current.children.add(node);
            }
            current = node;
        }
    }

    /**
     * Checks whether relation is already in the trie.
     *
     * @param relation
     * @return
     */
    public boolean search(T[] relation) {
        Node current = root;
        for (T element : relation) {
            Node node = current.findChildByValue(element);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.children.isEmpty();
    }

    public Node getRoot() {
        return root;
    }

    public void print() {
        root.print();
    }

}

