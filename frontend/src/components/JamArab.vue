<script setup>
import { defineProps, computed } from 'vue'

const props = defineProps(['waktu'])

const toArabic = (str) => {
  if (!str) return ""
  const arabicDigits = ['٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩']
  return str.replace(/\d/g, d => arabicDigits[d])
}

const waktuArab = computed(() => toArabic(props.waktu))
</script>

<template>
  <div class="mt-6 p-8 bg-gray-900 rounded-3xl shadow-xl border-4 border-emerald-500 text-center animate-bounce-slow">
    <p class="text-emerald-400 text-sm font-bold tracking-widest mb-2 uppercase">Waktu Server</p>
    <div class="text-7xl font-mono text-white font-bold">
      {{ waktuArab }}
    </div>
  </div>
</template>

<style scoped>
@reference "../style.css";
.animate-bounce-slow { animation: bounce 2s infinite; }
@keyframes bounce {
  0%, 100% { transform: translateY(-5%); }
  50% { transform: translateY(0); }
}
</style>