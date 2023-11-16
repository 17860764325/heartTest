<template>
  <div v-for="(item, index) in comments" :key="index" style="padding-right: 3px">
    <!-- 一级 -->
    <div class="auther">
      <img class="imga" :src="item.avatar" alt="" />
      <div>
        <div class="auther-men1">{{ item.userName }}</div>
      </div>
      <!-- 删除和回复按钮 -->
      <button class="auther-delte " @click="reply(item)"><img class="auther-delte-icon"
                                                              src="@/assets/delete.png" /></button>
      <button class="auther-reply0" @click="reply(item)">回复</button>
    </div>
    <div
      style="
        margin-left: 40px;
        margin-top: -8px;
        font-size: 12px;
        color: rgb(127, 127, 129);
      "
    >
      2022.12.3
    </div>
    <div
      class="reply"
      style="word-wrap: break-word; word-break: break-all; overflow: hidden"
    >
      {{ item.content }}
    </div>
    <div v-show="showUid === item.uid">
      <!-- 回复框 -->
      <div class="auther">
        <img
          class="imga"
          src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
          alt=""
        />
        <div class="auther-men">作者</div>
      </div>
      <a-form-item class=".auther-a">
        <a-textarea
          @change="handinput"
          style="left: 30px; width: 250px"
          :rows="1"
          :value="textvalue"
        ></a-textarea>
        <div
          v-show="waring1"
          style="margin-left: 30px; font-size: 9px; color: red"
        >
          评论区内容不能为空
        </div>
      </a-form-item>
      <div class="sure-no">
        <button class="bind-sure" @click="bindsure(item)">确认</button>
        <button class="bind-no" @click="bindcancle(item)">取消</button>
      </div>
    </div>
    <!-- 11 -->
    <div>
      <div v-show="!item.showTitle" @click="showTitles(item)">
        <!--评论折叠部分 -->
        <div class="show-title"  v-if="item.reply.length>0">
          查看全部{{item.reply.length}}条回复<down-outlined style="color: rgba(0, 0, 0, 0.5);font-size:12px;margin-left:2px;"/>
        </div>
      </div>
      <!-- 二级回复显示 -->
      <div v-show="item.showTitle"
           style="margin-left: 20px"
           v-for="(items, indexs) in item.reply"
           :key="indexs"
      >
        <div class="auther">
          <img class="imga" :src="items.avatar" alt="" />
          <div>
            <div class="auther-men4">作者 回复 {{ item.userName }}</div>
          </div>
          <!-- 删除和回复按钮 -->
          <button class="auther-delte " @click="reply(item)"><img class="auther-delte-icon"
                                                                  src="@/assets/delete.png" /></button>
          <button class="auther-reply3" @click="reply(items)">回复</button>
        </div>
        <div
          style="
            margin-left: 40px;
            margin-top: -8px;
            font-size: 12px;
            color: rgb(127, 127, 129);
          "
        >
          2022.12.3
        </div>
        <div
          class="replyon"
          style="word-wrap: break-word; word-break: break-all; overflow: hidden"
        >
          {{ items.content }}
        </div>
        <div v-show="showUid === items.uid">
          <!-- 回复框 -->
          <div class="auther">
            <img
              class="imga"
              src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
              alt=""
            />
            <div class="auther-men">作者</div>
          </div>
          <a-form-item class=".auther-a">
            <a-textarea
              @change="handinput"
              style="left: 30px; width: 250px"
              :rows="1"
              :value="textvalue"
            ></a-textarea>
            <div
              v-show="waring1"
              style="margin-left: 30px; font-size: 9px; color: red"
            >
              评论区内容不能为空
            </div>
          </a-form-item>
          <div class="sure-no">
            <button class="bind-sure" @click="bindsure">确认</button>
            <button class="bind-no" @click="bindcancle(item)">取消</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {
  defineComponent,
  onMounted,
  onBeforeUnmount,
  ref,
  reactive,
  watch,
} from "vue";
import Comment from "@/components/comment/Comment.vue";
import { storeToRefs } from "pinia";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import {RestOutlined,DownOutlined} from "@ant-design/icons-vue";
dayjs.extend(relativeTime);
export default defineComponent({
  components: {
    Comment,
    RestOutlined,
    DownOutlined,

  },
  name: "ComList",
  props: ["comments"],
  setup(props) {
    const comments = props.comments;
    let showreply = ref(false);
    let textvalue = ref("");
    let showUid = ref("1");
    let showNew = ref("121");
    let comment = ref("");
    let waring1 = ref(false);
    let Boole = false;
    let showtitle1 = ref(true);
    let showtitle2 = ref(false);
    // 显示评论
    const showTitles = (item: any) => {
      item.showTitle="true"
    };
    //回复
    const reply = (item: any) => {
      console.log(item, "item");
      item.show = "true";
      showUid.value = item.uid;
    };
    // 输入框按钮
    const handinput = (e: any) => {
      console.log(e.target.value, "e");
      textvalue.value = e.target.value;
    };
    // 确定按钮
    const bindsure = (item: any) => {
      if (textvalue.value === "") {
        waring1.value = true;
      } else {
        waring1.value = false;
        textvalue.value = "";
        showUid.value = "1";
        console.log(dataTime.value, "当前时间");
      }
    };
    // 取消按钮
    const bindcancle = (item: any) => {
      textvalue.value = "";
      showUid.value = "1";
      waring1.value = false;
    };
    let dataTime = ref("");

    //定义 timer 初始值及类型
    let timer: NodeJS.Timer | null = null;

    // 当前时间
    const getNowTime = () => {
      const now = new Date();
      const year = now.getFullYear();
      const month =
        now.getMonth() >= 9 ? now.getMonth() + 1 : `0${now.getMonth() + 1}`;
      const date = now.getDate() >= 10 ? now.getDate() : `0${now.getDate()}`;
      const hour = now.getHours() >= 10 ? now.getHours() : `0${now.getHours()}`;
      const minutes =
        now.getMinutes() >= 10 ? now.getMinutes() : `0${now.getMinutes()}`;
      const seconds =
        now.getSeconds() >= 10 ? now.getSeconds() : `0${now.getSeconds()}`;
      dataTime.value = `${year}年${month}月${date}日 ${hour}:${minutes}:${seconds}`;
    };
    onMounted(() => {
      getNowTime();
      timer = setInterval(() => {
        // console.log(dataTime.value, "time");
        getNowTime();
      }, 1000);
    });

    onBeforeUnmount(() => {
      // 清理定时器要处理 timer 的类型
      clearInterval(Number(timer));
    });
    return {
      showtitle1,
      showtitle2,
      showTitles,
      dataTime,
      handinput,
      waring1,
      textvalue,
      Boole,
      reply,
      showUid,
      showNew,
      showreply,
      comment,
      comments,
      bindsure,
      bindcancle,
    };
  },
});
</script>

