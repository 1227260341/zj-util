package com.zj.modules.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @ClassName TreeUtile
 * @Description TODO
 * @Date 2018/10/30 0030 下午 11:29
 * @Version 1.0
 */
public class TreeUtil {
	/**
	 * @Author 
	 * @Description //TODO 
	 * @Date 2018/10/31 0031 上午 12:02
	 * @Param nodes :所有的节点列表
	 * @return
	 */
    public List data(List<Node> nodes) {
         ArrayList<Node> rootNode = new ArrayList<>();
	     for(Node node:nodes){
	          if(node.getParentId().equals("0")){
	                rootNode.add(node);
	          }
	     }
	     for(Node node:rootNode){
	         List<Node> child = getChild(String.valueOf(node.getParentId()), nodes);
	         node.setChildren(child);
	     }
	     return rootNode;
	}


    /**
     * @return
     * @Author
     * @Description //TODO 获取根节点的子节点
     * @Date 2018/10/30 0030 下午 11:37
     * @Param
     */
    public List<Node> getChild(String id, List<Node> allNode) {
        //存放子菜单的集合
        ArrayList<Node> listChild = new ArrayList<>();
        for (Node node : allNode) {
            if (node.getParentId().equals(id)) {
                listChild.add(node);
            }
        }
        //递归：
        for (Node node : listChild) {
            node.setChildren(getChild(String.valueOf(node.getParentId()), allNode));
        }
        if (listChild.size() == 0) {
            return null;
        }
        return listChild;
    }
}