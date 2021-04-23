package net.gaox.solution;

import java.util.Arrays;

/**
 * <p> 简单实现二叉树 </p>
 *
 * @author gaox·Eric
 * @date 2021/3/17 15:52
 */
public class TreeDemo {

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.add("G");
        binaryTree.add("A");
        binaryTree.add("O");
        binaryTree.add("X");
        binaryTree.add("I");
        binaryTree.add("Z");
        System.out.println(Arrays.toString(binaryTree.toArray()));
    }
}

