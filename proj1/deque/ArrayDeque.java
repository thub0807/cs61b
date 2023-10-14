package deque;

public class ArrayDeque<T> implements Deque<T> {

    public T[] items = (T[]) new Object[8];
    private int size;
    private int fpointer;
    private int lpointer;

    public ArrayDeque(){
        size =0;
        fpointer =3;
        lpointer = 4;
    }
    public ArrayDeque(T item){
        items[3] = item;
        size++;
        fpointer =2;
        lpointer =4;
    }
    @Override
    public void addFirst(T item) {
        items[fpointer] = item;
        size++;
        fpointer--;
        if (fpointer==-1){
            resize(size*2);
        }
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int first = Math.abs(capacity-size)/2;
        System.arraycopy(items,fpointer+1,newItems,first,size);
        items= newItems;
        fpointer = first-1;
        lpointer =first+size;
    }

    @Override
    public void addLast(T item) {
        items[lpointer] = item;
        size++;
        lpointer++;
        if (lpointer== items.length){
            resize(size*2);
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int len = size();
        int start = fpointer+1;
        while(len>0){
            if (len==1){
                System.out.print(items[start]);
            }
            System.out.print(items[start]+"->");
            start = (start+1)%len;
            len--;
        }

    }

    @Override
    public T removeFirst() {
        if (isEmpty()){
            return null;
        }
        fpointer++;
        T item = items[fpointer];
        items[fpointer] = null;
        size--;
        shrinkSize();
        return item;
    }



    @Override
    public T removeLast() {
        if (isEmpty()){
            return null;
        }
        lpointer--;
        T item = items[lpointer];
        items[lpointer] = null;
        size--;
        shrinkSize();
        return item;
    }

    private void shrinkSize() {
        if (isEmpty()){
            resize(8);
        }else if (items.length/4>size&&size>4){
            resize(size*2);
        }

    }
    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        int i = fpointer+index+1;
        return items[i];
    }
}
