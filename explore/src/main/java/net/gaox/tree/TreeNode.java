package net.gaox.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 树节点对象 </p>
 *
 * @author gaox·Eric
 * @date 2024-07-04 10:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    private int id;
    private String name;
    private List<TreeNode> children = new ArrayList<>();

    public TreeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

}
