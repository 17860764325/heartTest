<template>
  <div class="my-4">
    <a-button @click="scrollTo(100)" class="mr-2"> 滚动到100px位置</a-button>
    <a-button @click="scrollTo(800)" class="mr-2"> 滚动到800px位置</a-button>
    <a-button @click="scrollTo(0)" class="mr-2"> 滚动到顶部</a-button>
    <a-button @click="scrollBottom()" class="mr-2"> 滚动到底部</a-button>
  </div>
  <div class="scroll-wrap">
    <ScrollContainer style="border: blue 3px solid" class="mt-4" ref="scrollRef">
      <ul class="p-3">
        <template  v-for="index in chhatLogList" :key="index.id">
          <li class="p-2" :style="{ display:'flex',flexDirection: 'column',alignItems: index.sendPerson === username?'flex-end':'flex-start'}">
              <h1 :style="{color:index.sendPerson === username?'green':'red'}">{{ index.createTime }}｜｜《{{ index.sendPerson }}》:</h1>
              <span>{{ index.about }}</span>
          </li>
        </template>
      </ul>
    </ScrollContainer>
  </div>
</template>
<script setup lang="ts">
import {ScrollActionType, ScrollContainer} from "@/components/Container";
import {ref, unref} from "vue";
import {store} from "@/store";

const props = defineProps({
  chhatLogList: {
    type: Array
  }
})
const username = store.state.value["app-user"]["userInfo"].username
const scrollRef = ref<Nullable<ScrollActionType>>(null);
const getScroll = () => {
  const scroll = unref(scrollRef);
  if (!scroll) {
    throw new Error('scroll is Null');
  }
  return scroll;
};

function scrollTo(top: number) {
  getScroll().scrollTo(top);
}

function scrollBottom() {
  getScroll().scrollBottom();
}






</script>
<style>
.scroll-wrap {
  width: 100%;
  height: 300px;
  background-color: @component-background;
}
</style>
