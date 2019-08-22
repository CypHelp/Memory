package com.ab.view.dialog;

import java.util.Random;

import com.ab.R;
import com.ab.view.dialog.CommonDialog.DialogConstant;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 带动画的对话框
 * 
 * @author wuwf
 * 
 */
public abstract class BaseDialog extends Dialog {
	/**
	 * 对话框的布局
	 */
	protected View mDialogView;
	protected ButtonClickListener mButtonClickListener;
	protected Context mContext;
	private int position = -1;
	private int animation = -1;
	private boolean mFlag = true;;

	public BaseDialog(Context context) {
		super(context, R.style.dialog);
		this.mContext = context;
		mDialogView = View.inflate(context, setLayoutView(), null);
		this.setContentView(mDialogView);
		setOwnerActivity((Activity) context);
	}

	public BaseDialog(Context context, int style) {
		super(context, style);
		this.mContext = context;
		mDialogView = View.inflate(context, setLayoutView(), null);
		this.setContentView(mDialogView);
		setOwnerActivity((Activity) context);
	}
	public interface ButtonClickListener {
		void okClick();

		void cancleClick();
	}

	public void setClickListener(ButtonClickListener buttonClickListener) {
		mButtonClickListener = buttonClickListener;
	}
	
	public void setAnimation(int style) {
		animation = style;
	}

	/**
	 * 设置对话框出现的位置
	 * @param position
	 */
	public void setShowPosition(int position) {
		this.position = position;
	}

	/**
	 * 设置对话框全屏
	 */
	public void setWidthFullScreen(boolean flag) {
		this.mFlag  = flag;
	}
	
	private void setFullScreen() {
		// 对话框设置全屏
				WindowManager windowManager = ((Activity) mContext).getWindowManager();
				DisplayMetrics dm = new DisplayMetrics();
				windowManager.getDefaultDisplay().getMetrics(dm);
				int width = dm.widthPixels;
				WindowManager.LayoutParams lp = this.getWindow().getAttributes();
				lp.width =	width; // 设置宽度
	}
	
	/**
	 * 设置用哪个布局
	 * 
	 * @param resid
	 */
	protected abstract int setLayoutView();

	/**
	 * 显示随机动画
	 */
	public void showRandomAnim() {
		Effectstype type = null;
		int i = new Random(System.currentTimeMillis()).nextInt(13);
		switch (Math.abs(i)) {
		case 0:
			type = Effectstype.Fadein;
			break;
		case 1:
			type = Effectstype.Fall;
			break;
		case 2:
			type = Effectstype.Fliph;
			break;
		case 3:
			type = Effectstype.Flipv;
			break;
		case 4:
			type = Effectstype.Newspager;
			break;
		case 5:
			type = Effectstype.RotateBottom;
			break;
		case 6:
			type = Effectstype.RotateLeft;
			break;
		case 7:
			type = Effectstype.Sidefill;
			break;
		case 8:
			type = Effectstype.SlideBottom;
			break;
		case 9:
			type = Effectstype.Slideleft;
			break;
		case 10:
			type = Effectstype.Slideright;
			break;
		case 11:
			type = Effectstype.Slidetop;
			break;
		case 12:
			type = Effectstype.Slit;
			break;
		default:
			break;
		}

		show(type);
	}
	
	/**
	 * 带特效动画的显示
	 * @param type
	 */
	public void show(Effectstype type) {
		BaseEffects animator = type.getAnimator();
		animator.setDuration(700);
		animator.start(mDialogView);
		show();
	}

	@Override
	public void show() {
		Window window = this.getWindow();
		//位置默认在底部
		if (position == -1) {
			position = DialogConstant.POSITION_BOTTOM;
		}

		window.setGravity(position); // 此处可以设置dialog显示的位置
		//如果没设置动画，则默认从底部
		if (animation != -1) {
			window.setWindowAnimations(animation); // 添加动画
		} else {
			window.setWindowAnimations(DialogConstant.ANIMATION_BOTTOM);
		}
		
		if(mFlag) {
			setFullScreen();
		}

		super.show();
	}

}
