package com.rookie.diting.constants;

/**
 * 敏感词类型枚举
 */
public enum SensitiveWordType {

    VIOLENCE_TERROR("violence_terror", "暴恐", "violence_terror.txt"),
    POLITICAL("political", "反动", "political.txt"),
    LIVELIHOOD("livelihood", "民生", "livelihood.txt"),
    PORNOGRAPHY("pornography", "色情", "pornography.txt"),
    CORRUPTION("corruption", "贪腐", "corruption.txt"),
    ALL("all", "全部", "all.txt");

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
    public static SensitiveWordType fromName(String name) {
        if (name == null) {
            return null;
        }
        for (SensitiveWordType type : SensitiveWordType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
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