package com.test.foodie;

import android.view.View;

public interface OnItemClickListener {

    void onAddCartClick(DBModel dbModel, View v);
    void onRemoveCartClick(DBModel dbModel, View v);
    void onDescriptionClick(DBModel dbModel, View v);


}
