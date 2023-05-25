package com.miku.similarity.check.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ge Mingjia
 * @date 2023/5/24
 */
public class FileSimilarityChecker {

    // 文件1的路径
    private static final String FILE1_PATH = "targetfile/1.txt";
    // 文件2的路径
    private static final String FILE2_PATH = "targetfile/2.txt";

    public static void main(String[] args) {
        try {
            // 读取文件1的内容
            String file1Content = readFileContent(FILE1_PATH);
            // 读取文件2的内容
            String file2Content = readFileContent(FILE2_PATH);
            // 计算文件相似度
            double similarity = calculateSimilarity(file1Content, file2Content);
            System.out.println("文件相似度: " + similarity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取文件内容
    private static String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        // 使用BufferedReader逐行读取文件内容
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }

    // 计算文件相似度
    private static double calculateSimilarity(String content1, String content2) {
        // 获取文本内容的词频向量
        Map<String, Integer> vector1 = getTermFrequency(content1);
        Map<String, Integer> vector2 = getTermFrequency(content2);

        // 计算向量的点积
        double dotProduct = calculateDotProduct(vector1, vector2);
        // 计算向量的模
        double magnitude1 = calculateMagnitude(vector1);
        double magnitude2 = calculateMagnitude(vector2);

        // 计算相似度
        return dotProduct / (magnitude1 * magnitude2);
    }

    // 计算词频
    private static Map<String, Integer> getTermFrequency(String content) {
        Map<String, Integer> termFrequency = new HashMap<>();
        // 将文本内容按空白字符分割为单词数组
        String[] words = content.split("\\s+");

        // 统计每个单词的出现频率
        for (String word : words) {
            termFrequency.put(word, termFrequency.getOrDefault(word, 0) + 1);
        }

        return termFrequency;
    }

    // 计算向量的点积
    private static double calculateDotProduct(Map<String, Integer> vector1, Map<String, Integer> vector2) {
        double dotProduct = 0;

        // 遍历较小的向量
        Map<String, Integer> smallerVector = vector1.size() < vector2.size() ? vector1 : vector2;
        Map<String, Integer> largerVector = vector1.size() >= vector2.size() ? vector1 : vector2;

        // 计算点积
        for (Map.Entry<String, Integer> entry : smallerVector.entrySet()) {
            String term = entry.getKey();
            int frequency1 = entry.getValue();
            int frequency2 = largerVector.getOrDefault(term, 0);

            dotProduct += frequency1 * frequency2;
        }

        return dotProduct;
    }

    // 计算向量的模
    private static double calculateMagnitude(Map<String, Integer> vector) {
        double magnitudeSquared = 0;

        // 计算向量模的平方
        for (int frequency : vector.values()) {
            magnitudeSquared += frequency * frequency;
        }

        // 对模的平方进行开方，得到模的值
        return Math.sqrt(magnitudeSquared);
    }
}

