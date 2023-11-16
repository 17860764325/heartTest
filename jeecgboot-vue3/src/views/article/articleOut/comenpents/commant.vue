<template>
  <civ >
    <childrenComment v-if="initLoading" @refrash="getCommants" :styleIndex="1" :list="list"></childrenComment>
  </civ>
</template>
<script lang="ts" setup>
import {onMounted, ref, nextTick} from 'vue';
import {getCommantByPageIdList} from "../../commant/CommantInfo.api"
import childrenComment from "./commantChildrens.vue"


const props = defineProps({
  pageinfo: {
    type: Object
  }
})

const count = 3;

const initLoading = ref(true);
const loading = ref(false);
const data = ref([]);
const list = ref([]);


async function getCommants() {
  const initLoading = ref(false);
  console.log(props.pageinfo, "pageInfo")
  await getCommantByPageIdList(props.pageinfo?.id).then(res => {
    list.value = res
    initLoading.value = true
  })
}

getCommants()




</script>
<style scoped>
.demo-loadmore-list {
  min-height: 350px;
}
</style>
