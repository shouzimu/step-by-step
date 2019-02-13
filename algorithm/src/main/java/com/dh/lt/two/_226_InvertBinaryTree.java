package com.dh.lt.two;

import com.dh.lt.common.TreeNode;
import com.dh.lt.common.TreeNodeIterator;
import com.dh.lt.one._102_BinaryTreeLevelOrderTraversal;
import org.junit.Test;

import java.util.List;

/**
 * ClassName: _226_InvertBinaryTree
 *
 * @author lihong <intelcorecpu@gmail.com>
 * @Description:
 * 226. Invert Binary Tree
 * https://leetcode.com/problems/invert-binary-tree/
 * @date 2019/2/13 16:29
 */
public class _226_InvertBinaryTree {
    public TreeNode invertTree(TreeNode root) {
        reserve(root);
        return root;
    }

    /**
     * 运行结果
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
     * Memory Usage: 35.7 MB, less than 0.91% of Java online submissions for Invert Binary Tree.
     *
     * 速度还可以，空间还需要继续优化
     * @param root
     */
    private void reserve(TreeNode root) {
        if (root == null) {
            return;
        }
        //交换左右孩子
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = right;
        root.right = left;
        reserve(left);
        reserve(right);
    }

    @Test
    public void testReserve() {
        TreeNode root = TreeNodeIterator.initTestTree();
        _102_BinaryTreeLevelOrderTraversal order = new _102_BinaryTreeLevelOrderTraversal();
        List<List<Integer>> l1 = order.levelOrderV1(root);
        order.print(l1);
        System.out.println();
        root = invertTree(root);
        l1 = order.levelOrderV1(root);
        order.print(l1);
    }

}