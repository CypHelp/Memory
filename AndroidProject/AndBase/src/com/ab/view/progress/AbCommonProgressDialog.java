package com.ab.view.progress;

import com.ab.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;


public class AbCommonProgressDialog extends Dialog  {
	public AbCommonProgressDialog(Context context) {
		super(context, R.style.CustomProgressDialog);
		this.setContentView(R.layout.commonprogressdialog);
		this.getWindow().getAttributes().gravity = Gravity.CENTER;
	}
	
	public void setMessage(String s) {
		TextView tvMsg = (TextView) this.findViewById(R.id.id_tv_loadingmsg);
		tvMsg.setText(s);  
	}

}
