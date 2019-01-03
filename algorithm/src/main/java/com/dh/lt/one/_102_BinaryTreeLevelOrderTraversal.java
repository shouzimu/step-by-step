package com.dh.lt.one;

import com.dh.lt.common.TreeNode;
import com.dh.lt.common.TreeNodeIterator;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 按层级别遍历二叉树
 *
 * @link https://leetcode.com/problems/binary-tree-level-order-traversal/
 * <p>
 * <p>
 * 输入 [3,9,20,null,null,15,7]
 * ----3
 * -- / \
 * --9  20
 * ____/  \
 * --15   7
 * <p>
 * 输出如下内容，每一层的元素放到一个数组当中
 * [
 * --  [3],
 * --  [9,20],
 * --  [15,7]
 * ]
 */
public class _102_BinaryTreeLevelOrderTraversal {

    /**
     * @param root
     * @return
     * @see com.dh.lt.common.TreeNodeIterator
     * 我们在TreeNodeIterator中实现的bfs就是按照层级遍历，但是怎么做到每一层放到一个数组中呢
     * 可以这么处理，遍历的时候将下一层全部放到队列中中
     * <p>
     * 这是第一版，使用了两个队列来存储内容，性能较低，在while中没想好怎么处理每一层的情况
     * 看了极客时间后才反应过来，实际上在写queue.size的时候就可以通过这个来区分这一层结束了
     * 这样就可以省掉一个队列
     */
    public List<List<Integer>> levelOrderV1(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (null == root) {
            return res;
        }
        Queue<TreeNode> currentQueue = new ArrayDeque<>();
        Queue<TreeNode> nextQueue = new ArrayDeque<>();
        currentQueue.add(root);
        while (!currentQueue.isEmpty() || !nextQueue.isEmpty()) {
            if (!currentQueue.isEmpty()) {
                List<Integer> levelList = new ArrayList<>(currentQueue.size());
                while (!currentQueue.isEmpty()) {
                    TreeNode t = currentQueue.poll();
                    if (null != t.left) {
                        nextQueue.add(t.left);
                    }
                    if (null != t.right) {
                        nextQueue.add(t.right);
                    }
                    levelList.add(t.val);
                }
                res.add(levelList);
            } else {
                List<Integer> levelList = new ArrayList<>(currentQueue.size());
                while (!nextQueue.isEmpty()) {
                    TreeNode t = nextQueue.poll();
                    if (null != t.left) {
                        currentQueue.add(t.left);
                    }
                    if (null != t.right) {
                        currentQueue.add(t.right);
                    }
                    levelList.add(t.val);
                }
                res.add(levelList);
            }
        }
        return res;
    }

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
            res.add(levelList);
        }
        return res;
    }

    @Test
    public void testLevelOrder() {
        TreeNode root = TreeNodeIterator.initTestTree();
        levelOrder(root);
    }
}
