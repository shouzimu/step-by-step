package com.dh.lt.zero;


import com.dh.lt.common.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/validate-binary-search-tree/
 * 验证输入的数据是否是二叉搜索树
 * 什么是二叉搜索树：
 * 1、若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
 * 2、若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
 * 3、任意节点的左、右子树也分别为二叉查找树；
 * 4、没有键值相等的节点
 * <p>
 * 所以二叉搜索树 中序遍历是一个升序的数据(左子树->根->右子树)
 * <p>
 * 解法一：
 * 中序遍历树，检测结果是否为升序的序列，如果是则为二叉搜索树，否则不是
 * <p>
 * <p>
 * 解法二：
 */
public class _98_ValidateBinarySearchTree {

    public boolean isValidBST(TreeNode root) {
        List<Integer> valusList = new ArrayList<>();
        inOrder(root, valusList);
        boolean bst = true;
        for (int i = 1; i < valusList.size(); i++) {
            if (valusList.get(i) <= valusList.get(i - 1)) {
                bst = false;
                break;
            }
        }
        return bst;
    }

    void inOrder(TreeNode node, List<Integer> valList) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            inOrder(node.left, valList);
        }
        valList.add(node.val);
        if (node.right != null) {
            inOrder(node.right, valList);
        }
    }


    @Test
    public void testIsValidBST() {
        TreeNode root = new TreeNode(5);

        TreeNode l1 = new TreeNode(5);
        TreeNode r1 = new TreeNode(4);
        root.left = l1;
       // root.right = r1;

        TreeNode l1c_L = new TreeNode(1);
        TreeNode l1c_R = new TreeNode(3);
        // l1.left = l1c_L;
        //l1.right = l1c_R;

        TreeNode r1c_L = new TreeNode(3);
        TreeNode r1c_R = new TreeNode(6);
        r1.left = r1c_L;
        r1.right = r1c_R;

        System.out.println(isValidBST(root));
    }
}
