<template>
  <Dialog v-model:visible="open" modal header="Panoramax" :style="{ width: 'min(1100px, 96vw)' }" @show="loadViewer">
    <div class="panoramax-viewer-shell">
      <small v-if="details" class="panoramax-details">{{ details }}</small>
      <ProgressSpinner v-if="loading" />
      <pnx-photo-viewer
        v-if="ready"
        :endpoint="endpoint"
        :sequence="sequenceId || undefined"
        :picture="pictureId || undefined"
        url-parameters="false"
      />
    </div>
  </Dialog>
</template>

<script setup>
import { computed, ref } from 'vue'
import Dialog from 'primevue/dialog'
import ProgressSpinner from 'primevue/progressspinner'

const props = defineProps({
  visible: Boolean,
  endpoint: { type: String, default: '' },
  sequenceId: { type: String, default: null },
  pictureId: { type: String, default: null },
  details: { type: String, default: '' }
})
const emit = defineEmits(['update:visible'])
const loading = ref(false)
const ready = ref(false)
const open = computed({ get: () => props.visible, set: (value) => emit('update:visible', value) })

const loadViewer = async () => {
  if (ready.value || loading.value) return
  loading.value = true
  try {
    await import('@panoramax/web-viewer-photoviewer')
    ready.value = true
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.panoramax-viewer-shell { min-height: min(65vh, 700px); display: grid; place-items: center; }
pnx-photo-viewer { width: 100%; height: min(65vh, 700px); }
</style>
