import java.util.Iterator;


/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013-9-2       
 *        
 * 
 *  Purpose:  A double-ended queue or deque (pronounced "deck") is a 
 *  generalization of a stack and a queue that supports inserting and 
 *  removing items from either the front or the back of the data structure.
 *  
 *
 *----------------------------------------------------------------*/

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int N;
	
	private class Node{
		private Item item;
		private Node next;
		private Node prev;
	}

	public Deque(){                     // construct an empty deque
		N = 0;
		first = last = null;
	}
	
	public boolean isEmpty(){           // is the deque empty?
		return N==0?true:false;
	}
	
	public int size(){                  // return the number of items on the deque
		return N;
	}
	
	public void addFirst(Item item){    // insert the item at the front
		check(item);
		Node inserting = new Node();
		inserting.item = item;
		Node temp;
		if(isEmpty()){
			last = inserting;
			temp = inserting;
		}else{
			temp = first;
		}
		temp.prev = inserting;
		inserting.next = temp;
		first = inserting;
		first.prev = null;
		last.next=null;
		N++;
	}
	
	public void addLast(Item item){     // insert the item at the end
		check(item);
		Node inserting = new Node();
		inserting.item = item;
		Node temp;
		if(isEmpty()){
			first = inserting;
			temp = inserting;
		}else{
			temp = last;
		}
		temp.next = inserting;
		inserting.prev = temp;
		last = inserting;
		first.prev = null;
		last.next = null;
		N++;
	}
	
	public Item removeFirst(){          // delete and return the item at the front
		emptyCheck();
		Node temp = first;
		if(N==1){
			first = last = null;
		}else {
			first = temp.next;
			first.prev = null;
		}
		N--;
		return temp.item ;
	}
	
	public Item removeLast(){           // delete and return the item at the end
		emptyCheck();
		Node temp = last;
		if(N==1){
			first = last = null;
		}else{
			last = temp.prev;
			last.next = null;
		}
		N--;
		return temp.item;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
//		Iterator<Item> tmp = new deQueueIterator();
//		while(tmp.hasNext()){
//			StdOut.print(tmp.next()+ " ");
//		}
		return new deQueueIterator();
	}
	
	private void check(Item item){
		if(item == null){
			throw new java.lang.NullPointerException();
		}
		
	}
	
	private void emptyCheck(){
		if(N==0){
			throw new java.util.NoSuchElementException();
		}
	}
	
	
	private class deQueueIterator implements Iterator<Item> {
		
		private Node current = first;
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return (current!= null)?true:false;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if(hasNext()){
				Item tmp = current.item;
				current = current.next;
				return tmp;
			}else{
				throw new java.util.NoSuchElementException();
			}
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			 throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args){
		Deque<Integer> deque = new Deque<Integer>();
//		deque.addLast(2);
		deque.addFirst(1);
//		deque.addLast(2);
		deque.addFirst(2);
		deque.addFirst(3);
		deque.addFirst(4);
		deque.addFirst(5);
		for (Iterator iter = deque.iterator(); iter.hasNext();) {
			
			for (Iterator iter2 = deque.iterator(); iter2.hasNext();){
				
			}
		}
//		deque.removeFirst();
//		deque.removeLast();
//		deque.iterator();
	}
}
 