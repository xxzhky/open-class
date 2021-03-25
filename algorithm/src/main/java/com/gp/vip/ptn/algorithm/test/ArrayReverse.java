package com.gp.vip.ptn.algorithm.test;

/**
 * @author: Fred
 * @date: 2020/8/23 10:58 上午
 * @description: (类的描述)
 */
public class ArrayReverse {

    public static void main(String [] args)
    {
        int [][] TestMatrix = { { 1, 3, 5, 7 }, { 2, 4, 6, 8 } } ;
        int [][] MatrixC = Transpose(TestMatrix, 2, 4) ;


        myPrint(TestMatrix);
        System.out.println("-------转置后---------") ;
        myPrint(MatrixC);
    }


    public static int [][] Transpose(int [][] Matrix, int Line, int List) {
        int [][] MatrixC = new int [List] [Line] ;
        for (int i = 0; i < Line; i++) {
            for (int j = 0; j < List; j++) {
                MatrixC[j][i] = Matrix[i][j] ;
            }
        }
        return MatrixC ;
    }


    public static void myPrint(int temp [][]) {
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                System.out.print(temp[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
