<template>
  <div>
    <a-card>
      <a-steps :current="current">
        <a-step v-for="item in steps" :key="item.id" title=""/>
      </a-steps>
      <div class="steps-content">
        <itemTitle v-if="isLod" :currentItem="steps[current]" @score="handleScore"
                   @canNext="nextc = true"></itemTitle>
      </div>
      <div class="steps-action">
        <a-button
          style="margin-left: 20px"
          type="success"
          @click="submit"
          size="large"
        >
          交卷
        </a-button>

        <a-button size="large" v-if="current < steps.length - 1" type="primary" @click="next">提交本题，并到下一题</a-button>


        <!--        <a-button v-if="current > 0" style="margin-left: 8px" @click="prev">上一题</a-button>-->
        <a-button size="large" style="float:right" @click="cacelTitle" type="primary" danger>取消测试(成绩作废)
        </a-button>
      </div>
    </a-card>
    <a-card>
      <countdown v-if="start" :remainTime="countdowntime" @finish="onFinish"></countdown>
    </a-card>
  </div>
</template>
<script lang="ts" setup>
import {defineComponent, ref} from 'vue';
import {message} from 'ant-design-vue';
import {examinationTitleList} from "../../defend/ExaminationAll.api"
import itemTitle from "./TitleItem.vue"
import {useMessage} from "@/hooks/web/useMessage";
import {saveOrUpdate} from "../../log/AnswerQuestionLog.api"
import countdown from "@/views/test/testOnLine/components/countdown.vue";


// 消息
const {createMessage, createErrorModal, createConfirm} = useMessage();

// Emits声明
const emit = defineEmits(['cancel']);
// vue3的方式接受参数
const props = defineProps({
  paperInfo: {
    type: Object,
  }
});

// 当前时间
const start = ref(false)
let countdowntime = 0;
//
// 获取试卷的分钟数值，计算出多少秒
function countdownon() {
  countdowntime = Number(props.paperInfo?.limitTime) * 60
  start.value = true
}

countdownon()


// 倒计时加载完毕
const onFinish = () => {
  // 直接交卷
  submit("time");
};

// 组件是否加载
const isLod = ref(false)

// 本体是否做完
const nextc = ref(false)


const current = ref<number>(0);
const next = () => {
  console.log(nextc.value, '是否答完本题')
  // 判断这题是否已经做完
  if (nextc.value) {
    current.value++;
    nextc.value = false
  } else {
    createMessage.warning("请答完本题！")
  }

};
const prev = () => {
  current.value--;
};
const steps = ref([])


// 获取试卷相关的提题目
async function getAllTitle() {
  await examinationTitleList({id: props.paperInfo?.examinationCode}).then((res) => {
    console.log(res)
    steps.value = res.result.records
    steps.value.push({remark: '恭喜完成！', questionId: '999'})
    isLod.value = true
  })
}

getAllTitle()

function dateFormat() {
  var date = new Date()
  var year = date.getFullYear()
  var month = date.getMonth() + 1 < 10 ?
    '0' + (date.getMonth() + 1) : date.getMonth() + 1
  var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
  var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
  var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
  var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
  console.log(year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds)
  return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
}

// 子数据
const logDateDetail = ref<Array<Object>>([])
// 总得分
const totalScore = ref<number>(0)

// 统计分数
function handleScore(data) {
  /**
   * 子表部分
   * data包含：
   * 题目 id，
   * 题目名称
   * 答案选择
   * 得分
   */
  const item = {
    // 问题名称
    titleName: data.title.remark,
    // 问题编码/问题id
    titleCode: data.title.questionId,
    // 答案编码
    answerCode: data.answer.answerCode,
    // 答案名称
    remark: data.answer.answerName,
    // 本题得分
    socre: data.answer.answerScore,
    // 本体的当前时间，格式化一下时间
    makeTime: dateFormat()
  }
  logDateDetail.value.push(item)

  console.log(logDateDetail.value, "every");

  // 记分数
  totalScore.value += Number(data.answer.answerScore)
}

// 交卷方法
async function submit(flag) {
  if (flag === 'time'){
    /**
     * 主表部分：
     * 做题人
     * 试卷编码
     * 得分
     * 试卷分类
     * 当前做题时间
     */
      // 主数据
    const item = {
        // 试卷编码
        paperCode: props.paperInfo?.examinationCode,
        // 试卷名称
        paperName: props.paperInfo?.examinationPaper,
        // 总分
        totalScore: totalScore.value,
        // 试卷分类
        paperType: props.paperInfo?.examinationType,
        // 子表
        answerQuestionLogDetailList: logDateDetail.value
      }
    // 数据持久化
    await saveOrUpdate(item, false).then((res) => {
      console.log(res)
      if (res === '添加成功！') {
        createMessage.success("完成测试！")
        emit("cancel")
      }
    })
  }else {
    createConfirm({
      iconType: 'warning',
      title: '提示',
      content: '是否交卷？',
      okText: '确认',
      onOk: async function () {
        /**
         * 主表部分：
         * 做题人
         * 试卷编码
         * 得分
         * 试卷分类
         * 当前做题时间
         */
          // 主数据
        const item = {
            // 试卷编码
            paperCode: props.paperInfo?.examinationCode,
            // 试卷名称
            paperName: props.paperInfo?.examinationPaper,
            // 总分
            totalScore: totalScore.value,
            // 试卷分类
            paperType: props.paperInfo?.examinationType,
            // 子表
            answerQuestionLogDetailList: logDateDetail.value
          }
        // 数据持久化
        await saveOrUpdate(item, false).then((res) => {
          console.log(res)
          if (res === '添加成功！') {
            createMessage.success("完成测试！")
            emit("cancel")
          }
        })
      },
    });
  }

}

// 取消答题
function cacelTitle() {
  createConfirm({
    iconType: 'warning',
    title: '提示',
    content: '是否结束测试？',
    okText: '确认',
    onOk: function () {
      emit("cancel")
    },
  });

}
</script>
<style scoped>
.steps-content {
  display: flex;
  justify-content: center;
  border: 1px dashed #e9e9e9;
  border-radius: 6px;
  background-color: #fafafa;
  min-height: 200px;
}

.steps-action {
  display: flex;
  justify-content: space-between;
  margin-top: 24px;
}

[data-theme='dark'] .steps-content {
  background-color: #2f2f2f;
  border: 1px dashed #404040;
}

.countdown {
  font-size: 48px;
  color: #fff;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
}
</style>
