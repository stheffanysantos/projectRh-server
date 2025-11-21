package org.example.backendrh.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.backendrh.model.Ponto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PontoService {

    // Registrar ponto (entrada/saída)
    public Ponto registrarPonto(String funcionarioId) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();

        Ponto ponto = new Ponto(funcionarioId, new Date());

        DocumentReference docRef = firestore.collection("pontos").document();
        ponto.setId(docRef.getId());
        docRef.set(ponto).get();

        return ponto;
    }

    // Listar pontos por funcionário
    public List<Ponto> listarPorFuncionario(String funcionarioId) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = firestore.collection("pontos")
                .whereEqualTo("funcionarioId", funcionarioId)
                .orderBy("dataHora", Query.Direction.ASCENDING)
                .get();

        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
        List<Ponto> pontos = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            pontos.add(doc.toObject(Ponto.class));
        }
        return pontos;
    }

    // Listar todos os registros de ponto
    public List<Ponto> listarTodos() throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = firestore.collection("pontos").get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();

        List<Ponto> pontos = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            pontos.add(doc.toObject(Ponto.class));
        }
        return pontos;
    }

    // Contar pontos por funcionário (para cards do frontend)
    public int contarPorFuncionario(String funcionarioId) throws Exception {
        return listarPorFuncionario(funcionarioId).size();
    }
}
