<template>
  <v-dialog
    :model-value="modelValue"
    persistent
    max-width="800px"
    @update:model-value="$emit('update:modelValue', $event)"
  >
    <v-card>
      <v-card-title>
        <span class="headline">{{ dialogTitle }}</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <!-- Campos Principais -->
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.nome"
                label="Nome Completo"
                required
                variant="outlined"
                density="compact"
              />
            </v-col>
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.cpf"
                label="CPF"
                required
                variant="outlined"
                density="compact"
                hint="Apenas números"
              />
            </v-col>
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.email"
                label="Email"
                type="email"
                required
                variant="outlined"
                density="compact"
              />
            </v-col>
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.senha"
                label="Senha"
                :type="showPassword ? 'text' : 'password'"
                :append-inner-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                required
                variant="outlined"
                density="compact"
                :hint="editMode ? 'Deixe em branco para não alterar' : 'Senha de acesso'"
                @click:append-inner="showPassword = !showPassword"
              />
            </v-col>
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.dataNascimento"
                label="Data de Nascimento"
                type="date"
                required
                variant="outlined"
                density="compact"
              />
            </v-col>
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.dataCadastro"
                label="Data de Cadastro"
                type="date"
                readonly
                disabled
                variant="outlined"
                density="compact"
                hint="Definido pelo sistema"
              />
            </v-col>

            <!-- Endereço -->
            <v-col cols="12">
              <v-divider class="my-4"></v-divider>
              <div class="text-h6 mb-2">Endereço</div>
            </v-col>

            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.endereco.telefone"
                label="Telefone"
                variant="outlined"
                density="compact"
                hint="Com DDD, apenas números"
              />
            </v-col>
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.endereco.cep"
                label="CEP"
                required
                variant="outlined"
                density="compact"
                hint="Apenas números"
              />
            </v-col>
            <v-col cols="12" sm="9">
              <v-text-field
                v-model="localUsuario.endereco.rua"
                label="Rua / Logradouro"
                required
                variant="outlined"
                density="compact"
              />
            </v-col>
            <v-col cols="12" sm="3">
              <v-text-field
                v-model="localUsuario.endereco.numero"
                label="Número"
                required
                variant="outlined"
                density="compact"
              />
            </v-col>
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="localUsuario.endereco.cidade"
                label="Cidade"
                required
                variant="outlined"
                density="compact"
              />
            </v-col>

          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="grey darken-1"
          variant="text"
          @click="emitCancel"
        >
          Cancelar
        </v-btn>
        <v-btn
          color="blue darken-1"
          variant="elevated"
          @click="emitSave"
        >
          Salvar
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts" setup>
import {reactive, watch, computed, ref} from 'vue';

// --- Props ---
const props = defineProps<{
  modelValue: boolean;
  usuario: any | null;
  editMode: boolean;
}>();

const emit = defineEmits(["update:modelValue", "save", "cancel"]);

const showPassword = ref(false);

const defaultUser = {
  nome: "",
  cpf: "",
  email: "",
  senha: "",
  dataNascimento: null,
  dataCadastro: new Date().toISOString().split('T')[0],
  endereco: {
    telefone: "",
    rua: "",
    numero: "",
    cidade: "",
    cep: ""
  }
};

const localUsuario = reactive(JSON.parse(JSON.stringify(defaultUser)));

const dialogTitle = computed(() =>
  props.editMode ? "Editar Usuário" : "Adicionar Usuário"
);

watch(() => props.usuario, (newVal) => {
  Object.assign(localUsuario, JSON.parse(JSON.stringify(defaultUser)));

  if (newVal) {
    for (const key in defaultUser) {
      if (key !== 'endereco' && newVal.hasOwnProperty(key)) {
        (localUsuario as any)[key] = newVal[key];
      }
    }

    if (newVal.endereco) {
      for (const subKey in defaultUser.endereco) {
        if (newVal.endereco.hasOwnProperty(subKey)) {
          (localUsuario.endereco as any)[subKey] = newVal.endereco[subKey];
        }
      }
    }

    if (props.editMode) {
      localUsuario.senha = '';
    }
  } else {
    localUsuario.dataCadastro = new Date().toISOString().split('T')[0];
  }


}, {deep: true, immediate: true});

// --- Methods ---
const emitSave = () => {
  const dataToSave = JSON.parse(JSON.stringify(localUsuario));

  if (props.editMode && !dataToSave.senha) {
    delete dataToSave.senha;
  }

  emit("save", dataToSave);
};

const emitCancel = () => {
  emit("cancel");
  emit("update:modelValue", false);
};

</script>

<style scoped>
.v-card-text {
  max-height: 70vh;
  overflow-y: auto;
}
</style>
