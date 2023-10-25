package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {

    private Node root;
    private HashSet<K> Set = new HashSet<>();

    private class Node{
        private K key;
        private V value;
        private Node left,right;
        private int size;
        public Node(K k,V v,int s){
            this.key =k;
            this.value =v;
            this.size =s;
        }
    }
    public BSTMap(){

    }


    @Override
    public void clear() {
        for ( K set:Set){
            root=remove(root, set);
            //Set.remove(set);
        }
        Set.clear();

    }

    @Override
    public boolean containsKey(K key) {
        if (containsKey(root,key)!=null){
            return true;
        }else {
            return false;
        }
    }
    private K containsKey(Node r,K key){
        if (r ==null) return null;
        if (key==null) throw new IllegalArgumentException("key cant null");
        int cmp = key.compareTo(r.key);
        if (cmp>0) return containsKey(r.right,key);
        else if (cmp<0) return containsKey(r.left,key);
        else return r.key;
    }

    @Override
    public V get(K key) {
        return get(root,key);
    }
    private V get(Node r,K key){
        if (r ==null) return null;
        if (key==null) throw new IllegalArgumentException("key cant null");
        int cmp = key.compareTo(r.key);
        if (cmp>0) return get(r.right,key);
        else if (cmp<0) return get(r.left,key);
        else return r.value;
    }

    @Override
    public int size() {
        return size(root);
    }
    private int size(Node r){
        if (r==null) return 0;
        return r.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, value);
        Set.add(key);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, value);
        else if (cmp > 0) x.right = put(x.right, key, value);
        else              x.value = value;
        // also use recursion to compute current size of x
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    @Override
    public Set<K> keySet() {
        return Set;
    }

    public V remove(K key){
        if (key == null) throw new IllegalArgumentException("calls remove() with a null key");
        V removeValue = get(key);
        root = remove(root, key);
        Set.remove(key);
        return removeValue;
    }

    private Node remove(Node x, K key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        // recursive to x.left or x.right to remove and build new x.left or x.right
        if      (cmp < 0) x.left = remove(x.left,  key);
        else if (cmp > 0) x.right = remove(x.right, key);
        else {
            if (x.left == null)   return x.right;
            if (x.right == null)  return x.left;
            // find the smallest Node in right and remove it
            // then, using the smallest Node to replace x and link x.left
            Node temp = x;// 保存根节点
            x = minNode(temp.right);//寻找最小节点作为根节点
            x.right = removeMin(temp.right);//
            x.left = temp.left;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node minNode(Node x) {
        if (x.left == null) return x;
        else  return minNode(x.left);
    }

    // find the smallest Node, then, using right of the smallest Node to replace itself
    private Node removeMin(Node x){
        if (x.left == null) return x.right;
        x.left = removeMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value){
        if (key == null) throw new IllegalArgumentException("calls remove() with a null key");
        root = remove(root, key);
        Set.remove(key);
        return value;
    }


    @Override
    public Iterator<K> iterator() {
        return new iteratorOfK(root);
    }

    private class iteratorOfK implements Iterator<K>{
        private Node node;

        public iteratorOfK(Node x){
            this.node = x;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public K next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            } else {
                K minKey = minNode(node).key;
                // we need use remove() in BSTMap
                node = BSTMap.this.remove(node, minKey);
                return minKey;
            }
        }
    }

    public void printInOrder(){
        printInOrderByMidTravel(root);
    }

    private void printInOrderByMidTravel(Node x){
        if (x == null) return;
        printInOrderByMidTravel(x.left);
        System.out.println("key: " + x.key + " value: " + x.value);
        printInOrderByMidTravel(x.right);
    }
}
