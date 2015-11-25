/**
 * CS21120 Assignment 1
 * 
 * DEPQ - Double Ended Priority Queue
 * 
 * To solve the assignment problem with DEPQ I used various versions of two linked heaps.
 * In this method I am using two different types of the heap. Min heap and Max heap.
 * The min heap stores smallest element at the beginning of the queue,
 * The max heap stores biggest element at the beginning of the queue.
 * 
 * 
 * @author Lukasz Wrzolek
 * 
 * @references: 
 * 1: blackboard.aber.ac.uk/Course Documents/Code Samples/ Priority Queue - Look how it should look like. Expand() method taken from here.
 * 2: blackboard.aber.ac.uk/Course Documents/Lecture Slides - Priority Queues && Notes about 1st Assignment
 * 3: http://www.cise.ufl.edu/~sahni/dsaaj/enrich/c13/double.htm - introduction to heaps and general information
 * 4: https://en.wikipedia.org/wiki/Double-ended_priority_queue#Dual_structure_method - general information what dual structure method is
 * 5: https://www.youtube.com/watch?v=W81Qzuz4qH0 - after 1min -> perfect explanation how bubbleUp and bubbleDown is working
 * 6: Big O - http://bigocheatsheet.com/ - How to calculate Big O, extended material from the lectures
 * 
 */

package cs21120.depq;

import java.util.Arrays;

public class luw19DEPQ {
	
		public static void main(String[] args){
		luw19DEPQ p = new luw19DEPQ();
		System.out.println("Start");
		p.add(8);System.out.println("added 8");
		p.add(5);System.out.println("added 5");
		p.add(7);System.out.println("added 7");
		p.add(3);System.out.println("added 3");
		p.add(9);System.out.println("added 9");
		p.add(2);System.out.println("added 2");
		p.add(1);System.out.println("added 1");
		p.add(4);System.out.println("added 4");
		p.add(21);System.out.println("added 21");
		p.add(17);System.out.println("added 17");
		p.print();
		
	}
	
	
	public void print(){
		System.out.println("Max");
		System.out.println(Arrays.toString(maxHeap.heapElements));
		System.out.println(maxHeap.size);
		System.out.println("Min");
		System.out.println(Arrays.toString(minHeap.heapElements));
		System.out.println(minHeap.size);
	}
	
	
	/**
	 * I need to track index of the variable in the heaps. To solve that I created 
	 * class Node which holding data information of comparable elements in the heaps.
	 */
	class Node {
		
		@Override
		public String toString() {
			return "Node [e=" + element + ", max=" + maxHeapIndex + ", min=" + minHeapIndex
					+ "]";
		}

		//Variable of Comparable type to store the information
		Comparable element;
		//To track index of the element in maxHeap  
		int maxHeapIndex;
		//To track index of the element in minHeap
		int minHeapIndex;
		
		/**
		 * Method that gets element, maxHeapIndex and minHeap index as parameters
		 * 
		 * @param element 
		 * @param maxHeapIndex - Location in the maxHeap
		 * @param minHeapIndex - Location in the minHeap
		 */
		
		public Node(Comparable element, int maxHeapIndex, int minHeapIndex){
			this.element = element;
			this.maxHeapIndex = maxHeapIndex;
			this.minHeapIndex = minHeapIndex;
		}
		
		/**
		 * Setting index in the max heap
		 * 
		 * @param index - index in max heap
		 */
		public void setMaxHeapIndex(int index){
			this.maxHeapIndex = index;
		}
		
		/**
		 * Setting index in the min heap
		 * 
		 * @param index - index in min heap
		 */
		public void setMinHeapIndex(int index){
			this.minHeapIndex = index;
		}
		
		/**
		 * When I'm inserting new value so new node I need to have method to compare 
		 * two nodes as an arguments. To make sure that parent will be always bigger than child (maxHeap)
		 * or parent will be always lower than child (minHeap) 
		 * 
		 * @param node - inserted value
		 * @return  positive value - if result is positive
		 * 			0 if two nodes are equal
		 * 			negative value - if the result is negative
		 * 
		 */
		public int compareTo(Node node){
			return this.element.compareTo(node.element);
		}
		
	}
		
		/**
		 * Class Heap - We cannot create a heap, it must be min or max heap. I solve that problem with creating
		 * a boolean variable isMax. 'isMax' is checking which heap is it. In constructor of the Heap I'm setting up
		 * the maxHeap(true), minHeap(false).
		 * This class creating tree based data structure. 
		 * In minHeam the smallest element is on the top, in maxHeap the biggest is on the top.
		 * 
		 * 
		 * @author Lukasz Wrzolek
		 */
		
	class Heap {
			
		//Array which holding elements of Node type
		Node heapElements[];
		//Number of elements in the Array 
		int size;
		/**
		 * setting the heap
		 * true - for MaxHeap
		 * false - for MinHeap
		 */
		boolean isMax;
		/**
		 * Setting up the nominal array without constructor
		 */
		public Heap(){
			heapElements = new Node[1000];
			size = 0;
		}
		/**
		 * Setting up the specified array with constructor
		 * @param capacity - value of the size of the array
		 * @param isMax - choosing the heap
		 */
		public Heap(int capacity,boolean isMax){
			this.isMax = isMax;
			heapElements = new Node[capacity];
			size = 0;
			
		}
		
