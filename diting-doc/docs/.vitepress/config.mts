import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: '/diting/',
  title: '谛听-文档站',
  description: '轻量级高性能敏感词识别与数据脱敏组件',
  themeConfig: {
    siteTitle: '谛听',
    // 主题级选项
    nav: [
      { text: '快速开始', link: '/quick-start' },
      {text: '博客', link: '/blog'}
    ],
    sidebar: {
      '/blog/': [
          {
            text: '谛听开发博客',
            items: [
              { text: '谛听开发计划', link: '/blog/diting-plan' },
              { text: '谛听（Diting）开发日记：Day 1 - 敏感词库加载', link: '/blog/day1' },
              { text: '谛听（Diting）开发日记：Day 2 - 扩展加载器功能', link: '/blog/day2' },
              { text: '谛听（Diting）开发日记：Day 3 - 深入浅出 AC 自动机', link: '/blog/day3' }
            ]
          }
      ]
    }
  }
})
