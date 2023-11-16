<template>
  <div class="item">
    <a-row style="margin-bottom: 0px">
      <h1 style="font-size: 20px">
        {{ props.currentItem.remark ? props.currentItem.remark : '' }}</h1>
    </a-row>
    <a-row>
      <a-radio-group v-model:value="value" style="display: flex;">

        <a-radio style="font-size: 20px;" v-for="item in answers" :value="item.answerScore"
                 @click="chooseAnswer(item)"><h1>{{ item.answerName }}</h1></a-radio>
      </a-radio-group>
    </a-row>
  </div>
</template>
<script lang="ts" setup>
import {ref, watch} from "vue";
import {answerList} from "../../title/Title.api"

const emit = defineEmits(['score', 'canNext']);

const props = defineProps({
  currentItem: {
    type: Object,
  }
});

const answers = ref([])

// 获取所有答案
async function getAllAnswer() {
  // 如果是最后完成测试界面就不获取答案
  if (props.currentItem?.questionId === '999') {
    answers.value = []
  } else {
    await answerList({questionId: props.currentItem?.questionId}).then((res) => {
      console.log(res, 'answer')
      answers.value = res.records
    })
  }

}

getAllAnswer()

// 生命一个变脸个存储当前的选择的答案对象
const currentAnswer = ref({})

// 每次选择答案都会调用哦那个这个函数
function chooseAnswer(data) {
  currentAnswer.value = data
}

// 选择的结果
const value = ref()

// 监视函数，监视是否上一题或者下一题
watch(
  () => props.currentItem,
  () => {
    getAllAnswer()
    // 将当前所选那只回传给积记分系统
    const param = {title: props.currentItem, answer: currentAnswer.value}
    emit("score", param)
    // 清空 当前所选值
    value.value = null
  }
)
// 监测选择了那个选项
watch(
  () => value.value,
  () => {
    console.log(value.value, '当前选项值')
    if (value.value !== null && value.value !== '' && value.value !== undefined) {
      console.log("可以进行下一题了")
      emit("canNext")
    }
  }
)


</script>
<style>
.item {
  font-size: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-align: center;
}
</style>
