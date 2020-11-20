package cl.inacap.registrocovid;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static cl.inacap.registrocovid.CrearPacienteActivity.validarRut;

public class MainActivity extends AppCompatActivity {

    private EditText idUsuario, passUsuario;
    private Button btnLoginUser;

    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.idUsuario = findViewById(R.id.txtIngreso);
        this.passUsuario = findViewById(R.id.txtPassword);
        this.btnLoginUser = findViewById(R.id.login_btn);

        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user =  idUsuario.getText().toString().trim();
                String pass = passUsuario.getText().toString().trim();
                try{
                    if (user.isEmpty()){
                        Toast.makeText(MainActivity.this, "Ingrese nombre de Usuario"
                                , Toast.LENGTH_SHORT).show();
                    }else if (validarRut(user) == false) {
                        Toast.makeText(MainActivity.this, "Ingrese una Cuenta Valida"
                                , Toast.LENGTH_SHORT).show();
                    }if (validarRut(user)== true){

                        if (pass.isEmpty()){
                        Toast.makeText(MainActivity.this, "Debe ingresar contraseña"
                                , Toast.LENGTH_SHORT).show();

                        }else if (user.length() == 10) {
                            if (pass.equals(user.substring(4, 8))) {
                                startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Ingrese un Usuario Valido"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }else if(user.length() == 9){
                            if (pass.equals(user.substring(3,7))){
                            startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
                        }else {
                                Toast.makeText(MainActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            }catch (Exception e){

                }

        }});}}


