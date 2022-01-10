<template>
    <v-container>
        <table border="1px" class="footerText mt-5 mr-5" style="color: grey;">
            <tr v-if="!member">
                <td>회원님의 정보를 불러오는 중입니다...</td>
            </tr>
            <tr>
                <td>id</td>
                <td>{{ member.id }}</td>
            </tr>
            <tr>
                <td>password</td>
                <td>{{ password }}</td>
            </tr>
            <tr>
                <td>name</td>
                <td>{{ member.name }}</td>
            </tr>
            <tr>
                <td>location</td>
                <td>{{ member.location }}</td>
            </tr>
            <tr>
                <td>identity</td>
                <td>{{ member.memberIdentityList[0].identity }}</td>
            </tr>
            <tr>
                <td>birthDay</td>
                <td>{{ member.birthDay }}</td>
            </tr>
            <tr>
                <td>phoneNo</td>
                <td>{{ member.phoneNo }}</td>
            </tr>
            <tr>
                <td>가입일</td>
                <td>{{ member.regDate }}</td>
            </tr>
            <hr :style="{ width: '100%' }">
            <tr v-if="!taste">
                <td>아직 선호 사항을 정하지 않으셨어요!</td>
            </tr>
        </table>
    </v-container>
</template>

<script>
export default {
    name: 'MyProfile',
    props: {
        member: Object,
        taste: Object
    },
    data() {
        return {
            password: "***********"
        }
    }
}
</script>


<style scoped>

table {
    max-width: 350px;
}

tr td:nth-child(2) {
    text-align: right;
    color: white;
}

</style>