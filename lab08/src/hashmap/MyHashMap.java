package hashmap;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    // 桶的容量
    private int initialCapacity;
    // 负载因子，用来判断上面时候该进行大小调整
    private double loadFactor;
    // item的数量
    private int size;

    /** Constructors */
    public MyHashMap() {
        initialCapacity = 16;
        loadFactor = 0.75;
        size = 0;
        buckets = createBuckets(initialCapacity);
    }

    public MyHashMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        loadFactor = 0.75;
        size = 0;
        buckets = createBuckets(initialCapacity);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        size = 0;
        buckets = createBuckets(initialCapacity);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    private Collection<Node>[] createBuckets(int size) {
        Collection[] ArrayBuckets = new Collection[size];
        for (int i = 0; i < size; i++) {
            ArrayBuckets[i] = createBucket();
        }
        return (Collection<Node>[]) ArrayBuckets;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    private Node getNode(K key){
        // 获取key在哪一个桶
        int indexBucket = Math.floorMod(key.hashCode(), buckets.length);

        // 遍历该桶所有的Node
        for(Node node : buckets[indexBucket]) {
            if(node.key.equals(key)) {
                return node;
            }
        }

        return null;
    }


    @Override
    public V get(K key) {
        Node node = getNode(key);
        if(node == null) {
            return null;
        }
        else {
            return node.value;
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<Node>[] resizeBuckets(int newSize){
        // 创建一个新的buckets，并初始化
        Collection[] ArrayBuckets = new Collection[newSize];
        for (int i = 0; i < newSize; i++) {
            ArrayBuckets[i] = createBucket();
        }

        // 将原buckets的Node重新分配到新的buckets
        for(int i = 0; i < initialCapacity; i++) {
            for(Node node : buckets[i]) {
                int indexBucket = Math.floorMod(node.key.hashCode(), ArrayBuckets.length);
                ArrayBuckets[indexBucket].add(node);
            }
        }
        initialCapacity = newSize;
        return (Collection<Node>[])ArrayBuckets;
    }

    @Override
    public void put(K key, V value) {

        // 判断是否要resize
        if( (double) size / initialCapacity >= loadFactor){
            buckets = resizeBuckets(2*initialCapacity);
        }

        Node node = getNode(key);
        if(node != null) {
            node.value = value;
        }
        else {
            int indexBucket = Math.floorMod(key.hashCode(), buckets.length);
            buckets[indexBucket].add(new Node(key, value));
            size++;
        }
    }

    @Override
    public boolean containsKey(K key) {
        Node node = getNode(key);
        if(node == null) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for(int i = 0; i < initialCapacity; i++) {
            buckets[i].clear();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for(Collection<Node> bucket : buckets) {
            for(Node node : bucket) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        int indexBucket = Math.floorMod(key.hashCode(), buckets.length);
        for(Node node : buckets[indexBucket]) {
            if(node.key.equals(key)) {
                V value = node.value;
                buckets[indexBucket].remove(node);
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        //返回一个Iterator对象
        return new Iterator<K>() {
            // 分别记录遍历到的bucket和node的下标
            int indexBucket = 0;
            int indexNode = 0;
            @Override
            public boolean hasNext() {
                // 遍历所有的bucket
                while(indexBucket < initialCapacity) {
                    // 如果indexNode小于LinkedList的size，则说明该桶还有Node，返回true
                    if(indexNode < buckets[indexBucket].size()) {
                        return true;
                    }
                    // 否则indexBucket++，将indexNode重置为0
                    else{
                        indexBucket++;
                        indexNode = 0;
                    }
                }
                // 当所有的bucket都遍历完
                return false;
            }

            @Override
            public K next() {
                Collection<Node> bucket = buckets[indexBucket];
                indexBucket++;
                Object[] list = bucket.toArray();
                Node node = (Node)list[indexNode];
                return node.key;

            }
        };
    }
}
