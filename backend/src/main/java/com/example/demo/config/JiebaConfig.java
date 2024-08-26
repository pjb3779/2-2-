package com.example.demo.config;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JiebaConfig {
    @Bean
    public JiebaSegmenter jiebaSegmenter() {
        return new JiebaSegmenter();
    }
}
