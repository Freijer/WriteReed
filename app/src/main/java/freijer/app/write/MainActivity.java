package freijer.app.write;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;


public class MainActivity extends AppCompatActivity {

    private EditText editText, editText2, editText3;
    private Button Write, rid;
    private TextView ViewShow, Show, show3;

    SharedPreferences sPref;


    final String SAVED_TEXT = "Сохранение";
    final String SAVED_TEXT_TWO = "Пересохранение";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

        Write = findViewById(R.id.Write);
        rid = findViewById(R.id.rid);
        ViewShow  = findViewById(R.id.ViewShow);
        Show  = findViewById(R.id.Show);
        show3  = findViewById(R.id.show3);

        LoadText();

    }

    public void Load (View v) {
        LoadText();
    }

    public void LoadText(){
        try {
            FileInputStream fileInput = openFileInput("savetxt.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer strbuf = new StringBuffer();
            String Lines;

            while ((Lines = buffer.readLine()) != null){
                strbuf.append(Lines + "\n");
            }
            show3.setText(strbuf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        String savedTextTwo = sPref.getString(SAVED_TEXT_TWO, "");
        ViewShow.setText(savedText);
        Show.setText(savedTextTwo);
        Toast.makeText(this, "Text load", Toast.LENGTH_SHORT).show();

    }

    public void Save (View v) {
        SaveText();
    }
    public void SaveText(){
        String myText;
        myText = editText3.getText().toString();
        try {
            FileOutputStream fileout = openFileOutput("savetxt.txt", MODE_PRIVATE);
            fileout.write(myText.getBytes());
            fileout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, editText.getText().toString());
        ed.putString(SAVED_TEXT_TWO, editText2.getText().toString());
        ed.commit();
        Toast.makeText(this, "Text save", Toast.LENGTH_SHORT).show();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveText();

    }
}

