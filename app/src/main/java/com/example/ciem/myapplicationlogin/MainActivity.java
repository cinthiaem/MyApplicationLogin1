package com.example.ciem.myapplicationlogin;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    EditText txtUser, txtPassword;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         txtUser = (EditText)findViewById(R.id.txtUser);
         txtPassword = (EditText)findViewById(R.id.txtPassword);
         btnIngresar = (Button)findViewById(R.id.btnIngresar);

         btnIngresar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new operaSOAP().execute(txtUser.getText().toString().trim(),txtPassword.getText().toString().trim());
             }
         });
    }
    private class operaSOAP extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://WS/";
        static final String METHODNAME = "consultaUsuario";
        static final String URL = "http://192.168.137.77:8080/WebAppLogin/WebServiceLogin?WSDL";
        static final String SOAP_ACTION = NAMESPACE + METHODNAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);
            request.addProperty("usuario","CIEM");
            request.addProperty("contrasenia","14011412");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject (request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try{
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.d("reps",response.toString());
            }catch (Exception e){
                Log.d("exx",e.getMessage());
            }
            return null;
        }
    }
}
