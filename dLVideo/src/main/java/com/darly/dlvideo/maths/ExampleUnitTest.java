package com.darly.dlvideo.maths;


import java.util.Random;


/**
 * @author zhangyh2 Mathos 下午4:53:10 TODO 数字黑洞理念
 *         任意数字，偶数个数，奇数个数，位数，继续下去，逃脱不了123；基本测试下来，3步就可以找到123
 */
public class ExampleUnitTest {

    private int i = 0;

    /*@Test*/
    public void addition_isCorrect() {
        findResult(new Random().nextInt(65534) + ""
                + new Random().nextInt(65534) + new Random().nextInt(65534));
        hailstone(new Random().nextInt(65534) * new Random().nextInt(65534));
    }

    private String findResult(String arg) {
        int uneven = 0;
        int even = 0;
        int extent = 0;
        for (char car : arg.toCharArray()) {
            extent++;
            try {
                int ca = Integer.parseInt(car + "");
                if (ca % 2 == 0) {
                    // 偶数
                    even++;
                } else {
                    uneven++;
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        String res = even + "" + uneven + "" + extent;
        System.out.println(arg + "----" + res);
        if ("123".equals(res)) {
            return res;
        } else {
            // 递归搞定
            return findResult(res);
        }
    }

    /**
     * 下午5:25:39
     *
     * @author zhangyh2 TODO 冰雹猜想
     */
    private long hailstone(long args) {
        System.out.println("第" + i + "次生成的数据，继续进行运算" + args);
        if (args % 2 == 0) {
            // 偶数
            args /= 2;
        } else {
            args = 3 * args + 1;
        }
        i++;
        if (args == 1) {
            System.out.println("第" + i + "次生成的数据，继续进行运算" + args);
            return args;
        } else {
            return hailstone(args);
        }
    }
}
