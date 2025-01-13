#!/usr/bin/env sh

# 确保脚本抛出遇到的错误
set -e

cd diting-doc

# 构建文档站
npm run docs:build

pwd

# 进入生成的文件夹
cd docs/.vitepress/dist

# 如果存在旧的 Git 仓库，清理它
rm -rf .git

# 初始化 git 仓库
git init
git add -A
git commit -m 'deploy'

# 部署到 gh-pages 分支
git push -f git@github.com:Eumenides1/diting.git main:gh-pages

cd -