package org.example.backendrh.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.backendrh.model.Ferias;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FeriasService {

    private static final String COLLECTION_NAME = "ferias";

    // 🔹 Criar férias
    public Ferias criar(Ferias ferias) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        if (ferias.getId() == null || ferias.getId().isEmpty()) {
            ferias.setId(db.collection(COLLECTION_NAME).document().getId());
        }
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                .document(ferias.getId())
                .set(ferias);
        future.get();
        return ferias;
    }

    // 🔹 Listar todas as férias
    public List<Ferias> listar() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Ferias> lista = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            lista.add(doc.toObject(Ferias.class));
        }
        return lista;
    }

    // 🔹 Atualizar
    public Ferias atualizar(String id, Ferias ferias) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ferias.setId(id);
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(id).set(ferias);
        future.get();
        return ferias;
    }

    // 🔹 Deletar
    public String deletar(String id) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(id).delete();
        future.get();
        return "Férias com ID " + id + " deletada com sucesso!";
    }
}
