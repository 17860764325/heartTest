<template>
  <div class="page">
    <a-row v-for="item in listAll" style="margin: 10px" >
      <span style="width: 100px">{{ item.realname }}
        <span v-if="item.onLine===true" style="color: #00bb00">(在线)</span>
        <span v-else style="color: red">(离线)</span>
      </span>
      <a-button style="width: 100px" type="primary" @click="chat(item.username)">联系</a-button>
    </a-row>
  </div>

</template>
<script lang="ts" setup>
import {listNoCareTenant} from "@/views/system/user/user.api"
import {ref} from "vue";
import {store} from "@/store";
const username = store.state.value["app-user"]["userInfo"].username
const realName = store.state.value["app-user"]["userInfo"].realname
const emit = defineEmits(['changePeople']);
const props = defineProps({
  // 在线人数
  userList: {
    type: Array
  }
})
const listAll = ref([])
// 所有人员
async function getALlPeople(){
  console.log("sdfa")
  await listNoCareTenant({}).then(res =>{
    res.records.forEach(item => {
      if (item.username !== username){
        listAll.value.push(item)
      }
    })
    console.log(props.userList,'1')
    listAll.value.forEach(item1 => {
      const flterData = props.userList?.filter(no => no?.name === item1.username)
      console.log(flterData,'2')
      if (flterData.length > 0 ){
        item1.onLine = true
      }
    })
    console.log(listAll.value,'3')
  })
}
getALlPeople()


function chat(name){
  emit("changePeople",name)
}
</script>
<style>
.page{
  width: 100%;
  border: grey 3px solid ;
  background-color: white;
}
</style>
