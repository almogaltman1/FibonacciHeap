/**
 * FibonacciHeap
 * <p>
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    private static int totalLinks = 0;
    private static int totalCuts = 0;
    private HeapNode min;
    private HeapNode first;
    private int size;
    private int numberOfTrees;
    private int numberOfMarkedNodes;


    /**
     * public boolean isEmpty()
     * <p>
     * Returns true if and only if the heap is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * public HeapNode insert(int key)
     * <p>
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     * <p>
     * Returns the newly created node.
     */
    public HeapNode insert(int key) {
        HeapNode newNode = new HeapNode(key);
        //if first node in heap
        if (this.isEmpty()) {
            this.min = newNode;
            this.first = newNode;
            this.numberOfTrees++;
        } else {
            //check if this is new min
            if (key < this.min.getKey()) {
                this.min = newNode;
            }
            insertFirst(newNode);

        }
        //update heap fields (number of trees is updated in insertFirst function)
        this.size++;

        return newNode;
    }

    /**
     * public void deleteMin()
     * <p>
     * Deletes the node containing the minimum key.
     */
    public void deleteMin() {

        if (this.isEmpty()) {
            //do nothing
            return;
        }

        //update fields of heap
        this.size--;
        this.numberOfTrees = this.numberOfTrees - 1 + this.min.getRank();

        HeapNode minPrev = this.min.getPrev();
        HeapNode minNext = this.min.getNext();
        HeapNode firstChild = this.min.getChild();

        if (this.size == 0) { //min was only noe in heap, turn heap to by empty
            this.first = null;
            this.min = null;
            return;
        } else if (firstChild != null) { //min has child
            fixRoots(firstChild); //fixing the children of min, because they will become roots
            if (this.first == this.min) { //if min was first, update
                this.first = firstChild;
            }
            if (this.min != minNext) { //min has child and brother
                HeapNode lastChild = firstChild.getPrev();
                //update heap to have deleted min children as roots instead of min
                minPrev.setNext(firstChild);
                firstChild.setPrev(minPrev);
                minNext.setPrev(lastChild);
                lastChild.setNext(minNext);
            }
        } else { //min has brother and not child
            if (this.first == this.min) { //if min was first, update
                this.first = minNext;
            }
            //bypass the min in the root list
            minPrev.setNext(minNext);
            minNext.setPrev(minPrev);
        }

        this.min.setChild(null);
        isolatedRoot(this.min); //min is no longer in the heap

        successiveLinking();

        return;
    }

    /**
     * public HeapNode findMin()
     * <p>
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     */
    public HeapNode findMin() {
        return this.min;
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * <p>
     * Melds heap2 with the current heap.
     */
    public void meld(FibonacciHeap heap2) {

        if (heap2 != null && !heap2.isEmpty()) {
            if (this.isEmpty()) { //heap is empty and heap 2 not empty
                this.first = heap2.first;
                this.min = heap2.min;
            } else { //both not empty
                if (this.min.getKey() > heap2.min.getKey()) {
                    this.min = heap2.min;
                }

                HeapNode lastInThis = this.first.getPrev();
                HeapNode lastInHeap2 = heap2.first.getPrev();

                this.first.setPrev(lastInHeap2);
                lastInHeap2.setNext(this.first);

                lastInThis.setNext(heap2.first);
                heap2.first.setPrev(lastInThis);

            }
            this.size = this.size + heap2.size;
            this.numberOfTrees = this.numberOfTrees + heap2.numberOfTrees;
            this.numberOfMarkedNodes = this.numberOfMarkedNodes + heap2.numberOfMarkedNodes;
        }

    }

    /**
     * public int size()
     * <p>
     * Returns the number of elements in the heap.
     */
    public int size() {
        return this.size;
    }

    /**
     * public int[] countersRep()
     * <p>
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
     */
    public int[] countersRep() {

        if (this.isEmpty()) {
            return new int[0];
        }

        int maxRank = 0;
        HeapNode node = this.first;
        do {
            if (node.getRank() > maxRank) {
                maxRank = node.getRank();
            }
            node = node.getNext();
        }
        while (node != this.first);

        int[] countersRepArr = new int[maxRank + 1];

        node = this.first;
        do {
            countersRepArr[node.getRank()]++;
            node = node.getNext();
        }
        while (node != this.first);

        return countersRepArr;
    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     */
    public void delete(HeapNode x) {
        decreaseKey(x, x.getKey() - this.min.getKey() + 1);
        deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        x.setKey(x.getKey() - delta);
        //update min if needed
        if (x.getKey() < this.min.getKey()) {
            this.min = x;
        }

        if (x.getParent() != null && x.getKey() < x.getParent().getKey()) { // not root and heap rules are broken
            cascadingCuts(x);
        }
    }

    /**
     * public int potential()
     * <p>
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * <p>
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     */
    public int potential() {
        return this.numberOfTrees + 2 * numberOfMarkedNodes;
    }

    /**
     * public static int totalLinks()
     * <p>
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     */
    public static int totalLinks() {
        return FibonacciHeap.totalLinks;
    }

    /**
     * public static int totalCuts()
     * <p>
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts() {
        return FibonacciHeap.totalCuts;
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * <p>
     * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     * <p>
     * ###CRITICAL### : you are NOT allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k) {
        int[] arr = new int[k];
        if (H.isEmpty() || k <= 0) {
            return arr;
        }

        FibonacciHeap minHeap = new FibonacciHeap();
        HeapNode currMin = H.findMin();
        for (int i = 0; i < k; i++) {
            //insert next smallest value to arr
            arr[i] = currMin.getKey();
            //insert children of currMin to minHeap
            HeapNode child = currMin.getChild();
            if (child != null) {
                //if currMin have children, insert to minHeap
                for (int c = 0; c < currMin.getRank(); c++) {
                    //insert into minHeap only value of child's key. update fictiveNode to be connected to the real node it represents
                    HeapNode fictiveNode = minHeap.insert(child.getKey());
                    fictiveNode.setConnectionForkMin(child);
                    child = child.getNext();
                }
            }
            HeapNode fictiveCurrMin = minHeap.findMin();
            currMin = fictiveCurrMin.getConnectionForkMin();
            minHeap.deleteMin();
        }

        return arr;
    }

    //helping functions

    /**
     * private void insertFirst(HeapNode newNode)
     * <p>
     * Inserts newNode to the beginning of the heap, size field is updated in insert function
     */
    private void insertFirst(HeapNode newNode) {
        //connect to the heap
        HeapNode last = this.first.getPrev();
        HeapNode wasFirst = this.first;
        this.first = newNode; //insert newNode to the left
        newNode.setNext(wasFirst);
        newNode.setPrev(last);
        //update last and wasFirst fields to point the newNode
        last.setNext(newNode);
        wasFirst.setPrev(newNode);

        //update heap fields
        this.numberOfTrees++;
    }

    /**
     * private void insertLast(HeapNode newNode)
     * <p>
     * Inserts newNode to the end of the heap, size field isn't adjusted because no nodes added to the heap
     */
    private void insertLast(HeapNode node) {
        HeapNode last = this.first.getPrev();
        //connect to the heap, new node will be last
        node.setPrev(last);
        node.setNext(this.first);
        //update last and first fields to point the newNode
        last.setNext(node);
        this.first.setPrev(node);

        //update heap fields
        this.numberOfTrees++;
    }

    /**
     * private void isolatedRoot(HeapNode root)
     * <p>
     * Disconnects the node root from this brothers
     * precondition: root.parent() == null (root is actually root)
     */
    private void isolatedRoot(HeapNode root) {
        //isolate root from his brothers
        root.setPrev(root);
        root.setNext(root);
    }

    /**
     * private void fixRoots(HeapNode node)
     * <p>
     * Node is a pointer to the first node in the current root list, witch need to be updated
     * Updates roots to be unmarked and parent to be null
     */
    private void fixRoots(HeapNode node) {
        int unmarked = 0;

        HeapNode currNode = node;
        do {
            currNode.setParent(null);
            if (currNode.isMarked()) {
                currNode.setMarked(false);
                unmarked++;
            }
            currNode = currNode.getNext();
        }
        while (currNode != node);

        this.numberOfMarkedNodes -= unmarked;
    }

    /**
     * private void cascadingCuts(HeapNode node)
     * <p>
     * Node is a pointer to the node which we want to cut from its parent
     * Recursive function which performs a cascading cut process starting at node
     * precondition: node.getParent()!=null
     */
    private void cascadingCuts(HeapNode node) {
        HeapNode parent = node.getParent();
        cut(node);
        if (parent.getParent() != null) { //parent is not root
            if (!parent.isMarked()) {
                parent.setMarked(true);
                this.numberOfMarkedNodes++;
            } else {
                cascadingCuts(parent);
            }
        }
    }

    /**
     * private void cut(HeapNode node)
     * <p>
     * Node is a pointer to the node which we want to cut from its parent
     * Cuts node from its parent
     * precondition: node.getParent()!=null
     */
    private void cut(HeapNode node) {

        HeapNode nodeParent = node.getParent();

        // update node related fields
        node.setParent(null);
        if (node.isMarked()) {
            node.setMarked(false);
            this.numberOfMarkedNodes--;
        }

        //update node parent and brothers related fields
        nodeParent.setRank(nodeParent.getRank() - 1);

        if (node.getNext() == node) { // means node is only child of its parent
            nodeParent.setChild(null);
        } else {
            if (nodeParent.getChild() == node) {
                nodeParent.setChild(node.getNext());
            }
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }

        // reconnect node to tree
        insertFirst(node);

        // update tree related fields, number of trees updated in insertFirst
        FibonacciHeap.totalCuts++;
    }

    //TODO: delete getFirst() before handing in the project
    public HeapNode getFirst() {
        return this.first;
    }

    /**
     * private void successiveLinking()
     * Conducts a successive linking on the heap
     */
    private void successiveLinking() {
        int arrSize = 2*((int)(Math.log(this.size) / Math.log(2))) + 1;
        HeapNode[] buckets = new HeapNode[arrSize];
        toBuckets(buckets);
        fromBuckets(buckets);
    }

    /**
     * private HeapNode link(HeapNode h1, HeapNode h2)
     * connect 2 trees with the same rank from the heap. Returns the root of the joined tree
     * precondition: h1.getParent()==null && h2.getParent()==null (h1 and h2 are roots)
     * precondition: h1.getRank()==h2.getRank()
     * precondition: h1 and h2 are not related (not connected to each other)
     */
    private HeapNode link(HeapNode h1, HeapNode h2) {
        HeapNode root = h1; //initialize root as h1
        HeapNode son = h2; //initialize son as h2
        if (h2.getKey() < h1.getKey()) { // switch if needed
            root = h2;
            son = h1;
        }

        HeapNode firstChild = root.getChild();
        if (firstChild != null) { //case when root has child
            HeapNode lastChild = firstChild.getPrev();
            son.setNext(firstChild);
            firstChild.setPrev(son);
            son.setPrev(lastChild);
            lastChild.setNext(son);
        }

        //update son to be root's first child
        son.setParent(root);
        root.setChild(son);
        root.setRank(root.getRank() + 1);
        FibonacciHeap.totalLinks++;

        return root;
    }

    /**
     * private void toBuckets(HeapNode[] buckets)
     * Changes heap to an array in the successive link process as we saw in class
     */
    private void toBuckets(HeapNode[] buckets) {
        HeapNode x = this.first;
        HeapNode y;
        x.getPrev().setNext(null);
        while (x != null) {
            y = x;
            x = x.getNext();
            isolatedRoot(y);
            while (buckets[y.getRank()] != null) {
                //no place in the correct cell
                y = link(y, buckets[y.getRank()]);
                buckets[y.getRank() - 1] = null;
            }
            buckets[y.getRank()] = y;
        }

        // actual heap is "empty" and the heap is in an array
        this.first = null;
        this.min = null;
        this.numberOfTrees = 0;

    }

    /**
     * private void fromBuckets(HeapNode[] buckets)
     * Changes array back into heap form
     */
    private void fromBuckets(HeapNode[] buckets) {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                if (this.first == null) {
                    this.min = buckets[i];
                    this.first = buckets[i];
                }
                insertLast(buckets[i]);
                if (buckets[i].getKey() < this.min.getKey()) {
                    this.min = buckets[i];
                }
            }
        }
    }

    /**
     * public class HeapNode
     * <p>
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in another file.
     */
    public static class HeapNode {

        public int key;
        private int rank;
        private boolean marked;
        private HeapNode child;
        private HeapNode next;
        private HeapNode prev;
        private HeapNode parent;
        private HeapNode connectionForkMin;


        public HeapNode(int key) {
            this.key = key;
            this.rank = 0;
            this.marked = false;
            this.child = null;
            this.next = this;
            this.prev = this;
            this.parent = null;
            this.connectionForkMin = null;
        }

        public int getKey() {
            return this.key;
        }

        public void setKey(int k) {
            this.key = k;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public boolean isMarked() {
            return marked;
        }

        public void setMarked(boolean marked) {
            this.marked = marked;
        }

        public HeapNode getChild() {
            return child;
        }

        public void setChild(HeapNode child) {
            this.child = child;
        }

        public HeapNode getNext() {
            return next;
        }

        public void setNext(HeapNode next) {
            this.next = next;
        }

        public HeapNode getPrev() {
            return prev;
        }

        public void setPrev(HeapNode prev) {
            this.prev = prev;
        }

        public HeapNode getParent() {
            return parent;
        }

        public void setParent(HeapNode parent) {
            this.parent = parent;
        }

        public HeapNode getConnectionForkMin() {
            return connectionForkMin;
        }

        public void setConnectionForkMin(HeapNode connectionForkMin) {
            this.connectionForkMin = connectionForkMin;
        }
    }
}
