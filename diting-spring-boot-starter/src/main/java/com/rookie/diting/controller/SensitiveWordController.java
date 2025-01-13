package com.rookie.diting.controller;

import com.rookie.diting.constants.DesensitizationType;
import com.rookie.diting.constants.ReplacementType;
import com.rookie.diting.domain.MatchedWord;
import com.rookie.diting.domain.SensitiveWordResult;
import com.rookie.diting.utils.DitingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.stream.Collectors;

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
        // 获取敏感词检测结果
        SensitiveWordResult result = DitingUtil.getSensitiveWords(text);

        // 将结果传递到页面
        model.addAttribute("inputText", text);
        model.addAttribute("containsSensitive", result.isHasSensitiveWord());
        model.addAttribute("sensitiveWords", result.getMatchedWords());  // 提取敏感词内容
        return "sensitive-word-console";
    }

    @PostMapping("/sanitize-text")
    public String sanitizeText(@RequestParam("text") String text,
                               @RequestParam("replaceChar") char replaceChar,
                               Model model) {
        // 获取脱敏后的文本
        SensitiveWordResult result = DitingUtil.replaceSensitiveWords(text, DesensitizationType.FULL_REPLACEMENT, ReplacementType.ASTERISK);

        // 将结果传递到页面
        model.addAttribute("inputText", text);
        model.addAttribute("sanitizedText", result.getText()); // 获取脱敏后的文本
        model.addAttribute("replaceChar", replaceChar);

        return "sensitive-word-console";
    }
}
