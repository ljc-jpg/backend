<template>
    <div class="content">
        {{content}}
    </div>
</template>

<script>
export default {
    data() {
        return {
            content:"liveWeb"
        }
    },
}
</script>

<style scoped>
.content{
    height: 100%;
    width: 100%;
    background: red;
}
</style>