		/**
		 * Code taken from the lectures 
		 * Method to increase the size of the Node array.
		 */
		private void expand(){
			int newsize = heapElements.length*2;
			Node[] newHeapElements = new Node[newsize];
			System.arraycopy(heapElements, 0, newHeapElements, 0, heapElements.length);
			heapElements = newHeapElements;
		}
		
		/**
		 * Swapping two elements
		 * 
		 * @param index1 - index of first node
		 * @param index2 - index of second node
		 */
		private void swap(int index1, int index2){
			//when I am swapping element in heap(min or max)
			// i need to do the same in the opposite heap
			setUpOtherHeapIndex(index1,index2);
			setUpOtherHeapIndex(index2,index1);
			Node temp = heapElements[index1];
			heapElements[index1] = heapElements[index2];
			heapElements[index2] = temp;
		}
		
		/**
		 * Checking value of the first node
		 *
		 * @return first element in the heap otherwise null if heap is empty
		 */
		public Node getFirstNode(){
			//checking if array is empty
			if (size == 0){
				return null;
			//if not
			}else
				return heapElements[0];
			
		}
		
		/**
		 * Calculating trhe parent position
		 * Lecture 6, slide 27(If childs are 2n+1 and 2n+2, parent is 2n/2 -> n)
		 * @param child - position of the child connected to that parent
		 * @return parent position
		 */
		public int parent(int child){
			return (child - 1)/2;
		}
		
		/**
		 * Bubbling is a swapping parent and children
		 * here we are swapping smaller children with bigger parent
		 * Bubbling is different for each heap. In Max Heap 
		 * @param index - child position
		 */
		private void bubbleUp(int index){
			int parent = parent(index);
			//Setting bubbleUp for MinHeap
			if(isMax==false){
				//Swapping until current child value is bigger than parent value
				while(index > 0 && (compare(parent,index) > 0)){
					//Swapping the variables 
					swap(index,parent);
					//setting variable of the parent to the index
					index = parent;
					//calculating parent index for the current node
					parent = parent(index);
				}
				//Changing BubblingUp for MaxHeap,
				//It needs to sort heap from biggest value to smallest
			}else{
				while(index > 0 && (compare(parent,index) < 0)){
					//swapping the values
					swap(index,parent);
					//setting the variable of the index
					index = parent;
					//calculating the index for the current node
					parent = parent(index);
				}
			}
		}
		
		private void bubbleDown(int parent) {

			while(true){
				int child;
				//Lecture slides for left and right child indexes
				int left = parent * 2 + 1;
				int right = parent * 2 + 2;
				
			//I need to check if the array in not empty
				if(size == 0)
					break;
				//if array is not empty
				if (size != 0)
					//assign the child value to the left value to compare
					child = left;
				//if the is no children to break the loop
				if (right>=size)
					break;
				//comparing left and right
				if (right>=size)
					child = left;
				else if(compare(left,right) <= 0)
					child = left;
				else
					child = right;
					
				if(isMax == false){
					if (compare(parent, child) > 0){
						//parent is bigger than children so it needs to be swapped 
						swap (child,parent);
						parent = child;
					} else
						//parent is not bigger than children so we don't need too bubble down
						return;
				}else{
					//for MaxHeap parent has to be bigger than child
					//so result of our compare method need to be false
					if(compare(parent, child) < 0){
						swap(child,parent);
						parent = child;
					} else
						//parent is bigger than children so we don't need to bubble it down 
						return;
				}
			}
		}
		/**
		 * 
		 * @param parent - describes parent node
		 * @param children - describes children node
		 * @return  positive value - if child is bigger than parent
		 * 			0 - if it's equal
		 * 		    negative value - if child is less than parent
		 */
		public int compare(int parent, int child){
			return heapElements[parent].compareTo(heapElements[child]);
		}
		
		/**
		 * Method which will set up the position(index) in the second heap
		 * 
		 * 
		 * @param index - index of the node in first heap
		 * @param newIndex - index of the node in second heap
		 */
		public void setUpOtherHeapIndex(int index, int newIndex){
			if(isMax==false){
				heapElements[index].setMinHeapIndex(newIndex);
			}else{
				heapElements[index].setMaxHeapIndex(newIndex);
			}
			
		}
			
