package com.zeus.multiuseapp.listener;

import com.zeus.multiuseapp.models.TodoItem;

import java.util.List;

/**
 * Created by dell on 22-04-2016.
 */
public interface OnToDoListItemChangeListener {
    void onTodoListItemChanged(List<TodoItem> items);
}
