package org.example.backendrh.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.example.backendrh.model.HistoricoAlteracao;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HistoricoService {

    // Registrar uma nova alteração no Firestore
    public void registrarAlteracao(String funcionarioNome, String campo, String valorAntigo, String valorNovo) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();

        HistoricoAlteracao alteracao = new HistoricoAlteracao();
        alteracao.setId(firestore.collection("historico").document().getId());

        // Define data/hora atual formatada
        String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        alteracao.setDataHora(dataHora);

        alteracao.setFuncionarioNome(funcionarioNome);
        alteracao.setCampoAlterado(campo);
        alteracao.setValorAntigo(valorAntigo);
        alteracao.setValorNovo(valorNovo);

        // Salva no Firestore
        firestore.collection("historico")
                .document(alteracao.getId())
                .set(alteracao)
                .get(); // Aguarda a conclusão
    }

    // Listar todas as alterações registradas
    public List<HistoricoAlteracao> listar() throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = firestore.collection("historico").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<HistoricoAlteracao> historico = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            historico.add(doc.toObject(HistoricoAlteracao.class));
        }

        // Ordena pela data/hora mais recente (opcional)
        historico.sort((a, b) -> b.getDataHora().compareTo(a.getDataHora()));

        return historico;
    }
}
