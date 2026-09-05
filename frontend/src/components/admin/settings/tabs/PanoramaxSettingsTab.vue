<template>
  <div>
    <SettingSection title="Panoramax">
      <SettingItem v-for="setting in settings" :key="setting.key" :setting="setting" @reset="resetSetting(setting)">
        <template #control="{ setting }">
          <InputSwitch v-if="setting.valueType === 'BOOLEAN'" v-model="setting.currentValue" @change="updateSetting(setting)" />
          <InputText v-else v-model="setting.currentValue" class="endpoint-input" @change="updateSetting(setting)" />
        </template>
      </SettingItem>
      <div class="section-actions">
        <Button label="Test endpoint" icon="pi pi-check" :loading="testing" @click="testEndpoint" />
      </div>
      <Message v-if="testMessage" :severity="testSuccess ? 'success' : 'error'">{{ testMessage }}</Message>
    </SettingSection>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import Button from 'primevue/button'
import InputSwitch from 'primevue/inputswitch'
import InputText from 'primevue/inputtext'
import Message from 'primevue/message'
import SettingSection from '../SettingSection.vue'
import SettingItem from '../SettingItem.vue'
import { useAdminSettings } from '@/composables/useAdminSettings'
import apiService from '@/utils/apiService'

const { loadSettings, updateSetting, resetSetting } = useAdminSettings()
const settings = ref([])
const testing = ref(false)
const testSuccess = ref(false)
const testMessage = ref('')
onMounted(async () => { settings.value = await loadSettings('panoramax') })
const testEndpoint = async () => {
  testing.value = true
  try {
    const response = await apiService.post('/admin/settings/panoramax/test')
    testSuccess.value = response?.success ?? response?.data?.success ?? false
    testMessage.value = response?.message || response?.data?.message || 'Endpoint test completed'
  } catch (error) {
    testSuccess.value = false
    testMessage.value = error?.response?.data?.message || 'Endpoint test failed'
  } finally { testing.value = false }
}
</script>

<style scoped>
.endpoint-input { width: min(56vw, 720px); min-width: 420px; }
.section-actions { margin: 1rem; }
</style>
