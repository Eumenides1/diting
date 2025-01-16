package com.rookie.diting.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 敏感词类型枚举
 */
public enum SensitiveWordType {

    VIOLENCE_TERROR("violence_terror", "暴恐", "violence_terror.enc"),
    POLITICAL("political", "反动", "political.enc"),
    LIVELIHOOD("livelihood", "民生", "livelihood.enc"),
    PORNOGRAPHY("pornography", "色情", "pornography.enc"),
    CORRUPTION("corruption", "贪腐", "corruption.enc"),
    ALL("all", "全部", "all.enc"),
    CUSTOM("custom", "自定义", "custom.enc"); // 新增自定义类型;

    private final String name;    // 敏感词类型名称（唯一标识）
    private final String desc;    // 敏感词类型描述
    private final String filename; // 对应的文件名

    /**
     * 构造函数
     * @param name 唯一标识
     * @param desc 描述
     * @param filename 对应的文件名
     */
    SensitiveWordType(String name, String desc, String filename) {
        this.name = name;
        this.desc = desc;
        this.filename = filename;
    }
    @JsonValue
    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getFilename() {
        return filename;
    }

    /**
     * 根据 name 查找枚举
     * @param name 枚举的名称
     * @return 对应的枚举类型，未找到返回 null
     */
    @JsonCreator
    public static SensitiveWordType fromName(String name) {
        if (name == null) {
            return CUSTOM; // 如果 name 为 null，默认返回 CUSTOM
        }
        for (SensitiveWordType type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return CUSTOM; // 未匹配到返回 CUSTOM
    }



    @Override
    public String toString() {
        return "SensitiveWordType{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}