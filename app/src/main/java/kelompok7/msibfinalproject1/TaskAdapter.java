package kelompok7.msibfinalproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> taskList;

    private Context context;


    public TaskAdapter(Context context, List<Task> countryList){
        this.context = context;
        this.taskList = countryList;
    }
    public TaskAdapter(Context context, List<Task> taskList, editTaskListener editListener, deleteTaskListener deleteListener){
        this.context = context;
        this.taskList = taskList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }
    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.idTaskTv.setText(String.valueOf(task.getId()));
        holder.taskNameTv.setText(task.getTaskName());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView idTaskTv;
        private TextView taskNameTv;
        private Button editButton;
        private Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTaskTv = itemView.findViewById(R.id.id_task_tv);
            taskNameTv = itemView.findViewById(R.id.taskTV);

            editButton = itemView.findViewById(R.id.btnEdit);
            deleteButton = itemView.findViewById(R.id.btnComplete);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editListener.onEditTask(getAdapterPosition());
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.onDeleteTask(getAdapterPosition());
                }
            });
        }
    }

    public interface editTaskListener{
        void onEditTask(int position);
    }
    public interface deleteTaskListener{
        void onDeleteTask(int position);
    }
    private editTaskListener editListener;
    private deleteTaskListener deleteListener;
}
