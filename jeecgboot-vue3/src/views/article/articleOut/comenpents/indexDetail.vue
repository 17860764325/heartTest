<template>
    <a-card :title="props.item.text" style="width: 100%;margin: 10px">
      <template #extra><a-button @click="backIndex()">返回</a-button></template>
      <!--        专栏文章列表-->
      <div v-for="no in pages">
        <a @click="pageDetailOpen(no)">{{no.title}}</a>
        <br/>
      </div>
    </a-card>
<!--  <pageDetail @register="registerModal"></pageDetail>-->
</template>
<script lang="ts" setup>
import {getAllPageQuery,list} from  "../../page/PageInfo.api"
import back from "@/views/demo/permission/back/index.vue";
import {ref} from "vue";
import {useModal} from "@/components/Modal";
// const [registerModal, {openModal,setModalProps}] = useModal();

const emit =  defineEmits(["cancel","view"])

const props = defineProps({
  item: {
    type: Object
  }
})

// 文章详情的弹窗

// 所有文章 无限
const pages = ref([]);
async function  getAllPages(){
  await getAllPageQuery(props.item?.value).then((res) => {
    pages.value= res
  })
}
getAllPages()


function backIndex(){
  emit("cancel")
}

// 文章详情打开
function pageDetailOpen(data){
  // setModalProps({defaultFullscreen:true})
  // openModal(true ,data)
  emit("view",data)
}

</script>
<style>

</style>
