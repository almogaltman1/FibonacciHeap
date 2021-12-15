import java.util.Arrays;

public class Tester {
    public static void main(String[] args) {


        FibonacciHeap h = new FibonacciHeap();
        h.insert(1);
        h.insert(2);

        FibonacciHeap.HeapNode childOfFirst = new FibonacciHeap.HeapNode(6);
        FibonacciHeap.HeapNode brotherOfChildOfFirst = new FibonacciHeap.HeapNode(7);

        h.getFirst().setChild(childOfFirst);
        childOfFirst.setParent(h.getFirst());

        childOfFirst.setNext(brotherOfChildOfFirst);
        childOfFirst.setPrev(brotherOfChildOfFirst);
        brotherOfChildOfFirst.setPrev(childOfFirst);
        brotherOfChildOfFirst.setNext(childOfFirst);

        brotherOfChildOfFirst.setParent(h.getFirst());

        h.cut(childOfFirst);

        printHeap(h);

//        int[] arr = h.countersRep();
//
//        for (int i=0;i<arr.length;i++){
//            System.out.println(i+":"+arr[i]);
//        }
//
//        FibonacciHeap h2 = new FibonacciHeap();
//        h2.insert(3);
//        h2.insert(4);

//        h.meld(h2);
//        System.out.println(h.getFirst().getKey()+":"+h.getFirst().getPrev().getKey());
//        System.out.println(h.getFirst().getPrev().getKey()+":"+h.getFirst().getPrev().getNext().getKey());
//
//        System.out.println(h.size());
//        printHeap(h);

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
}
