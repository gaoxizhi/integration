package net.gaox.hutool.tree.entity;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * <p> 树形数据处理 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-12 09:16
 */
@Slf4j
public class TreeNodeTest {
    private volatile static AtomicLong id = new AtomicLong(0);

    public static void main(String[] args) {
        List<BookMark> list = getBookMark();
        // 在后面buildSingle中会被丢弃
        // list.get(50).setParentId(200L);
        List<TreeNode<Long>> treeNodes = toTreeNode(list);
        Tree<Long> idTree = TreeUtil.buildSingle(treeNodes, 0L);

        String idList = list.stream().map(BookMark::getId)
                .map(s -> String.format("%03d", s))
                .collect(Collectors.joining(", "));
        log.info("id        = {}", idList);
        String parentIds = list.stream().map(BookMark::getParentId)
                .map(s -> String.format("%03d", s))
                .collect(Collectors.joining(", "));
        log.info("parentIds = {}", parentIds);
        String printTree = getTreeStruct(idTree, "", true, true);
        log.info("printTree\n {}", printTree);
        log.info("toJson {}", JSON.toJSONString(idTree));
    }

    public static List<BookMark> getBookMark() {
        List<BookMark> list = new ArrayList<>(100);
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            BookMark bm = new BookMark();
            bm.setId(id.incrementAndGet());
            List<Long> ids = list.stream().map(BookMark::getId).collect(Collectors.toList());
            ids.add(0L);
            int index = random.nextInt(ids.size());
            bm.setParentId(ids.get(index));
            bm.setName("书签" + bm.getId());
            bm.setUrl("吼吼");
            list.add(bm);
        }

        return list;
    }

    public static List<TreeNode<Long>> toTreeNode(List<BookMark> list) {
        List<TreeNode<Long>> treeNodes = list.stream().map(bookMark -> {
            TreeNode<Long> n = new TreeNode<>();
            n.setId(bookMark.getId());
            n.setParentId(bookMark.getParentId());
            n.setName(bookMark.getName());
            return n;
        }).collect(Collectors.toList());
        return treeNodes;
    }

    /**
     * 类似Linux 'tree'命令的格式打印
     *
     * @param tree       tree
     * @param prefix     锁进
     * @param isLastNode
     */
    /**
     * 获取树形结构字符串
     *
     * @param tree        tree
     * @param prefix      前缀
     * @param isFirstNode 当前节点是否是父节点
     * @param isLastNode  当前节点是否是其父节点的最后一个子节点
     * @return 数型字符串
     */
    public static String getTreeStruct(Tree<Long> tree, String prefix, boolean isFirstNode, boolean isLastNode) {
        StringBuilder sb = new StringBuilder();
        // 打印带有最后一个子节点的适当前缀和标记的当前节点
        String marker = isLastNode ? "└── " : "├── ";
        if (isFirstNode) {
            sb.append("root");
        } else {
            sb.append(prefix).append(marker).append(tree.getName());
            if (null != tree.getParent().getParent()) {
                sb.append(" (parent=").append(getNodeParentPath(tree)).append(")");
            }
        }

        // Recursively print child nodes
        List<Tree<Long>> children = tree.getChildren();
        if (CollectionUtil.isNotEmpty(children)) {
            String newPrefix = prefix + (isLastNode ? "    " : "│   ");
            for (int i = 0; i < children.size(); i++) {
                isLastNode = i == children.size() - 1;
                String s = getTreeStruct(children.get(i), newPrefix, false, isLastNode);
                sb.append("\n").append(s);
            }
        }
        return sb.toString();
    }

    /**
     * 获取父节点
     *
     * @param tree tree
     * @return 父节点
     */
    public static String getParentPath(Tree<Long> tree) {
        if (null == tree) {
            return null;
        }
        if (null == tree.getParentId()) {
            return null;
        }
        Tree<Long> parent = tree.getParent();
        CharSequence name = parent.getName();
        if (null == name) {
            return null;
        }
        String parents = getParentPath(parent);
        if (null != parents) {
            name = name + "-->" + parents;
        }
        return name.toString();
    }

    /**
     * 获取节点父路径
     *
     * @param tree tree
     * @return 节点父路径
     */
    public static String getNodeParentPath(Tree<Long> tree) {
        if (null == tree) {
            return "";
        }
        // 获取父节点
        tree = tree.getParent();
        Stack<String> pathStack = new Stack<>();
        while (tree != null) {
            CharSequence name = tree.getName();
            if (name != null) {
                pathStack.push(name.toString());
            }
            tree = tree.getParent();
        }
        return pathStack.isEmpty() ? "" : pathStack.toString();
    }

}
