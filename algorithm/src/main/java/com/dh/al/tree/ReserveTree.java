package com.dh.al.tree;

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




}
