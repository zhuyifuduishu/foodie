package com.whu.utils;

import java.math.BigDecimal;

/**
 * @author Created By MrXi on 2020/1/8
 */
public class CurrencyUtils {


    /**
     * 分转元，带有小数点
     * @param amount 要转换的金额
     * @return 返回转换后的结果
     * @author MrXi
     */
    public static String getFen2YuanWithPoint(Integer amount) {
        return BigDecimal.valueOf(amount).divide(new BigDecimal(100)).toString();
    }

    /**
     * 分转元，不带小数，只取整数位
     * @param amount 要转换的金额
     * @return 返回转换后的结果
     * @author MrXi
     */
    public static Integer getFen2Yuan(Integer amount) {
        return BigDecimal.valueOf(amount).divide(new BigDecimal(100)).intValue();
    }

    /**
     * 元转分，返回整数
     * @param amount 要转换的金额
     * @return 返回转换后的结果
     * @author MrXi
     */
    public static Integer getYuan2Fen(Integer amount) {
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).intValue();
    }

    /**
     * 元转分，返回整数
     * @param amount 要转换的金额
     * @return 返回转换后的结果
     * @author MrXi
     */
    public static Integer getYuan2Fen(String amount) {
        return BigDecimal.valueOf(Double.parseDouble(amount)).multiply(new BigDecimal(100)).intValue();
    }

//	public static void main(String[] args) {
//		System.out.println(new CurrencyUtils().getYuan2Fen("12.366"));
//	}

}
