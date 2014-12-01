package com.keitsen.demo.basic.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用树结构
 * @author Miles XP
 *
 */
public class Tree {

	private String id;
	
	private String text;
	
	private String url;
	
	@SuppressWarnings("unused")
	private boolean leaf;
	
	private List<Tree> children = new ArrayList<Tree>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isLeaf() {
		return !(this.children.size()>0);
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	
	
}
