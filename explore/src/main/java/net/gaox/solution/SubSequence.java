package net.gaox.solution;

/**
 * <p> 判断子序列 </p>
 * 原字符串通过删除部分内容，匹配到子字符串
 *
 * @author gaox·Eric
 * @date 2021/3/25 14:16
 */
public class SubSequence {

    public static void main(String[] args) {
        final String source = "plan";
        final String target = "polarization";
        final SubSequence a = new SubSequence();
        a.isSubsequence("at", "after");
        System.out.println();
    }

    /**
     * 动态规划
     * 状态：dp[i][j]为s的从头开始到i的子字符串是否为t从头开始到j的子字符串的子序列
     * <p>
     * 状态转移公式：
     * <p>
     * 1、当char[i]==char[j]时，则字符i一定是j的子序列，如果0~i-1子字符串是0~j-1子字符串的子序列，
     * 则dp[i][j]=true，所以dp[i][j] = dp[i-1][j-1]；
     * <p>
     * 2、当char[i]!=char[j]时，即判断当前0~i子字符串是否是0~j-1的子字符串的子序列，
     * 即dp[i][j] = dp[i][j - 1]（这里也就是移动j了，来找i对应的位置的元素和j-1时的位置的元素是否相等）。
     * 如ab，eabc，虽然s的最后一个字符和t中最后一个字符不相等，但是因为ab是eab的子序列，所以ab也是eabc的子序列
     * <p>
     * 3、初始化：空字符串一定是t的子字符串的子序列，所以dp[0][j]=true。
     * <p>
     * 4、结果：返回dp[sLen][tLen]。
     *
     * @param source 子字符串
     * @param target 原字符串
     * @return 是否匹配 {@link Boolean}
     */
    public boolean isSubsequenceDynamic(String source, String target) {

        int sLen = source.length(), tLen = target.length();
        if (sLen > tLen) {
            return false;
        }
        if (sLen == 0) {
            return true;
        }

        boolean[][] dp = new boolean[sLen + 1][tLen + 1];
        // 初始化
        for (int j = 0; j < tLen; j++) {
            // 空字符串一定是t的子字符串的子序列
            dp[0][j] = true;
        }

        //dp
        for (int i = 1; i <= sLen; i++) {
            for (int j = 1; j <= tLen; j++) {
                if (source.charAt(i - 1) == target.charAt(j - 1)) {
                    // 因为s.charAt(i - 1) == target.charAt(j - 1)，所以分别移动i和j的指针，接着判断下一个i和j对应的元素值是否相等
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 这里是s.charAt(i - 1) ！= target.charAt(j - 1)，所以移动j，i先不动，看看目前这个i对应的元素和j的下一个元素j-1的元素 是否相等
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }
        return dp[sLen][tLen];
    }

    /**
     * 遍历子串，将字符串从第i位置后当前字符个数累加
     *
     * @param source 子字符串
     * @param target 原字符串
     * @return 是否匹配 {@link Boolean}
     */
    public boolean isSubsequence(String source, String target) {
        int i = 0;
        for (char ch : source.toCharArray()) {
            while (i < target.length() && target.charAt(i) != ch) {
                i++;
            }
            i++;
        }
        return i <= target.length() ? true : false;
    }

    /**
     * java中非常好的一个方法，indexOf(char c,int m)意思是从第m位置开始寻找该索引，
     * 找到则返回该索引，否则返回-1，利用该特性我们通过对索引处理从而获得解。
     *
     * @param source 子字符串
     * @param target 原字符串
     * @return 是否匹配 {@link Boolean}
     */
    public boolean isSubsequenceStringIndexOf(String source, String target) {

        char[] arr = source.toCharArray();
        int j = -1;
        for (int i = 0; i < arr.length; i++) {
            j = target.indexOf(arr[i], j + 1);
            if (j == -1) {
                return false;
            }
        }
        return true;
    }

}
