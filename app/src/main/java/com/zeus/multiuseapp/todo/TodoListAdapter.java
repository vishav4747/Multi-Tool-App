package com.zeus.multiuseapp.todo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.ItemTouchHelperAdapter;
import com.zeus.multiuseapp.common.ItemTouchHelperViewHolder;
import com.zeus.multiuseapp.listener.OnStartDragListener;
import com.zeus.multiuseapp.listener.OnToDoListItemChangeListener;
import com.zeus.multiuseapp.models.TodoItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by dell on 19-04-2016.
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<TodoItem> mTodoItems;
    private Context mContext;
    private OnStartDragListener mDragListener;
    private OnToDoListItemChangeListener mToDoListChanged;


    public TodoListAdapter(List<TodoItem> items, Context context, OnStartDragListener dragListener,
                           OnToDoListItemChangeListener onToDoListItemChangeListener) {
        mTodoItems = items;
        mContext = context;
        mDragListener = dragListener;
        mToDoListChanged = onToDoListItemChangeListener;

    }

    @Override
    public int getItemCount() {
        return mTodoItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_todo_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TodoItem selectedTodo = mTodoItems.get(position);
        holder.mToCheckBox.setText(selectedTodo.getTitle());
        if (selectedTodo.isChecked()) {
            holder.mTodoDate.setVisibility(View.VISIBLE);
            holder.mTodoDate.setText(selectedTodo.getModifiedDate());
            holder.mToCheckBox.setChecked(true);
            holder.mToCheckBox.setPaintFlags(holder.mToCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.mHandleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void OnItemMove(int fromPosition, int toPosition) {
        Collections.swap(mTodoItems, fromPosition, toPosition);
        mToDoListChanged.onTodoListItemChanged(mTodoItems);
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void OnItemDismissed(int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public TextView mTodoDate;
        public CheckBox mToCheckBox;
        private ImageView mHandleView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTodoDate = (TextView) itemView.findViewById(R.id.todoListCreated);
            mToCheckBox = (CheckBox) itemView.findViewById(R.id.todoListCheckbox);
            mHandleView = (ImageView) itemView.findViewById(R.id.tHandle);

        }

        @Override
        public void OnItemSelected() {
            itemView.setBackgroundColor(Color.rgb(197, 199, 233));

        }

        @Override
        public void OnItemClear() {
            itemView.setBackgroundColor(Color.rgb(179, 229, 252));
        }
    }
}
