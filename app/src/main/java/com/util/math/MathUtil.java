package com.util.math;

import android.graphics.Point;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 数学计算学
 * @author YeFeiHu
 *
 */
public class MathUtil {

	/**
	 * 计算两点之间的距离
	 * @param posX
	 * @param posY
	 * @param toX
	 * @param toY
	 * @return
	 */
	public static double computeDeuceDistance(int posX, int posY, int toX, int toY) {
		return Math.sqrt((posX - toX)*(posX - toX) + (posY - toY)*(posY - toY));
	}


	/**
	 * 计算圆上的某点坐标
	 * <p>要求知道圆心 坐标  半径 和 坐标角度
	 * @param angle 角度
	 * @param radius 半径
	 * @return
	 */
    public Point computeCoordinates(int centerX, int centerY, int angle, int radius) {
            int x = (int) (centerX
                    + (float) (radius * Math.cos(Math.toRadians(angle))));
            int y = (int) (centerY
                    + (float) (radius * Math.sin(Math.toRadians(angle))));
            return new Point(x,y);
    }



	 /**
     * 计算某点的角度
     *  <p>要求知道圆心坐标 和点的坐标 没有负数
     */
    public static int computeCurrentAngle(int centerX, int centerY, float x, float y) {
        float distance = (float) Math
                .sqrt(((x - centerX) * (x -centerX) + (y - centerY)
                        * (y - centerY)));
        int degree = (int) (Math.acos((x -centerX) / distance) * 180 / Math.PI);
        if (y < centerY) {
            degree = -degree;
        }
        //Log.d("RoundSpinView", "x:" + x + ",y:" + y + ",degree:" + degree);
        return degree;
    }


    /**
     * 计算某点的角度
     *  <p>要求知道圆心坐标 和点的坐标
     */
    public static int computeCurrent4Angle(int centerX, int centerY, float x, float y) {
        float distance = (float) Math
                .sqrt(((x - centerX) * (x -centerX) + (y - centerY)
                        * (y - centerY)));
        int degree = (int) (Math.acos((x -centerX) / distance) * 180 / Math.PI);
        //Log.d("RoundSpinView", "x:" + x + ",y:" + y + ",degree:" + degree);
     //   Log.v("LOG", "角度== "+degree);
        return degree;
    }




    /**
     * 把字节数转换成String单位
     * @param fileByteLength
     * @return
     * 如 传入 1024 返回 1.00MB
     */
    public static String convertUnit(long fileByteLength){

        if(fileByteLength <= 1024){
            return fileByteLength+"B";
        }else if(fileByteLength/1024.00 <= 1024){
            float size = (float) (fileByteLength/1024.00);
            return new DecimalFormat("#.##").format(size)+"KB";
        }else if(fileByteLength/1024.00/1024 <= 1024){
            float size = (float) (fileByteLength/1024.00/1024);
            return new DecimalFormat("#.##").format(size)+"MB";
        }else if(fileByteLength/(1024.00*1024*1024) <= 1024){
            float size = (float) (fileByteLength/1024.00/1024/1024);
            return new DecimalFormat("#.##").format(size)+"GB";
        }else{
            float size = (float)(fileByteLength/1024.00/1024/1024/1024);
            return new DecimalFormat("#.##").format(size)+"TB";
        }
    }


    /**
     * 解析N二次方的参数值
     * @param value
     * 如 194 解析为 128 * 64 * 2 的参数值
     * @return
     */
    public static List<Integer> getParsingN2Param(int value) {
         mList = new ArrayList<Integer>();

        List<Integer> list = mList;
        mList = null;
        return list;
    }


    private static List<Integer> mList;

    /**
     * @param size
     * @param n
     * @see #getParsingN2Param(int)
     */
    private static void parsingN2Param(int size, int n) {
        if(size < n) {
            n = n/2;
            int number = n;
            //System.out.println(number);
            mList.add(number);
            size = size -n;
            parsingN2Param(size, 1);
        }else if(size == n){
            //System.out.println(size);
            mList.add(size);
        }else {
            parsingN2Param(size, n*2);
        }
    }

}
