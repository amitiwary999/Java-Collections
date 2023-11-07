package LinkedList;

public class AtLinkedList<E> {
    private Node firstElement;
    private Node lastElement;
    private int size;

    public AtLinkedList() {
    }

    private void indexPositionCheck(int index){
        if(index>size){
            throw new IndexOutOfBoundsException("index out of bound size is " + size);
        }
    }

    private void elementPositionCheck(int index){
        if(index>=size){
            throw new IndexOutOfBoundsException("index out of bound, size is " + size);
        }
    }

    private void removeFirstItem(){
        Node temp = firstElement.nextElement;
        firstElement.nextElement = null;
        firstElement.element = null;
        firstElement = temp;
        if(temp == null) lastElement = null;
        size--;
    }

    private void removeItem(int index){
        Node prev = firstElement;
        Node cur = firstElement;
        int count = index;
        while(count>0){
            prev = cur;
            cur = cur.nextElement;
            count--;
        }
        prev.nextElement = cur.nextElement;
        cur.nextElement = null;
        cur.element = null;
        if(index == size-1){
            lastElement = prev;
        }
        size--;
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
        indexPositionCheck(index);
        if(index == 0){
            Node curNode = new Node(e, firstElement);
            firstElement = curNode;
        } else if(index == size){
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

    public void removeElement(int index){
        elementPositionCheck(index);
        if(index == 0) {
            removeFirstItem();
        } else {
            removeItem(index);
        }
    }

    public void setElement(E e, int index){
        elementPositionCheck(index);
        int count = index;
        Node temp = firstElement;
        while(count>0){
            temp = temp.nextElement;
            count--;
        }
        temp.element = e;
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
