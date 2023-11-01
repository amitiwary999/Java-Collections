package LinkedList;

public class AtLinkedList<E> {
    private Node firstElement;
    private Node lastElement;

    public AtLinkedList() {
    }

    public void add(E e){
        Node currItem = new Node(e, null);
        if(firstElement == null){
            firstElement = currItem;
        }
        if(lastElement != null) {
            lastElement.nextElement = currItem;
        }
        lastElement = currItem;
    }

    public void printElements(){
        Node temp = firstElement;
        while(temp != null){
            System.out.println(temp.element);
            temp = temp.nextElement;
        }
    }

    private static class Node<E> {
        E element;
        Node nextElement;

        public Node(E element, Node nextElement) {
            this.element = element;
            this.nextElement = nextElement;
        }
    }
}
