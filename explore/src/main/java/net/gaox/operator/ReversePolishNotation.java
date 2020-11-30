package net.gaox.operator;

import java.util.Stack;
import java.util.stream.Collectors;

/**
 * <p> 栈数据结构 </p>
 * 以四则运算为例
 *
 * @author gaox·Eric
 * @date 2019/10/9 11:13
 */

public class ReversePolishNotation {
    public static void main(String[] args) {
        //测试用例
        /*      表达式           |     逆波兰式
         * 1+2*3-4*5-6+7*8-9    |   123*+45*-6-78*+9-
         * 6*(5+(2+3)*8+3)      |   6523+8*+3+*
         * a+b*c+(d*e+f)*g      |   abc*+de*f+g*f
         */

        // 解释效果
        // abcd*-*e+fg/hij*+k-*-
        String str = "a*(b-c*d)+e-f/g*(h+i*j-k)";
        //运算符
        Stack<Character> operators = new Stack<>();
        //输出结果
        Stack output = new Stack();
        rpn(operators, output, str);
        System.out.println(output.stream().map(Object::toString).collect(Collectors.joining("")));
    }

    private static void rpn(Stack<Character> operators, Stack output, String str) {
        char[] chars = str.toCharArray();
        int pre = 0;
        //是否为数字（只要不是运算符，都是数字），用于截取字符串
        boolean digital;
        int len = chars.length;
        // 左括号的数量
        int bracket = 0;

        for (int i = 0; i < len; ) {
            pre = i;
            digital = Boolean.FALSE;
            //截取数字
            while (i < len && !OperatorEnum.isOperator(chars[i])) {
                i++;
                digital = Boolean.TRUE;
            }

            if (digital) {
                output.push(str.substring(pre, i));
            } else {
                //运算符
                char o = chars[i++];
                if (o == '(') {
                    bracket++;
                }
                if (bracket > 0) {
                    if (o == ')') {
                        while (!operators.empty()) {
                            char top = operators.pop();
                            if (top == '(') {
                                break;
                            }
                            output.push(top);
                        }
                        bracket--;
                    } else {
                        //如果栈顶为 ( ，则直接添加，不顾其优先级
                        //如果之前有 ( ，但是 ( 不在栈顶，则需判断其优先级，如果优先级比栈顶的低，则依次出栈
                        while (!operators.empty() && operators.peek() != '(' && OperatorEnum.cmp(o, operators.peek()) <= 0) {
                            output.push(operators.pop());
                        }
                        operators.push(o);
                    }
                } else {
                    while (!operators.empty() && OperatorEnum.cmp(o, operators.peek()) <= 0) {
                        output.push(operators.pop());
                    }
                    operators.push(o);
                }
            }

        }
        //遍历结束，将运算符栈全部压入output
        while (!operators.empty()) {
            output.push(operators.pop());
        }
    }
}

