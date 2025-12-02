<script setup>
  import { ref } from 'vue'
  import CalculatorArab from './components/kalkulator.vue'
  import JamArab from './components/JamArab.vue'     // Import Jam
  import DaduArab from './components/DaduArab.vue'   // Import Dadu

  const inputKata = ref('')
  const hasilTerjemahan = ref('')
  const bgClass = ref('bg-gray-50 text-gray-900')

  // --- STATE UNTUK GIMMICK ---
  const activeGimmick = ref(null) // null, 'CALC', 'CLOCK', 'DICE'
  const gimmickData = ref(null)   // Data tambahan (misal jam string atau angka dadu)

  const executeCommand = (commandString) => {
  if (!commandString) return;

  // Reset visual
  document.body.style.filter = 'none';
  document.body.style.transform = 'none';
  document.body.style.transition = 'filter 0.5s, transform 1s';

  // 1. JS Raw
  if (commandString.startsWith('JS:')) {
  try { eval(commandString.substring(3)); } catch (e) {}
  activeGimmick.value = null;
  return;
}

  const [type, value] = commandString.split(':');

  if (type !== 'BG') bgClass.value = 'bg-gray-50 text-gray-900';

  // --- LOGIKA UTAMA SWITCH ---
  if (type === 'CALC') {
  activeGimmick.value = 'CALC';
}
  else if (type === 'CLOCK') {
  activeGimmick.value = 'CLOCK';
  gimmickData.value = value;
}
  else if (type === 'DICE') {
  activeGimmick.value = 'DICE';
  gimmickData.value = value; // Simpan angka dadu (misal "5")
}
  else if (type === 'BG') {
  bgClass.value = value;
  activeGimmick.value = null;
}
  else if (type === 'ALERT') {
  alert(value);
  activeGimmick.value = null;
}
  else if (type === 'EFFECT' && value === 'BLUR') {
  document.body.style.filter = 'blur(4px)';
  activeGimmick.value = null;
}
  else {
  activeGimmick.value = null;
}
}

  const cariTerjemahan = async () => {
  try {
  const respon = await fetch(`/kamus?kata=${inputKata.value}`)
  const data = await respon.json()

  hasilTerjemahan.value = data.arti

  if (data.gimmick) {
  executeCommand(data.gimmick)
} else {
  activeGimmick.value = null
  document.body.style.transform = 'none';
  document.body.style.filter = 'none';
}
} catch (error) {
  hasilTerjemahan.value = "Gagal memuat."
}
}
</script>

<template>
  <div :class="bgClass" class="min-h-screen flex flex-col items-center py-12 px-4 sm:px-6 lg:px-8 font-sans transition-colors duration-500">

    <div class="text-center mb-10">
      <h1 class="text-4xl font-extrabold text-emerald-600 tracking-tight">QomuS</h1>
      <p class="mt-2 text-lg text-gray-500">Kamus Interaktif & Fitur Rahasia</p>
    </div>

    <div class="w-full max-w-md flex gap-2">
      <input v-model="inputKata" placeholder="Ketik: 'jam', 'dadu', 'kalkulator'" @keyup.enter="cariTerjemahan"
             class="flex-1 shadow-sm focus:ring-emerald-500 focus:border-emerald-500 block w-full sm:text-sm border-gray-300 rounded-md p-3 border text-gray-900"/>
      <button @click="cariTerjemahan" class="bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-2 px-4 rounded shadow transition cursor-pointer">Cari</button>
    </div>

    <div v-if="hasilTerjemahan" class="mt-8 p-6 bg-white/80 backdrop-blur-sm border border-green-200 rounded-xl shadow-sm text-center w-full max-w-md transition-all">
      <h3 class="text-sm uppercase tracking-wide text-green-800 font-semibold">Hasil Bahasa Arab:</h3>
      <p class="mt-2 text-4xl text-emerald-700 font-bold arabic-font">{{ hasilTerjemahan }}</p>
    </div>

    <div class="w-full flex justify-center mt-6 fade-in">

      <CalculatorArab v-if="activeGimmick === 'CALC'" />

      <JamArab v-if="activeGimmick === 'CLOCK'" :waktu="gimmickData" />

      <DaduArab v-if="activeGimmick === 'DICE'" :angka="gimmickData" />

    </div>

  </div>
</template>

<style>
.arabic-font { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
.fade-in { animation: fadeIn 0.5s ease-in; }
@keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }
</style>