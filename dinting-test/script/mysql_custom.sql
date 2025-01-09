-- 创建测试数据库
CREATE DATABASE IF NOT EXISTS sensitive_db;

-- 使用测试数据库
USE sensitive_db;

-- 创建表 sensitive_words
CREATE TABLE IF NOT EXISTS sensitive_words (
                                               id INT AUTO_INCREMENT PRIMARY KEY,
                                               word VARCHAR(255) NOT NULL,
                                               is_active TINYINT(1) NOT NULL DEFAULT 1,
                                               type VARCHAR(50) NOT NULL
);

-- 插入测试数据
INSERT INTO sensitive_words (word, is_active, type) VALUES
                                                        ('word1', 1, 'prohibited'),
                                                        ('word2', 1, 'prohibited'),
                                                        ('word3', 0, 'allowed'),
                                                        ('word4', 1, 'allowed');