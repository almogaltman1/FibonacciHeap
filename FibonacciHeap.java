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
        } else {
            //check if this is new min
            if (key < this.min.getKey()) {
                this.min = newNode;
            }
            //connect to the heap
            HeapNode last = this.first.getPrev();
            HeapNode wasFirst = this.first;
            this.first = newNode; //insert newNode to the left
            newNode.setNext(wasFirst);
            newNode.setPrev(last);
            //update last and wasFirst fields to point the newNode
            last.setNext(newNode);
            wasFirst.setPrev(newNode);
        }
        //update heap fields
        this.numberOfTrees++;
        this.size++;

        return newNode;
    }

    /**
     * public void deleteMin()
     * <p>
     * Deletes the node containing the minimum key.
     */
    public void deleteMin() {
        return; // should be replaced by student code

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

        if (heap2!=null && !heap2.isEmpty()) {
            if (this.isEmpty()) {
                this.first = heap2.first;
                this.min = heap2.min;
            } else {
                if (this.min.getKey() < heap2.min.getKey()) {
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

        if (this.isEmpty()){
            return new int[0];
        }

        int maxRank = 0;
        HeapNode next = this.first;
        do {
            if (next.getRank() > maxRank) {
                maxRank = next.getRank();
            }
            next = next.getNext();
        }
        while (next != this.first);

        int[] countersRepArr = new int[maxRank+1];

        next = this.first;
        do {
            countersRepArr[next.getRank()]++;
            next = next.getNext();
        }
        while (next != this.first);

        return countersRepArr;
    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     */
    public void delete(HeapNode x) {
        return; // should be replaced by student code
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        x.setKey(x.getKey()-delta);
        if (x.getParent()!=null && x.getKey()<x.getParent().getKey()){ // not root and heap rules are broken
            cascadingCuts(x);
        }

        if (x.getKey()<this.min.getKey()){
            this.min=x;
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
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }

    //helping functions

    /**
     * private void insertLast(HeapNode newNode)
     * <p>
     * Inserts newNode to the end of the heap
     */
    private void insertLast(HeapNode newNode) {
        HeapNode last = this.first.getPrev();
        //connect to the heap, new node will be last
        newNode.setPrev(last);
        newNode.setNext(this.first);
        //update last and first fields to point the newNode
        last.setNext(newNode);
        this.first.setPrev(newNode);

        //update heap fields
        this.numberOfTrees++;
        this.size++;
    }

    /**
     * private void isolatedRoot(HeapNode root)
     * <p>
     * Disconnects the node root from this brothers
     */
    private void isolatedRoot(HeapNode root) {
        HeapNode left = root.getPrev();
        HeapNode right = root.getNext();
        //isolate root from his brothers
        root.setPrev(root);
        root.setNext(root);
        //connect the brothers to each other
        left.setNext(right);
        right.setPrev(left);
    }

    /**
     * private void fixRoots(HeapNode node)
     * <p>
     * Node is a pointer to the first node in the current root list, witch need to be updated
     * Updates roots to be unmarked and parent to be null
     */
    private void fixRoots(HeapNode node) {
        int unmarked = 0;
        //fix first
        node.setParent(null);
        if (node.isMarked()) {
            node.setMarked(false);
            unmarked++;
        }
        //fix all the ohters
        HeapNode currNode = node.getNext();
        while (currNode != node) {
            currNode.setParent(null);
            if (currNode.isMarked()) {
                currNode.setMarked(false);
                unmarked++;
            }
            currNode = currNode.getNext();
        }
        this.numberOfMarkedNodes -= unmarked;
    }

    // TODO: change this function to private
    /**
     * private void cascadingCuts(HeapNode node)
     * <p>
     * Node is a pointer to the node which we want to cut from its parent
     * Recursive function which performs a cascading cut process starting at node
     * precondition: node.getParent()!=null
     */
    protected void cascadingCuts(HeapNode node){
        cut(node);
        node = node.getParent();
        if(node.getParent()!=null){
            if (!node.isMarked()){
                node.setMarked(true);
                numberOfMarkedNodes++;
            }
            else{
                cascadingCuts(node);
            }
        }
    }

    // TODO: change this function to private
    /**
     * private void cut(HeapNode node)
     * <p>
     * Node is a pointer to the node which we want to cut from its parent
     * Cuts node from its parent
     * precondition: node.getParent()!=null
     */
    protected void cut(HeapNode node){

        HeapNode nodeParent = node.getParent();

        // update node related fields
        node.setParent(null);
        node.setMarked(false);

        //update node parent and brothers related fields
        nodeParent.setRank(nodeParent.getRank()-1);

        if (node.getNext()==node){ // means node is only child of its parent
            nodeParent.setChild(null);
        }
        else{
            if(nodeParent.getChild()==node){
                nodeParent.setChild(node.getNext());
            }
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }

        // reconnect node to tree
        HeapNode firstBeforeCut = this.first;
        HeapNode lastInHeap = this.first.getPrev();
        node.setNext(firstBeforeCut);
        node.setPrev(lastInHeap);
        lastInHeap.setNext(node);
        firstBeforeCut.setPrev(node);

        // update tree related fields
        this.first=node;
        this.numberOfTrees++;
        this.numberOfMarkedNodes--;
        totalCuts++;

    }

    //TODO: delete getFirst() before handing in the project
    public HeapNode getFirst() {
        return this.first;
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


        public HeapNode(int key) {
            this.key = key;
            this.rank = 0;
            this.marked = false;
            this.child = null;
            this.next = this;
            this.prev = this;
            this.parent = null;
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
    }
}
