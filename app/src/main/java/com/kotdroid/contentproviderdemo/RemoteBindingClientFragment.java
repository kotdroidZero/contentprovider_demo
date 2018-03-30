package com.kotdroid.contentproviderdemo;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user12 on 30/3/18.
 */

public class RemoteBindingClientFragment extends Fragment {

    private static final int GET_RANDOM_NUMBER_FLAG = 0;
    @BindView(R.id.tvRandomNumber) TextView tvRandomNumber;

    private Intent serviceIntent;

    private boolean isBound;

    Messenger randomNumberRequestMessenger, randomNumberReceiveMessenger;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_remote_binding, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.kotdroid.cpconsumerdemo", "com.kotdroid.cpconsumerdemo.RemoteBoundService"));
    }


    @OnClick({R.id.btnUnbindService, R.id.btnBindService, R.id.btnRandomNumber})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBindService:
                getActivity().bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

                break;
            case R.id.btnUnbindService:
                getActivity().unbindService(mServiceConnection);
                Toast.makeText(getContext(), "Service unbinded", Toast.LENGTH_SHORT).show();
                isBound = false;
                break;
            case R.id.btnRandomNumber:
                getRandomNumber();
                break;
        }
    }

    private void getRandomNumber() {
        if (isBound) {
            Message requestMessage = Message.obtain(null, GET_RANDOM_NUMBER_FLAG);
            requestMessage.replyTo = randomNumberReceiveMessenger;
            try {
                randomNumberRequestMessenger.send(requestMessage);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "can't get until the service is not bound", Toast.LENGTH_SHORT).show();
        }

    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder binder) {
            randomNumberRequestMessenger = new Messenger(binder);
            randomNumberReceiveMessenger = new Messenger(new ReceiveRandomNumberHandler());
            isBound = true;
            Toast.makeText(getContext(), "Service bound", Toast.LENGTH_SHORT).show();
        }

        @Override public void onServiceDisconnected(ComponentName name) {
            randomNumberReceiveMessenger = null;
            randomNumberRequestMessenger = null;
            isBound = false;
        }
    };

    private class ReceiveRandomNumberHandler extends Handler {
        @SuppressLint("SetTextI18n") @Override public void handleMessage(Message msg) {
            int randomNumberValue = 0;
            switch (msg.what) {
                case GET_RANDOM_NUMBER_FLAG:
                    randomNumberValue = msg.arg1;
                    tvRandomNumber.setText("The Random number is : " + randomNumberValue);
                    break;
                default:
                    break;
            }
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mServiceConnection = null;
    }
}
