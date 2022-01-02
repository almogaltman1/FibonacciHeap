//public class SectionB {
//
//    public static void build(int m, FibonacciHeap heap){
//        FibonacciHeap.HeapNode[] arrPointer = new FibonacciHeap.HeapNode[m];
//        for (int k=m-1; k>=0; k--){ //insert 0 to m-1
//            arrPointer[k] = heap.insert(k);
//        }
//        heap.insert(-1);
//        //HeapPrinter.print(heap,false);
//        heap.deleteMin();
//        HeapPrinter.print(heap,false);
//        for (int i=(int)(Math.log(m)/Math.log(2));i>=1;i--){
//            heap.decreaseKey(arrPointer[m - (int)(Math.pow(2,i))+1],m+1);
//        }
//        heap.decreaseKey(arrPointer[m-2],m+1);
//        HeapPrinter.print(heap,false);
//    }
//
//    private static void build2(int m, FibonacciHeap heap){
//        for (int k=0; k<=m; k++){
//            heap.insert(k);
//        }
//        HeapPrinter.print(heap,false);
//        for (int i=1; i<=3*m/4; i++){
//            heap.deleteMin();
//            HeapPrinter.print(heap,false);
//        }
//        HeapPrinter.print(heap,false);
//    }
//
//    public static void main(String[] args){
//        //Q1
////        int pow = 4;
////        FibonacciHeap heap = new FibonacciHeap();
////        long startTime = System.currentTimeMillis();
////        build((int)Math.pow(2,pow), heap);
////        long endTime = System.currentTimeMillis();
////        System.out.println("Time: " + (endTime-startTime));
////        System.out.println("total links: " + FibonacciHeap.totalLinks());
////        System.out.println("total cuts: " + FibonacciHeap.totalCuts());
////        System.out.println("total potential: " + heap.potential());
//
//        //Q2
//        //int pow = 14;
//        FibonacciHeap heap = new FibonacciHeap();
//        long startTime = System.currentTimeMillis();
//        build2(12/*(int)Math.pow(3,pow)-1*/, heap);
//        long endTime = System.currentTimeMillis();
//        System.out.println("Time: " + (endTime-startTime));
//        System.out.println("total links: " + FibonacciHeap.totalLinks());
//        System.out.println("total cuts: " + FibonacciHeap.totalCuts());
//        System.out.println("total potential: " + heap.potential());
//    }
//
//}
