package com.example.lmsnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        final EditText edit_name = findViewById(R.id.edit_name);
        final EditText edit_position= findViewById(R.id.edit_position);
        final EditText edit_nic= findViewById(R.id.edit_nic);
        Button btn=findViewById(R.id.btn_submit);
        Button btn_open = findViewById(R. id.btn_open);

        configureNextButton();

        btn_open.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, RVActivity.class);
            startActivity(intent);
        });
        DAOEmployee dao = new DAOEmployee();
        Employee emp_edit = (Employee)getIntent().getSerializableExtra("EDIT");
        if(emp_edit != null){
            btn.setText("UPDATE");
            edit_name.setText(emp_edit.getName());
            edit_position.setText(emp_edit.getPosition());

            btn_open.setVisibility(View.GONE);
        }else{
            btn.setText("SUBMIT");
            btn_open.setVisibility(View.VISIBLE);
        }

        btn.setOnClickListener(v->{
            Employee emp = new Employee(edit_name.getText().toString(),edit_position.getText().toString(),edit_nic.getText().toString());
            if(emp_edit == null) {
                dao.add(emp).addOnSuccessListener(suc -> {
                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }else{
                    HashMap<String,Object> hashMap= new HashMap<>();
                hashMap.put("name",edit_name.getText().toString());
                hashMap.put("position",edit_position.getText().toString());
                dao.update(emp_edit.getKey(),hashMap).addOnSuccessListener(suc->{
                    Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(er->{
                    Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
//            HashMap<String,Object> hashMap= new HashMap<>();
//            hashMap.put("name",edit_name.getText().toString());
//            hashMap.put("position",edit_position.getText().toString());
//            dao.update("-N1M83bi-rXY153d16zA",hashMap).addOnSuccessListener(suc->{
//                Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
//            }).addOnFailureListener(er->{
//                Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
//            });
//            dao.remove("-N1M83bi-rXY153d16zA").addOnSuccessListener(suc->{
//                Toast.makeText(this, "Record is removed", Toast.LENGTH_SHORT).show();
//            }).addOnFailureListener(er->{
//                Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
//            });
        });



    }
    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.btn_switch);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ActualMain.class));
            }
        });

    }
}