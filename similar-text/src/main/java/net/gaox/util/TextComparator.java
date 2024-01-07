package net.gaox.util;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p> 文本相似度工具 </p>
 *
 * @author gaox·Eric
 * @date 2023-04-17 20:21
 */
@Slf4j
public class TextComparator {

    /**
     * 两两对比函数
     *
     * @param textA 文本A
     * @param textB 文本B
     * @return 相似度
     */
    public static Double getCosineSimilarity(String textA, String textB) {
        // 从文本中提取出关键词数组
        List<String> wordListA = extractWordFromText(textA);
        List<String> wordListB = extractWordFromText(textB);

        List<Double> vectorA = new ArrayList<>();
        List<Double> vectorB = new ArrayList<>();
        // 将关键词数组转换为词向量并保存在 vectorA 和 vectorB 中
        convertWordList2Vector(wordListA, wordListB, vectorA, vectorB);

        // 计算向量夹角的余弦值
        Double cosine = countCosine(vectorA, vectorB);
        if (Double.isNaN(cosine)) {
            return cosine;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.0000");
        String formatStr = decimalFormat.format(cosine);

        cosine = Double.parseDouble(formatStr);

        return cosine;
    }

    /**
     * 提取文本中有实意的词
     *
     * @param text 文本
     * @return 实意词 list
     */
    private static List<String> extractWordFromText(String text) {
        // resultList 用于保存提取后的结果
        List<String> resultList = new ArrayList<>();

        // 当 text 为空字符串时，使用分词函数会报错，所以需要提前处理这种情况
        if (StringUtils.isEmpty(text)) {
            return resultList;
        }

        // 提取所有的 1.名词/n ; 2.动词/v ; 3.形容词/a
        List<Nature> natures = Arrays.asList(Nature.n, Nature.v, Nature.a, Nature.vn);

        // 分词
        List<Term> termList = HanLP.segment(text);
        resultList = termList.stream()
                .filter(s -> natures.contains(s.nature))
                .map(s -> s.word)
                .collect(Collectors.toList());

        return resultList;
    }

    /**
     * 将单词数组转换为单词向量，结果保存在 vectorA 和 vectorB 里
     *
     * @param wordListA 文本 A 的单词数组
     * @param wordListB 文本 B 的单词数组
     * @param vectorA   文本 A 转换成为的向量 A
     * @param vectorB   文本 B 转换成为的向量 B
     * @return vocabulary 词汇表
     */
    private static List<String> convertWordList2Vector(List<String> wordListA, List<String> wordListB,
                                                       List<Double> vectorA, List<Double> vectorB) {
        // 词汇表
        List<String> vocabulary = new ArrayList<>();

        // 获取词汇表 wordListA 的频率表，并同时建立词汇表
        Map<String, Double> frequencyTableA = buildFrequencyTable(wordListA, vocabulary);

        // 获取词汇表 wordListB 的频率表，并同时建立词汇表
        Map<String, Double> frequencyTableB = buildFrequencyTable(wordListB, vocabulary);

        // 根据频率表得到向量
        getWordVectorFromFrequencyTable(frequencyTableA, vectorA, vocabulary);
        getWordVectorFromFrequencyTable(frequencyTableB, vectorB, vocabulary);

        return vocabulary;
    }

    /**
     * 建立词汇表 wordList 的频率表，并同时建立词汇表
     *
     * @param wordList   单词数组
     * @param vocabulary 词汇表
     * @return Map<单词, 频率>
     */
    private static Map<String, Double> buildFrequencyTable(List<String> wordList, List<String> vocabulary) {
        // 先建立频数表
        Map<String, Integer> countTable = new HashMap<>();
        for (String word : wordList) {
            if (countTable.containsKey(word)) {
                countTable.put(word, countTable.get(word) + 1);
            } else {
                countTable.put(word, 1);
            }
            // 词汇表中是无重复元素的，所以只在 vocabulary 中没有该元素时才加入
            if (!vocabulary.contains(word)) {
                vocabulary.add(word);
            }
        }
        // totalCount 用于记录词出现的总次数
        int totalCount = wordList.size();
        // 将频数表转换为频率表
        Map<String, Double> frequencyTable = new HashMap<>(countTable.keySet().size());
        for (String key : countTable.keySet()) {
            frequencyTable.put(key, (double) countTable.get(key) / totalCount);
        }
        return frequencyTable;
    }

    /**
     * 根据词汇表和文本的频率表计算词向量，最后 wordVector 和 vocabulary 应该是同维的
     *
     * @param frequencyTable 频率表
     * @param wordVector     转换后的词向量
     * @param vocabulary     词汇表
     */
    private static void getWordVectorFromFrequencyTable(Map<String, Double> frequencyTable, List<Double> wordVector, List<String> vocabulary) {
        for (String word : vocabulary) {
            Double value = 0.0;
            if (frequencyTable.containsKey(word)) {
                value = frequencyTable.get(word);
            }
            wordVector.add(value);
        }
    }

    /**
     * 计算向量 A 和向量 B 的夹角余弦值
     *
     * @param vectorA 词向量 A
     * @param vectorB 词向量 B
     * @return
     */
    private static Double countCosine(List<Double> vectorA, List<Double> vectorB) {
        // 分别计算向量的平方和
        Double sqrtA = countSquareSum(vectorA);
        Double sqrtB = countSquareSum(vectorB);

        // 计算向量的点积
        Double dotProductResult = IntStream.range(0, vectorA.size()).mapToDouble(i -> vectorA.get(i) * vectorB.get(i)).sum();

        return dotProductResult / (sqrtA * sqrtB);
    }

    /**
     * 计算向量平方和的开方
     *
     * @param vector 词向量 list
     * @return
     */
    private static Double countSquareSum(List<Double> vector) {
        Double result = 0.0;
        for (Double value : vector) {
            result += value * value;
        }
        return Math.sqrt(result);
    }

}
