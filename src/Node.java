/**
 * Program Name: Klotski.java
 * Discussion:   generic Node class used for solving the puzzle
 * Written By:   Zhiying Li
 * Date:         2016/12/12
 */

import java.util.ArrayList;

class Node<E> { // Use tree data structure
    private E data;
    private Node<E> parent;
    private ArrayList<Node<E>> children;

    public Node() {
        this.children = new ArrayList<>();
    }

    public Node(E data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public Node(E data, Node<E> parent) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public E getData() {
        return data;
    }

    public Node<E> getParent() {
        return parent;
    }

    public Node<E> addChild(E childData) {
        Node<E> childNode = new Node(childData, this);
        children.add(childNode);
        return childNode;
    }

    public void removeChild(int value) {
        if (value < children.size())
            children.remove(value);
    }

    public ArrayList<Node<E>> getChildren() {
        return children;
    }

    public boolean isRoot() {
        if (getParent() != null)
            return false;
        else
            return true;
    }

    public boolean isLeaf() {
        if (!getChildren().isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void printAllChildren() { // only for test
        if (!getChildren().isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                System.out.println(
                    children.get(i).data + " " + children.get(i));
                children.get(i).printAllChildren();
            }
        }
        else
            System.out.println("END");
    }
}
