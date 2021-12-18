import java.util.Arrays;

public class Tester {
    public static void main(String[] args) {

        test1();
//
//        FibonacciHeap h = new FibonacciHeap();
//        FibonacciHeap.HeapNode node1 = h.insert(1);
//        h.insert(4);
//        h.insert(2);
//        h.insert(5);
//        h.insert(3);
//        h.deleteMin();
//        h.deleteMin();
//        HeapPrinter.print(h,true);
//        System.out.println(h.size());
//        System.out.println(h.potential());
//        System.out.println(h.findMin().getKey());
//        System.out.println(h.getFirst().getKey());
//        System.out.println(FibonacciHeap.totalCuts());
//        System.out.println(FibonacciHeap.totalLinks());









//        FibonacciHeap h = new FibonacciHeap();
//        FibonacciHeap.HeapNode node1 = h.insert(1);
//        h.insert(2);
//        h.insert(3);
//        h.insert(4);
//        int arrSize = 2*((int)(Math.log(h.size()) / Math.log(2))) + 1;
//        FibonacciHeap.HeapNode[] buckets = new FibonacciHeap.HeapNode[arrSize];
//        h.toBuckets(buckets);
//        h.fromBuckets(buckets);
//        HeapPrinter.print(h,true);
//        System.out.println("blah");
//        System.out.println(h.size());
//        System.out.println(h.potential());
//        System.out.println(h.findMin().getKey());
//        System.out.println(h.getFirst().getKey());
//        System.out.println(FibonacciHeap.totalCuts());
//        System.out.println(FibonacciHeap.totalLinks());


//        FibonacciHeap h = new FibonacciHeap();
//        FibonacciHeap h2 = new FibonacciHeap();
//        FibonacciHeap.HeapNode node1 = h.insert(1);
//        FibonacciHeap.HeapNode node2 = h2.insert(2);
//        h2.link(node1,node2);
//        HeapPrinter.print(h,true);


//        FibonacciHeap heap = new FibonacciHeap();
//        FibonacciHeap heap2 = new FibonacciHeap();
//        FibonacciHeap.HeapNode node1 = heap.insert(-1);
//        heap.insert(2);
//        heap.insert(3);
//        FibonacciHeap.HeapNode node4 = heap.insert(4);
//        FibonacciHeap.HeapNode node5 = heap.insert(5);
//        //int[] arr = heap.countersRep();
//        //System.out.println(Arrays.toString(arr));
//        heap.decreaseKey(node4, node4.getKey()-heap.findMin().getKey()+1);
//        System.out.println(heap.getFirst().getKey() + " is first");
//        System.out.println(heap.findMin().getKey() + "is min");
//        System.out.println(heap.size());
//        System.out.println(heap.potential());
//        System.out.println(FibonacciHeap.totalCuts());
//        System.out.println(FibonacciHeap.totalLinks());
//
//
////        heap.deleteMin();
////        heap.deleteMin();
//        HeapPrinter.print(heap,true);




//
//        FibonacciHeap h = new FibonacciHeap();
//        FibonacciHeap.HeapNode node1 = h.insert(1);
//        h.insert(2);
//
//        FibonacciHeap.HeapNode childOfFirst = new FibonacciHeap.HeapNode(6);
//        FibonacciHeap.HeapNode brotherOfChildOfFirst = new FibonacciHeap.HeapNode(7);
//        brotherOfChildOfFirst.setMarked(true);
//
//        h.getFirst().setChild(childOfFirst);
//        childOfFirst.setParent(h.getFirst());
//
//        childOfFirst.setNext(brotherOfChildOfFirst);
//        childOfFirst.setPrev(brotherOfChildOfFirst);
//        brotherOfChildOfFirst.setPrev(childOfFirst);
//        brotherOfChildOfFirst.setNext(childOfFirst);
//
//        brotherOfChildOfFirst.setParent(h.getFirst());
//        node1.setRank(2);
//
//        // brotherOfChildOfFirst - parent
//        FibonacciHeap.HeapNode childOf7 = new FibonacciHeap.HeapNode(14);
//        FibonacciHeap.HeapNode brotherOfChildOf7 = new FibonacciHeap.HeapNode(10);
//
//        brotherOfChildOfFirst.setChild(childOf7);
//        childOf7.setParent(brotherOfChildOfFirst);
//
//        childOf7.setNext(brotherOfChildOf7);
//        childOf7.setPrev(brotherOfChildOf7);
//        brotherOfChildOf7.setPrev(childOf7);
//        brotherOfChildOf7.setNext(childOf7);
//
//        brotherOfChildOf7.setParent(brotherOfChildOfFirst);
//        brotherOfChildOfFirst.setRank(2);
//        //fields of heap not correct in this test
//        //h.decreaseKey(childOf7,9);
//        h.cascadingCuts(childOf7);
//
//        HeapPrinter.print(h,true);

//        int[] arr = h.countersRep();
//
//        for (int i=0;i<arr.length;i++){
//            System.out.println(i+":"+arr[i]);
//        }
//
//        FibonacciHeap h2 = new FibonacciHeap();
//        h2.insert(3);
//        h2.insert(4);
//
//        h.meld(h2);
//        System.out.println(h.getFirst().getKey()+":"+h.getFirst().getPrev().getKey());
//        System.out.println(h.getFirst().getPrev().getKey()+":"+h.getFirst().getPrev().getNext().getKey());
//
//        System.out.println(h.size());
//        printHeap(h);
//
//        FibonacciHeap.HeapNode first = h.getFirst();
//        FibonacciHeap.HeapNode childOfFirst = new FibonacciHeap.HeapNode(6);
//        FibonacciHeap.HeapNode next = new FibonacciHeap.HeapNode(5);
//        FibonacciHeap.HeapNode childOfSecond = new FibonacciHeap.HeapNode(10);
//        next.setChild(childOfSecond);
//        childOfSecond.setNext(childOfSecond);
//        first.setChild(childOfFirst);
//        childOfFirst.setNext(next);
//        next.setNext(childOfFirst);
//        printHeap(h);
    }

    public static void printHeap(FibonacciHeap h) {
        printHeapRec(h.getFirst(), "");
    }

    public static void printHeapRec(FibonacciHeap.HeapNode node, String indent) {
        if (node == null) {
            return;
        }

        FibonacciHeap.HeapNode next = node;

        do {
            System.out.println(indent + next.getKey());
            if (next.getChild() != null) {
                printHeapRec(next.getChild(), indent + "  ");
            }
            next = next.getNext();
        }

        while (next != node);

    }

    static void test1() {
        String test = "test1";
        Heap heap = new Heap();
        FibonacciHeap fibonacciHeap = new FibonacciHeap();
        for (int i = 0; i < 1000; i++) {//@@@@@@@ i<1000 @@@@@
            heap.insert(0 + i);
            fibonacciHeap.insert(0 + i);
        }
        while (!heap.isEmpty()) {
            if (heap.findMin() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                System.out.println("min or size is wrong");
                System.out.println(heap.findMin() + " is min of heap");
                System.out.println(fibonacciHeap.findMin().getKey() + " is min of fib");
                System.out.println(heap.size() + " is size of heap");
                System.out.println(fibonacciHeap.size() + " is size of fib");
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            System.out.println("not empty");
    }
}
