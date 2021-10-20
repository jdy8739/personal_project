<template>
  <div class="out" style="text-align: center; vertical-align: middle; margin-top: 200px;">

    <vueper-slides class="in" :touchable="false" :bullets="false" :arrows="false" ref="first" 
    style="width: 100%; display: inline-block;" :slide-ratio="1 / 2" fixed-height="300px;">

      <vueper-slide
        :key="1"
        :style="'background-color: white;'">
        <template v-slot:content>

            <p class="mainText">사용할 ID를 입력해주세요</p>

          <div style="margin-top: 40px; height: 200px; background: #42b8d4">
            <input type="text" style="width: 20%; margin-top: 40px;" v-model="id"/>

            <v-btn text style="margin-left: 10px;" class="btnText" @click="checkIdDupli">
                ID 중복 확인
            </v-btn>

            <br/>

            <v-btn text large disabled v-if="!isDupliChecked">
                <v-icon color="white;">
                    done
                </v-icon>
            </v-btn>

            <v-btn text large @click="$refs.first.next()" v-else-if="isDupliChecked">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">사용 가능한 id입니다</p>
            </v-btn>
          </div>
          
        </template>
      </vueper-slide>
      
      <vueper-slide
        :key="2"
        :style="'background-color: white;'">
        <template v-slot:content>

            <p class="mainText">비밀번호를 입력해주세요</p>

          <div style="margin-top: 40px; height: 200px; background: #42b8d4">

            <label style="color: white;">
                비밀번호
                <input type="password" style="width: 10%; margin-top: 40px;" v-model="password"/>
            </label>
            &nbsp;&nbsp;&nbsp;
            <label style="color: white;">
                비밀번호 확인
                <input type="password" style="width: 10%; margin-top: 40px;" v-model="passwordChk"/>
            </label>
            <br/>
            <v-btn text @click="$refs.first.previous()" class="btnText" style="margin: 10px; color: white;">
                이전
            </v-btn>

            <v-btn text large disabled v-if="!checkSamePassword()">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">비밀번호와 비밀번호 재확인을 입력해주세요</p>
            </v-btn>

            <v-btn text large @click="$refs.first.next()" v-else-if="checkSamePassword()">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">비밀번호가 일치합니다</p>
            </v-btn>
          </div>
          
        </template>
      </vueper-slide>

      <vueper-slide
        :key="3"
        :style="'background-color: white;'">
        <template v-slot:content>

            <p class="mainText">email과 휴대번호를 입력해주세요</p>

          <div style="margin-top: 40px; height: 200px; background: #42b8d4">
            <label style="color: white;">
                E-mail
                <input type="text" style="width: 12%; margin-top: 40px;" v-model="email"/>
            </label>
            &nbsp;&nbsp;&nbsp;
            <label style="color: white;">
                휴대번호
                <input type="text" style="width: 12%; margin-top: 40px;" v-model="phoneNumber"/>
            </label>

            <br/>
            <v-btn text @click="$refs.first.previous()" class="btnText" style="margin: 10px; color: white;">
                이전
            </v-btn>

            <v-btn text large disabled v-if="!checkMailAndNumber()">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">이메일과 휴대번호가 올바른지 확인해주세요</p>
            </v-btn>

            <v-btn text large @click="$refs.first.next()" v-else-if="checkMailAndNumber()">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">다음으로</p>
            </v-btn>
          </div>
          
        </template>
      </vueper-slide>

      <vueper-slide
        :key="4"
        :style="'background-color: white;'">
        <template v-slot:content>

            <p class="mainText">이름과 생년월일을 입력해주세요</p>

          <div style="margin-top: 40px; height: 200px; background: #42b8d4">

            <label style="color: white;">
                이름
                <input type="text" style="width: 8%; margin-top: 40px; margin-right: 20px;" v-model="name"/>
            </label>

            <label style="color: white;">
                생년월일 6자리
                <input type="text" style="width: 5%; margin-top: 40px;" v-model="birthday"/>
            </label>
         
            <br/>
            <v-btn text @click="$refs.first.previous()" class="btnText" style="margin: 10px; color: white;">
                이전
            </v-btn>

            <v-btn text large disabled v-if="!isRightBirthday()">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">생일이 올바른지 확인해주세요</p>
            </v-btn>

            <v-btn text large @click="$refs.first.next()" v-else-if="isRightBirthday()">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">다음으로</p>
            </v-btn>
          </div>
          
        </template>
      </vueper-slide>

      <vueper-slide
        :key="5"
        :style="'background-color: white;'">
        <template v-slot:content>

            <p class="mainText">반려 동물을 키우거나 키워보신적이 있나요?</p>

          <div style="margin-top: 40px; height: 200px; background: #42b8d4;">
            
            
                <v-radio-group row v-model="haveRaisedPet" style="display: inline-block; margin-top: 40px;" class="btnText" dark>
                    <v-radio
                        label="네"
                        value="y"
                    ></v-radio>
                    <v-radio
                        label="아니오"
                        value="n"
                    ></v-radio>
                </v-radio-group>
                     
            <br/>
            <v-btn text @click="$refs.first.previous()" class="btnText" style="margin: 10px; color: white;">
                이전
            </v-btn>

            <v-btn text large @click="$refs.first.next()">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">다음으로</p>
            </v-btn>
          </div>
          
        </template>
      </vueper-slide>

      <vueper-slide
        v-if="haveRaisedPet == 'y'"
        :key="6"
        :style="'background-color: white;'">
        <template v-slot:content>

            <p class="mainText">어떤 반려동물과 함께하고있나요?</p>

          <div style="margin-top: 40px; height: 200px; background: #42b8d4;">

                <div style="display: inline-block; margin-top: 40px;">
                    <span class="row btnText">
                        <v-checkbox label="강아지" value="dog" v-model="whichAnimal" dark style="margin-right: 20px;"/>
                        <div v-if="dogChecked()" style="margin-right: 40px;">
                            <input type="number" style="width: 50px; margin-top: 10px;" v-model="numDog"/>
                            마리
                        </div>

                        <v-checkbox label="고양이" value="cat" v-model="whichAnimal" dark style="margin-right: 20px;"/>
                        <div v-if="catChecked()" style="margin-right: 40px;">
                            <input type="number" style="width: 50px; margin-top: 10px;" v-model="numCat"/>
                            마리
                        </div>

                        <v-checkbox label="그 외" value="etc" v-model="whichAnimal" dark/>
                    </span>
                </div>
                    
            <br/>
            <v-btn text @click="$refs.first.previous()" class="btnText" style="margin: 10px; color: white;">
                이전
            </v-btn>

            <v-btn text large @click="havePets()">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">다음으로</p>
            </v-btn>
          </div>
          
        </template>
      </vueper-slide>

      <vueper-slide
        :key="7"
        :style="'background-color: white;'">
        <template v-slot:content>

            <p class="mainText">입력하신 정보가 맞나요? 맞다면 회원가입을 눌러주세요 :)</p>

          <div style="margin-top: 40px; height: 200px; background: #42b8d4;">
            
            <div style="justify-content: center; padding-top: 40px;">
                <p class="btnText" style="margin-right: 30px; display: inline;">id: {{ id }}</p>
                <p class="btnText" style="margin-right: 30px; display: inline;">e-mail: {{ email }}</p>
                <p class="btnText" style="margin-right: 30px; display: inline;">휴대번호: {{ phoneNumber }}</p>
                <p class="btnText" style="margin-right: 30px; display: inline;">생년월일: {{ birthday }}</p>

                <p v-if="petsRaised != ''" class="btnText" style="display: inline;">반려동물: {{ petsRaised }}</p>
                <p v-else class="btnText" style="display: inline;">반려동물: 없음</p>
            </div>
           
            <br/>
            <v-btn text @click="$refs.first.previous()" class="btnText" style="margin: 10px; color: white;">
                이전
            </v-btn>

            <v-btn text large @click="$refs.first.next()">
                <p class="btnText" style="margin: 10px;">회원가입</p>
            </v-btn>
          </div>
          
        </template>
      </vueper-slide>

      <vueper-slide
        :key="8"
        :style="'background-color: white;'">
        <template v-slot:content>

            <p class="mainText">가입을 축하드려요! 마지막으로 본 서비스에서 사용할 닉네임을 설정해주세요 :)</p>

          <div style="margin-top: 40px; height: 200px; background: #42b8d4;">
            <input type="text" style="width: 10%; margin-top: 40px;" v-model="nickname"/>

            <v-btn text style="margin-left: 10px;" class="btnText" @click="checkNicknameDupli">
                닉네임 중복 확인
            </v-btn>

            <br/>
   
            <p v-if="!isDupliNickChecked" class="btnText" style="margin: 10px;">닉네임 중복을 확인해주세요!</p>

            <v-btn v-else-if="isDupliNickChecked" text large @click="singnup">
                <v-icon color="white;">
                    done
                </v-icon>
                <p class="btnText" style="margin: 10px;">확인</p>
            </v-btn>
          </div>
          
        </template>
      </vueper-slide>

    </vueper-slides>
  </div>
