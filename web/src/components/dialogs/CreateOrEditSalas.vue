<template>
  <v-dialog
    :model-value="modelValue"
    persistent
    max-width="800px"
    @update:model-value="$emit('update:modelValue', $event)"
  >
    <v-card>
      <v-card-title>
        {{ editMode ? "Editar Sala" : "Adicionar Sala" }}
      </v-card-title>
      <v-card-text>
        <v-text-field
          v-model="localSala.nome"
          label="Nome"
          required
        />
        <v-text-field
          v-model="localSala.capacidade"
          label="Capacidade"
        />
      </v-card-text>
      <v-card-actions>
        <v-btn
          color="gray"
          @click="$emit('cancel')"
        >
          Cancelar
        </v-btn>
        <v-btn
          color="primary"
          @click="emitSave"
        >
          Salvar
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts" setup>
import {reactive, watch} from "vue";

const props = defineProps<{
  modelValue: boolean;
  sala: any;
  editMode: boolean;
}>();

const emit = defineEmits(["update:modelValue", "save", "cancel"]);

const localSala = reactive({...props.sala});

watch(() => props.sala, (newVal) => {
  Object.assign(localSala, newVal);
}, {deep: true});

const emitSave = () => {
  emit("save", {...localSala});
};
</script>
