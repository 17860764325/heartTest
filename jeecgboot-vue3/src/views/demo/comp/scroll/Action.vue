<template>
  <PageWrapper title="滚动组件函数示例" content="基于el-scrollbar">
    <div class="my-4">
      <a-button @click="scrollTo(100)" class="mr-2"> 滚动到100px位置 </a-button>
      <a-button @click="scrollTo(800)" class="mr-2"> 滚动到800px位置 </a-button>
      <a-button @click="scrollTo(0)" class="mr-2"> 滚动到顶部 </a-button>
      <a-button @click="scrollBottom()" class="mr-2"> 滚动到底部 </a-button>
      <a-button @click="setScrollToBottom()" class="mr-2"> 滚动到底部1 </a-button>
    </div>
    <div class="scroll-wrap">
      <ScrollContainer class="mt-4" ref="scrollRef">
        <ul class="p-3">
          <template v-for="index in 10" :key="index">
            <li class="p-2" :style="{ border: '1px solid #eee' }">
              {{ index }}
            </li>
          </template>
        </ul>
      </ScrollContainer>
    </div>
  </PageWrapper>
</template>
<script lang="ts">
import {defineComponent, nextTick, ref, unref} from 'vue';
  import { ScrollContainer, ScrollActionType } from '/@/components/Container/index';
  import { PageWrapper } from '/@/components/Page';

  export default defineComponent({
    components: { ScrollContainer, PageWrapper },
    setup() {
      const scrollRef = ref<Nullable<ScrollActionType>>(null);
      const getScroll = () => {
        const scroll = unref(scrollRef);
        if (!scroll) {
          throw new Error('scroll is Null');
        }
        return scroll;
      };

      /**
       * 控制滚动条滚动到容器的底部
       */
      async function setScrollToBottom() {
        // 注意：需要通过 nextTick 以等待 DOM 更新完成
        await nextTick(()=>{
          var div = document.getElementsByClassName('scroll-wrap')
          div.scrollTop = div.scrollHeight
        })
      };
      function scrollTo(top: number) {
        getScroll().scrollTo(top);
      }
      function scrollBottom() {
        getScroll().scrollBottom();
      }
      return {
        scrollTo,
        scrollRef,
        scrollBottom,
        setScrollToBottom
      };
    },
  });
</script>
<style lang="less" scoped>
  .scroll-wrap {
    width: 50%;
    height: 300px;
    background-color: @component-background;
  }
</style>
