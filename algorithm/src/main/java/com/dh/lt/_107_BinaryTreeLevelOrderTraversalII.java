package com.dh.lt;

import com.dh.lt.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
 * 这个题和102逻辑一样，都是按层遍历树，只是将层按照倒序返回
 * 所以我们可以利用List.add(0,value)来实现
 */
public class _107_BinaryTreeLevelOrderTraversalII {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (null == root) {
            return res;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            List<Integer> levelList = new ArrayList<>(queueSize);
            for (int i = 0; i < queueSize; i++) {
                TreeNode t = queue.poll();
                if (null != t.left) {
                    queue.add(t.left);
                }
                if (null != t.right) {
                    queue.add(t.right);
                }
                levelList.add(t.val);
            }
            res.add(0, levelList);
        }
        return res;
    }
}
