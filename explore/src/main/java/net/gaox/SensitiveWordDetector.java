package net.gaox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-08-25 14:29
 */

public class SensitiveWordDetector {

    private static final String IS_END_FLAG = "isEnd";
    private static final String END_VALUE = "1";

    // 定义DFA图的节点
    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        boolean isEnd = false;

        public void setEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }
    }

    private Node root = new Node();

    /**
     * 构建DFA图
     */
    public void buildDFA(String[] sensitiveWords) {
        for (String word : sensitiveWords) {
            Node node = root;
            for (char c : word.toCharArray()) {
                node.children.putIfAbsent(c, new Node());
                node = node.children.get(c);
            }
            node.setEnd(true);
        }
    }

    /**
     * 查找文本中的敏感词
     */
    public int findSensitiveWord(String text, int startIndex, int matchType) {
        if (startIndex > text.length()) {
            return 0;
        }

        boolean isSensitiveWord = false;
        int matchLength = 0;
        Node currentNode = root;

        for (int i = startIndex; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            currentNode = currentNode.children.get(currentChar);

            if (currentNode != null) {
                matchLength++;

                if (currentNode.isEnd) {
                    isSensitiveWord = true;
                    if (matchType == 1) { // 最短匹配
                        break;
                    }
                }
            } else {
                break;
            }
        }

        if (matchLength < 2 || !isSensitiveWord) {
            matchLength = 0;
        }

        return matchLength;
    }

    /**
     * 获取文本中所有敏感词的个数和总位数
     */
    public int[] countSensitiveWords(String text) {
        int count = 0;
        int totalLength = 0;
        int index = 0;
        while (index < text.length()) {
            int matchLength = findSensitiveWord(text, index, 1); // 使用最短匹配
            if (matchLength > 0) {
                count++;
                totalLength += matchLength;
                index += matchLength;
            } else {
                index++;
            }
        }
        return new int[]{count, totalLength};
    }

    /**
     * 替换文本中的敏感词为星号（*）
     */
    public String replaceSensitiveWords(String text) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            int matchLength = findSensitiveWord(text, index, 1); // 使用最短匹配
            if (matchLength > 0) {
                result.append("**");
                index += matchLength;
            } else {
                result.append(text.charAt(index));
                index++;
            }
        }
        return result.toString();
    }

    /**
     * 获取文本中的所有敏感词及其起始位置
     */
    public List<String[]> getSensitiveWordsWithPositions(String text) {
        List<String[]> sensitiveWords = new ArrayList<>();
        int index = 0;
        while (index < text.length()) {
            int matchLength = findSensitiveWord(text, index, 1); // 使用最短匹配
            if (matchLength > 0) {
                String sensitiveWord = text.substring(index, index + matchLength);
                sensitiveWords.add(new String[]{sensitiveWord, Integer.toString(index)});
                index += matchLength;
            } else {
                index++;
            }
        }
        return sensitiveWords;
    }

    public static void main(String[] args) {
        SensitiveWordDetector detector = new SensitiveWordDetector();
        String[] sensitiveWords = {"bad", "word", "test"};
        detector.buildDFA(sensitiveWords);

        String text = "This is a bad test word";
        int[] counts = detector.countSensitiveWords(text);
        System.out.println("Sensitive words count: " + counts[0]);
        System.out.println("Total length of sensitive words: " + counts[1]);

        String replacedText = detector.replaceSensitiveWords(text);
        System.out.println("Replaced text: " + replacedText);

        List<String[]> sensitiveWordsWithPositions = detector.getSensitiveWordsWithPositions(text);
        System.out.println("Sensitive words and positions:");
        for (String[] info : sensitiveWordsWithPositions) {
            System.out.println("Word: " + info[0] + ", Position: " + info[1]);
        }
    }
}
