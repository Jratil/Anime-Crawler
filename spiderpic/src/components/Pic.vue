<template>
  <div class="pic">
    <!-- waterfall2 瀑布流组件 -->
    <!-- <waterfall
      :data="datas"
      @loadmore="getData"
      :col="col"
    >
      <template>
        <div v-for="link in links" v-bind:key="link.src">
          <img :lazy-src="link.src" />
        </div>
      </template>
    </waterfall>-->

    <!-- vue-waterfall-easy瀑布流加载组件 -->
    <vue-waterfall-easy
      ref="waterfall"
      :imgsArr="links"
      :gap="20"
      :maxCols="col"
      @click="preview"
      @scrollReachBottom="getData"
    >
      <!-- 头部内容 -->
      <div slot="waterfall-head">
        <div class="nav">
          <div class="title">
            <p>Welcome</p>
            <p class="sub-title">(加载可能有点慢，请耐心等待哦~)</p>
          </div>
          <div class="pageTool"></div>
        </div>
      </div>
      <!-- 加载完显示 -->
      <div slot="waterfall-over">被你看光了~</div>
      <!-- <div slot="loading" slot-scope="{isFirstLoad}">
        <div slot="loading" v-if="isFirstLoad">
          <template>
            <div>
                <Loading text="加载中，请耐心等待哦..." :loading="loading"></Loading>
            </div>
          </template>
        </div>
        <div v-else>
          Loading...
        </div>
      </div> -->
    </vue-waterfall-easy>
  </div>
</template>
<script>
import vueWaterfallEasy from "vue-waterfall-easy";

export default {
  name: "Pic",
  data() {
    return {
      links: [],
      datas: [],
      col: 4,
      // 后端待补充
      page: 1,
      group: 1,
      loading: true
    };
  },
  computed: {
    // waterfall2加载组件使用
    // itemWidth() {
    //   return (
    //     (document.documentElement.clientWidth - 15 * (this.col + 1)) / this.col
    //   );
    // },
    // gutterWidth() {
    //   // return (10*0.5*(document.documentElement.clientWidth/375));
    //   return 15;
    // }
  },
  components: {
    vueWaterfallEasy
  },
  methods: {
    getData() {
      if (this.gorup === 5) {
        // this.$refs.waterfall.waterfallOver();
        this.page++;
        this.group = 1;
      }
      this.$http
        .get(
          "http://120.79.172.32:8080/api/gallery/" +
            this.page +
            "/" +
            this.group
        )
        .then(
          response => {
            let pictureList = new Array();
            pictureList = response.body.data.picture_list;
            pictureList.forEach(item => {
              this.datas.push(item.picUrl);
              let obj = new Object();
              obj.src = item.picUrl;
              obj.hrefKey = item.picUrl;
              this.links = this.links.concat(obj);
            });
            this.group++;
            // console.log(this.datas);
            // console.log(response.body.data);
          },
          response => {
            console.warn("出错了");
            alert("出错了");
          }
        )
        .catch(err => {
          console.error(err);
          alert(err);
        });
    },
    preview(event, { index, value }) {
      this.$ImagePreview(this.datas, parseInt(index));
    },
    topLoadStart() {
      this.$LoadingBar.start();
    },
    loadSuccess() {
      this.$LoadingBar.success();
    }
  },
  created() {
    this.getData();
  }
};
</script>
<style>
.nav {
  position: relative;
  top: 0px;
  height: 80px;
  border-bottom: 1px solid #33353373;
}
.title {
  position: absolute;
  height: 100%;
  margin-top: 10px;
  margin-left: 50px;
  font-size: 25px;
}
.sub-title {
  font-size: 15px;
}
.pageTool {
  position: absolute;
  width: 200px;
  height: 100%;
  right: 0px;
  /* border: 1px solid red; */
}
.pic {
  position: relative;
  margin: 0;
  width: 100%;
  height: 100%;
}
</style>