package com.dh.lt.zero;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * 36. Valid Sudoku
 * <p>
 * https://leetcode.com/problems/valid-sudoku/
 * <p>
 * 每行必须包含数字1-9，不得重复。
 * 每列必须包含数字1-9，不得重复。
 * 网格的9个3x3子框中的每个子框必须包含数字1-9，不能重复。
 */
public class _36_ValidSudoku {


    /**
     * 定义3个 HashSet 分别存储每一行 每一列 和小子框中已存在的数
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        HashSet<String> rowSet = new HashSet<>();
        HashSet<String> columnSet = new HashSet<>();
        HashSet<String> childSet = new HashSet<>();
        if (null == board || board.length == 0) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    String row = i + "_" + board[i][j];
                    String column = j + "_" + board[i][j];
                    String child = 3*(i/3)+i/3 +"_"+ 3*(j/3)+j/3 +"_"+board[i][j];
                    //判断行列是否重复
                    if (!rowSet.add(row) || !columnSet.add(column) || !childSet.add(child)) {
                        return false;
                    }

                }
            }
        }
        return true;
    }


    @Test
    public void testIsValidSudoku() {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '4', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        boolean res = isValidSudoku(board);
        Assert.assertEquals(true, res);
    }
}
