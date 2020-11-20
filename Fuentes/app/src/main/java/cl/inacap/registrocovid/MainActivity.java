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

    protected void onResume() {
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
                String user = idUsuario.getText().toString().trim();
                String pass = passUsuario.getText().toString().trim();
                
              if (!user.isEmpty()){
                  //obtener el largo del rut
                  int digitosUser = user.length();
                  //obtener los ultimos 4 digitos del rut sin digito verificador
                  String passUser = user.substring(digitosUser -6, digitosUser-2);
                  int guion = user.indexOf(digitosUser-1);
                  if (!pass.isEmpty()){
                      //validar rut
                      if (validarRut(user) && user.contains("-")){
                          //validar contraseña
                          if (pass.equalsIgnoreCase(passUser)){
                            //iniciar MainActivity
                              startActivity(new Intent(MainActivity.this, PrincipalActivity.class));

                          } else{
                              Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                          }
                      } else{
                          Toast.makeText(MainActivity.this, "Ingrese rut válido", Toast.LENGTH_SHORT).show();
                      }
                  } else {
                      Toast.makeText(MainActivity.this, "Ingrese contraseña de Usuario", Toast.LENGTH_SHORT).show();
                  }
              } else{
                  Toast.makeText(MainActivity.this, "Ingrese nombre de Usuario", Toast.LENGTH_SHORT).show();
              }

            }
        });
    }
}


