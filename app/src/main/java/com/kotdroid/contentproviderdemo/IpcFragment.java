package com.kotdroid.contentproviderdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user12 on 28/3/18.
 */

public class IpcFragment extends Fragment {

    @BindView(R.id.btnSaveInPrefs) Button btnSave;
    @BindView(R.id.etTextPrefs) EditText etTextMessage;
    @BindView(R.id.etTextFile) EditText etTextFile;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ipc, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);


    }

    @OnClick({R.id.btnSaveInPrefs})
    public void saveFile(View view) {

        switch (view.getId()) {
            case R.id.btnSaveInPrefs:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("IPCDemo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("testingIPC", etTextMessage.getText().toString().trim());
                editor.apply();
                break;
            case R.id.btnSaveInStorage:
                Log.e("privateAppdataPath", Environment.getDataDirectory() + "/file");
                File filesDir = getActivity().getFilesDir();
                File file = new File(filesDir, "demofile.txt");
                try {
                    FileOutputStream outputStream = new FileOutputStream(file, true);
                    outputStream.write(etTextFile.getText().toString().trim().getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
//        File file = null;
//        String text = etTextMessage.getText().toString().trim();
//        FileOutputStream fos = null;
//        try {
//            file = setUpImageFile(Util.LOCAL_STORAGE_BASE_PATH_FOR_FILES);
//
//            fos = new FileOutputStream(file, true);
//            fos.write(text.getBytes());
//            tvPath.setText(text + " written to " + file.getAbsolutePath());
//        } catch (IOException e) {
//            tvPath.setText(e.getLocalizedMessage());
//        }
    }

    public static File setUpImageFile(String directory) throws IOException {
        File imageFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File storageDir = new File(directory);
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d("CameraSample", "failed to create directory");
                    return null;
                }
            }
            imageFile = new File(storageDir, "aativa.txt");
        }
        return imageFile;
    }
}
