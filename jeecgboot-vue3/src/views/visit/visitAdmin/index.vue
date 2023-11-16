<template>
  <a-row style="width: 100%;height: 100%">
    <!--    // 人员列表-->
    <a-col style="width: 20%">
      <!--      // 在线人员数-->
      <a-row style="height: 50%">
        <!--        需要做成组件，方便每次获取进行刷新-->
        <userListModal v-if="reloadOnLineUsers" :userList="userList"
                       @changePeople="cahngePeople"></userListModal>
      </a-row>
      <!--      // 个人信息-->
      <a-row style="display:flex;flex-direction: column;border: 3px solid grey;height: 50%;background-color: white">
        <h1>你的用户名：{{ realName }}</h1>
        <p></p>
        <a-button @click="conecct" type="primary">上线</a-button>
      </a-row>
    </a-col>
    <!--    // 聊天框-->
    <a-col style="width: 80%;border: 3px solid grey;">
      <!--      // 聊天记录-->
      <a-row style="height: 80%;flex-direction: column;border: grey 3px solid">
        <!--        <ChatLog :chhatLogList="chatLogs" ></ChatLog>-->
        <div v-if="chatLogLodding">
          <div class="scroll-wrap">
            <ScrollContainer style="border: grey 3px solid" class="mt-4" ref="scrollRef">
              <ul class="p-3">
                <template v-for="index in chatLogs" :key="index.id">
                  <li class="p-2" :style="{ display:'flex',flexDirection: 'column',alignItems: index.sendPerson === username?'flex-end':'flex-start'}">
                    <h1 :style="{color:index.sendPerson === username?'green':'red'}">
                      {{ index.createTime }}｜｜《{{ index.sendRealName }}》:</h1>
                    <span>{{ index.about }}</span>
                  </li>
                </template>
              </ul>
            </ScrollContainer>
          </div>
          <div class="my-5">
            <a-button @click="scrollTo(0)" class="mr-2"> 滚动到顶部</a-button>
            <a-button @click="scrollBottom()" class="mr-2"> 滚动到底部</a-button>
          </div>
        </div>
      </a-row>
      <!--      // 信息发发送-->
      <a-row style="height: 20%;background-color: #6c8cfd">
        <textarea v-model="value" style="width: 100%;height: 100px;"
                  v-on:keyup.enter="sendMessage"></textarea>
        <a-button type="success" @click="sendMessage">发送</a-button>
<!--        <a-button type="success" @click="addData">添加数据</a-button>-->
      </a-row>
    </a-col>
  </a-row>


  <!--  <h1>姓名：</h1>-->
  <!--  <a-input></a-input>-->
  <!--  <a-button @click="conecct">链接管理员进行咨询</a-button>-->
  <!--  <h1>发送内容:</h1>-->
  <!--  <a-input :value(v-model)="message"></a-input>-->
  <!--  <a-button @click="sendMessage">发送</a-button>-->
  <!--  <a-button @click="closeLink">关闭连接</a-button>-->
</template>
<script lang="ts" setup>
import {ScrollActionType, ScrollContainer} from "@/components/Container";
import {store} from '@/store'
import {ref, unref} from "vue";
import userListModal from "./components/userList/userList.vue"
import {Tinymce} from "@/components/Tinymce";
import ChatLog from "./components/chatLog/chatLog.vue"
import {defHttp} from '/@/utils/http/axios';
import {useMessage} from "@/hooks/web/useMessage";

// 消息
const {createMessage, createErrorModal, createConfirm} = useMessage();

const message = ref()
const wb = ref()
// 发送方-当前登录人
const username = store.state.value["app-user"]["userInfo"].username
const realName = store.state.value["app-user"]["userInfo"].realname
// 接收方-根据用户交互不停改变
const reciveOne = ref()
// 人员列表--在线
const userList = ref<Array<Object>>([])

// 刷新在线人数
const reloadOnLineUsers = ref(true)
// 刷新聊天界面
const chatLogLodding = ref(false)

// 发送框内容
const value = ref();

// 发送框内容发生改变调用事件
function handleChange(value: string) {
  console.log(value);
}

