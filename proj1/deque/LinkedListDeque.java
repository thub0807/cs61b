package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>,Iterable<T>{
    private Node head ;
    private int size;

    public LinkedListDeque(){
        // 无参构造 构造单sentinel 和 circular
        head = new Node(null,null,null);
        head.next = head;
        head.pre = head;
        size =0;
    }
    public LinkedListDeque(LinkedListDeque<T> other){
        // 有参构造 将other遍历添加
        head = new Node(null,null,null);
        head.next = head;
        head.pre = head;
        size =0;
        for (int i=0;i<other.size();i++){
            addLast(get(i));
        }
    }

    // 使用recursive 实现获得第i个
    public T getRecursive(int index){
        if (index<0 && index>size-1){
            return null;
        }
        return getRecursiveHelp(index,head.next);
    }
    public T getRecursiveHelp(int index,Node head){
        // recursive 终止条件
        if (index ==0){
            return head.item;
        }
        return getRecursiveHelp(index-1,head.next);
    }



    private  class Node{
        public T item;
        public Node pre;
        public Node next;

        Node(T i,Node p,Node n){
                item =i;
                pre=p;
                next=n;
        }
        @Override
        public String toString(){
            if (item ==null){
                return "null";
            }
            return item.toString();
        }

    }

    public void addFirst(T item) {
        // new 一个 pre：为head，next：head.next；
        Node firstNode = new Node(item,head,head.next);
        head.next.pre = firstNode;
        head.next =firstNode;
        size++;
    }
    public void addLast(T item) {
        Node lastNode = new Node(item,head.pre,head);
        head.pre.next = lastNode;
        head.pre = lastNode;
        size++;
    }
    public boolean isEmpty(){
        return size==0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (Node i = head.next;i!=head;i=i.next){
            if (i.next==head){
                System.out.print(i.item);
                break;
            }
            System.out.print(i.item+" ");
        }

    }

    public T removeFirst() {
        if (this.isEmpty()){
            return null;
        }
        T remove = head.next.item;
        head.next = head.next.next;
        head.next.pre = head;
        size--;
        return remove;
    }

    public T removeLast() {
        if (isEmpty()){
            return null;
        }
        T remove = head.pre.item;
        head.pre = head.pre.pre;
        head.pre.next =head;
        size--;
        return remove;
    }

    public T get(int index) {
        if (index>size){
            return null;
        }
        Node node = head.next;
        for (int i=0;i<index;i++){
            node = node.next;
        }
        return node.item;
    }
    @Override
    public Iterator<T> iterator() {

        return new LinkedListDequeIterator();
    }
    private class LinkedListDequeIterator implements Iterator<T>{
        private int len;
        public LinkedListDequeIterator() {
            len =0;
        }
        @Override
        public boolean hasNext() {
            return len<size;
        }

        @Override
        public T next() {
            T item = get(len);
            len++;
            return item;
        }
    }
    public boolean equals(Object o){
        if (this ==o){
            return true;
        }
        if (o==null){
            return false;
        }
        if (!(o instanceof Deque)){
            return false;
        }
        Deque<T> d =(Deque<T>) o;
        if (d.size()!=this.size()){
            return false;
        }
        for (int i =0;i<d.size();i++){
            if (!d.get(i).equals(this.get(i))){
                return false;
            }
        }
        return true;
    }
}
