package ArrayList;

import java.util.Arrays;

public class AtArrayList<T> {
    public static final Object[] DEFAULT_DATA = {};
    Object[] listData;
    public AtArrayList() {
        this.listData = DEFAULT_DATA;
    }

    private void indexRangeCheck(int index){
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
        Object[] tempArraySplitSecond = Arrays.copyOfRange(listData, index, listData.length);
        listData = Arrays.copyOf(listData, listData.length + 1);
        listData[index] = element;
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

    public void delete(int index){
        indexRangeCheck(index);
        Object[] tempArraySplitSecond = Arrays.copyOfRange(listData, index+1, listData.length);
        System.arraycopy(tempArraySplitSecond, 0, listData, index, tempArraySplitSecond.length);
        listData = Arrays.copyOf(listData, listData.length - 1);
    }
}
