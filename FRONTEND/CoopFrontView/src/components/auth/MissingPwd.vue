<template>
  <v-container>
    <v-layout row v-if="error">
      <v-flex xs12 sm6 offset-sm3>
        <app-alert @dismissed="onDismissed" :text="error.message"></app-alert>
      </v-flex>
    </v-layout>
    <v-layout row>
      
      <v-flex xs12 sm6 offset-sm3>
        <img src="https://github.com/AOMC-Coop/AOMC/blob/master/image/coop_logo2.png?raw=true" width="70" height="70">
        <br><br>
        <v-toolbar>
            
            <v-toolbar-items>
            <!-- 추후 버튼 말고 다른 태그로 교체 -->
            <v-btn flat>Enter your e-mail</v-btn>
            </v-toolbar-items>
        </v-toolbar>
        <img
        <v-card>
          <v-card-text>
            <v-container>
              <form @submit.prevent="sendMail"> 
                <v-layout row>
                  <v-flex xs12>
                    <!-- 잘못된 양식의 이메일 입력시 경고 띄우는 코드 가져오기 -->
                    <v-text-field
                      name="email"
                      label="E-mail"
                      id="email"
                      v-model="userInfo.uid"
                      type="email"
                      required></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout>
                  <v-flex xs12>
                    <v-btn type="submit" :loading="loading">Send email to verify</v-btn>
                  </v-flex>
                </v-layout>
              </form>
            </v-container>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import axios from 'axios'

const baseURI = localStorage.getItem('baseURI')

  export default {
    name : 'Signin',
    data () {
      return {
        userInfo: {
          uid: '',
        },
        info:'',
      }
    },
    props: {
      source: String
    },
    computed: {
      user () {
        return this.$store.getters.user
      },
      error () {
        return this.$store.getters.error
      },
      loading () {
        return this.$store.getters.loading
      }
    },
    watch: {
      user (value) {
        if (value !== null && value !== undefined) {
          this.$router.push('/chat/0')
        }
      }
    },
    methods: {
      // onSignin () {
      //   this.$store.dispatch('signUserIn', {email: this.email, password: this.password})
      // },

      onDismissed () {
        this.$store.dispatch('clearError')
      },
      sendMail : function () {
      // this.$store.state.ip + `:8082/login`  
      let idx = localStorage.getItem("userIdx");
      let url = `http://localhost:8082/api/members/missing/` + idx;
        axios.post(
          url,
          { headers: { 'token': `${token}` }}    
          ).then(response => { 
            let description = response.data.description
            if(description == "Fail Login : Wrong ID"){
              alert("Your ID is not signed up yet! please check your ID again!")
            } else if (description == "Fail Login : Wrong Password"){
              alert("Wrong Password!")
            } else if (description == "Fail Login"){
              alert("Withdrew ID!")
            } else {



              localStorage.setItem('token', response.data.data.token)
              console.log(JSON.stringify(localStorage))
              localStorage.setItem('idx', response.data.data.idx)
              
              localStorage.setItem("userId", response.data.data.uid); //test용으로 임의로 넣어놈. 원래는 로그인 할때 넣어야 함
              localStorage.setItem("userIdx", response.data.data.idx);
              // localStorage.setItem("userIdx", 5);
              localStorage.setItem("userNickName", response.data.data.nickname);

              this.$store.state.userId = response.data.data.uid;
              this.$store.state.userIdx = response.data.data.idx; //test용으로 넣어놈. 로그인 할때 받아야함
              this.$store.state.userNickName = response.data.data.nickname; //test용으로 넣어놈. 로그인 할때 받아야함

              console.log(this.$store.state.userId);
              console.log(this.$store.state.userIdx);
              console.log(this.$store.state.userNickName);

              console.log(localStorage.getItem("userId"));
              console.log(localStorage.getItem("userIdx"));
              console.log(localStorage.getItem("userNickName"));

              this.checkIsTeam(response.data.data.idx);
             
            }
            }
          ).catch(e => {
            console.log(e)
            this.errors(e)
            location.href = './login'
          })
      }   
    }
  }
  // `${baseURI}/api/login`
</script>

