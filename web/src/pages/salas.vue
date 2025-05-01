<template>
  <v-container
    fluid
    class="page-container rounded elevation-4"
  >
    <DefaultTable
      search-placeholder="Buscar Sala"
      add-button-text="Adicionar Sala"
      :table-items="salas"
      :headers="headers"
      @add="openDialog"
      @edit="editSala"
      @toggle="toggleActive"
    />

    <CreateOrEditSalas
      v-model="dialog"
      :sala="sala"
      :edit-mode="editMode"
      @save="onSaveSala"
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
import CreateOrEditSalas from "@/components/dialogs/CreateOrEditSalas.vue";
import DefaultTable from "@/components/DefaultTable.vue";

export default {
  name: "SalasPage",
  components: {DefaultTable, ConfirmDialog, CreateOrEditSalas},
  setup() {
    const search = ref("");
    const dialog = ref(false);
    const confirmClose = ref(false);
    const editMode = ref(false);
    const sala = ref({id: null, nome: "", capacidade: ""});

    const salas = ref([
      {
        id: 1,
        nome: "Sala 1",
        capacidade: 10
      }
    ]);

    const headers = [
      {title: "Nome", key: "nome"},
      {title: "Capacidade", key: "capacidade"},
      {title: "Ações", key: "actions", sortable: false}
    ];

    const openDialog = () => {
      sala.value = {id: null, nome: "", capacidade: "", value: 0};
      editMode.value = false;
      dialog.value = true;
    };

    const editSala = (item) => {
      sala.value = {...item};
      editMode.value = true;
      dialog.value = true;
    };

    const onSaveSala = (data) => {
      if (editMode.value) {
        const index = salas.value.findIndex(f => f.id === data.id);
        salas.value[index] = {...data};
      } else {
        data.id = salas.value.length + 1;
        salas.value.push({...data});
      }
      dialog.value = false;
    };

    const toggleActive = (item) => {
      const salaToUpdate = salas.value.find(f => f.id === item.id);
      if (salaToUpdate) {
        salaToUpdate.active = !salaToUpdate.active;
      }
    };

    return {
      search,
      dialog,
      confirmClose,
      sala,
      salas,
      headers,
      openDialog,
      editSala,
      onSaveSala,
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
