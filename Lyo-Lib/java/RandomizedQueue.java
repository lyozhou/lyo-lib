import java.util.Iterator;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013-9-2       
 *        
 * 
 *  Purpose:  A randomized queue is similar to a stack or queue, 
 *  except that the item removed is chosen uniformly at random from 
 *  items in the data structure. 
 *  
 *
 *----------------------------------------------------------------*/

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item [] items;
	private int N;

	public RandomizedQueue() { // construct an empty randomized queue
		items = (Item[]) new Object[1];
		N = 0;
	}

	public boolean isEmpty() { // is the queue empty?
		return N == 0 ? true : false;
	}

	public int size() { // return the number of items on the queue
		return N;
	}

	public void enqueue(Item item) { // add the item
		check(item);
		if(items.length == N){
			resize(items.length*2);
		}
		items[N] = item;
		N++;
	}

	public Item dequeue() { // delete and return a random item
		emptyCheck();
		int randomN = StdRandom.uniform(N);
		Item itemToDelete = items[randomN];
		if(N-1 == randomN){
			items[randomN] = null;
		}else{
			items[randomN] = items[N-1];
			items[N-1] = null;
		}
		if(N < items.length/4){
			resize(items.length/2);
		}
		N--;
		return itemToDelete;
	}
	
	public Item sample(){               // return (but do not delete) a random item
		emptyCheck();
		int randomN = StdRandom.uniform(N);
		Item itemToDelete = items[randomN];
		return itemToDelete;
	}
	
	private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

	private void check(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException();
		}

	}

	private void emptyCheck() {
		if (N == 0) {
			throw new java.util.NoSuchElementException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		Iterator<Item> tmp = new deQueueIterator();
		while (tmp.hasNext()) {
			StdOut.print(tmp.next() + " ");
		}
		return new deQueueIterator();
	}

	private class deQueueIterator implements Iterator<Item> {

		private int current = 0;
		private int[] indices;
		
		public deQueueIterator(){
			indices = new int[N];
			for(int i=0;i<N;i++){
				indices[i]=i;
			}
			StdRandom.shuffle(indices);
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current < N;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if (hasNext()) {
				return items[indices[current++]];
			} else {
				throw new java.util.NoSuchElementException();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
		deque.enqueue(1);
		deque.enqueue(2);
		deque.enqueue(3);
		deque.enqueue(4);
		deque.enqueue(5);
		deque.dequeue();
		deque.dequeue();
		deque.dequeue();
		deque.dequeue();
//		deque.dequeue();
		deque.iterator();
	}
}
