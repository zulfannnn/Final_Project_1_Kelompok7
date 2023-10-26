package kelompok7.msibfinalproject1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.editTaskListener, TaskAdapter.deleteTaskListener{

    private ImageButton addButton;
    private RecyclerView listtaskRv;
    SQLiteDatabaseHandler db;
    private List<Task> taskList;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.btnadd);
        listtaskRv = findViewById(R.id.list_task_rv);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showForm(null, false);
            }
        });
        db = new SQLiteDatabaseHandler(this);

        loadDataTask();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listtaskRv.setLayoutManager(layoutManager);
    }
    private void loadDataTask(){
        taskList = db.getAllTask();
        taskAdapter = new TaskAdapter(this, taskList,this,this);
        listtaskRv.setAdapter(taskAdapter);
    }
    private void showForm(Task taskEdit, boolean isEdit) {
        AlertDialog.Builder fromBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.activity_popup,null);
        fromBuilder.setView(view);

        AlertDialog popup = fromBuilder.create();
        popup.show();

        EditText taskNameInput = view.findViewById(R.id.edittask);
        Button saveButton = view.findViewById(R.id.btnasave);

        if (isEdit) {
            taskNameInput.setText(taskEdit.getTaskName());
            saveButton.setText("UPDATE DATA");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String taskName = taskNameInput.getText().toString();
                if(!isEdit){
                    //ADD DATA
                    Task task = new Task(taskName);
                    db.addTask(task);

                }else{
                    //EDIT DATA
                    taskEdit.setTaskName(taskName);
                    db.updateTask(taskEdit);
                }

                loadDataTask();
                popup.dismiss();

            }
        });
    }
    @Override
    public void onEditTask(int position) {
        Task selectedTaskEdit = taskList.get(position);
        showForm(selectedTaskEdit, true);
    }

    @Override
    public void onDeleteTask(int position) {
        Task selectedTaskDelete = taskList.get(position);
        db.deleteTask(selectedTaskDelete);
        loadDataTask();
    }

}
