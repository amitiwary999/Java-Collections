package LinkedList;

public class AtLinkedList<E> {
    private Node firstElement;
    private Node lastElement;
    private int size;

    public AtLinkedList() {
    }

    private void indexRangeCheck(int index){
        if(index>=size){
            throw new Error("index out of bound");
        }
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
        size++;
    }

    public void add(int index, E e){
        indexRangeCheck(index);
        if(index == 0){
            Node curNode = new Node(e, firstElement);
            firstElement = curNode;
        } else if(index == size-1){
            Node curNode = new Node(e, null);
            lastElement.nextElement = curNode;
            lastElement = curNode;
        } else {
            Node temp = firstElement;
            Node prev = firstElement;
            int counter = index;
            while(counter>0){
                prev = temp;
                temp = temp.nextElement;
                counter--;
            }
            Node curNode = new Node(e, temp);
            prev.nextElement = curNode;
        }
        size++;
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
