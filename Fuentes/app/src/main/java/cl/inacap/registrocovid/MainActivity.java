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

                if (user.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese nombre de Usuario"
                            , Toast.LENGTH_SHORT).show();
                }else{
                    String rutValidado = user;
                    if (validarRut(rutValidado) == false){
                        Toast.makeText(MainActivity.this, "Ingrese una Cuenta Valida"
                                , Toast.LENGTH_SHORT).show();
                    }

                    int digitos = user.length();
                    String cuatroDigitos = user.substring(digitos -5,digitos-1);
                      if(cuatroDigitos.equalsIgnoreCase(cuatroDigitos)) {
                        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(MainActivity.this, "Cuenta Invalida", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }
}