package com.example.jdbcmysql.controladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jdbcmysql.R;
import com.example.jdbcmysql.modelos.DBConnection;
import com.example.jdbcmysql.modelos.Usuario;

import java.sql.SQLException;

public class MainActivity2 extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtTelefono;
    private EditText txtPass;
    private TextView tvId;
    private String id;
    private DBConnection dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtNombre = findViewById(R.id.txtNombre);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtPass = findViewById(R.id.txtPass);
        tvId = findViewById(R.id.tvId);

        id = getIntent().getStringExtra("id");
        tvId.setText(id);

        dbConnection = new DBConnection();

        try {
            Usuario usuario = dbConnection.obtenerUsuarioPorId(Integer.parseInt(id));
            txtNombre.setText(usuario.getNombre());
            txtEmail.setText(usuario.getEmail());
            txtTelefono.setText(usuario.getTelefono());
            txtPass.setText(usuario.getPass());
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity2.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void clickRegresar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickBorrar(View view) {
        try {
            dbConnection.borrarUsuario(id);
            Toast.makeText(MainActivity2.this, "El usuario se borró de forma exitosa", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity2.this, "Error al borrar el usuario " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void clickEditar(View view) {
        String nombre = txtNombre.getText().toString();
        String email = txtEmail.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String pass = txtPass.getText().toString();

        try {
            dbConnection.editarUsuario(id, nombre, email, telefono, pass);
            Toast.makeText(MainActivity2.this, "El usuario ha sido editado de forma exitosa", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity2.this, "Error al editar el usuario " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
