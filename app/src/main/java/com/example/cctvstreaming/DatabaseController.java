package com.example.cctvstreaming;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseController {
    private static StorageReference storageReference;
    private static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static void writeData()
    {

        Map<String,Integer> map = new HashMap<>();
        map.put("a",50);
        firebaseFirestore.collection("test")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("message","okay");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("message","fail");
                    }
                });

    }
    public static void saveSchool(School school)
    {
        firebaseFirestore.collection(school.getDistrict())
                .document(school.getSubDistrict())
                .collection(school.getSubDistrict())
                .document(school.getName())
                .set(school)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public static void getSchool(String district,String subDistrict,SchoolListInterface schoolListInterface)
    {
        ArrayList<School> schools = new ArrayList<>();
        firebaseFirestore.collection(district)
                .document(subDistrict)
                .collection(subDistrict)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                        {
                            String name = (String) documentSnapshot.get("name");
                            String district = (String) documentSnapshot.get("district");
                            String subDistrict = (String) documentSnapshot.get("subDistrict");
                            String link = (String) documentSnapshot.get("link");
                            ArrayList<String> cameraNames = (ArrayList<String>) documentSnapshot.get("cameraNames");
                            School school = new School(name,district,subDistrict,cameraNames,link);
                            schools.add(school);

                            System.out.println(school);
                            Log.d("name",String.valueOf(school.getCameraNames().size()));
                            System.out.println(String.valueOf(school.getCameraNames().size()));
                            for(String x : school.getCameraNames())
                            {
                                Log.d("name",x);
                            }
                        }
                        schoolListInterface.setSchool(schools);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}
