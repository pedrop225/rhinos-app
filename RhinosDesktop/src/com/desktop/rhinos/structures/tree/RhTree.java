package com.desktop.rhinos.structures.tree;

public class RhTree<T> {

	private RhNode<T> root;
	
	public RhTree() {}
	
	public RhTree(T data) {
		setRoot(data);
	}
	
	public void setRoot(T data) {
		root = new RhNode<T>(data);
	}
	
	public void add(T p_data, T data) {
		RhNode<T> node = new RhNode<T>(data);
		RhNode<T> parent = new RhNode<T>(p_data);
	
		parent = (parent.equals(root)) ? root : search(parent, root);
		parent.children.add(node);
	}
	
	public RhNode<T> getRoot() {
		return root;
	}
	
	private RhNode<T> search(RhNode<T> p, RhNode<T> root) {
		
		RhNode<T> r = null;
		
		int ind = root.children.indexOf(p);
		if (ind >= 0)
			r = (RhNode<T>)root.children.get(ind);
			
		else {
			for (int i = 0; i < root.children.size(); i++) {
				r = search(p, (RhNode<T>)root.children.get(i));
				if (r != null)
					break;
			}
		}
			
		return r;
	}
}
