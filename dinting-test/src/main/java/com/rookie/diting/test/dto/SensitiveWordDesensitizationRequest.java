package com.rookie.diting.test.dto;

import com.rookie.diting.constants.DesensitizationType;
import com.rookie.diting.constants.ReplacementType;

/**
 * Name：SensitiveWordDesensitizationRequest
 * Author：eumenides
 * Created on: 2025/1/15
 * Description:
 */
public class SensitiveWordDesensitizationRequest {
    private String text;
    private DesensitizationType desensitizationType;
    private ReplacementType replacementType = ReplacementType.ASTERISK; // 默认使用星号替换

    // Getter and Setter
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DesensitizationType getDesensitizationType() {
        return desensitizationType;
    }

    public void setDesensitizationType(DesensitizationType desensitizationType) {
        this.desensitizationType = desensitizationType;
    }

    public ReplacementType getReplacementType() {
        return replacementType;
    }

    public void setReplacementType(ReplacementType replacementType) {
        this.replacementType = replacementType;
    }
}