<style>
.sure-no {
  margin-left: 40px;
  margin-top: -16px;
  display: flex;
  flex-wrap: nowrap;
}
.bind-sure,
.bind-no {
  width: 58px;
  height: 24px;
  text-align: center;
  line-height: 24px;
  margin-left: 10px;
  background-color: #eceaea;
  border: 1px solid #d2d0d0;
  border-radius: 2px;
}
.auther-a {
  margin-left: 30px;
}
.auther {
  width: 300px;
  height: 30px;
  line-height: 30px;
  /* border: 1px solid black; */
  display: flex;
  flex-wrap: nowrap;
}
.auther2 {
  width: 300px;
  height: 30px;
  line-height: 30px;
  margin-left: 30px;
  /* border: 1px solid black; */
  display: flex;
  flex-wrap: nowrap;
}
.reply {
  width: 240px;
  /* height: 30px; */
  /* border: 1px solid rgb(202, 200, 200); */
  margin-left: 40px;
  word-wrap: break-word;
  word-break: normal;
}
.replyon {
  width: 220px;
  /* height: 30px; */
  /* border: 1px solid rgb(202, 200, 200); */
  margin-left: 40px;
  word-wrap: break-word;
  word-break: normal;
}
.imga {
  width: 30px;
  height: 30px;
  border: 1px solid rgb(232, 229, 229);
  border-radius: 50%;
}
.auther-men {
  margin-left: 10px;
  width: 130px;
}
.auther-men1 {
  margin-left: 10px;
  width: 180px;
}
.show-title {
  margin-left: 41px;
  font-size: 8px;
  color: #918c8c;
}
.auther-men4 {
  margin-left: 10px;
  width: 160px;
}
.auther-menn {
  margin-left: 10px;
  width: 160px;
}
.auther-men2 {
  margin-left: 10px;
  width: 150px;
}
.auther-delte {
  width: 14px;
  height: 24px;
  line-height: 24px;
  text-align: center;
  margin-top: 3px;
  background-color: none;
  border: none;
}
.auther-delte-icon{
  width: 14px;
  height:14px;
  margin-top:-3px;
}
.auther-reply1 {
  width: 90px;
  height: 24px;
  line-height: 24px;
  text-align: center;
  margin-top: 3px;
  border: none;
  background-color: #77d197;
  margin-left: 20px;
}
.auther-reply3 {
  width: 40px;
  height: 24px;
  line-height: 24px;
  text-align: center;
  margin-top: 3px;
  background-color: #f9f9f7;
  color: rgb(127, 127, 129);
  margin-right: 2px;
  border: none;
  margin-left: 13px;
}
.auther-reply0 {
  width: 40px;
  height: 24px;
  line-height: 24px;
  text-align: center;
  margin-top: 3px;
  background-color: #f9f9f7;
  color: rgb(127, 127, 129);
  margin-right: 2px;
  border: none;
  margin-left: 13px;
}
</style>
