<template>
  <BasicModal v-bind="$attrs" @register="registerModal" destroyOnClose title="文章查看" :width="800"
              @ok="closeMMM" @cancel="closeMMM">
    <!--    文章展示-->
    <div class="mt-2">
      <a-card id="" style="border: 1px solid black " :title="pageInfo.title">
<!--        <JEditor :value="pageInfo.about" />-->
        <div v-html="pageInfo.about">
        </div>
      </a-card>
    </div>
    <!-- 评论功能-->
    <h1>相关评论：</h1>
    <!--    自己添加评论-->
    <div >
      <a-car>
        <a-form-item>
          <a-textarea placeholder="请在这里输入你的评论！" style="border: 1px solid black;border-radius: 10px;" v-model:value="value"></a-textarea>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="submitCommant">提交评论</a-button>
        </a-form-item>
      </a-car>
    </div>
    <!--    评论展示-->
    <div style="border: 1px solid black">
      <commant v-if="isCommant" :pageinfo="pageInfo"></commant>
    </div>

  </BasicModal>
</template>
<script setup lang="ts">
import {nextTick, ref} from 'vue'
import {MarkdownViewer} from "@/components/Markdown";
import {BasicModal, useModalInner} from "@/components/Modal";
import commant from "./commant.vue"
import {saveOrUpdate} from "../../commant/CommantInfo.api"
import {useMessage} from "@/hooks/web/useMessage";
import JEditor from "@/components/Form/src/jeecg/components/JEditor.vue";
// 消息
const {createMessage, createErrorModal, createConfirm} = useMessage();
const isCommant = ref(false)
const value = ref()
const pageInfo = ref({})

const emit = defineEmits(["cancel"])

const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
  pageInfo.value = data
  isCommant.value = true
});

const list = ref([])


async function submitCommant() {
  isCommant.value = false
  const data = {
    about: value.value,
    pageId: pageInfo.value.id,
  }
  await saveOrUpdate(data, false).then(res => {
    createMessage.info(res)
    value.value = []
    isCommant.value = true
  })
}

function closeMMM(){
  emit("cancel")
}

</script>
<style>

</style>
