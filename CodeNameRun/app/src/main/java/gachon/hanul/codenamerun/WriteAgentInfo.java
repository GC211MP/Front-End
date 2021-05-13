package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.AsyncNotedAppOp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import gachon.hanul.codenamerun.api.SqliteDto;
import gachon.hanul.codenamerun.api.StoreManager;

public class WriteAgentInfo extends AppCompatActivity {

    //TODO name height weight 를 입력하고 확인 버튼을 누르면, 유저 정보가 update 된다.
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_agent_info);

        /* tts(korean) download */
        showDialogForTTSDownLoad();


        okButton =findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //TODO name height weight(editText) 를 서버에 넘긴다

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
    }
















    /* TTS 한국어 다운로드 안내 팝업
    * 한번만 실행하면 되기 때문에 회원가입 창에 넣었습니다 */
    private void showDialogForTTSDownLoad() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("음성 서비스 사용을 위한 다운로드");
        builder.setMessage("앱을 사용하기 위해서는 한국어 TTS 다운로드가 필요합니다.\n"
                + "TTS 음성 데이터를 다운로드 하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("다운로드", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callTTSDownloadIntent = new Intent();
                callTTSDownloadIntent.setPackage("com.google.android.tts");
                callTTSDownloadIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                callTTSDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callTTSDownloadIntent);
                // startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}