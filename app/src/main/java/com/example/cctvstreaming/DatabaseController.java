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
import com.google.firebase.auth.FirebaseUser;
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
    public static void test()
    {
        firebaseFirestore.collection("test")
                .whereEqualTo("a","ok")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            Log.d("newTest",documentSnapshot.getId());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("newTest","No");
                    }
                });
    }
    public static void signOut()
    {
        firebaseAuth.signOut();
    }
    public static void checkAccess(SignInterface signInterface)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null)
        {
            signInterface.onComplete(null,"Sign In first");
        }
        else
        {
            getUser(firebaseUser.getUid(),signInterface);
        }
    }
    public static void signIn(String email,String password,SignInterface signInterface)
    {
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser firebaseUser = authResult.getUser();
                        if(firebaseUser.isEmailVerified())
                        {
                            getUser(firebaseUser.getUid(),signInterface);
                        }
                        else
                        {
                            signInterface.onComplete(null,"Please Verify Your Email");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signInterface.onComplete(null,e.getMessage());
                    }
                });
    }

    public static void getUser(String uid,SignInterface signInterface)
    {
        firebaseFirestore.collection("Users")
                .document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String name;
                        String district;
                        String subDistrict;
                        String recognition;
                        String number;
                        String status;
                        String id;
                        String email;
                        id = uid;
                        String about;
                        String accessLabel;

                        name = (String) documentSnapshot.get("name");
                        if (name == null)
                        {
                            Log.d("newM","No");
                        }
                        district = (String) documentSnapshot.get("district");
                        subDistrict = (String) documentSnapshot.get("subDistrict");
                        recognition = (String) documentSnapshot.get("recognition");
                        number = (String) documentSnapshot.get("number");
                        status = (String) documentSnapshot.get("status");
                        email = (String) documentSnapshot.get("email");
                        about = (String) documentSnapshot.get("about");
                        accessLabel = (String) documentSnapshot.get("accessLabel");
                        String institution = (String) documentSnapshot.get("institution");
                        User user = new User(name,district,subDistrict,recognition,number,id,email,status,about,accessLabel,institution);
                        signInterface.onComplete(user,"okay");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signInterface.onComplete(null,e.getMessage());
                    }
                });
    }

    public static void signUp(String email,String password,SignUpInterface signUpInterface)
    {

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        FirebaseUser firebaseUser = authResult.getUser();
                        sendVerification(firebaseUser,signUpInterface);
                        DatabaseReference myRef = database.getReference(firebaseUser.getUid());
                        myRef.setValue(0);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signUpInterface.onCompleteSignUp(e.getMessage());
                    }
                });
    }
    private static void sendVerification(FirebaseUser firebaseUser,SignUpInterface signUpInterface)
    {
        firebaseUser.sendEmailVerification()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        signUpInterface.onCompleteSignUp("ok");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signUpInterface.onCompleteSignUp(e.getMessage());
                    }
                });
    }
    public static void updateVal(String id,int value)
    {
        DatabaseReference myRef = database.getReference(id);
        value = value + 1;
        myRef.setValue(value)
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
    private static void getSendNotifications(String id)
    {
        firebaseFirestore.collection("Notifications")
                .whereEqualTo("senderId",id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Notification> notifications = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                        {
                            String description;
                            String senderName;
                            String subject;
                            String senderId;
                            String recId;
                            String reqTime;
                            String notificationId;
                            description = (String) documentSnapshot.get("description");
                            senderName = (String) documentSnapshot.get("senderName");
                            subject = (String) documentSnapshot.get("subject");
                            senderId= (String) documentSnapshot.get("senderId");
                            recId = (String) documentSnapshot.get("recId");
                            reqTime = (String) documentSnapshot.get("reqTime");
                            notificationId = (String) documentSnapshot.get("notificationId");
                            Notification notification = new Notification(description,senderId,senderName,reqTime,subject,recId);
                            notifications.add(notification);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private static void getReceiveNotifications(String id)
    {
        firebaseFirestore.collection("Notifications")
                .whereEqualTo("recId",id)
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

    public static void saveNotification(Notification notification,ListInterface listInterface)
    {

        firebaseFirestore.collection("Notifications")
                .add(notification)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        sendNotification(notification.getRecId());

                        listInterface.setSubmit("ok");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listInterface.setSubmit(" no ok");
                    }
                });
    }
    public static void saveUser(User user,Notification notification,ListInterface listInterface)
    {
        Log.d("today","there");
        user.setStatus("requested");
        firebaseFirestore.collection("Users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("today","kothay");
                        if(user.getAccessLabel() == null)
                        {
                            Log.d("today","here");
                            listInterface.setSubmit("ok");
                        }
                        else if(user.getAccessLabel().compareToIgnoreCase("district") == 0)
                        {
                            listInterface.setSubmit("Set value");
                        }
                        else if(user.getAccessLabel().compareToIgnoreCase("upozilla") == 0)
                        {
                            getDc(user.getDistrict(),notification,listInterface);
                        }
                        else if(user.getAccessLabel().compareToIgnoreCase("institution") == 0)
                        {
                            getUno(user.getDistrict(), user.getSubDistrict(),notification,listInterface);
                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listInterface.setSubmit("no ok");
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
                        Log.d("ip",school.getIp());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public static void setDc(String district,String id)
    {
        firebaseFirestore.collection("Districts")
                .document(district)
                .update("dc",id);
    }
    public static void setUno(String district,String subDis,String id)
    {
        firebaseFirestore.collection("Districts")
                .document(district)
                .collection(subDis)
                .document(subDis)
                .update("dc",id);
    }
    public static void getDc(String district,Notification notification,ListInterface listInterface)
    {
        firebaseFirestore.collection("Districts")
                .document(district)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("noman","kaksnka");
                        if(documentSnapshot.get("dc") == null)
                        {
                            listInterface.setSubmit("There is no one to accept your Request");
                            return;
                        }
                        Log.d("noman",(String) documentSnapshot.get("dc"));
                        notification.setRecId((String) documentSnapshot.get("dc"));
                        saveNotification(notification,listInterface);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listInterface.setSubmit("no ok");
                    }
                });

    }
    public static void getUno(String district,String subDistrict,Notification notification,ListInterface listInterface)
    {
        firebaseFirestore.collection("Districts")
                .document(district)
                .collection(district)
                .document(subDistrict)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.get("uno") == null)
                        {
                            listInterface.setSubmit("There is no one to accept your Request");
                            return;
                        }
                        notification.setRecId((String) documentSnapshot.get("uno"));
                        saveNotification(notification,listInterface);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listInterface.setSubmit("no");
                    }
                });

    }
    // districts
    public static void getDistrictList(ListInterface listInterface)
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
                        listInterface.setDistrict(districts);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listInterface.setDistrict(null);
                    }
                });
    }
    //sub districts
    public static void getSubDisList(String district,ListInterface listInterface)
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
                        listInterface.setSubDis(districts);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listInterface.setSubDis(null);
                    }
                });
    }

    public static void getSchoolDisList(String district,String subDis,ListInterface listInterface)
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
                        listInterface.setSchool(districts);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listInterface.setSchool(null);
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
    public static void getIndSchool(String dis,String sub,String sName,SchoolListInterface schoolListInterface)
    {
        ArrayList<School> schools = new ArrayList<>();
        firebaseFirestore.collection("Districts")
                .document(dis)
                .collection(dis)
                .document(sub)
                .collection(sub)
                .document(sName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
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
                        schoolListInterface.setSchool(schools);

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