		/**
		 * adding node to the heap
		 * Always adding at the last place in the heap so we need to check 
		 * if it needs to be bubbled up.
		 * 
		 * @param node - node which wants to be added
		 */
		public void add(Node node){
			size++;
			//expanding array if it needs to be done
			if (size > heapElements.length){
				expand();
			}
				heapElements[size-1] = node;
				//If needs, bubble up
				bubbleUp(size-1);
		}
		
		
		/**
		 * Removing a node and fixing the tree order after removal.
		 * 
		 * @param index
		 */
		public void remove(int index){
			//need to check if array is not empty
			if (size == 0)
				return;
				
			//if the array is not empty
			if (size != 0)
				size--;
			//checking the location of the node
			if(index == size){
				heapElements[index] = null;
			}else {
				//replacing actual node with the last element in the array
				Node element = heapElements[size];
				//setting up last element to be null
				heapElements[size]=null;
				//swapping
				heapElements[index] = element;
				//setting up it in two heaps after removing.
				setUpOtherHeapIndex(index,index);
				
				//WE need to check what to do with the node
				//while parent is smaller than actual node
				bubbleUp(index);
				//while child is smaller than parent, try to bubble it down
				bubbleDown(index);
			}
			return;
		}
				
	}
			
	

	
	
	/**
	 * DEPQ builder 
	 * 
	 * @author Lukasz Wrzolek
	 */
	
	private Heap maxHeap;
	private Heap minHeap;
	/**
	 * 
	 * @param size - describes size of the heap
	 */
	public luw19DEPQ(int size){
		maxHeap = new Heap(size,true);
		minHeap = new Heap(size,false);
	}
	
	/**
	 * Setting up heap without constructor, size is 50
	 */
	public luw19DEPQ(){
		maxHeap = new Heap (50, true);
		minHeap = new Heap (50, false);
	}
	
	
	/**
	 * Inspecting element without deleting. It first node in the minHeap, so we just checking that
	 * 
	 * That method takes O(1) time because it only checking the first value in array and returning it
	 */
	public Comparable inspectLeast() {
		// get first node in minHeap
		Node node = minHeap.getFirstNode();
		return node.element;
	}

	/**
	 * Inspecting element without deleting. It first node in the maxHeap, so we just checking that
	 * 
	 * That method takes O(1) time because it only checking the first value in array and returning it
	 */
	public Comparable inspectMost() {
		//get first node in maxHeap
		Node node = maxHeap.getFirstNode();
		return node.element;
	}

	/**
	 * Adding new element
	 * 
	 * Time complexity of this method is 2 * O(log n) = O(log n)
	 * Why? We have 2 heaps and each of them has O(log n) time.
	 * After multiplying log(n) by 2 it doesn't change much.
	 * graph: http://archive.gamedev.net/archive/reference/programming/features/trees2/log.gif
	 */
	public void add(Comparable c) {
		//Creating local variable 'size'. It has the same size as the heap size. 
		//I dont need to create secong size for minHeap. They are both the same.
		int size = maxHeap.size;
		//Creating a new node, and setting it up to the max and min heap
		Node newNode = new Node (c, size, size);
		//add node to the maxHeap and minHeap. 
		//Method add in Heap will put it in the right place after bubblung (or without)
		maxHeap.add(newNode);
		minHeap.add(newNode);
	}

	/**
	 * This method taking the smallest element from the heap and removing it
	 * 
	 * Time complexity for that method will be 3*O(log n)= O(log n)
	 * Why? When we are removing the smallest element from the minHeap it takes O(log n)
	 * we need to bubble down so it takes O(log n)
	 * and removing from the maxHeap - takes O(log n)
	 * here we don't need  to bubble down. In max heap we will remove leaf.
	 * 
	 */
	public Comparable getLeast() {
		//checking if our heap is not empty
		if (maxHeap.size == 0)
			return null;
		//take first element from minHeap so the smallest one
		Node smallest = minHeap.getFirstNode();
		//remove that value from the tree
		minHeap.remove(0);
		//remove the same value from the maxHeap
		maxHeap.remove(smallest.maxHeapIndex);
		//return smallest element
		return smallest.element;
	}

	/**
	 * This method taking the smallest element from the heap and removing it
	 * 
	 * Time complexity for that method will be 3*O(log n)= O(log n)
	 * Why? When we are removing the smallest element from the maxHeap it takes O(log n)
	 * we need to bubble down so it takes O(log n)
	 * and removing from the minHeap - takes O(log n)
	 * here we don't need  to bubble down. In min heap we will remove leaf.
	 * 
	 */
	public Comparable getMost() {
		//checking if our heap is not empty
		if (maxHeap.size == 0)
			return null;
		//take first element from maxHeap so the biggest one
		Node biggest = maxHeap.getFirstNode();
		//remove it
		maxHeap.remove(0);
		//remove the same from the minHeap
		minHeap.remove(biggest.minHeapIndex);
		//return biggest element
		return biggest.element;
	}

	/**
	 * Retruning false if heap is not empty
	 * 			 true if heap is empty
	 * 
	 * Method takes O(1) time. It's only taking a size and comparing it
	 */
	public boolean isEmpty() {
		return maxHeap.size == 0;
	}

	/**
	 * Return size of the heap, number of elements in the array
	 * 
	 * Method takes O(1) time. it's checking the size only and returning integer.
	 *
	 */
	public int size() {
		return maxHeap.size;
	}
}
