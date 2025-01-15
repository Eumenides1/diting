import DefaultTheme from "vitepress/theme";
import "./styles/index.css"; //引入自定义的样式
import Confetti from "./components/Confetti.vue";
import SensitiveWordTool from "./components/SensitiveWordTool.vue";
import mediumZoom from 'medium-zoom';
import { onMounted, watch, nextTick } from 'vue';
import { useRoute } from 'vitepress';

export default {
    extends: DefaultTheme,
    setup() {
        const route = useRoute();
        const initZoom = () => {
            // mediumZoom('[data-zoomable]', { background: 'var(--vp-c-bg)' }); // 默认
            mediumZoom(".main img", { background: "var(--vp-c-bg)" }); // 不显式添加{data-zoomable}的情况下为所有图像启用此功能
        };
        onMounted(() => {
            initZoom();
        });
        watch(
            () => route.path,
            () => nextTick(() => initZoom())
        );
    },
    // ...DefaultTheme, //或者这样写也可
    enhanceApp({ app, router }) {
        app.component("Confetti", Confetti); //注册全局组件
        app.component("SensitiveWordTool",SensitiveWordTool);
    },
};