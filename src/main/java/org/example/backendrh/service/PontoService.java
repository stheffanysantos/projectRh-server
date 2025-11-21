package org.example.backendrh.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.backendrh.model.Ponto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PontoService {

    // Registrar ponto
    public Ponto registrarPonto(String funcionarioId) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();

        Ponto ponto = new Ponto(funcionarioId, new Date());

        DocumentReference docRef = firestore.collection("pontos").document();
        ponto.setId(docRef.getId());
        docRef.set(ponto).get();

        return ponto;
    }

    // Listar pontos por funcionário
    public List<Ponto> listarPorFuncionario(String funcionarioId) {
        try {
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
        } catch (Exception e) {
            System.err.println("Erro ao listar pontos para funcionário " + funcionarioId + ": " + e.getMessage());
            return new ArrayList<>(); // Retorna lista vazia caso dê erro
        }
    }

    // Listar todos os registros de ponto
    public List<Ponto> listarTodos() {
        try {
            Firestore firestore = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> future = firestore.collection("pontos").get();
            List<QueryDocumentSnapshot> docs = future.get().getDocuments();

            List<Ponto> pontos = new ArrayList<>();
            for (DocumentSnapshot doc : docs) {
                pontos.add(doc.toObject(Ponto.class));
            }
            return pontos;
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os pontos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Contar pontos por funcionário
    public int contarPorFuncionario(String funcionarioId) {
        try {
            return listarPorFuncionario(funcionarioId).size();
        } catch (Exception e) {
            System.err.println("Erro ao contar pontos para funcionário " + funcionarioId + ": " + e.getMessage());
            return 0; // Retorna 0 caso dê erro
        }
    }
}
