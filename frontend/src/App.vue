<script setup>
import { ref } from 'vue'
import CalculatorArab from './components/kalkulator.vue'

const inputKata = ref('')
const hasilTerjemahan = ref('')
const showCalculator = ref(false)

const cariTerjemahan = async () => {
  try {
    const respon = await fetch(`/kamus?kata=${inputKata.value}`)
    const data = await respon.text()


    if (data.includes('#GIMMICK_CALC')) {
      const parts = data.split('#')
      hasilTerjemahan.value = parts[0]
      showCalculator.value = true
    } else {
      hasilTerjemahan.value = data
      showCalculator.value = false
    }

  } catch (error) {
    console.error(error)
    hasilTerjemahan.value = "Gagal memuat."
  }
}
</script>

<template>
  <div class="min-h-screen bg-gray-50 flex flex-col items-center py-12 px-4 sm:px-6 lg:px-8 font-sans">

    <div class="text-center mb-10">
      <h1 class="text-4xl font-extrabold text-emerald-600 tracking-tight">QomuS</h1>
      <p class="mt-2 text-lg text-gray-500">Kamus Interaktif & Fitur Rahasia</p>
    </div>

    <div class="w-full max-w-md flex gap-2">
      <input
          v-model="inputKata"
          placeholder="Ketik: 'kalkulator' atau 'sekolah'"
          @keyup.enter="cariTerjemahan"
          class="flex-1 shadow-sm focus:ring-emerald-500 focus:border-emerald-500 block w-full sm:text-sm border-gray-300 rounded-md p-3 border"
      />
      <button
          @click="cariTerjemahan"
          class="bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-2 px-4 rounded shadow transition"
      >
        Cari
      </button>
    </div>

    <div v-if="hasilTerjemahan" class="mt-8 p-6 bg-green-50 border border-green-200 rounded-xl shadow-sm text-center w-full max-w-md transition-all">
      <h3 class="text-sm uppercase tracking-wide text-green-800 font-semibold">Hasil Bahasa Arab:</h3>
      <p class="mt-2 text-4xl text-emerald-700 font-bold arabic-font">{{ hasilTerjemahan }}</p>
    </div>

    <div v-if="showCalculator" class="w-full flex justify-center mt-6 fade-in">
      <CalculatorArab />
    </div>

  </div>
</template>

<style>
.arabic-font {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
/* Animasi kecil agar munculnya halus */
.fade-in {
  animation: fadeIn 0.5s ease-in;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>