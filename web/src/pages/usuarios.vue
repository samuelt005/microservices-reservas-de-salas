<template>
  <v-container
    fluid
    class="page-container rounded elevation-4"
  >
    <DefaultTable
      search-placeholder="Buscar Usuário"
      add-button-text="Adicionar Usuário"
      :table-items="usuarios"
      :headers="headers"
      @add="openDialog"
      @edit="editUsuario"
      @toggle="toggleActive"
    />

    <CreateOrEditUsuarios
      v-model="dialog"
      :usuario="usuario"
      :edit-mode="editMode"
      @save="onSaveUsuario"
      @cancel="confirmClose = true"
    />

    <ConfirmDialog
      v-model="confirmClose"
      title="Confirmar saída"
      message="Tem certeza que deseja sair sem salvar?"
      @confirm="dialog = false"
    />
  </v-container>
</template>

<script lang="ts">
import {onMounted, ref} from "vue";
import ConfirmDialog from "@/components/dialogs/ConfirmDialog.vue";
import DefaultTable from "@/components/DefaultTable.vue";
import CreateOrEditUsuarios from "@/components/dialogs/CreateOrEditUsuarios.vue";
import {
  createUsuario,
  getUsuario,
  getUsuarios, updateUsuario
} from '@/services/usuariosService.js';

export default {
  name: "ProductsPage",
  components: {CreateOrEditUsuarios, DefaultTable, ConfirmDialog},
  setup() {
    const search = ref("");
    const dialog = ref(false);
    const confirmClose = ref(false);
    const editMode = ref(false);
    const usuario = ref({
      nome: "string",
      cpf: "string",
      email: "string",
      senha: "string",
      dataNascimento: "2025-05-01",
      dataCadastro: "2025-05-01",
      endereco: {
        telefone: "string",
        rua: "string",
        numero: "string",
        cidade: "string",
        cep: "string"
      }
    });

    const usuarios = ref([]);

    const headers = [
      {title: "Nome", key: "name"},
      {title: "CPF", key: "cpf"},
      {title: "E-mail", key: "email"},
      {title: "Data de Nascimento", key: "dataNascimento"},
      {title: "Data de Cadastro", key: "dataCadastro"},
      {title: "Endereço", key: "endereco"},
      {title: "Ações", key: "actions", sortable: false}
    ];

    onMounted(() => {
      getData();
    });

    const getData = async () => {
      try {
        const res = await getUsuarios();
        usuarios.value.push(...res);
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
      }
    };

    const openDialog = () => {
      usuario.value = {id: null, name: "", email: "", birthDate: 0};
      editMode.value = false;
      dialog.value = true;
    };

    const editUsuario = (item) => {
      usuario.value = {...item};
      editMode.value = true;
      dialog.value = true;
    };

    const onSaveUsuario = async (data) => {
      if (editMode.value) {
        const statusCode = await updateUsuario(data.id, data);

        if (statusCode === 200) {
          const updatedFacility = await getUsuario(data.id);
          const index = usuarios.value.findIndex(f => f.id === data.id);
          usuarios.value[index] = updatedFacility;
        }
      } else {
        const createdFacility = await createUsuario(data);

        if (createdFacility) {
          usuarios.value.push(createdFacility);
        }
      }
      dialog.value = false;

      await getData();
    };

    const toggleActive = (item) => {
      const productToUpdate = usuarios.value.find(f => f.id === item.id);
      if (productToUpdate) {
        productToUpdate.active = !productToUpdate.active;
      }
    };

    return {
      search,
      dialog,
      confirmClose,
      usuario,
      usuarios,
      headers,
      openDialog,
      editUsuario,
      onSaveUsuario,
      toggleActive,
      editMode
    };
  }
};
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  background: rgba(255, 255, 255, 0.9);
  height: 100%;
}
</style>
