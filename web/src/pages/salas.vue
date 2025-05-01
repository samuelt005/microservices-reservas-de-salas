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
import {onMounted, ref} from "vue";
import ConfirmDialog from "@/components/dialogs/ConfirmDialog.vue";
import CreateOrEditSalas from "@/components/dialogs/CreateOrEditSalas.vue";
import DefaultTable from "@/components/DefaultTable.vue";
import {
  createSala, getSala,
  getSalas, updateSala
} from '@/services/salasService.js';

export default {
  name: "SalasPage",
  components: {DefaultTable, ConfirmDialog, CreateOrEditSalas},
  setup() {
    const search = ref("");
    const dialog = ref(false);
    const confirmClose = ref(false);
    const editMode = ref(false);
    const sala = ref({id: null, nome: "", capacidade: ""});

    const salas = ref([]);

    const headers = [
      {title: "Nome", key: "nome"},
      {title: "Capacidade", key: "capacidade"},
      {title: "Ações", key: "actions", sortable: false}
    ];

    onMounted(() => {
      getData();
    });

    const getData = async () => {
      try {
        const res = await getSalas();
        salas.value.push(...res);
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
      }
    };

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

    const onSaveSala = async (data) => {
      if (editMode.value) {
        const statusCode = await updateSala(data.id, data);

        if (statusCode === 200) {
          const updatedFacility = await getSala(data.id);
          const index = salas.value.findIndex(f => f.id === data.id);
          salas.value[index] = updatedFacility;
        }
      } else {
        const createdFacility = await createSala(data);

        if (createdFacility) {
          salas.value.push(createdFacility);
        }
      }
      dialog.value = false;

      await getData();
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
