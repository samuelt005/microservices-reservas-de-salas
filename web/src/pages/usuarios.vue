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
      @save="onSaveProduct"
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
import {ref} from "vue";
import ConfirmDialog from "@/components/dialogs/ConfirmDialog.vue";
import DefaultTable from "@/components/DefaultTable.vue";
import CreateOrEditUsuarios from "@/components/dialogs/CreateOrEditUsuarios.vue";

export default {
  name: "ProductsPage",
  components: {CreateOrEditUsuarios, DefaultTable, ConfirmDialog},
  setup() {
    const search = ref("");
    const dialog = ref(false);
    const confirmClose = ref(false);
    const editMode = ref(false);
    const usuario = ref({id: null, nome: "", cpf: "", data_nascimento: "", data_cadastro: "", email: "", cep: "", cidade: "", numero: "", rua: "", telefone: "", senha: ""});

    const usuarios = ref([]);

    const headers = [
      {title: "Nome", key: "name"},
      {title: "CPF", key: "cpf"},
      {title: "E-mail", key: "email"},
      {title: "Data de Nascimento", key: "birthDate"},
      {title: "Ativo", key: "inactive"},
      {title: "Ações", key: "actions", sortable: false}
    ];

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

    const onSaveProduct = (data) => {
      if (editMode.value) {
        const index = usuarios.value.findIndex(f => f.id === data.id);
        usuarios.value[index] = {...data};
      } else {
        data.id = usuarios.value.length + 1;
        usuarios.value.push({...data});
      }
      dialog.value = false;
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
      onSaveProduct,
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
