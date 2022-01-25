<template>
    <div>
        <v-container >
            <v-simple-table dark dense class="mt-5 description">
                <tr class="top-row">
                    <td>순번</td>
                    <td>id</td>
                    <td>이름</td>
                    <td>지역</td>
                    <td>번호</td>
                    <td>생년월일</td>
                    <td colspan="2">가입일</td>
                </tr>
                <br>
                <tr v-for="(member, i) in members" :key="i" class="member-row top-row">
                    <td>{{ i }}</td>
                    <td>{{ member.id }}</td>
                    <td>{{ member.name }}</td>
                    <td>{{ member.location }}</td>
                    <td>{{ member.phoneNo }}</td>
                    <td>{{ member.birthDay }}</td>
                    <td>{{ member.regDate }}</td>
                    <v-btn class="btn" @click="delMember(member.memberNo)">회원 삭제</v-btn>
                </tr>
            </v-simple-table>
        </v-container>
    </div>
</template>

<script>

export default {
    name: 'MemberList',
    props: {
        members: {
            type: Array,
            required: true
        }
    },
    methods: {
        delMember(memberNo) {
            this.$emit('delMember', memberNo);
        }
    }
}
</script>

<style scoped>

.top-row>td {
    text-align: center;
}

.member-row {
    transition: all 0.5s;
}

.member-row:hover {
    color: grey;
}

</style>