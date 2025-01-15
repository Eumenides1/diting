import { defineConfig } from 'vitepress'
import { generateSidebar } from "vitepress-sidebar";

const vitepressSidebarOptions = {
  documentRootPath: "/docs",
  collapsed: false, //折叠组关闭
  collapseDepth: 2, //折叠组2级菜单
  removePrefixAfterOrdering: true, //删除前缀，必须与prefixSeparator一起使用
  prefixSeparator: "_", //删除前缀的符号
};

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: '/diting/',
  title: '谛听-文档站',
  description: '轻量级高性能敏感词识别与数据脱敏组件',
  sidebar: generateSidebar(vitepressSidebarOptions),
  head: [
    ['link', { rel: 'icon', href: '/public/favicon.ico' }],
    [
      'link',
      { rel: 'preconnect', href: 'https://fonts.googleapis.com' }
    ],
    [
      'link',
      { rel: 'preconnect', href: 'https://fonts.gstatic.com', crossorigin: '' }
    ],
    [
      'link',
      { href: 'https://fonts.googleapis.com/css2?family=Roboto&display=swap', rel: 'stylesheet' }
    ],
    [
      'script',
      { id: 'register-sw' },
      `;(() => {
        if ('serviceWorker' in navigator) {
          navigator.serviceWorker.register('/sw.js')
        }
      })()`
    ],
    [
      'script',
      { async: '', src: 'https://www.googletagmanager.com/gtag/js?id=TAG_ID' }
    ],
    [
      'script',
      {},
      `window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());
      gtag('config', 'TAG_ID');`
    ]
  ],
  themeConfig: {
    siteTitle: '谛',
    editLink: {
      pattern: "https://github.com/Eumenides1/diting/blob/main/diting-doc/docs/:path", // 自己项目仓库地址
      text: "在 github 上编辑此页",
    },
    // 主题级选项
    nav: [
      { text: '快速开始', link: '/quick-start' },
      {text: '配置',link: '/config'},
      {text: '工具',link: '/tool'},
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
  },
  vite: {
    build: {
      rollupOptions: {
        output: {
          manualChunks: undefined, // 防止分包问题
        },
      },
    },
    server: {
      proxy: {
        '/api': {
          target: 'http://106.54.240.161:8888', // 替换为你的后端服务地址
          changeOrigin: true,
        },
      },
    },
  },
})
