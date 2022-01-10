<template>
    <v-container>
        <form @submit.prevent="onSubmit">
            <v-text-field label="ID(e-mail)" type="email" required v-model="user.id" dark/>
            <v-text-field label="비밀번호(10자리 이상)" type="password" required v-model="user.password" class="cutTopMargin" dark/>
            <v-text-field label="비밀번호 재확인" type="password" required v-model="user.passwordCheck" class="cutTopMargin" dark/>
            <v-text-field label="이름" type="text" required v-model="user.name" class="cutTopMargin" dark/>
            <v-select v-model="user.location" :items="locations" label="지역 선택" multiple class="cutTopMargin" dark/>
            <input type="date" name="date" required v-model="user.birthDay">

            <v-radio-group v-model="user.identity" row dark>
                <v-radio label="개인" value="individual" color="teal"/>
                <v-radio label="아티스트" value="artist" color="red"/>
                <!-- <v-radio label="관리자" value="admin"></v-radio> -->
            </v-radio-group>
            
            <v-text-field label="휴대전화" type="text" required v-model="user.phoneNo" dark/>
            <br/>
            <div>
                <v-btn type="submit" text class="ma-2" color="error lighten-4">
                    제출
                </v-btn>
                &emsp;
                <v-btn text class="ma-2" color="error lighten-2" @click="goBack">
                    뒤로가기
                </v-btn>
            </div>
        </form>
    </v-container>
</template>

<script>
export default {
    name: 'Signup',
    data() {
        return {
            user: {
                id: '',
                password: '',
                passwordCheck: '',
                name: '',
                location: '',
                birthDay: '',
                identity: '',
                phoneNo: '',
            },
            locations: [
                { text: '서울 - 강남', value: '서울 - 강남'},
                { text: '서울 - 홍대', value: '서울 - 홍대'},
                { text: '서울 - 그 외', value: '서울 - 그 외'},
                { text: '부산', value: '부산'},
                { text: '인천', value: '인천'},
                { text: '대구', value: '대구'},
                { text: '전주', value: '전주'}
            ]
        }
    },
    methods: {
        onSubmit() {
            if(this.user.identity == '') {
                alert('개인, 아티스트 구분을 선택해주세요!');
                return;
            } else if(this.user.location == '') {
                alert('지역을 선택해주세요!');
                return;
            }  

            if(!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{11,}$/.test(this.user.password)) {
                alert("최소 11 자, 최소 하나의 문자 및 하나의 숫자를 입력해 주세요.");
            } else if(this.user.password != this.user.passwordCheck) {
                alert('비밀번호와 비밀번호 재확인이 일치하지 않습니다!');
            } else if(!/^[가-힣|a-z|A-Z|/\s/g]+$/.test(this.user.name)) {
                alert('이름에 올바른 문자를 입력해주세요.');
            } else if(!/^[0-9]{3}[0-9]{3,4}[0-9]{4}/.test(this.user.phoneNo)) {
                alert('올바른 휴대전화 번호 형식이 아닙니다!');
            } else {
                this.$emit('submit', this.user);
            }
        },
        goBack() {
            this.$router.push({
                name: 'MainPage'
            });
        }
    }
}
</script>