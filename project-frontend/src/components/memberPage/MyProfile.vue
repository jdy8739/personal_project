<template>
    <v-container>
        <table border="1px">
            <tr class="footerText" style="color: grey;">
                <td width="12%" class="left">id</td>
                <td width="10%">password</td>
                <td width="8%">name</td>
                <td width="25%">location</td>
                <td width="8%">identity</td>
                <td width="8%">birthDay</td>
                <td width="8%">phoneNo</td>
                <td width="12%" style="text-align: right;">regDate</td>
            </tr>
            <tr v-if="!member">
                <td colspan="8">회원 정보를 불러오지 못했습니다!</td>
            </tr>
            <tr v-else-if="member" class="item" style="color: white;">
                <td>{{ member.id }}</td>
                <td>{{ password }}</td>
                <td>{{ member.name }}</td>
                <td>{{ member.location }}</td>
                <td>{{ member.memberIdentityList[0].identity }}</td>
                <td>{{ member.birthDay }}</td>
                <td>{{ member.phoneNo }}</td>
                <td class="right">{{ member.regDate }}</td>
            </tr>
            <br/>
            <br/>
            <br/>
            <tr class="footerText" style="color: grey;">
                <td>선택 취향</td>
                <td colspan="2">선택한 장르</td>
                <td colspan="2">곡 느낌 선호사항</td>
                <td colspan="2">공연장 선호사항</td>
                <td colspan="2" class="right">선택한 아티스트</td>
            </tr>
            <tr v-if="taste" class="item" style="color: white;">
                <td></td>
                <td colspan="2">{{ taste.chosenGenres }}</td>
                <td colspan="2">{{ taste.speedTaste }}</td>
                <td colspan="2">{{ taste.atmosphereTaste }}</td>
                <td colspan="2" class="right">{{ taste.chosenArtists }}</td>
            </tr>
            <tr v-else-if="!taste" class="item">
                <td colspan="8">아직 선호사항을 정하지 않으셨어요!</td>             
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