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

    public void add(E e){
        if(elementLength == vectorData.length){
            increaseSize();
        }
        vectorData[elementLength] = e;
        elementLength = elementLength + 1;
    }
}