</template>

<script>
import axios from 'axios'

import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'

export default {
  components: { VueperSlides, VueperSlide },
  data() {
      return {
          isDupliChecked: false,
          id: '', //
          confirmedId: '',

          password: '', //
          passwordChk: '',
          email: '', //
          phoneNumber: '', //

          name: '', //
          birthday: '', //

          haveRaisedPet: '',
          whichAnimal: [],

          numDog: 0,
          numCat: 0,

          petsRaised: '', //
          nickname: '',
          confirmedNickname: '',

          isDupliNickChecked: false
      }
  },
  methods: {
    checkIdDupli() {

        const id = this.id

        axios.post(`http://localhost:8888/petto/member/idDupliChk/${ id }`)
            .then((res) => {
                if(res.data == true) {

                    this.isDupliChecked = true
                    this.confirmedId = this.id
                } else {
                    alert('동일한 id가 존재합니다.')
                }
            })
            .catch(() => {
                alert('잠시 후에 다시 시도해주세요.')
            })
    },

    checkSamePassword() {
        if(this.password != "" && this.password == this.passwordChk) {
            return true
        } else {
            return false
        }
    },

    checkMailAndNumber() {
        if(this.email != "" && this.phoneNumber != "") {
            return true
        } else {
            return false
        }
    },

    isRightBirthday() {
        return true
    },

    dogChecked() {
        if(this.whichAnimal.includes("dog")) {
            return true
        }
        return false
    },

    catChecked() {
        if(this.whichAnimal.includes("cat")) {
            return true
        }
        return false
    },

    havePets() {
        if(this.whichAnimal == '') {
            alert('선택사항에 체크해주세요!')
            return false
        }

        var tmpArr = []
        if(this.whichAnimal.includes("dog")) {

            if(this.numDog <= 0) return false
            tmpArr.push("강아지 " + this.numDog + "마리")
        }

        if (this.whichAnimal.includes("cat")) {

            if(this.numCat <= 0) return false
            tmpArr.push("고양이 " + this.numCat + "마리")
        }

        if (this.whichAnimal.includes("etc")) tmpArr.push("그 외")

        let tmpArrSecond = JSON.stringify(tmpArr)
        this.petsRaised = tmpArrSecond.replace(/\[/gi, "").replace(/\]/gi, "").replace(/"/gi, ""),
  
        this.$refs.first.next()
    },

    checkNicknameDupli() {
        const nickname = this.nickname

        axios.post(`http://localhost:8888/petto/member/nicknameDupliChk/${ nickname }`)
            .then((res) => {
                if(res.data == true) {

                    this.isDupliNickChecked = true
                    this.confirmedNickname = this.nickname

                } else {
                    alert('동일한 닉네임이 존재합니다.')
                }
            })
            .catch(() => {
                alert('잠시 후에 다시 시도해주세요.')
            })
    },

    singnup() {
        const id = this.id
        const password = this.password
        const email = this.email
        const phoneNumber = this.phoneNumber
        const name = this.name
        const birthday = this.birthday
        const petsRaised = this.petsRaised
        const nickname = this.nickname

        axios.post('http://localhost:8888/petto/member/signup', { id, password, email, phoneNumber, name, birthday, petsRaised, nickname })
            .then(() => {
                alert('가입이 완료되었습니다!')
                this.$router.push({
                    name: 'MainPage'
                })
            })
            .catch(() => {
                alert('잠시 후에 다시 시도해주세요.')
            })
    },

    showAlert(msg) {
      alert(msg);
    }
  },
  watch: {
      id() {
          if(this.id != this.confirmedId) {
              this.isDupliChecked = false
          }
      },
      nickname() {
          if(this.nickname != this.confirmedNickname) {
              this.isDupliNickChecked = false
          }
      }
  },
  computed: {
      
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Batang:wght@700&display=swap');

.mainText {
    margin-top: 50px;
    font-family: 'Gowun Batang', serif;
    font-size: 20px;
    font-weight: normal;
    font-style: normal;
    color: #42b8d4;
}

.btnText {
    font-family: 'Gowun Batang', serif;
    color: white;
}


</style>