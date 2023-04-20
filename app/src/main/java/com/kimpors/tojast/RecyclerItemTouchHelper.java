package com.kimpors.tojast;

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

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private TodoAdapter adapter;

    public RecyclerItemTouchHelper(TodoAdapter adapter)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getLayoutPosition();
        if(direction == ItemTouchHelper.LEFT)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete task");
            builder.setMessage("Are you shure you want to delte this task");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            adapter.delete(adapter.getContext(), position);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.notifyDataSetChanged();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            Intent intent = new Intent(adapter.getContext(), CreateActivity.class);
            intent.putExtra("id", adapter.get(position).getId());
            adapter.getContext().startActivity(intent);
            //TODO edit item
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

        if(dX > 0)
        {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_edit);
        }
        else {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_delete);
        }

        background = new ColorDrawable(Color.WHITE);

        int iconMargin = (item.getHeight() - icon.getIntrinsicHeight()) / 2;
        int backgroundCornerOffset = 20;

        bounds.top = item.getTop() + iconMargin;
        bounds.bottom = bounds.top + icon.getIntrinsicHeight();

        if(dX > 0)
        {
            bounds.left = item.getLeft() + iconMargin;
            bounds.right = item.getLeft() + iconMargin + icon.getIntrinsicWidth();

            icon.setBounds(bounds);
            background.setBounds(item.getLeft(), item.getTop(), item.getLeft()+ ((int)dX) + backgroundCornerOffset, item.getBottom());
        }
        else if(dX < 0){

            bounds.left = item.getRight() - iconMargin - icon.getIntrinsicWidth();
            bounds.right = item.getRight() - iconMargin;

            icon.setBounds(bounds);
            background.setBounds(item.getRight() + ((int)dX) - backgroundCornerOffset, item.getTop(), item.getRight(), item.getBottom());
        }
        else{
            background.setBounds(0,0,0,0);
        }

        background.draw(c);
        icon.draw(c);
    }
}
