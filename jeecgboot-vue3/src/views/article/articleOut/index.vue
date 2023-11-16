<template>
  <div v-if="!modalvisable">
    <!--  背景板-->
    <a-row>
      <a-col :span="24">
        <img class="imageBack" src="@/assets/images/imageHeartBack.jpg"/>
      </a-col>
    </a-row>
    <!--  文章-->
    <a-row>
      <a-col v-for="item in allArticleList" :span="8">
        <a-card :title="item.text" style="width: 95%;height:300px;margin: 10px;">
          <template #extra><a @click="indexDetailMore(item)">more</a></template>

          <!--        专栏文章列表-->
          <div style="width:100%; overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" v-for="no in pages(item.value)">
            <a @click="pageDetailOpen(no)">{{ no.title }}</a>
            <br/>
          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
  <indexDetail v-if="modalvisable" :item="index" @cancel="indexDetailCencel" @view="pageDetailOpen"></indexDetail>

  <pageDetail v-if="openModalVisable" @register="registerModal" @cancel="modalClose"></pageDetail>
</template>
<script lang="ts" setup>
import {ajaxGetDictItems} from '/@/utils/dict';
import {ref} from "vue";
import indexDetail from "./comenpents/indexDetail.vue"
import {getAllPageQuery, list} from "../page/PageInfo.api"
import {useModal} from "@/components/Modal";
import  pageDetail from "./comenpents/pageDetail.vue"

// 所有栏目分类
const allArticleList = ref([])
// 所有文章
const pagesAllList = ref([])

// 是否打开弹窗
const openModalVisable = ref(false)

// 文章详情的弹窗
const [registerModal, {openModal,setModalProps,closeModal}] = useModal();

// 获取所有栏目分类
async function getAll() {
  await ajaxGetDictItems("article_classify", null).then((res) => {
    allArticleList.value = res
  })
  await list({}).then(res => {
    pagesAllList.value = res.records
  })
}

getAll()

function pages(type) {
  console.log(pagesAllList.value,"all")
  const result = pagesAllList.value.filter(item => item.pageType === type)
  const resultNew = [];
  if (result.length > 5) {
    for (let i = 0; i < 5; i++) {
        resultNew.push(result[i])
    }
    return resultNew
  } else {
    console.log(result,type)
    return result
  }
}

// 详情分类列表分类组件是否渲染的控制变量
const modalvisable = ref(false)
const index = ref()

// 打开弹窗方法
function indexDetailMore(data) {
  index.value = data
  modalvisable.value = true
}

// 关闭弹窗的方法
function indexDetailCencel() {
  modalvisable.value = false
}

// 文章详情打开
function pageDetailOpen(data){
  openModalVisable.value = true
  setTimeout(()=>{
    setModalProps({defaultFullscreen:true})
    openModal(true ,data)
  },800)

}
// 关闭弹窗
function modalClose(){
  openModalVisable.value = false
  closeModal(true)
}


</script>
<style>
.imageBack {
//height: 300px; //width: 100%;
}
</style>
