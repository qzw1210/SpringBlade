package com.smallchill.common.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 节点数据封装
 */
public class TreeNode {

	/**
	 * 节点id
	 */
	private String id;

	/**
	 * 父节点id
	 */
	private String parentId;

	/**
	 * 序号
	 */
	private String num;
	
	/**
	 * 节点名称
	 */
	private String name;

	/**
	 * 是否上级节点
	 */
	private boolean isParent;
	
	/**
	 * 是否有上级节点
	 */
	private boolean hasParent;

	/**
	 * 是否选中
	 */
	private boolean checked;

	/**
	 * 是否选中
	 */
	private boolean nocheck;

	/**
	 * 节点图标
	 */
	private String icon;

	/**
	 * 子节点数据
	 */
	private List<TreeNode> children;
	
	/**
	 * 父节点数据
	 */
	private List<TreeNode> parent;

	private List<TreeNode> linkedList = new ArrayList<TreeNode>();
	private List<TreeNode> linkedListP = new ArrayList<TreeNode>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	
	public boolean hasParent() {
		return hasParent;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public List<TreeNode> getParent() {
		return parent;
	}

	public void setParent(List<TreeNode> parent) {
		this.parent = parent;
	}

	
	public void buildNodes(List<TreeNode> nodeList){
		for (TreeNode treeNode : nodeList) {
			List<TreeNode> linkedList = treeNode.findChildNodes(nodeList, treeNode.getId());
			List<TreeNode> linkedListp = treeNode.findParentNodes(nodeList, treeNode.getParentId());
			if (linkedList.size() > 0) {
				treeNode.setIsParent(true);
				treeNode.setChildren(linkedList);
			}
			if(linkedListp.size() > 0) {
				treeNode.setHasParent(true);
				treeNode.setParent(linkedListp);
			}
		}
	}
	
	public List<TreeNode> findChildNodes(List<TreeNode> list, Object parentId) {
		if (list == null && parentId == null)
			return null;
		for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
			TreeNode node = (TreeNode) iterator.next();
			// 根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (node.getParentId() != "0"
					&& parentId.toString().equals(node.getParentId())) {
				recursionFn(list, node);
			}
		}
		return linkedList;
	}
	
	public List<TreeNode> findParentNodes(List<TreeNode> list, Object childId) {
		if (list == null && childId == null)
			return null;
		for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
			TreeNode node = (TreeNode) iterator.next();
			if (childId.toString().equals(node.getId())) {
				recursionFnP(list, node);
			}
		}
		return linkedListP;
	}
	
	

	private void recursionFn(List<TreeNode> list, TreeNode node) {
		List<TreeNode> childList = getChildList(list, node);// 得到子节点列表
		if (childList.size() > 0) {// 判断是否有子节点
			linkedList.add(node);
			Iterator<TreeNode> it = childList.iterator();
			while (it.hasNext()) {
				TreeNode n = (TreeNode) it.next();
				recursionFn(list, n);
			}
		} else {
			linkedList.add(node);
		}
	}
	
	private void recursionFnP(List<TreeNode> list, TreeNode node) {
		List<TreeNode> parentList = getParentList(list, node);// 得到父节点列表
		if (parentList.size() > 0) {// 判断是否有父节点
			linkedListP.add(node);
			Iterator<TreeNode> it = parentList.iterator();
			while (it.hasNext()) {
				TreeNode n = (TreeNode) it.next();
				recursionFnP(list, n);
			}
		} else {
			linkedListP.add(node);
		}
	}

	// 得到子节点列表
	private List<TreeNode> getChildList(List<TreeNode> list, TreeNode node) {
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		Iterator<TreeNode> it = list.iterator();
		while (it.hasNext()) {
			TreeNode n = (TreeNode) it.next();
			if (n.getParentId().equals(node.getId())) {
				nodeList.add(n);
			}
		}
		return nodeList;
	}
	
	// 得到子节点列表
	private List<TreeNode> getParentList(List<TreeNode> list, TreeNode node) {
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		Iterator<TreeNode> it = list.iterator();
		while (it.hasNext()) {
			TreeNode n = (TreeNode) it.next();
			if (n.getId().equals(node.getParentId())) {
				nodeList.add(n);
			}
		}
		return nodeList;
	}


}
