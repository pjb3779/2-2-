package com.example.demo.controller.service;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @author oop起洞
 * @Description:
 * @date 2024/6/8 16:23
 */
@Service
public class SearchService {
    private final JiebaSegmenter jiebaSegmenter;

    @Autowired
    public SearchService(JiebaSegmenter jiebaSegmenter) {
        this.jiebaSegmenter = jiebaSegmenter;
    }

    public List<String> analyzeKeywords(String keyword) {
        List<SegToken> segTokens = jiebaSegmenter.process(keyword, JiebaSegmenter.SegMode.SEARCH);
        return segTokens.stream()
                .map(segToken -> segToken.word)
                .collect(Collectors.toList());
    }
}
