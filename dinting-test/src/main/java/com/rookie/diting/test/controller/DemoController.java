package com.rookie.diting.test.controller;

import com.rookie.diting.domain.SensitiveWordResult;
import com.rookie.diting.test.dto.ApiSensitiveWordResponse;
import com.rookie.diting.test.dto.SensitiveWordDesensitizationRequest;
import com.rookie.diting.test.dto.SensitiveWordRequest;
import com.rookie.diting.utils.DitingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name：SensitiveWordController
 * Author：eumenides
 * Created on: 2025/1/15
 * Description:
 */
@RestController
@RequestMapping("/api/sensitive-words")
public class DemoController {

    /**
     * 检查文本是否包含敏感词
     *
     * @param request 包含需要检测的文本
     * @return 包含敏感词检测结果的响应
     */
    @PostMapping("/find")
    public ResponseEntity<ApiSensitiveWordResponse> findSensitiveWords(@RequestBody SensitiveWordRequest request) {
        try {
            // 调用工具类获取敏感词结果
            SensitiveWordResult result = DitingUtil.getSensitiveWords(request.getText());
            // 将结果包装为 ApiSensitiveWordResponse
            ApiSensitiveWordResponse response = ApiSensitiveWordResponse.from(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiSensitiveWordResponse errorResponse = new ApiSensitiveWordResponse();
            errorResponse.setStatus("ERROR");
            errorResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 检查文本并脱敏
     *
     * @param request 包含需要检测的文本及脱敏配置
     * @return 包含脱敏结果的响应
     */
    @PostMapping("/find-and-desensitize")
    public ResponseEntity<ApiSensitiveWordResponse> findAndDesensitize(@RequestBody SensitiveWordDesensitizationRequest request) {
        try {
            // 调用工具类获取脱敏结果
            SensitiveWordResult result = DitingUtil.replaceSensitiveWords(
                    request.getText(),
                    request.getDesensitizationType(),
                    request.getReplacementType()
            );
            // 将结果包装为 ApiSensitiveWordResponse
            ApiSensitiveWordResponse response = ApiSensitiveWordResponse.from(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiSensitiveWordResponse errorResponse = new ApiSensitiveWordResponse();
            errorResponse.setStatus("ERROR");
            errorResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
