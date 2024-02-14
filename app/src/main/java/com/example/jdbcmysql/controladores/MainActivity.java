package com.example.jdbcmysql.controladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jdbcmysql.R;
import com.example.jdbcmysql.modelos.DBConnection;
import com.example.jdbcmysql.modelos.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtTelefono;
    private EditText txtPass;
    private TableLayout tbUsuarios;
    private String idGlobal;
    private DBConnection dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNombre = findViewById(R.id.txtNombre);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtPass = findViewById(R.id.txtPass);
        tbUsuarios = findViewById(R.id.tbUsuarios);

        dbConnection = new DBConnection();

        cargaTabla();
    }

    public void cargaTabla() {
        tbUsuarios.removeAllViews();

        try {
            ArrayList<Usuario> usuarios = dbConnection.obtenerUsuarios();
            for (Usuario usuario : usuarios) {
                View registro = LayoutInflater.from(MainActivity.this).inflate(R.layout.table_row, null, false);
                TextView colNombre = registro.findViewById(R.id.colNombre);
                TextView colEmail = registro.findViewById(R.id.colEmail);
                View colEditar = registro.findViewById(R.id.colEditar);
                View colBorrar = registro.findViewById(R.id.colBorrar);

                colNombre.setText(usuario.getNombre());
                colEmail.setText(usuario.getEmail());
                colEditar.setId(usuario.getId());
                colBorrar.setId(usuario.getId());

                tbUsuarios.addView(registro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clickTablaEditar(View view) {
        idGlobal = String.valueOf(view.getId());
        try {
            Usuario usuario = dbConnection.obtenerUsuarioPorId(Integer.parseInt(idGlobal));
            txtNombre.setText(usuario.getNombre());
            txtEmail.setText(usuario.getEmail());
            txtTelefono.setText(usuario.getTelefono());
            txtPass.setText(usuario.getPass());
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void clickGuardaEditar(View view) {
        String nombre = txtNombre.getText().toString();
        String email = txtEmail.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String pass = txtPass.getText().toString();

        try {
            dbConnection.editarUsuario(idGlobal, nombre, email, telefono, pass);
            Toast.makeText(MainActivity.this, "El usuario ha sido editado de forma exitosa", Toast.LENGTH_LONG).show();
            cargaTabla();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error al editar el usuario " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void clickTablaBorrar(View view) {
        String id = String.valueOf(view.getId());
        try {
            dbConnection.borrarUsuario(id);
            Toast.makeText(MainActivity.this, "El usuario se borró exitosamente", Toast.LENGTH_LONG).show();
            cargaTabla();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error al borrar el usuario " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void clickBtnInsertar(View view) {
        String nombre = txtNombre.getText().toString();
        String email = txtEmail.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String pass = txtPass.getText().toString();

        try {
            dbConnection.insertarUsuario(nombre, email, telefono, pass);
            Toast.makeText(MainActivity.this, "Usuario insertado exitosamente", Toast.LENGTH_LONG).show();
            cargaTabla();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void cickVer(View view) {
        EditText txtId = findViewById(R.id.txtId);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("id", txtId.getText().toString());
        startActivity(intent);
    }

    public void clickReset(View view) {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtPass.setText("");
    }
}



