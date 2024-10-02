package net.gaox.tree;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p> 树工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-07-04 10:06
 */
@Slf4j
public class TreeSearchUtil {
    public static TreeNode findTreeContainingId(List<TreeNode> trees, int targetId) {
        return trees.stream()
                .filter(tree -> treeContainsId(tree, targetId))
                .findFirst().orElse(null);
    }

    private static boolean treeContainsId(TreeNode node, int targetId) {
        if (node.getId() == targetId) {
            return true;
        }
        return node.getChildren().stream().anyMatch(child -> treeContainsId(child, targetId));
    }

    public static TreeNode cloneTreeFromForest(List<TreeNode> forest, int targetId) {
        // 复制整棵树
        return forest.stream()
                .filter(tree -> treeContainsId(tree, targetId)).findFirst()
                .map(TreeSearchUtil::deepCopyNode).orElse(null);
    }

    private static TreeNode deepCopyNode(TreeNode originalNode) {
        TreeNode newNode = new TreeNode(originalNode.getId() + 10000, originalNode.getName());
        originalNode.getChildren().stream().map(TreeSearchUtil::deepCopyNode).forEach(newNode::addChild);
        return newNode;
    }

    public static void printSubtree(TreeNode node) {
        log.info("node = {}", JSONObject.toJSONString(node));
        for (TreeNode child : node.getChildren()) {
            printSubtree(child);
        }
    }

}
