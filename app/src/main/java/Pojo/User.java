package Pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    private String DisplayName;
    private String Email;
    private List<Subject> Subjects = new ArrayList<>();
    private List<Job> Tasks = new ArrayList<>();

    public User (){
        Subjects.add(new Subject("1", "To Do"));
    }

    public User(String displayName, String email) {
        DisplayName = displayName;
        Email = email;
        Subjects.add(new Subject("1", "To Do"));
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void addSubjects(Subject subject) { Subjects.add(subject); }

    public List<Subject> getSubjects(){ return Subjects; }

    public void addTasks(Job job) { this.Tasks.add(job); }

    public List<Job> getTasks() {
        return Tasks;
    }

    public void DumpTasks()
    {
        Tasks.clear();
    }

    public void DumpSubjects()
    {
        Subjects.clear();
        Subjects.add(new Subject("1", "To Do"));
    }

    public void DeleteSubjectById(String id)
    {
        for(int a = 0 ; a < Subjects.size(); a++)
        {
            if(id.equals(Subjects.get(a).getId())) {
                Subjects.remove(a);
                return;
            }
        }
    }

    public void SetCompletedTaskById(String id, boolean completed)
    {
        for(int a = 0 ; a < Tasks.size(); a++)
        {
            if(id.equals(Tasks.get(a).getId()))
                Tasks.get(a).setCompleted(completed);
        }
    }

    public void DeleteTaskById(String id)
    {
        for(int a = 0 ; a < Tasks.size(); a++)
        {
            if(id.equals(Tasks.get(a).getId())) {
                Tasks.remove(a);
                return;
            }
        }
    }

    public void SortTasksByDate()
    {
        SortByDay(Tasks.size());
        SortByMonth(Tasks.size());
        SortByYear(Tasks.size());
    }

    public void SortByDay(int iterations)
    {
        if(iterations != 0 )
        {
            for(int a = 0; a < iterations - 1; a++)
            {
                if(Tasks.get(a + 1).getDay() < Tasks.get(a).getDay())
                    Collections.swap(Tasks, a, (a + 1));
            }
            SortByDay(iterations - 1);
        }
    }

    public void SortByMonth(int iterations)
    {
        if(iterations != 0 )
        {
            for(int a = 0; a < iterations - 1; a++)
            {
                if(Tasks.get(a + 1).getMonth() < Tasks.get(a).getMonth())
                    Collections.swap(Tasks, a, (a + 1));
            }
            SortByMonth(iterations - 1);
        }
    }

    public void SortByYear(int iterations)
    {
        if(iterations != 0 )
        {
            for(int a = 0; a < iterations - 1; a++)
            {
                if(Tasks.get(a + 1).getYear() < Tasks.get(a).getYear())
                    Collections.swap(Tasks, a, (a + 1));
            }
            SortByYear(iterations - 1);
        }
    }

    public int GetFirstIndexTaskPosterior()
    {
        Control control = new Control();
        for(int a = 0; a < Tasks.size(); a++)
        {
            if(control.IsDatePosterior(Tasks.get(a).getDay(), Tasks.get(a).getMonth(), Tasks.get(a).getYear()))
                return a;
        }
        return -1;
    }

    public int GetFirstIndexTaskFromPosteriorWeek()
    {
        Control control = new Control();
        for(int a = 0; a < Tasks.size(); a++)
        {
            if(control.IsDateFromPosteriorWeek(Tasks.get(a).getDay(), Tasks.get(a).getMonth(), Tasks.get(a).getYear())) {
                return a;
            }
        }
        return -1;
    }

    public int GetQuantityTasksByDate(int day, int month, int year)
    {
        int tasksCounter = 0;
        for(Job job : Tasks)
        {
            if(job.getDay() == day && job.getMonth() == month && job.getYear() == year)
                tasksCounter++;
        }
        return tasksCounter;
    }

    public int GetQuantityPendingTasksByDate(int day, int month, int year)
    {
        int tasksCounter = 0;
        for(Job job : Tasks)
        {
            if(job.getDay() == day && job.getMonth() == month && job.getYear() == year && job.isCompleted() == false)
                tasksCounter++;
        }
        return tasksCounter;
    }

    public List<Job> GetTasksByDate(String date)
    {
        Control control = new Control();
        List<Job> dateTasks = new ArrayList<>();
        for(Job job : Tasks)
        {
            if(date.equals(control.FormatDate(job.getDay(), job.getMonth(), job.getYear())))
                dateTasks.add(job);
        }
        return dateTasks;
    }

    public int GetIndexById(String id)
    {
        for(int a = 0 ; a < Tasks.size(); a++)
        {
            if(id.equals(Tasks.get(a).getId()))
                return a;
        }
       return -1;
    }

}
