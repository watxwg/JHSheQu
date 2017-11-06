package com.jhcms.common.dialog.actionsheet;

import android.animation.ObjectAnimator;
import android.view.View;

public class FadeExit extends BaseAnimatorSet {
	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(//
				ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(duration));
	}
}
