package com.example.ershou;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ershou.Util.SharedPreferencesUtils;
import com.example.ershou.Util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class TabFragmentThree extends Fragment {
    private TextView username;
    private TextView nickname, guanyu, update;
    ImageView hj;
    TextView state;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabfragmentfour, container, false);
        username = (TextView) v.findViewById(R.id.username);
        hj = v.findViewById(R.id.hj);
        state = v.findViewById(R.id.state);
        RequestParams ps = new RequestParams();
        ps.add("zh", SharedPreferencesUtils.getParam(getActivity(), "zh", "").toString());
        UserClient.get("hj/get", ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (content.equals("1")) {

                } else {
                    state.setText(content);
                    new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage(content).setPositiveButton("确定",null).show();
                }
            }
        });
        hj.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams ps = new RequestParams();
                ps.add("zh", SharedPreferencesUtils.getParam(getActivity(), "zh", "").toString());
                UserClient.get("hj/get", ps, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        if (content.equals("1")) {
                            RequestParams pss = new RequestParams();
                            pss.add("zhuohao", SharedPreferencesUtils.getParam(getActivity(), "zh", "").toString());
                            UserClient.get("hj/add", pss, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);
                                    state.setText(content);
                                    RequestParams ps = new RequestParams();
                                    ps.add("zh", SharedPreferencesUtils.getParam(getActivity(), "zh", "").toString());
                                    UserClient.get("hj/get", ps, new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(String content) {
                                            super.onSuccess(content);
                                            if (content.equals("1")) {

                                            } else {
                                                state.setText(content);
                                                new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage(content).setPositiveButton("确定",null).show();
                                            }
                                        }
                                    });
                                }
                            });
                        } else {
                            state.setText(content);
                        }
                    }
                });
            }
        });
        guanyu = (TextView) v.findViewById(R.id.guanyu);
        update = (TextView) v.findViewById(R.id.update);
        guanyu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(getActivity(), guanyu.class));
            }
        });
        update.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(getActivity(), update.class));
            }
        });
        nickname = (TextView) v.findViewById(R.id.nickname);
        username.setText("帐号：" + SharedPreferencesUtils.getParam(getActivity(), "username", ""));
        nickname.setText("昵称：" + SharedPreferencesUtils.getParam(getActivity(), "nickname", ""));
        return v;
    }
}
