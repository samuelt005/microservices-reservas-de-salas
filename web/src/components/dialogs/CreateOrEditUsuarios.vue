<template>
  <v-dialog
    :model-value="modelValue"
    persistent
    max-width="800px"
    @update:model-value="$emit('update:modelValue', $event)"
  >
    <v-card>
      <v-card-title>
        {{ editMode ? "Editar Produto" : "Adicionar Produto" }}
      </v-card-title>
      <v-card-text>
        <v-text-field
          v-model="localUsuario.nome"
          label="Nome"
          required
        />
        <v-text-field
          v-model="localUsuario.cpf"
          label="Descrição"
        />
        <v-text-field
          v-model="localUsuario.data_nascimento"
          label="Valor"
          required
        />
        <v-text-field
          v-model="localUsuario.data_cadastro"
          label="Valor"
          required
        />
        <v-text-field
          v-model="localUsuario.email"
          label="Valor"
          type="date"
          required
        />
        <v-text-field
          v-model="localUsuario.cep"
          label="Valor"
          type="date"
          required
        />
        <v-text-field
          v-model="localUsuario.cidade"
          label="Valor"
          type="date"
          required
        />
        <v-text-field
          v-model="localUsuario.numero"
          label="Valor"
          type="date"
          required
        />
        <v-text-field
          v-model="localUsuario.rua"
          label="Valor"
          type="date"
          required
        />
        <v-text-field
          v-model="localUsuario.telefone"
          label="Valor"
          type="date"
          required
        />
        <v-text-field
          v-model="localUsuario.senha"
          label="Valor"
          type="date"
          required
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
  usuario: any;
  editMode: boolean;
}>();

const emit = defineEmits(["update:modelValue", "save", "cancel"]);

const localUsuario = reactive({...props.usuario});

watch(() => props.usuario, (newVal) => {
  Object.assign(localUsuario, newVal);
}, {deep: true});

const emitSave = () => {
  emit("save", {...localUsuario});
};
</script>
