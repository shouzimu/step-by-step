package com.dh.lt;

import com.dh.lt.common.TreeNode;
import org.junit.Test;

import java.util.List;

/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 * <p>
 * 235. Lowest Common Ancestor of a Binary Search Tree
 */
public class _235_LowestCommonAncestorBST {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return p;
    }

    public boolean pathNode(TreeNode root, TreeNode node, List<TreeNode> path) {
        if(root == null){
            return false;
        }
        if(root == node){
            return true;
        }
        return false;
    }

    @Test
    public void testLowestCommonAncestor() {
        TreeNode root = new TreeNode(4);

        TreeNode l1 = new TreeNode(2);
        TreeNode r1 = new TreeNode(6);
        root.left = l1;
        root.right = r1;

        TreeNode l1c_L = new TreeNode(1);
        TreeNode l1c_R = new TreeNode(3);
        l1.left = l1c_L;
        l1.right = l1c_R;

        TreeNode r1c_L = new TreeNode(5);
        TreeNode r1c_R = new TreeNode(7);
        r1.left = r1c_L;
        r1.right = r1c_R;
    }
}
