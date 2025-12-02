<script setup>
import { defineProps, computed } from 'vue'

const props = defineProps(['angka'])

const angkaArab = computed(() => {
  const map = {1:'١', 2:'٢', 3:'٣', 4:'٤', 5:'٥', 6:'٦'}
  return map[props.angka] || props.angka
})
</script>

<template>
  <div class="mt-8 flex justify-center perspective-1000">
    <div class="w-32 h-32 bg-white rounded-2xl border-4 border-gray-800 flex items-center justify-center shadow-2xl rotate-animation">
      <span class="text-8xl text-gray-900 font-bold">{{ angkaArab }}</span>
    </div>
  </div>
</template>

<style scoped>
@reference "../style.css";
.perspective-1000 { perspective: 1000px; }
.rotate-animation { animation: roll 1s ease-out; }
@keyframes roll {
  0% { transform: rotateX(0deg) rotateY(0deg); }
  100% { transform: rotateX(720deg) rotateY(360deg); }
}
</style>