package com.k214110826.sqlite_ex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.k214110826.models.Product;
import com.k214110826.sqlite_ex.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    public static final String DB_NAME = "product_db.db";
    public static final String DB_FOLDER = "/databases";

    SQLiteDatabase db = null;

    public static final String TBL_NAME = "Product";
    public static final String COL_ID = "ProductId";
    public static final String COL_NAME = "ProductName";
    public static final String COL_PRICE = "ProductPrice";
//    private File DbFile;
    ArrayAdapter<Product> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        copyDb();
        loadDb();
        initAdapter();
    }

    private void initAdapter() {
        adapter = new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1);
        binding.lvProduct.setAdapter(adapter);
    }

    private void loadDb() {
        db = openOrCreateDatabase(DB_NAME,MODE_PRIVATE,null);
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME, null);
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + COL_ID + ">?", new String[]{"2"});
        //SELECT * FROM Product WHERE ProductId > 2
//
        //SELECT * FROM Product WHERE
        Cursor cursor = db.query(TBL_NAME, null, COL_NAME + " LIKE %?%", new String[]{"%h%"}, null, null, null);
        Product p;
        while (cursor.moveToNext()){
            p = new Product(cursor.getInt(0), cursor.getString((1)), cursor.getDouble(2));

            adapter.add(p);
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private void copyDb() {
        File dbFile = getDatabasePath(DB_NAME);
        if(!dbFile.exists()){
            if(copyDbFromAssets())
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            //copy
            else
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean copyDbFromAssets(){
        String dbPath = getApplicationInfo().dataDir + "/" + DB_FOLDER + "/" + DB_NAME;
        //truy xuất vo thư mục data/data/pakageName/databases/product_db.db
        try {
            InputStream inputStream = getAssets().open(DB_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_FOLDER);
            if (!f.exists()) {
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnAdd){
            //opening
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}