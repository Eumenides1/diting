package com.rookie.diting.controller;

import com.rookie.diting.utils.DitingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * Name：SensitiveWordController
 * Author：eumenides
 * Created on: 2025/1/12
 * Description:
 */

@Controller
public class SensitiveWordController {

    @GetMapping("/sensitive-word-console")
    public String showConsole() {
        return "sensitive-word-console";
    }

    @PostMapping("/check-sensitive-words")
    public String checkSensitiveWords(@RequestParam("text") String text, Model model) {
        // 获取敏感词列表
        Set<String> sensitiveWords = DitingUtil.getSensitiveWords(text);
        boolean containsSensitive = !sensitiveWords.isEmpty();

        // 将结果传递到页面
        model.addAttribute("inputText", text);
        model.addAttribute("containsSensitive", containsSensitive);
        model.addAttribute("sensitiveWords", sensitiveWords);

        return "sensitive-word-console";
    }

    @PostMapping("/sanitize-text")
    public String sanitizeText(@RequestParam("text") String text,
                               @RequestParam("replaceChar") char replaceChar,
                               Model model) {
        // 使用用户提供的替换字符脱敏文本
        String sanitizedText = DitingUtil.replaceSensitiveWords(text, replaceChar);

        // 将结果传递到页面
        model.addAttribute("inputText", text);
        model.addAttribute("sanitizedText", sanitizedText);

        return "sensitive-word-console";
    }
}
