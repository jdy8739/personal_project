<template>
    <div>

        <v-container style="width: 80%; margin-top: 30px;">
            <!-- <table border="1px">
                <tr class="listItem">
                    <td width="30">memberNo</td>
                    <td width="80" style="text-align: center">id</td>
                    <td width="80">name</td>
                    <td width="100">location</td>
                    <td width="80">identity</td>
                    <td width="100">birthDay</td>
                    <td width="80">phoneNo</td>
                    <td width="180" style="text-align: right">regDate</td>
                </tr>
                <tr v-if="!members">
                    <td colspan="8">회원 정보를 불러오지 못했습니다!</td>
                </tr>
                <tr v-else v-for="(member, index) in members" :key="index" class="item">
                   <td>{{ member.memberNo }}</td>
                   <td>
                       <p @click="sendNum(member.memberNo)" style="margin-bottom: -0px;">
                            <router-link :to="{ name: 'MemberDeletePage', params: { memberNo: member.memberNo } }">
                                    {{ member.id }}
                            </router-link>
                       </p>
                   </td>
                   <td>{{ member.name }}</td>
                   <td>{{ member.location }}</td>
                   <td>{{ member.memberIdentityList[0].identity }}</td> 
                   <td>{{ member.birthDay }}</td>
                   <td>{{ member.phoneNo }}</td>
                   <td style="text-align: right">{{ member.regDate }}</td>
                </tr>
            </table> -->
            <span style="float: left;">

                <v-select
                :items="auth"
                dark
                color="red"
                v-model="authFilter"
                style="width: 130px; display: inline-block;"/>

                &nbsp; &nbsp;

                <p style="display: inline-block;" class="description">회원 구분</p>

            </span>

            <br/>
            <br/>
            <br/>
            <br/>

            <v-data-table :headers="headerTitle"
                        :items="chkElem()"
                        :items-per-page="5"
                        :search="searchMember"
                        class="elevation-1"
                        dark>

                    <template v-slot:item="{ item, index }"> <!-- v-data-table에서 index뽑는 법 * item * 얘는 고정된 이름 -->
                        <tr>
                            <td>{{ index + 1 }}</td>
                            <td @click="handleClick(item.memberNo)">{{ item.id }}</td> 
                            <td>{{ item.name }}</td>
                            <td>{{ item.location }}</td>
                            <td>{{ item.memberIdentityList[0].identity }}</td>
                            <td>{{ item.birthDay }}</td>
                            <td>{{ item.phoneNo }}</td>
                            <td style="text-align: right">{{ item.regDate }}</td>
                        </tr>
                    </template>

            </v-data-table>

            <br/>

            <v-text-field label="찾을 회원의 정보를 입력하세요." v-model="searchMember" class="footerText"
            style="width: 300px; float: right;" color="error" dark/>

        </v-container>
    </div>
</template>

<script>
//import EventBus from '@/eventBus.js'

export default {
    name: 'MemberList',
    props: {
        members: {
            type: Array,
            requited: true
        }
    },
    data() {
        return {
            headerTitle: [
                { text: '회원 번호', value: 'memberNo' },
                { text: 'ID', value: 'id', width: '12%'},
                { text: '이름', value: 'name', width: '8%'},
                { text: '지역', value: 'location', width: '20%'},
                { text: 'Auth', value: 'identity', width: '5%'},
                { text: '생일', value: 'birthDay', width: '15%'},
                { text: '연락처', value: 'phoneNo', width: '10%'},
                { text: '가입일자', value: 'regDate', width: '15%'}
            ],
            searchMember: '',

            auth: [ 'individual', 'artist', '전체' ],
            authFilter: '',

            filteredMemberList: []
        }
    },
    methods: {
        sendNum(memberNo) {
            const payload = [ memberNo ]

            //EventBus.$emit('sendNum', payload)
            console.log('The num has been sent! ' + payload)
        },
        handleClick(memberNo) {
            this.$router.push({
                name:'MemberDeletePage',
                params: { memberNo: memberNo }
            })
        },

        chkElem() {
            if(this.authFilter == '' || this.authFilter == '전체') return this.members 
            else return this.filteredMemberList
        }
    },
    watch: {
        authFilter() {
            
            this.filteredMemberList = []
            
            if(this.authFilter == 'individual') {

                for(var i=0; i<this.members.length; i++) {
                    
                    if(this.members[i].memberIdentityList[0].identity == 'individual') this.filteredMemberList.push(this.members[i])
                }

            } else if(this.authFilter == 'artist') {

                for(var j=0; j<this.members.length; j++) {
                    
                    if(this.members[j].memberIdentityList[0].identity == 'artist') this.filteredMemberList.push(this.members[j])
                }
            } 
        }
    }
}
</script>

<style scoped>

</style>

<style>

.subTitle {
    font-size: 14px;
    font-style: italic;
    margin-top: -10px;
}

.listItem {
    font-size: 15px;
    font-style: italic;
}

.item {
    font-size: 13px;
}

</style>