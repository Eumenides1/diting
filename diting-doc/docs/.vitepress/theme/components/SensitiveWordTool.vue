<template>
  <div class="tool-container">
    <div class="card">
      <div class="form">
        <!-- 输入框 -->
        <div class="form-group">
          <label for="text" class="form-label">输入文本</label>
          <textarea
              id="text"
              v-model="inputText"
              placeholder="请输入需要检测的文本"
              rows="8"
              class="form-input"
          ></textarea>
        </div>

        <!-- 操作选择 -->
        <div class="form-group">
          <label for="action" class="form-label">选择操作</label>
          <select id="action" v-model="selectedAction" class="form-select">
            <option value="find">发现敏感词</option>
            <option value="find-and-desensitize">发现并脱敏</option>
          </select>
        </div>

        <!-- 脱敏方式 -->
        <div v-if="selectedAction === 'find-and-desensitize'" class="form-group">
          <label for="desensitization" class="form-label">脱敏方式</label>
          <select id="desensitization" v-model="desensitizationType" class="form-select">
            <option value="FULL_REPLACEMENT">完全替换</option>
            <option value="PARTIAL_REPLACEMENT">部分替换</option>
            <option value="BLUR">模糊处理</option>
          </select>
        </div>

        <!-- 提交按钮 -->
        <button class="btn" @click="performAction" :disabled="loading">
          {{ loading ? "处理中..." : "提交" }}
        </button>
      </div>

      <!-- 显示处理结果 -->
      <div class="result">
        <label for="result" class="form-label">处理后的文本</label>
        <div class="result-box" v-html="processedText"></div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const BASE_URL = process.env.NODE_ENV === 'production'
    ? 'https://diting-server-97557-9-1325155828.sh.run.tcloudbase.com/api'
    : '/api'; // 本地开发仍然使用代理

export default {
  data() {
    return {
      inputText: "", // 用户输入的文本
      processedText: "", // 处理后的文本（包括高亮显示）
      selectedAction: "find", // 默认操作为发现敏感词
      desensitizationType: "FULL_REPLACEMENT", // 默认脱敏方式
      loading: false, // 是否加载中
    };
  },
  methods: {
    async performAction() {
      if (!this.inputText.trim()) {
        alert("请输入文本");
        return;
      }

      this.loading = true;

      try {
        let response;
        if (this.selectedAction === "find") {
          // 调用发现敏感词的接口
          response = await axios.post(`${BASE_URL}/sensitive-words/find`, {
            text: this.inputText,
          });
          this.highlightSensitiveWords(response.data.matchedWords);
        } else if (this.selectedAction === "find-and-desensitize") {
          // 调用发现并脱敏的接口
          response = await axios.post(`${BASE_URL}/sensitive-words/find-and-desensitize`, {
            text: this.inputText,
            desensitizationType: this.desensitizationType,
          });
          this.replaceInputText(response.data.text);
        }
      } catch (error) {
        console.error(error);
        alert("操作失败，请稍后重试");
      } finally {
        this.loading = false;
      }
    },
    highlightSensitiveWords(matchedWords) {
      // 构建敏感词位置索引数组
      const ranges = matchedWords.map(word => ({
        start: word.startIndex,
        end: word.endIndex,
        word: word.word,
      }));

      // 排序：优先处理起始索引更小的词，起始索引相同则处理更长的词
      ranges.sort((a, b) => a.start - b.start || b.end - b.end);

      // 合并重叠的范围
      const mergedRanges = [];
      for (const range of ranges) {
        if (
            mergedRanges.length > 0 &&
            mergedRanges[mergedRanges.length - 1].end > range.start
        ) {
          // 合并范围：保留更大的结束索引
          mergedRanges[mergedRanges.length - 1].end = Math.max(
              mergedRanges[mergedRanges.length - 1].end,
              range.end
          );
        } else {
          // 不重叠的范围直接加入
          mergedRanges.push(range);
        }
      }

      // 构建高亮后的文本
      let highlightedText = "";
      let lastIndex = 0;

      for (const range of mergedRanges) {
        // 添加非敏感部分
        highlightedText += this.inputText.slice(lastIndex, range.start);
        // 添加高亮的敏感部分
        highlightedText += `<span style="color: red; font-weight: bold;">${this.inputText.slice(
            range.start,
            range.end
        )}</span>`;
        lastIndex = range.end; // 更新最后处理位置
      }

      // 添加剩余的文本
      highlightedText += this.inputText.slice(lastIndex);

      // 更新到输入框
      this.processedText = highlightedText;
    },
    replaceInputText(newText) {
      // 替换输入框内容为脱敏后的文本
      this.processedText = newText;
    },
  },
};
</script>
<style scoped>
/* 容器整体设置 */
.tool-container {
  display: flex;
  flex-direction: column; /* 上下布局 */
  max-width: 800px; /* 限制最大宽度 */
  width: 90%; /* 容器占屏幕宽度的 90% */
  margin: 40px auto;
  gap: 20px;
}

.card {
  padding: 30px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  font-size: 24px;
}

.description {
  margin-bottom: 20px;
  color: #666666;
}

/* 表单样式 */
.form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-label {
  margin-bottom: 8px;
  font-weight: bold;
}

.form-input {
  padding: 15px;
  border: 1px solid #dcdcdc;
  border-radius: 8px;
  font-size: 16px;
}

.form-select {
  padding: 10px;
  border: 1px solid #dcdcdc;
  border-radius: 8px;
  font-size: 16px;
}

.btn {
  padding: 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: #0056b3;
}

.result-box {
  font-family: Consolas, monospace;
  background-color: #f8f9fa;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style>