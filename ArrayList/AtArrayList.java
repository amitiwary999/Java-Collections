package ArrayList;

import java.util.Arrays;

public class AtArrayList<T> {
    public static final Object[] DEFAULT_DATA = {};
    Object[] listData;
    public AtArrayList() {
        this.listData = DEFAULT_DATA;
    }

    public void indexRangeCheck(int index){
        if(index>=listData.length){
            throw new Error("index out of bound");
        }
    }

    public void addData(T element){
        int currentSize = listData.length;
        listData = Arrays.copyOf(listData, currentSize+1);
        listData[currentSize] = element;
    }

    public void addData(int index, T element){
        indexRangeCheck(index);
        Object[]  tempArraySplitFirst = Arrays.copyOfRange(listData, 0, index);
        Object[] tempArraySplitSecond = Arrays.copyOfRange(listData, index, listData.length);
        tempArraySplitFirst = Arrays.copyOf(tempArraySplitFirst, tempArraySplitFirst.length + 1);
        tempArraySplitFirst[index] = element;
        System.arraycopy(tempArraySplitFirst, 0, listData, 0, index+1);
        System.arraycopy(tempArraySplitSecond, 0, listData, index+1, tempArraySplitSecond.length);
    }

    public void set(int index, T element){
        indexRangeCheck(index);
        listData[index] = element;
    }

    @SuppressWarnings("unchecked")
    public T getData(int index){
        indexRangeCheck(index);
        return (T)listData[index];
    }
}
