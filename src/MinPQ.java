import java.util.Comparator;

/**
 * Minimum Priority Queue implementation based in Heap
 */
public class MinPQ {

    private Bridge[] heap; // the heap to store data in
    private int size; // current size of the queue
    private Comparator<Integer> comparator; // the comparator to use between the objects

    private static final int DEFAULT_CAPACITY = 4; // default capacity
    private static final int AUTOGROW_SIZE = 4; // default auto grow

    public MinPQ(Comparator<Integer> comparator) {
        this.heap = new Bridge[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = comparator;
    }

    public void add(Bridge item) {

        if(size == heap.length - 1){
            grow();
        }
        size++;

        //find the next available position in the array
        heap[size] = item;

        //introduced item
        swim(size);

    }

    public Bridge peek() {
        if (size==0) { return null;}
        return heap[1];

    }

    public Bridge getMin() {

        if(size==0){ return null;}

        Bridge element = heap[1];
        heap[1] = heap[size];
        size--;

        sink(1);
        return element;

    }

    private void swim(int i) {
        if (i==1) { return; }
        int parent = i/2;
        while(i!=1 && comparator.compare(heap[i].getSum(),heap[parent].getSum())< 0) {
            this.swap(i, parent);
            i = parent;
            parent = i/2;
        }
    }

    private void sink(int i) {

        int leftChildPos = 2*i;
        int rightChildPos = 2*i + 1;

        if(leftChildPos > size) {
            return;
        }

        while(leftChildPos <= size){
            int max = leftChildPos;
            if(rightChildPos <= size){
                if(comparator.compare(heap[leftChildPos].getSum(), heap[rightChildPos].getSum()) >0 ){
                    max = rightChildPos;
                }
            }

            if(comparator.compare(heap[i].getSum(),heap[max].getSum()) <=0 ) {
                break;
            }else{
                swap(i,max);
                i= max;
                leftChildPos = 2*i;
                rightChildPos = 2*i+1;
            }
        }

    }

    private void swap(int i, int j) {
        Bridge tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    private void grow() {
        Bridge[] newHeap = new Bridge[heap.length + AUTOGROW_SIZE];

        for (int i = 0; i <= size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }

}
