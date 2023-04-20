package com.kimpors.tojast;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kimpors.tojast.Adapter.TodoAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    private final TodoAdapter adapter;
    private int position;

    public RecyclerItemTouchHelper(TodoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        position = viewHolder.getLayoutPosition();

        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext())
                    .setTitle("Delete task")
                    .setMessage("Are you sure you want to delete this task")
                    .setPositiveButton("Confirm", this)
                    .setNegativeButton(android.R.string.cancel, this);

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Intent intent = new Intent(adapter.getContext(), CreateActivity.class);
            intent.putExtra("id", adapter.get(position).getId());
            adapter.getContext().startActivity(intent);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;
        Rect bounds = new Rect();
        View item = viewHolder.itemView;

        icon = dX > 0
                ? ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_edit)
                : ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_delete);


        background = new ColorDrawable(Color.WHITE);

        int iconMargin = (item.getHeight() - icon.getIntrinsicHeight()) / 2;
        int backgroundCornerOffset = 20;

        bounds.top = item.getTop() + iconMargin;
        bounds.bottom = bounds.top + icon.getIntrinsicHeight();

        if (dX > 0) {
            bounds.left = item.getLeft() + iconMargin;
            bounds.right = item.getLeft() + iconMargin + icon.getIntrinsicWidth();

            icon.setBounds(bounds);
            background.setBounds(item.getLeft(), item.getTop(), item.getLeft() + ((int) dX) + backgroundCornerOffset, item.getBottom());
        } else if (dX < 0) {

            bounds.left = item.getRight() - iconMargin - icon.getIntrinsicWidth();
            bounds.right = item.getRight() - iconMargin;

            icon.setBounds(bounds);
            background.setBounds(item.getRight() + ((int) dX) - backgroundCornerOffset, item.getTop(), item.getRight(), item.getBottom());
        } else {
            background.setBounds(0, 0, 0, 0);
        }


        background.draw(c);
        icon.draw(c);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        adapter.delete(adapter.getContext(), position);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCancel(DialogInterface dialogInterface) {
        adapter.notifyDataSetChanged();
    }
}
