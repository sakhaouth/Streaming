package com.example.cctvstreaming;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static void signUp(String email,String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public static void updateVal(String id,int value)
    {
        DatabaseReference myRef = database.getReference(id);
        value = value + 1;
        myRef.setValue(String.valueOf(value))
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
    public static void sendNotification(String id)
    {
        DatabaseReference myRef = database.getReference(id);
        myRef.get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        if(value.compareTo("") == 0)
                        {
                            value = "0";
                        }
                        updateVal(id,Integer.parseInt(value));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private static void getNotifications(String id)
    {
        firebaseFirestore.collection("Notifications")
                .document(id)
                .collection(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Notification> notifications = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                        {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public static void saveNotification(Notification notification)
    {
        firebaseFirestore.collection("Notifications")
                .document(notification.getRecId())
                .collection(notification.getRecId())
                .add(notification)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public static void saveUser(User user)
    {
        firebaseFirestore.collection("Users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("message","saved");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("message","not saved");
                    }
                });
    }
    public static void storeSchool(School school)
    {
        DocumentReference documentReference = firebaseFirestore.collection("Districts")
                .document(school.getDistrict());
        Map<String ,Object> dummyMap= new HashMap<>();
        documentReference.set(dummyMap);
        documentReference = documentReference.collection(school.getDistrict())
                .document(school.getSubDistrict());
        documentReference.set(dummyMap);
        documentReference = documentReference.collection(school.getSubDistrict())
                .document(school.getName());
//        documentReference.set(dummyMap);
                documentReference.set(school)
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
    public static void getDc(String district, Context context)
    {
//        firebaseFirestore.collection("Districts")
//                .document(district)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        Log.d("noman","kaksnka");
//                        Log.d("noman",(String) documentSnapshot.get("dc"));
//                        Toast.makeText(context,(String) documentSnapshot.get("dc"),Toast.LENGTH_SHORT);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context,"Fail",Toast.LENGTH_SHORT);
//                    }
//                });
//        Toast.makeText(context,"Fail",Toast.LENGTH_SHORT);
    }
    public static void getUno(String district,String subDistrict)
    {
        firebaseFirestore.collection("Districts")
                .document(district)
                .collection(district)
                .document(subDistrict)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    // districts
    public static void getDistrictList()
    {
        firebaseFirestore.collection("Districts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> districts = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                        {
                            districts.add(documentSnapshot.getId());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    //sub districts
    public static void getSubDisList(String district)
    {
        firebaseFirestore.collection("Districts")
                .document(district)
                .collection(district)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> districts = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                        {
                            districts.add(documentSnapshot.getId());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public static void getSchoolDisList(String district,String subDis)
    {
        firebaseFirestore.collection("Districts")
                .document(district)
                .collection(district)
                .document(subDis)
                .collection(subDis)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<String> districts = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                        {
                            districts.add(documentSnapshot.getId());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
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
                        Log.d("message","saved");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("message","not saved");
                    }
                });
    }
    public static void getSubDistricts(String district,SubDistrictInterface subDistrictInterface)
    {
        ArrayList<SubDistrict> subDistricts = new ArrayList<>();
        firebaseFirestore.collection("Districts")
                .document(district)
                .collection(district)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<DocumentSnapshot> documentSnapshots = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot : documentSnapshots)
                        {

                            Log.d("hello",snapshot.toString());
                            subDistricts.add(new SubDistrict(snapshot.getId()));
                            Log.d("data",snapshot.getId());

                        }
                        subDistrictInterface.getSubDistrict(subDistricts);
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
        firebaseFirestore.collection("Districts")
                .document(district)
                .collection(district)
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
