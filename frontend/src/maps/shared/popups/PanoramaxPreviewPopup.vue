<template>
  <MapPopupCard title="Panoramax photo" icon-class="pi pi-images" variant="wide">
    <img class="panoramax-preview-image" :src="thumbnailUrl" alt="Panoramax coverage preview">
  </MapPopupCard>
</template>

<script setup>
import MapPopupCard from './MapPopupCard.vue'

defineProps({
  thumbnailUrl: { type: String, required: true }
})
</script>

<style scoped>
.panoramax-preview-image {
  display: block;
  width: 100%;
  max-height: 15rem;
  border-radius: 0.5rem;
  object-fit: cover;
}
</style>
