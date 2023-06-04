//
// Created by gmj23 on 2023/6/4.
//

#ifndef FILESIMILARITYCHECKER_FILE_SIMILARITY_CHECKER_H
#define FILESIMILARITYCHECKER_FILE_SIMILARITY_CHECKER_H
#pragma once

#include <string>
#include <unordered_map>

class FileSimilarityChecker {
public:
    static double calculateSimilarity(const std::string &file1Path, const std::string &file2Path);

private:
    static std::string readFileContent(const std::string &filePath);

    static std::unordered_map<std::string, int> getTermFrequency(const std::string &content);

    static double calculateDotProduct(const std::unordered_map<std::string, int> &vector1,
                                      const std::unordered_map<std::string, int> &vector2);

    static double calculateMagnitude(const std::unordered_map<std::string, int> &vector);
};
