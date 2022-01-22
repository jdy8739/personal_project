<template>
    <div>
        <label for="pic">
            <p class="mt-10">공연 홍보 사진을 두 장 선택해주세요.</p>
            &emsp;
            <input id="files" type="file" ref="files" multiple maxlength="2" @change="imgPreview($event)"/>
        </label>
        <div id="img-section"></div>
        <v-btn class="btn" dark @click="goNext">다음으로</v-btn>
        <v-btn class="btn" dark @click="cancel">취소</v-btn>
    </div>
</template>

<script>
export default {
    name: 'ConcertPicRegiForm',
    data() {
        return {
            files: ''
        }
    },
    methods: {
        imgPreview(e) {
            const files = e.target.files;
            const imgSection = document.querySelector('#img-section');

            let cnt = 0;
            for(let file of files) {
                if(!file.type.includes('image')) continue;
                
                const img = document.createElement('img');
                img.src = URL.createObjectURL(file);
                img.style.width = '310px';
                img.style.height = '390px';
                img.style.margin = '12px';

                img.id = Date.now() + cnt;
                img.className = 'con-pic';

                img.addEventListener('click', (e) => {
                    if(e.currentTarget.id === e.target.id) {
                        const id = `${ e.target.id }`;
                        const targetImg = document.getElementById(id);
                        targetImg.remove();
                    }
                });

                imgSection.append(img);
                cnt ++;
            }
        },
        cancel() {
            history.back();
        },
        goNext() {
            this.files = this.$refs.files.files;

            if(!this.files.length == 2) {
                this.$emit('toNextStep', this.files);
            } else {
                alert('사진의 개수는 두 장을 올려주세요!');
            }
        }
    }
}
</script>

<style>
    .con-pic {
        cursor: pointer;
        width: 310px;
        height: 390px;
        margin: 12px;
        transition: all 1s;
    }
    .con-pic:hover {
        opacity: 0.4;
    }
</style>

<style scoped>
    label {
        cursor: pointer;
    }
    label>p {
        display: inline-block;
    }
</style>