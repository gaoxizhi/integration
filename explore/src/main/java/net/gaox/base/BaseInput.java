package net.gaox.base;

import java.util.Scanner;

/**
 * @Description: <p> 基本输入方法 </p>
 * @ClassName BaseInput
 * @Author: gaox·Eric
 * @Date: 2019/3/31 16:13
 */
public class BaseInput {

    public static void main(String[] args) {

        String[] headLine = new String[]{"用户名", "年龄", "地址", "性别", "密码"};

        User user = new User();
        Scanner sc = new Scanner(System.in);
        System.out.println("下面请输入一个用户信息，格式：用户名，年龄，地址，性别,密码。");
        for (int i = 0; i < headLine.length; i++) {
            System.out.println("请输入：  " + headLine[i]);
            String str = sc.nextLine();
            switch (i) {
                case 0:
                    user.setName(str);
                    break;
                case 1:
                    user.setAge(Integer.parseInt(str));
                    break;
                case 2:
                    user.setAddr(str);
                    break;
                case 3:
                    user.setSex(str);
                    break;
                case 4:
                    user.setPasswd(str);
                    break;

                default:
                    break;
            }
        }
        user.sayPaly();
    }
}
