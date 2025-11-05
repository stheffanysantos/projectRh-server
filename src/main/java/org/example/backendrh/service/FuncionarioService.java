package org.example.backendrh.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.example.backendrh.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private HistoricoService historicoService;

    // Cadastro (criação)
    public Funcionario salvar(Funcionario funcionario) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();

        // Se id está preenchido (atualização), use o id fornecido
        if (funcionario.getId() != null && !funcionario.getId().isEmpty()) {
            firestore.collection("funcionarios")
                    .document(funcionario.getId())
                    .set(funcionario)
                    .get();
            return funcionario;
        }

        // Se id for nulo/vazio (novo), gere id automático
        DocumentReference docRef = firestore.collection("funcionarios").document();
        funcionario.setId(docRef.getId());
        docRef.set(funcionario).get();
        return funcionario;
    }

    // Buscar por id
    public Funcionario buscarPorId(String id) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentSnapshot doc = firestore.collection("funcionarios").document(id).get().get();
        return doc.toObject(Funcionario.class);
    }

    // Listar todos
    public List<Funcionario> listarTodos() throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = firestore.collection("funcionarios").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Funcionario> funcionarios = new ArrayList<>();
        for (DocumentSnapshot doc : documents) {
            funcionarios.add(doc.toObject(Funcionario.class));
        }
        return funcionarios;
    }

    // Atualizar com registro de histórico
    public Funcionario atualizar(Funcionario funcionario) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();

        // Busca o funcionário antigo no Firestore
        DocumentSnapshot antigoDoc = firestore.collection("funcionarios")
                .document(funcionario.getId())
                .get()
                .get();

        if (antigoDoc.exists()) {
            Funcionario antigo = antigoDoc.toObject(Funcionario.class);

            if (antigo != null) {
                // Compara campos e registra alterações
                if (!antigo.getCargo().equals(funcionario.getCargo())) {
                    historicoService.registrarAlteracao(funcionario.getNome(), "cargo", antigo.getCargo(), funcionario.getCargo());
                }
                if (!antigo.getDepartamento().equals(funcionario.getDepartamento())) {
                    historicoService.registrarAlteracao(funcionario.getNome(), "departamento", antigo.getDepartamento(), funcionario.getDepartamento());
                }
                if (!antigo.getStatus().equals(funcionario.getStatus())) {
                    historicoService.registrarAlteracao(funcionario.getNome(), "status", antigo.getStatus(), funcionario.getStatus());
                }
                if (antigo.getSalario() != funcionario.getSalario()) {
                    historicoService.registrarAlteracao(funcionario.getNome(), "salario",
                            String.valueOf(antigo.getSalario()), String.valueOf(funcionario.getSalario()));
                }
                if (!antigo.getEmail().equals(funcionario.getEmail())) {
                    historicoService.registrarAlteracao(funcionario.getNome(), "email", antigo.getEmail(), funcionario.getEmail());
                }
                if (!antigo.getTelefone().equals(funcionario.getTelefone())) {
                    historicoService.registrarAlteracao(funcionario.getNome(), "telefone", antigo.getTelefone(), funcionario.getTelefone());
                }
            }
        }

        // Atualiza os dados do funcionário
        firestore.collection("funcionarios")
                .document(funcionario.getId())
                .set(funcionario)
                .get(); // Aguarda a conclusão da operação

        return funcionario;
    }

    // Deletar
    public void deletar(String id) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection("funcionarios").document(id).delete();
    }
}
