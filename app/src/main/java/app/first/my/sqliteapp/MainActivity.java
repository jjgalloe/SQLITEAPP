package app.first.my.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname,editMarks,editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;

    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_Name);
        editSurname = (EditText)findViewById(R.id.editText_Surname);
        editTextId = (EditText)findViewById(R.id.editText_Id);
        editMarks = (EditText)findViewById(R.id.editText_Marks);
        btnAddData = (Button)findViewById(R.id.button_Add);
        btnViewAll = (Button)findViewById(R.id.button_ViewAll);
        btnUpdate = (Button)findViewById(R.id.button_Update);
        btnDelete = (Button)findViewById(R.id.button_Delete);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();

    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        boolean isUpdated = myDb.updateData(editTextId.getText().toString(),
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                        if (isUpdated == true)
                            Toast.makeText(MainActivity.this,"Data updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());

                        if (isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0){
                            //show message
                            showMessage("Error","No data was found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("ID :"+ res.getString(0)+"\n");
                            buffer.append("NAME :"+ res.getString(1)+"\n");
                            buffer.append("SURNAME :"+ res.getString(2)+"\n");
                            buffer.append("MARKS :"+ res.getString(3)+"\n\n");
                        }

                        //SHOW ALL DATA
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
