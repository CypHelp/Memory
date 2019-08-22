package com.ab.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView;

import com.ab.R;

/**
 * WIFI设置对话框
 * 
 * @author feng
 * 
 */
public class CommonDialog extends BaseDialog {

	/**
	 * 是
	 */
	private TextView mYesButton;

	/**
	 * 否
	 */
	private TextView mNoButton;
	/**
	 * 对话框里的内容
	 */
	private TextView mContentTextView;

	/**
	 * 对话框标题
	 */
	private TextView mTitleTextView;
	/**
	 * 输入框
	 */
	private EditText mEditText;
	
	public CommonDialog(Context context) {
		super(context);
		init();
		setOnListener();
	}

	public void setTitle(String string) {
		mTitleTextView.setText(string);
	}

	public void setContent(String string) {
		mContentTextView.setText(string);
	}

	public void setYes(String y){
		mYesButton.setText(y);
	}
	
	public void setNo(String n){
		mNoButton.setText(n);
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		mYesButton = (TextView) findViewById(R.id.btn_yes_restart);
		mNoButton = (TextView) findViewById(R.id.btn_no_restart);
		mContentTextView = (TextView) findViewById(R.id.dialog_content);
		mTitleTextView = (TextView) findViewById(R.id.dialog_title);
		mEditText = (EditText) findViewById(R.id.edittext);
	}

	/**
	 * 是否显示内容和编辑框
	 *
	 * @param content
	 * @param edit
	 */
	public void showContentEdittext(boolean content, boolean edit) {
		if (content && !edit) {
			mEditText.setVisibility(View.GONE);
			mContentTextView.setVisibility(View.VISIBLE);
		} else if (content && edit) {
			mEditText.setVisibility(View.VISIBLE);
			mContentTextView.setVisibility(View.VISIBLE);
		} else {
			mEditText.setVisibility(View.VISIBLE);
			mContentTextView.setVisibility(View.GONE);
		}
	}

	public String getEditString() {
		return mEditText.getText().toString();
	}

	public void setEditHint(String hint) {
		mEditText.setHint(hint);
	}

	public void setEditBackground(int drawableId) {
		mEditText.setBackgroundResource(drawableId);
	}

	public void setEditDefaultLines(int lines) {
		mEditText.setLines(lines);
	}

	private void setOnListener() {
		mYesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mButtonClickListener.okClick();
			}

		});
		mNoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mButtonClickListener.cancleClick();
			}
		});
	}

	@Override
	protected int setLayoutView() {
		return R.layout.common_dialog;
	}

	/**
	 * 对话框的一些属性接口
	 *
	 * @author wuwf
	 */
	public interface DialogConstant {
		int ANIMATION_BOTTOM = R.style.myAnimStyleDialog;
		int POSITION_BOTTOM = Gravity.BOTTOM;
		int POSITION_CENTER = Gravity.CENTER;
	}

	// 显示一个动画,以提示用户输入
	public void shakeEditText() {
		mEditText.startAnimation(shakeAnimation(5));

	}

	//CycleTimes动画重复的次数
	private Animation shakeAnimation(int CycleTimes) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
		translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

}
