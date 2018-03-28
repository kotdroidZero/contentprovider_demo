package com.kotdroid.contentproviderdemo;

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
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user12 on 28/3/18.
 */

public class IpcFragment extends Fragment {

    @BindView(R.id.btnSave) Button btnSave;
    @BindView(R.id.etTextMessage) EditText etTextMessage;
    @BindView(R.id.tvPath) TextView tvPath;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ipc, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);


    }

    @OnClick(R.id.btnSave)
    public void saveFile(View view) {
        File file = null;
        String text = etTextMessage.getText().toString().trim();
        FileOutputStream fos = null;
        try {
            file = setUpImageFile(Util.LOCAL_STORAGE_BASE_PATH_FOR_FILES);

            fos = new FileOutputStream(file, true);
            fos.write(text.getBytes());
            tvPath.setText(text + " written to " + file.getAbsolutePath());
        } catch (IOException e) {
            tvPath.setText(e.getLocalizedMessage());
        }
    }

    public static File setUpImageFile(String directory) throws IOException {
        File imageFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File storageDir = new File(directory);
            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }
            imageFile = new File(storageDir, "aativa.txt");
        }
        return imageFile;
    }
}
