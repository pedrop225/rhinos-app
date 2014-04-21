package com.desktop.rhinos.structures.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

public class RhNode<T> implements TreeNode {
	
	protected T data;
	protected ArrayList<RhNode<T>> children;
	
	public RhNode() {
		children = new ArrayList<RhNode<T>>();
	}
	
	public RhNode(T data) {
		this();
		this.data = data;
	}
	
	public T getData() {
		return data;
	}

	@Override
	public Enumeration<RhNode<T>> children() {
		return new Vector<RhNode<T>>(children).elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return (TreeNode)children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		//TODO
		return null;
	}

	@Override
	public boolean isLeaf() {
		return (children.size() == 0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		RhNode<T> n;
		try {
			n = (RhNode<T>)obj;
		}
		catch (ClassCastException e) {
			return false;
		}
		return data.equals(n.data);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}
