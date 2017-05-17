package com.db_in_android_sd_card;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.db_in_android_sd_card.adapter.NamesListRecyclerAdapter;
import com.db_in_android_sd_card.db.DBHelper;
import com.db_in_android_sd_card.models.NameModel;
import com.db_in_android_sd_card.util.AppLog;
import com.db_in_android_sd_card.util.AppUtil;

import java.util.ArrayList;

/**
 * Main Activity handling UI interaction with user.
 */
public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private ArrayList<NameModel> nameModelArrayList = new ArrayList<>();
    private NamesListRecyclerAdapter namesListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText autoCompleteTextView_name = (EditText) findViewById(R.id.autoCompleteTextView_name);

        RecyclerView recyclerView_namesList = (RecyclerView) findViewById(R.id.recyclerView_namesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView_namesList.setLayoutManager(mLayoutManager);
        nameModelArrayList.clear();
        namesListRecyclerAdapter = new NamesListRecyclerAdapter(MainActivity.this, nameModelArrayList);
        recyclerView_namesList.setAdapter(namesListRecyclerAdapter);

        Button buttonInsertInDB = (Button) findViewById(R.id.buttonInsertInDB);
        buttonInsertInDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(autoCompleteTextView_name.getText().toString())) {
                    attempt_insertInDB(autoCompleteTextView_name.getText().toString());
                    fetchAndSetDataInRecyclerView();
                } else {
                    AppUtil.showToast(MainActivity.this, getString(R.string.Enter_name), true);
                }
            }
        });

    }

    private void attempt_insertInDB(String nameValueToInsert) {
        DBHelper dbHelper = new DBHelper(this);
        NameModel nameModel = new NameModel();
        nameModel.setName(nameValueToInsert);
        if (dbHelper.insertNameValueInDB(nameModel) == -1) {
            AppUtil.showToast(MainActivity.this, getString(R.string.Error_while_inserting_new_row_in_DB), true);
        }
    }

    private void fetchAndSetDataInRecyclerView() {
        new FetchNamesAsync().execute();
    }

    /**
     * inner class to Fetch names from DB and update in a list
     */
    private class FetchNamesAsync extends AsyncTask<Object, Object, ArrayList<NameModel>> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage(getString(R.string.LoadingProgressDialog));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<NameModel> doInBackground(Object... params) {
            DBHelper dbHelper = new DBHelper(MainActivity.this);
            return dbHelper.fetchAllNameValuesForCurrentAppFromDB();
        }

        @Override
        protected void onPostExecute(ArrayList<NameModel> nameModelArrayList1) {
            super.onPostExecute(nameModelArrayList);
            nameModelArrayList.clear();
            nameModelArrayList.addAll(nameModelArrayList1);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (nameModelArrayList != null) {
                namesListRecyclerAdapter.notifyDataSetChanged();

                AppLog.v(LOG_TAG, "nameModelArrayList.size():" + nameModelArrayList.size());
            } else {
                AppUtil.showToast(MainActivity.this, getString(R.string.Names_list_is_empty_in_DB), true);
            }

        }
    }
}