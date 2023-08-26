<template>
    <div>
        <slot />
    <span>允许授权{{scopename}}</span>
    <ul>
        <li v-for="(t, i) in list" :key="i" :style="liStyle" @click="handClick(i)">
            <!--名称-->
            <span></span>
            <!--权限-->
            <span></span>
        </li>
    </ul>
    </div>
</template>

<script>
export default {
  props: {
    // 展示的数据，包含img路径和图标下部分的名称
    list: {
      type: Array
    },
    // 设置 img 的高度
    imgHeight: {
      type: String,
      default: ''
    },
    // 设置 img 的宽度
    imgWidth: {
      type: String,
      default: '27px'
    },
    // 如果需要折行显示，那么每一行显示多少个
    wrap: {
      type: [Number, String],
      default: 0
    },
    scopename: {
      type: String,
      default: ''
    }
  },
  setup (props, { emit }) {
    // 用户点击回调，将点击的item下标返回
    const handClick = i => {
      console.log(props.wrap)
      emit('change', i)
    }
    // 设置li样式，看 item 有多少项来分配占比，如5项那每个占 20%
    // 如果wrap存在说明需要折行显示，那么根据用户想要一行显示多少个来配置占比
    const width = props.wrap ? Math.floor(100 / props.wrap) : Math.floor(100 / props.list.length)
    const liStyle = {
      width: `${width}%`
    }
    // 设置img样式，可以单传宽度，那么高度与宽度相同。若都不传则默认宽高为27px
    const imgStyle = {
      width: props.imgWidth,
      height: props.imgHeight || props.imgWidth
    }
    return {
      handClick, imgStyle, liStyle
    }
  }
}
</script>

<style scoped>
ul {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    border-radius: 10px;
    padding: 10px 0;
    background-color: #fff;
}

li {
    display: flex;
    flex-direction: column;
    align-items: center;
}

li>span {
    font-size: 14px;
    color: #666;
    padding: 5px 0;
}
</style>
