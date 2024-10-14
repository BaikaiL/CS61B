public class UnionFind {
    // TODO: Instance variables
    int[] items;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        items = new int[N];
        for (int i = 0; i < N; i++) {
            items[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        int parent = parent(v);
        if(parent < 0){
            return -parent;
        }
        else{
            while(items[parent] >= 0){
                parent = items[parent];
            }
            return -items[parent];
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if(items[v] < 0){
            return items[v];
        }
        else{
            int parent = items[v];
            while(items[parent] >= 0){
                parent = items[parent];
            }
            return parent;
        }
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
	    int v1_parent = parent(v1);
        int v2_parent = parent(v2);

        if(v1_parent >= 0 && v2_parent >= 0){
            return v1_parent == v2_parent;
        }
        else{
            if(v1_parent == v2 || v2_parent == v1){
                return true;
            }
            else{
                return false;
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v >= items.length || v < 0){
            throw new IllegalArgumentException();
        }
        else if(items[v] < 0){
            return v;
        }
        else{
            int parent = parent(v);
            while(items[v] > 0){
                int tmp = items[v];
                items[v] = parent;
                v = tmp;
            }
        }
        return v;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(v1 == v2) return;
        int parent1 = find(v1);
        int parent2 = find(v2);
        if(sizeOf(parent1) <= sizeOf(parent2)){
            items[parent2] += items[parent1];
            items[parent1] = parent2;

        }
        else{
            items[parent1] += items[parent2];
            items[parent2] = parent1;
        }
    }

}
