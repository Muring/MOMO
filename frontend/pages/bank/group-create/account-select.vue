<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import SimpleAccount from "~/components/bank/SimpleAccount.vue";

definePageMeta({
  layout: "action",
});

const accounts = ref([
  { accountName: "저축은행", accountNumber: "123-1234-12345", money: 1000000 },
  { accountName: "효리은행", accountNumber: "123-1234-12346", money: 2000000 },
  { accountName: "성수은행", accountNumber: "123-1234-12347", money: 1000 },
  { accountName: "소이은행", accountNumber: "123-1234-12348", money: 100000 },
  { accountName: "준성은행", accountNumber: "123-1234-12349", money: 10000 },
  {
    accountName: "민우은행",
    accountNumber: "123-1234-12349",
    money: 100000000,
  },
]);

const isSelected = ref(false);
const selectedId = ref();
const isLoading = ref(false); // 로딩 상태 관리

const selectAccount = (index) => {
  selectedId.value = index;
  isSelected.value = true;
  // console.log(selectedId.value + " " + isSelected.value);
};

const router = useRouter();

// 비동기 데이터 로딩을 시뮬레이션하는 함수
const loadData = async () => {
  isLoading.value = true; // 로딩 시작
  try {
    // 데이터 로딩 로직 (여기서는 setTimeout을 사용하여 시뮬레이션)
    await new Promise((resolve) => setTimeout(resolve, 2000)); // 2초 대기
    // 데이터 로딩 완료
    isLoading.value = false; // 로딩 완료
    router.push("/bank/card-select"); // 로딩 완료 후 card-select 페이지로 이동
  } catch (error) {
    console.error("데이터 로딩 중 오류 발생:", error);
    isLoading.value = false; // 에러 시 로딩 상태 해제
  }
};

const goNext = () => {
  loadData(); // goNext가 호출되면 loadData 함수 실행
};
</script>

<template>
  <div v-if="isLoading">
    <Loading />
  </div>
  <div v-else class="account-container">
    <div
      v-for="(account, index) in accounts"
      :key="index"
      @click="selectAccount(index)"
      class="account"
      :class="{ selected: selectedId == index }"
    >
      <SimpleAccount
        :accountName="account.accountName"
        :accountNumber="account.accountNumber"
        :money="account.money"
        :status="false"
      />
    </div>
    <button v-if="!isSelected" class="second-btn">다음</button>
    <button v-else class="prime-btn" @click="goNext()">다음</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.account-container {
  padding: 5%;
  display: grid;
  grid-template-rows: repeat(auto, 1fr);
  gap: 30px;
}
.account {
  border-radius: 20px;
  border-color: none;
}

.prime-btn,
.second-btn {
  width: 100% !important;
  border-radius: 15px !important;
}
</style>