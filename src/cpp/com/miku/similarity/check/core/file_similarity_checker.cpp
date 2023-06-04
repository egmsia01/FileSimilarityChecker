//
// Created by gmj23 on 2023/6/4.
//

#include "file_similarity_checker.h"
#include <fstream>
#include <sstream>
#include <cmath>

double FileSimilarityChecker::calculateSimilarity(const std::string& file1Path, const std::string& file2Path) {
    std::string file1Content = readFileContent(file1Path);
    std::string file2Content = readFileContent(file2Path);

    std::unordered_map<std::string, int> vector1 = getTermFrequency(file1Content);
    std::unordered_map<std::string, int> vector2 = getTermFrequency(file2Content);

    double dotProduct = calculateDotProduct(vector1, vector2);
    double magnitude1 = calculateMagnitude(vector1);
    double magnitude2 = calculateMagnitude(vector2);

    return dotProduct / (magnitude1 * magnitude2);
}

std::string FileSimilarityChecker::readFileContent(const std::string& filePath) {
    std::ifstream file(filePath);
    std::stringstream buffer;
    buffer << file.rdbuf();
    return buffer.str();
}

std::unordered_map<std::string, int> FileSimilarityChecker::getTermFrequency(const std::string& content) {
    std::unordered_map<std::string, int> termFrequency;
    std::istringstream iss(content);
    std::string word;
    while (iss >> word) {
        termFrequency[word]++;
    }
    return termFrequency;
}

double FileSimilarityChecker::calculateDotProduct(const std::unordered_map<std::string, int>& vector1,
                                                  const std::unordered_map<std::string, int>& vector2) {
    double dotProduct = 0.0;
    const std::unordered_map<std::string, int>& smallerVector = vector1.size() < vector2.size() ? vector1 : vector2;
    const std::unordered_map<std::string, int>& largerVector = vector1.size() >= vector2.size() ? vector1 : vector2;

    for (const auto& entry : smallerVector) {
        const std::string& term = entry.first;
        int frequency1 = entry.second;
        int frequency2 = largerVector.count(term) > 0 ? largerVector.at(term) : 0;
        dotProduct += frequency1 * frequency2;
    }

    return dotProduct;
}

double FileSimilarityChecker::calculateMagnitude(const std::unordered_map<std::string, int>& vector) {
    double magnitudeSquared = 0.0;
    for (const auto& entry : vector) {
        int frequency = entry.second;
        magnitudeSquared += frequency * frequency;
    }
    return std::sqrt(magnitudeSquared);
}
