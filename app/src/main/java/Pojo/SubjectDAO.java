package Pojo;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.agenda.MainAgendaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.UUID;

import static Global.Info.currentUser;
import static Global.Info.stringSubjects;

public class SubjectDAO {

    FirebaseFirestore db;

    public SubjectDAO()
    {
        db = FirebaseFirestore.getInstance();
    }

    public void CreateSubject(String title, Context context)
    {
        String id = UUID.randomUUID().toString();
        Subject subject = new Subject (id, title);
        db.collection("Documents").document(currentUser.getEmail()).collection("Subject").document(id).set(subject)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        currentUser.addSubjects(subject);
                        Toast.makeText(context, "UPLOADED", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void GetSubjects(Context context)
    {
        db.collection("Documents").document(currentUser.getEmail()).collection("Subject").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        currentUser.DumpSubjects();
                        for(DocumentSnapshot doc : task.getResult())
                        {
                            Subject subject = new Subject(doc.getString("id"),
                                    doc.getString("subject"));
                            currentUser.addSubjects(subject);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error retrieving data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void DeleteSubject(String id, Context context)
    {
        db.collection("Documents").document(currentUser.getEmail()).collection("Subject").document(id).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "ERROR DELETING SUBJECT", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
