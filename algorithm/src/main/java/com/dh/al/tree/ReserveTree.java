package com.dh.al.tree;

import com.dh.lt.common.TreeNode;
import com.dh.lt.common.TreeNodeIterator;
import com.dh.lt.one._102_BinaryTreeLevelOrderTraversal;
import org.junit.Test;

import java.util.List;

/**
 * ClassName: ReserveTree
 *
 * @author lihong <intelcorecpu@gmail.com>
 * @Description: 反转一棵树
 * @date 2019/2/8 22:37
 */
public class ReserveTree {
    public void reserve(TreeNode root) {
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
        reserve(root);
        l1 = order.levelOrderV1(root);
        order.print(l1);
    }



}
