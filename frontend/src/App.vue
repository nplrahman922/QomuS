<script setup>
import { ref } from 'vue'
import CalculatorArab from './components/kalkulator.vue'

const inputKata = ref('')
const hasilTerjemahan = ref(null)
const showCalculator = ref(false)

const cariTerjemahan = async () => {
  try {
    const respon = await fetch(`/kamus?kata=${inputKata.value}`)
    const rawText = await respon.text()

    // Coba parse JSON
    try {
      const data = JSON.parse(rawText)
      hasilTerjemahan.value = data

      // Tampilkan kalkulator jika gimmick cocok
      showCalculator.value = data.gimmick === "GIMMICK_CALC"

      return
    } catch (e) {
      // Jika response bukan JSON (error message sederhana)
      hasilTerjemahan.value = { arti: rawText }
      showCalculator.value = false
    }

  } catch (error) {
    hasilTerjemahan.value = {
      arti: "Terjadi kesalahan menghubungi server."
    }
    showCalculator.value = false
  }
}
</script>

<template>
  <div class="min-h-screen bg-gray-50 flex flex-col items-center py-12 px-4 sm:px-6 lg:px-8 font-sans">

    <!-- Judul -->
    <div class="text-center mb-10">
      <h1 class="text-4xl font-extrabold text-emerald-600 tracking-tight">QomuS</h1>
      <p class="mt-2 text-lg text-gray-500">Kamus Interaktif & Fitur Rahasia</p>
    </div>

    <!-- Input -->
    <div class="w-full max-w-md flex gap-2">
      <input
          v-model="inputKata"
          placeholder="Ketik: 'belajar', 'kalkulator', dll."
          @keyup.enter="cariTerjemahan"
          class="flex-1 shadow-sm focus:ring-emerald-500 focus:border-emerald-500 block w-full sm:text-sm border-gray-300 rounded-md p-3 border"
      />
      <button
          @click="cariTerjemahan"
          class="bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-2 px-4 rounded shadow"
      >
        Cari
      </button>
    </div>

    <!-- Hasil -->
    <div
      v-if="hasilTerjemahan"
      class="mt-8 p-6 bg-green-50 border border-green-200 rounded-xl shadow-sm w-full max-w-md"
    >
      <h3 class="text-sm uppercase tracking-wide text-green-800 font-semibold text-center">
        Hasil Bahasa Arab:
      </h3>

      <p class="mt-3 text-4xl text-emerald-700 font-bold arabic-font text-center">
        {{ hasilTerjemahan.arti }}
      </p>

      <p
        v-if="hasilTerjemahan.motivasi"
        class="mt-4 text-xl text-center text-orange-600 font-semibold"
      >
        {{ hasilTerjemahan.motivasi }}
      </p>

      <div v-if="hasilTerjemahan.tips" class="mt-4">
        <h4 class="text-lg font-bold text-green-800 mb-2">Tips Belajar:</h4>
        <ul class="list-disc pl-6 text-green-700 space-y-1 text-left">
          <li v-for="(tip, i) in hasilTerjemahan.tips" :key="i">
            {{ tip }}
          </li>
        </ul>
      </div>
    </div>

    <!-- Kalkulator -->
    <div v-if="showCalculator" class="w-full flex justify-center mt-6 fade-in">
      <CalculatorArab />
    </div>

  </div>
</template>

<style>
.arabic-font {
  font-family: "Arial", "Scheherazade", "Amiri", serif;
}
.fade-in {
  animation: fadeIn 0.5s ease-in;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
