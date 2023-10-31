package Vector;

import java.util.Arrays;

public class AtVector<E> {
    /** TODO make it thread ssafe */
    Object[] vectorData;
    int elementLength = 0;
    int VECTOR_DEFAULT_GROW_SIZE = 10;

    public AtVector() {
        vectorData = new Object[VECTOR_DEFAULT_GROW_SIZE];
    }

    private void increaseSize(){
        int oldLength = vectorData.length;
        int incrCapacity = oldLength + VECTOR_DEFAULT_GROW_SIZE;
        vectorData = Arrays.copyOf(vectorData, incrCapacity);
    }

    public synchronized void add(E e){
        if(elementLength == vectorData.length){
            increaseSize();
        }
        vectorData[elementLength] = e;
        elementLength = elementLength + 1;
    }

    public synchronized void setElement(E e, int index){
        if(elementLength<=index){
            throw new Error("index is more then the length of vector");
        }
        vectorData[index] = e;
    }

    public synchronized int size(){
        return elementLength;
    }
}
