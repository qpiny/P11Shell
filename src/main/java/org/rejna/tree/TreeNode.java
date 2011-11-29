package org.rejna.tree;

import java.util.Iterator;
import java.util.Vector;

public class TreeNode<T> implements Iterable<TreeNode<T>> {
	private TreeNode<T> parent = null;
	private T data;
	private Vector<TreeNode<T>> children = new Vector<TreeNode<T>>();
	private int maxDepth = 0;
	private int depth = 0; // really usefull ?
	private int nleaf = 0;
	
	private TreeNode(TreeNode<T> parent, int maxDepth, int depth, T data, int nleaf) {
		this.parent = parent;
		this.maxDepth = maxDepth;
		this.depth = depth;
		this.data = data;
		this.nleaf = nleaf;
	}
	
	public static <T> TreeNode<T> newTree(T data) {
		return new TreeNode<T>(null, 0, 0, data, 1);
	}
	
	private void propagateDepth(int depth) {
		if (depth > maxDepth) {
			maxDepth = depth;
			if (parent != null)
				parent.propagateDepth(depth + 1);
		}
	}

	private void propagateNleaf(int nleaf) {
		this.nleaf += nleaf;
		if (parent != null)
			parent.propagateNleaf(nleaf);
	}
	
	public TreeNode<T> addChild(T data) {
		TreeNode<T> child = new TreeNode<T>(this, 0, depth + 1, data, 1);
		propagateDepth(1);
		if (children.size() != 0)
			propagateNleaf(1);
		children.add(child);
		return child;
	}
	
	public int getNleaf() {
		return nleaf;
	}
	
	public void removeChild(TreeNode<T> child) {
		children.remove(child);
		child.parent = null;
		propagateNleaf((children.size() == 0 ? 1 : 0) - child.nleaf);
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}

	@Override
	public Iterator<TreeNode<T>> iterator() {
		return children.iterator();
	}
	
	public int getMaxDepth() {
		return maxDepth;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<node depth=" + depth + " maxdepth=" + maxDepth + " nleaf=" + nleaf + " data=\"" + data.toString() + "\">");
		for (TreeNode<T> child : children)
			sb.append(child.toString());
		sb.append("</node>");
		return sb.toString();
	}
}
