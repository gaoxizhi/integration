package net.gaox.tree;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 树查找 </p>
 *
 * @author gaox·Eric
 * @date 2024-07-04 10:00
 */
@Slf4j
public class TreeSearch {

    public static void main(String[] args) {

        // 创建树节点
        TreeNode root1 = new TreeNode(1, "Root1");
        TreeNode child1_1 = new TreeNode(2, "Child 1-1");
        TreeNode child1_2 = new TreeNode(3, "Child 1-2");
        TreeNode grandChild1_2_1 = new TreeNode(4, "Grand Child 1-2-1");
        TreeNode grandChild1_2_2 = new TreeNode(5, "Grand Child 1-2-2");
        TreeNode root2 = new TreeNode(6, "Root2");
        TreeNode child2_1 = new TreeNode(7, "Child 2-1");
        TreeNode grandChild2_1_1 = new TreeNode(8, "Grand Child 2-1-1");
        TreeNode greatGrandChild2_1_1_1 = new TreeNode(9, "Great Grand Child 2-1-1-1");

        // 构建树结构
        root1.addChild(child1_1);
        root1.addChild(child1_2);
        child1_2.addChild(grandChild1_2_1);
        child1_2.addChild(grandChild1_2_2);
        root2.addChild(child2_1);
        child2_1.addChild(grandChild2_1_1);
        grandChild2_1_1.addChild(greatGrandChild2_1_1_1);

        // 创建树的列表
        List<TreeNode> treeList = new ArrayList<>();
        treeList.add(root1);
        treeList.add(root2);


        // 给定ID
        int targetId = 9;

        // 查找包含给定ID的树
        TreeNode containingTree = TreeSearchUtil.findTreeContainingId(treeList, targetId);

        if (containingTree != null) {
            log.info("The target ID is in the tree: {}", JSONObject.toJSONString(containingTree));
        } else {
            log.info("The target ID was not found in any tree.");
        }


        // 查找包含给定ID的树并复制子树
        TreeNode clonedSubtreeRoot = TreeSearchUtil.cloneTreeFromForest(treeList, targetId);

        if (clonedSubtreeRoot != null) {
            log.info("Cloned subtree root: {}", JSONObject.toJSONString(clonedSubtreeRoot));
            TreeSearchUtil.printSubtree(clonedSubtreeRoot);
        } else {
            log.info("No subtree found with the given ID.");
        }
    }
}

