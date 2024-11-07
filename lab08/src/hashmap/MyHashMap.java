package hashmap;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

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

    @Override
    public void put(K key, V value) {
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
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
