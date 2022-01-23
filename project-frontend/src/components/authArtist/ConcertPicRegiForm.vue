<template>
    <div>
        <label for="pic">
            <p class="mt-10">공연 홍보 사진을 두 장 선택해주세요.</p>
            &emsp;
            <input id="files" type="file" ref="files" multiple maxlength="2" @change="imgPreview($event)" class="my-img"/>
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
            files: []
        }
    },
    methods: {
        imgPreview(e) {
            this.files = [...e.target.files];
            const imgSection = document.querySelector('#img-section');
            const imgInput = document.getElementsByClassName('my-img')[0];

            if(this.files.length > 2) {
                alert('사진은 두 장만 선택해주세요!');
                return;
            } else if(this.files.length == 2) {
                imgInput.disabled = true;
            }

            let cnt = 0;
            for(let file of this.files) {
                if(!file.type.includes('image')) continue;
                
                const img = document.createElement('img');
                img.src = URL.createObjectURL(file);
                
                const id = Date.now() + cnt;
                img.id = id;
                file.id = id;

                img.className = 'con-pic';

                img.addEventListener('click', this.delPic);

                imgSection.append(img);
                cnt ++;
            }
            console.log(imgInput.value)

        },
        cancel() {
            history.back();
        },
        goNext() {
            if(this.files.length === 2) {
                this.$emit('toNextStep', this.files);
            } else {
                alert('사진의 개수는 두 장을 올려주세요!');
            }
        },
        delPic(e) {
            if(e.currentTarget.id === e.target.id) {
                const id = `${ e.target.id }`;
                const targetImg = document.getElementById(id);
                targetImg.remove();

                const imgInput = document.getElementsByClassName('my-img')[0];
                imgInput.disabled = false;

                if(this.files.length == 2) {
                    const tgIdx = this.files.findIndex(file => file.id == id);
                    this.files = [...this.files].splice(tgIdx, 1);
                } else {
                    this.files = '';
                    imgInput.value = '';
                }
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