// 根据 username 查询用户信息



// 创建链接，进到页面自动链接
function conecct() {
  wb.value = new WebSocket("ws://192.168.1.3:8080/jeecg-boot/qqServer/" + username)
  wb.value.onopen = function () {
    console.dir("-连接成功！");
  }
  wb.value.onmessage = function (data) {
    console.log(data, "data")
    // 如果接受的消息，开头是#@, 则是人员 list 的返回
    if (data.data !== undefined && data.data !== null && data.data !== '' && data.data.includes("#@,")) {
      userList.value = []
      reloadOnLineUsers.value = false
      data.data.split(",").forEach(item => {
        if (item !== '#@' && item !== '') {
          const user = {
            name: item
          }
          if (item !== username) {
            userList.value.push(user)
          }
          console.log(userList.value, "userList.value")
        }
      })
      setTimeout(()=>{
        reloadOnLineUsers.value = true
      },800)
    } else {
      // 刷新消息获取的方法重新渲染数据
      // 如果是其他的那就是正常消息的接受
      console.dir("-接收到消息:" + data.data);
      const reciveData = data.data.split("@#")
      console.log(reciveData,"reciveData")
      const message = {
        about:reciveData[1],
        sendPerson:reciveData[0],
        createTime:reciveData[2],
        sendRealName:reciveData[4]
      }
      reloadChatLogsAdd(message)
      setTimeout(() => {
        scrollBottom()
      }, 80)
    }
  }
}
//
// // 进页面自动连接
// conecct();

// 发送消息
function sendMessage() {
  if (reciveOne.value === null || reciveOne.value === '' || reciveOne.value === undefined) {
    createMessage.warning("请选择联系人！")
    return
  }
  if (value.value.trim() === '' || value.value.trim() === undefined || value.value.trim() === null) {
    createMessage.warning("请输入内容！")
    return
  }
  var nowDate = new Date();
  const sendMsgStr = username + "@#" + value.value + "@#" + (nowDate.getFullYear() + "-" + nowDate.getMonth() + "-" + nowDate.getDate() + " " + nowDate.getHours() + ":" + nowDate.getMinutes() + ":" + nowDate.getSeconds() + "@#" + reciveOne.value)
  wb.value.send(sendMsgStr)

  const message = {about:value.value,sendPerson:username,
    createTime:nowDate.getFullYear() + "-" + nowDate.getMonth() + "-" + nowDate.getDate() + " " + nowDate.getHours() + ":" + nowDate.getMinutes() + ":" + nowDate.getSeconds()}
  value.value = null
  console.log(message,'message')
  message.sendRealName = realName
  reloadChatLogsAdd(message)
  setTimeout(() => {
    scrollBottom()
  }, 80)
}

function closeLink() {
  wb.value.close();
}

// 消息记录
const chatLogs = ref([])

function cahngePeople(recivePerson) {
  reciveOne.value = recivePerson
  chatLogs.value = []
  chatLogLodding.value = false
  getChatLog(recivePerson)
  chatLogLodding.value = true
  setTimeout(() => {
    scrollBottom()
  }, 80)
}

async function getChatLog(recivePerson) {
  const params = {
    sendPerson: username,
    recivePerson: recivePerson
  }
  await defHttp.post({url: "/chat/chatLog/getChatLog", params}).then((res) => {
    chatLogs.value = res
  });
}

function submit() {
  console.log(value.value)
}

// 重新查询方法
function reloadChatLogs() {
  chatLogs.value = []
  chatLogLodding.value = false
  getChatLog(reciveOne.value)
  chatLogLodding.value = true
}
// 在原有基础上添加假数据方法
function reloadChatLogsAdd(message){
  chatLogs.value.push(message)
  setTimeout(() => {
    scrollBottom()
  }, 80)
}


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

function addData(){
  const dad = {about:'a',sendPerson:'as',createTime:'adsfa',sendRealName:'azcvz'}
chatLogs.value.push(dad)
  setTimeout(() => {
    scrollBottom()
  }, 80)
}

</script>
<style>
.scroll-wrap {
  width: 100%;
  height: 500px;
  background-color: white;
}
</style>
