import{_ as r,c as n,a0 as o,o as a}from"./chunks/framework.DFDi2gAL.js";const u=JSON.parse('{"title":"","description":"","frontmatter":{},"headers":[],"relativePath":"blog/index.md","filePath":"blog/index.md"}'),d={name:"blog/index.md"};function s(g,t,e,i,l,h){return a(),n("div",null,t[0]||(t[0]=[o('<h2 id="谛听-diting-的起源-互联网世界的-言论守门人" tabindex="-1">谛听（Diting）的起源：互联网世界的“言论守门人” <a class="header-anchor" href="#谛听-diting-的起源-互联网世界的-言论守门人" aria-label="Permalink to &quot;谛听（Diting）的起源：互联网世界的“言论守门人”&quot;">​</a></h2><p>在互联网的世界里，信息传播的速度快如闪电，每个人都可以随时随地发表自己的观点和言论。然而，这种自由并非没有边界。为了维护网络环境的健康与安全，<strong>敏感词识别</strong>成为了每个业务系统不可或缺的一部分。</p><p>无论是社交媒体、电商平台，还是企业内部系统，都需要对用户生成的内容进行敏感数据识别和过滤，以防止不合规信息的传播。然而，传统的敏感词过滤方案往往存在以下问题：</p><ul><li><strong>静态词库</strong>：敏感词库更新不及时，无法应对新型敏感词。</li><li><strong>绕过检测</strong>：用户通过拼音、简写、同音字等方式绕过敏感词检测。</li><li><strong>误判率高</strong>：简单的关键词匹配容易误判，缺乏对上下文的理解。</li></ul><p>为了解决这些问题，我们计划开发 <strong>谛听（Diting）</strong> ，一款基于 <strong>Java Spring Boot Starter</strong> 的敏感数据识别组件。它不仅支持多种敏感词库的存储方式，还通过 <strong>AC自动机</strong> 构建高效的敏感词匹配树，并引入 <strong>大模型</strong> 和 <strong>拼音识别</strong> 等扩展功能，全面提升敏感数据识别的准确性和灵活性。</p><h2 id="为什么叫-谛听" tabindex="-1">为什么叫“谛听”？ <a class="header-anchor" href="#为什么叫-谛听" aria-label="Permalink to &quot;为什么叫“谛听”？&quot;">​</a></h2><p>在中国神话中，<strong>谛听</strong>是地藏菩萨座下的神兽，它耳听八方，能辨善恶，能识真伪，是守护正义的象征。我们的项目 <strong>谛听（Diting）</strong> ，正是以此为灵感，致力于成为敏感数据识别领域的“神兽”！它用“耳朵”聆听每一段文本，用“智慧”辨别每一个敏感词，守护您的数据安全与内容合规。</p><h3 id="谛听的能力-耳听八方-明察秋毫" tabindex="-1">谛听的能力：耳听八方，明察秋毫！ <a class="header-anchor" href="#谛听的能力-耳听八方-明察秋毫" aria-label="Permalink to &quot;谛听的能力：耳听八方，明察秋毫！&quot;">​</a></h3><h4 id="基础能力-敏感词识别" tabindex="-1"><strong>基础能力：敏感词识别</strong> <a class="header-anchor" href="#基础能力-敏感词识别" aria-label="Permalink to &quot;**基础能力：敏感词识别**&quot;">​</a></h4><ul><li><strong>AC自动机</strong>：谛听的核心技能之一，能快速构建敏感词匹配树，精准定位文本中的敏感词。无论是藏在长文中的敏感词，还是伪装成拼音、同音字的“老鼠”，都逃不过谛听的“耳朵”。</li><li><strong>多源词库支持</strong>：谛听可以从 <strong>TXT文件</strong>、<strong>JSON文件</strong>、<strong>MySQL</strong>、<strong>Redis</strong> 等多种地方加载敏感词库，灵活应对不同场景的需求。</li></ul><h4 id="进阶能力-疑似敏感词检测" tabindex="-1"><strong>进阶能力：疑似敏感词检测</strong> <a class="header-anchor" href="#进阶能力-疑似敏感词检测" aria-label="Permalink to &quot;**进阶能力：疑似敏感词检测**&quot;">​</a></h4><ul><li><strong>评分机制</strong>：谛听不仅会识别已知敏感词，还会给文本打分。如果分数超过阈值，说明可能存在“疑似敏感词”。</li><li><strong>大模型助阵</strong>：谛听会请来 <strong>大模型</strong> 这位“AI侦探”，对疑似敏感词进行二次判断。如果确认是敏感词，谛听会把它抓进敏感词库，让敏感词们再也无法逃脱！</li></ul><h4 id="特殊能力-拼音与同音字识别" tabindex="-1"><strong>特殊能力：拼音与同音字识别</strong> <a class="header-anchor" href="#特殊能力-拼音与同音字识别" aria-label="Permalink to &quot;**特殊能力：拼音与同音字识别**&quot;">​</a></h4><ul><li><strong>拼音识别</strong>：谛听能听懂拼音，比如“minganci”就是“敏感词”！</li><li><strong>同音字识别</strong>：谛听还能识别同音字，比如“民感词”就是“敏感词”！</li><li><strong>简写识别</strong>：谛听甚至能看懂简写，比如“mgc”就是“敏感词”！</li></ul><h4 id="终极能力-动态词库更新" tabindex="-1"><strong>终极能力：动态词库更新</strong> <a class="header-anchor" href="#终极能力-动态词库更新" aria-label="Permalink to &quot;**终极能力：动态词库更新**&quot;">​</a></h4><ul><li><strong>自动学习</strong>：当大模型判断出新敏感词时，谛听会自动把它回写到用户的敏感词库中。敏感词们想玩“新花样”？没门！</li><li><strong>多节点同步</strong>：谛听支持多节点部署，确保词库更新的一致性，敏感词们无处可藏！</li></ul><hr><h2 id="谛听的应用场景-守护每一处角落" tabindex="-1">谛听的应用场景：守护每一处角落！ <a class="header-anchor" href="#谛听的应用场景-守护每一处角落" aria-label="Permalink to &quot;谛听的应用场景：守护每一处角落！&quot;">​</a></h2><p>谛听的能力如此强大，自然能在许多场景中大显身手。以下是一些典型的业务场景，它们都需要谛听的守护：</p><table tabindex="0"><thead><tr><th><strong>业务场景</strong></th><th><strong>场景描述</strong></th><th><strong>为什么需要谛听</strong></th><th><strong>示例</strong></th></tr></thead><tbody><tr><td><strong>社交媒体平台</strong></td><td>用户发布动态、评论、私信等内容。</td><td>防止暴力、色情、政治敏感等违规内容，维护社区氛围。</td><td>用户发帖：“今天天气真好，minganci！” → 检测到拼音“minganci”并拦截。</td></tr><tr><td><strong>电商平台</strong></td><td>用户发布商品描述、评价、客服聊天记录等。</td><td>防止商家或用户使用违规词汇，避免客服聊天中出现不当言论。</td><td>用户评价：“这家店卖假货，太垃圾了！” → 检测到敏感词“假货”并标记。</td></tr><tr><td><strong>在线教育平台</strong></td><td>学生和老师发布课程内容、讨论区发言、作业提交等。</td><td>防止课程内容或讨论区出现不当言论，保护未成年人。</td><td>学生发言：“老师，这道题太难了，我快崩溃了！” → 检测到负面情绪词汇并提醒。</td></tr><tr><td><strong>游戏平台</strong></td><td>玩家在游戏内聊天、发布评论、创建房间名称等。</td><td>防止玩家使用侮辱性、暴力性或政治敏感词汇，维护游戏环境。</td><td>玩家聊天：“你这个菜鸡，赶紧退游吧！” → 检测到侮辱性词汇并禁言。</td></tr><tr><td><strong>招聘平台</strong></td><td>企业发布招聘信息，求职者提交简历和评论。</td><td>防止招聘信息中出现歧视性、虚假或违规内容，避免求职者使用不当言论。</td><td>招聘信息：“只招男性，女性勿扰！” → 检测到性别歧视词汇并拦截。</td></tr><tr><td><strong>新闻与内容平台</strong></td><td>编辑发布新闻文章，用户评论文章。</td><td>防止新闻内容或评论中出现虚假信息、政治敏感内容或不当言论，确保内容合规性。</td><td>用户评论：“这篇文章完全是fake news！” → 检测到敏感词“fake news”并删除。</td></tr><tr><td><strong>金融与支付平台</strong></td><td>用户进行交易、发送消息或提交反馈。</td><td>防止用户在交易描述或消息中使用违规词汇，避免反馈中包含不当言论。</td><td>用户交易备注：“这是赌资，请查收！” → 检测到敏感词“赌资”并拦截。</td></tr><tr><td><strong>企业内部系统</strong></td><td>员工在聊天工具、邮件系统或文档中交流。</td><td>防止员工使用不当言论或泄露敏感信息，确保内部沟通合规性。</td><td>员工聊天：“老板真是个傻X！” → 检测到侮辱性词汇并警告。</td></tr><tr><td><strong>政府与公共服务平台</strong></td><td>市民提交意见、投诉或咨询。</td><td>防止市民提交的内容中包含政治敏感、暴力或侮辱性词汇，确保内容合规性。</td><td>市民投诉：“这个部门效率太低，简直是个摆设！” → 检测到负面情绪词汇并标记。</td></tr><tr><td><strong>医疗健康平台</strong></td><td>患者咨询医生、发布病情描述或评论。</td><td>防止患者发布虚假信息、不当言论或敏感内容，确保咨询内容的专业性和合规性。</td><td>患者描述：“我最近感觉很不舒服，可能是得了XX病。” → 检测到敏感病名并提醒医生注意。</td></tr></tbody></table><h2 id="谛听的使命-让敏感词无所遁形" tabindex="-1">谛听的使命：让敏感词无所遁形！ <a class="header-anchor" href="#谛听的使命-让敏感词无所遁形" aria-label="Permalink to &quot;谛听的使命：让敏感词无所遁形！&quot;">​</a></h2><p>谛听的使命很简单：<strong>让敏感词无所遁形！</strong>  无论是藏在文本里的敏感词，还是伪装成拼音、同音字的“老鼠”，谛听都能一网打尽。它不仅是敏感数据识别的“神兽”，更是您业务安全的守护者！</p><h2 id="加入谛听-一起守护数据安全" tabindex="-1">加入谛听，一起守护数据安全！ <a class="header-anchor" href="#加入谛听-一起守护数据安全" aria-label="Permalink to &quot;加入谛听，一起守护数据安全！&quot;">​</a></h2><p>如果您也在为敏感词问题头疼，不妨期待谛听！它不仅能帮您解决现有的敏感词问题，还能通过动态学习和扩展功能，应对未来的挑战。让我们一起，用谛听的力量，守护数据安全，净化网络环境！</p><p><strong>PS</strong>：敏感词们，谛听来了，你们准备好了吗？😎</p>',25)]))}const b=r(d,[["render",s]]);export{u as __pageData,b as default};
