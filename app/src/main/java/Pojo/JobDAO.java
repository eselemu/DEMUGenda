package Pojo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.agenda.MainActivity;
import com.example.agenda.MainAgendaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static Global.Info.currentUser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobDAO {
    FirebaseFirestore db;

    public JobDAO()
    {
        db = FirebaseFirestore.getInstance();
    }

    public void CreateJob(String title, String description, String subject, int year, int month, int day, Context context)
    {
        String id = UUID.randomUUID().toString();
        Job job = new Job (id, title, description, subject, year, month, day);
        db.collection("Documents").document(currentUser.getEmail()).collection("Task").document(id).set(job)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        currentUser.addTasks(job);
                        Toast.makeText(context, "UPLOADED", Toast.LENGTH_SHORT).show();
                        Intent changeMainAgenda = new Intent(context, MainAgendaActivity.class);
                        context.startActivity(changeMainAgenda);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void DeleteJob(String id, Context context)
    {
        db.collection("Documents").document(currentUser.getEmail()).collection("Task").document(id).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        currentUser.DeleteTaskById(id);
                        Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
                        Intent changeMainAgenda = new Intent(context, MainAgendaActivity.class);
                        context.startActivity(changeMainAgenda);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "ERROR DELETING TASK", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void UpdateCompletedJob(String id, boolean completed, Context context)
    {
        db.collection("Documents").document(currentUser.getEmail()).collection("Task").document(id).update("completed", completed)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        currentUser.SetCompletedTaskById(id, completed);
                        if(completed)
                            Toast.makeText(context, "CHECKED", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "UNCHECKED", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void GetJobs(Context context, boolean changeMain)
    {
        db.collection("Documents").document(currentUser.getEmail()).collection("Task").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        currentUser.DumpTasks();
                        for(DocumentSnapshot doc : task.getResult())
                        {
                            Job job = new Job(doc.getString("id"),
                                    doc.getString("title"),
                                    doc.getString("description"),
                                    doc.getString("subject"),
                                    doc.getLong("year").intValue(),
                                    doc.getLong("month").intValue(),
                                    doc.getLong("day").intValue());
                            job.setCompleted(doc.getBoolean("completed"));
                            currentUser.addTasks(job);
                        }
                        if(changeMain)
                        {
                            Intent changeMainAgenda = new Intent(context.getApplicationContext(), MainAgendaActivity.class);
                            context.startActivity(changeMainAgenda);
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
}
