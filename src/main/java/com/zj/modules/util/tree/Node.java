package com.zj.modules.util.tree;

import java.io.Serializable;
import java.util.List;

public class Node implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private Long id;//公司id
   
    private String name;//公司名字
    
    private Long parentId;
    
    private Integer level;
   
    private List<Node> children; //父节点的子类
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
    
    

}