<template>
  <a-list
    class="demo-loadmore-list"
    item-layout="horizontal"
    :data-source="props.list"
    :style="{marginLeft:props.styleIndex * 20 +'px'}"
  >
    <!--    <template #loadMore>-->
    <!--      <div-->
    <!--        v-if="!initLoading && !loading"-->
    <!--        :style="{ textAlign: 'center', marginTop: '12px', height: '32px', lineHeight: '32px' }"-->
    <!--      >-->
    <!--        <a-button @click="onLoadMore">loading more</a-button>-->
    <!--      </div>-->
    <!--    </template>-->
    <template #renderItem="{ item }">
      <a-list-item>
        <template #actions>
          <a @click="addReCommant(item)">回复</a>
        </template>
        <a-skeleton avatar :title="false" :loading="!!item.loading" active>
          <a-list-item-meta
            :description="item.about"
          >
            <template #title>
              <a><span style="color: #021d37;">{{ item.createBy }}</span>   <span style="color: green">{{item.createTime }}</span></a>
            </template>
            <template #avatar>
              <a-avatar :src="item.avatar"/>
            </template>

          </a-list-item-meta>
        </a-skeleton>
      </a-list-item>
      <div v-if="reCommant===item.id">
        <a-form-item>
          <a-textarea v-model:value="value"></a-textarea>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="submit(item)">提交评论</a-button>
        </a-form-item>
      </div>
      <CommantChildrens v-if="item.children !== null" :styleIndex="props.styleIndex + 1" :list="item.children" @refrash="reload"></CommantChildrens>
    </template>
  </a-list>
</template>
<script lang="ts" setup>
import {ref} from "vue";
import {saveOrUpdate} from "@/views/article/commant/CommantInfo.api";
const emit = defineEmits(["refrash"])
import {useMessage} from "@/hooks/web/useMessage";
// 消息
const {createMessage, createErrorModal, createConfirm} = useMessage();
const props = defineProps({
  list: {
    type: Array
  },
  styleIndex:{
    type:Number
  }
})

const value = ref()

const reCommant = ref()

function addReCommant(data) {
  console.log(data)
  reCommant.value = data.id
}

function reload(){
  emit("refrash")
}

async function submit(data){
  // data--父级数
  // 拼装数据
  const dataParam ={
    about:value.value,
    pageId:data.pageId,
    parentId:data.id
  }
  await saveOrUpdate(dataParam,false).then(res => {
    createMessage.info(res)
    value.value = []
  })
  // 关闭评论框
  reCommant.value = null
  reload()
}
</script>
