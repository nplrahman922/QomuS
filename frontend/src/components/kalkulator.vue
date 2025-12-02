<script setup>
import { ref } from 'vue'

const calcDisplay = ref('')

// --- HELPER: KONVERSI ANGKA ARAB <-> LATIN ---
const toArabic = (num) => {
  const arabicDigits = ['٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩']
  return num.toString().replace(/\d/g, d => arabicDigits[d])
}

const toLatin = (str) => {
  const arabicDigits = ['٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩']
  let res = str
  arabicDigits.forEach((digit, index) => {
    res = res.replaceAll(digit, index)
  })
  return res
}

// --- LOGIKA TOMBOL ---
const appendCalc = (char) => {
  calcDisplay.value += char
}

const clearCalc = () => {
  calcDisplay.value = ''
}

// --- LOGIKA HITUNG (PANGGIL BACKEND) ---
const calculateResult = async () => {
  try {
    // 1. Ubah ke Latin (misal "١+١" jadi "1+1")
    let expressionLatin = toLatin(calcDisplay.value)

    // 2. Encode agar aman di URL (tanda + tidak hilang)
    let encodedExpression = encodeURIComponent(expressionLatin)

    // 3. Minta Backend Menghitung
    const response = await fetch(`/hitung?ekspresi=${encodedExpression}`)
    const resultLatin = await response.text()

    // 4. Tampilkan Hasil
    if (resultLatin.startsWith("Error")) {
      calcDisplay.value = "Error"
    } else {
      calcDisplay.value = toArabic(resultLatin)
    }

  } catch (e) {
    console.error(e)
    calcDisplay.value = 'Error'
  }
}
</script>

<template>
  <div class="mt-8 bg-gray-900 p-6 rounded-2xl shadow-2xl max-w-sm w-full mx-auto border border-gray-700">
    <div class="bg-gray-100 text-gray-800 text-4xl text-right p-4 rounded-lg mb-4 font-mono h-20 flex items-center justify-end overflow-hidden">
      {{ calcDisplay || '٠' }}
    </div>

    <div class="grid grid-cols-4 gap-3">
      <button @click="clearCalc" class="btn btn-warning">C</button>
      <button @click="appendCalc('/')" class="btn btn-warning">÷</button>
      <button @click="appendCalc('*')" class="btn btn-warning">×</button>
      <button @click="appendCalc('-')" class="btn btn-warning">-</button>

      <button @click="appendCalc('٧')" class="btn btn-num">٧</button>
      <button @click="appendCalc('٨')" class="btn btn-num">٨</button>
      <button @click="appendCalc('٩')" class="btn btn-num">٩</button>
      <button @click="appendCalc('+')" class="btn btn-warning">+</button>

      <button @click="appendCalc('٤')" class="btn btn-num">٤</button>
      <button @click="appendCalc('٥')" class="btn btn-num">٥</button>
      <button @click="appendCalc('٦')" class="btn btn-num">٦</button>
      <div class="hidden"></div> <button @click="appendCalc('١')" class="btn btn-num">١</button>
      <button @click="appendCalc('٢')" class="btn btn-num">٢</button>
      <button @click="appendCalc('٣')" class="btn btn-num">٣</button>

      <button @click="calculateResult" class="row-span-2 bg-emerald-500 hover:bg-emerald-600 text-white rounded-lg text-xl font-bold">=</button>

      <button @click="appendCalc('٠')" class="col-span-2 btn btn-num">٠</button>
      <button @click="appendCalc('.')" class="btn btn-num">.</button>
    </div>
  </div>
</template>

<style scoped>
/* Wajib ada agar Tailwind terbaca di komponen ini */
@reference "../style.css";

.btn {
  @apply h-14 w-full flex items-center justify-center rounded-lg text-xl transition duration-200 active:scale-95 cursor-pointer;
}
.btn-num {
  @apply bg-gray-700 text-white hover:bg-gray-600;
}
.btn-warning {
  @apply bg-amber-500 text-black font-bold hover:bg-amber-600;
}
</style>