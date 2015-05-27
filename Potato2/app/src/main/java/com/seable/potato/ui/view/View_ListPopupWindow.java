package com.seable.potato.ui.view;


import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.seable.potato.R;

/**
 * @author 王维玉
 * @ClassName: View_ListPopupWindow
 * @Description: 登录页 - 历史记录列表 - 列表pop
 * @date 2015-01-23 08:34
 */
public class View_ListPopupWindow {

    private static final int EXPAND_LIST_TIMEOUT = 250;

    private Context mContext;
    private PopupWindow mPopup;
    private ListAdapter mAdapter;
    private DropDownListView mDropDownList;

    private int mDropDownHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mDropDownWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mDropDownHorizontalOffset;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;

    private int mListItemExpandMaximum = Integer.MAX_VALUE;

    private View mPromptView;
    private int mPromptPosition = POSITION_PROMPT_ABOVE;

    private DataSetObserver mObserver;

    private View mDropDownAnchorView;

    private Drawable mDropDownListHighlight;

    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;

    private final ResizePopupRunnable mResizePopupRunnable = new ResizePopupRunnable();
    private final PopupTouchInterceptor mTouchInterceptor = new PopupTouchInterceptor();
    private final PopupScrollListener mScrollListener = new PopupScrollListener();
    private final ListSelectorHider mHideSelector = new ListSelectorHider();

    private Handler mHandler = new Handler();

    private Rect mTempRect = new Rect();

    private boolean mModal;

    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;

    public View_ListPopupWindow(Context context) {
        this(context, null, 0, 0);
    }

    public View_ListPopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public View_ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public View_ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mContext = context;
        mPopup = new PopupWindow(context, attrs);
        mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.listview_popup_window_bg));
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.list_item_color));
    }

    public void setAdapter(ListAdapter adapter) {
        if (mObserver == null) {
            mObserver = new PopupDataSetObserver();
        } else if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            adapter.registerDataSetObserver(mObserver);
        }

        if (mDropDownList != null) {
            mDropDownList.setAdapter(mAdapter);
        }
    }

    public void setPromptPosition(int position) {
        mPromptPosition = position;
    }

    public void setModal(boolean modal) {
        mModal = true;
        mPopup.setFocusable(modal);
    }

    public void setBackgroundDrawable(Drawable d) {
        mPopup.setBackgroundDrawable(d);
    }

    public void setAnchorView(View anchor) {
        mDropDownAnchorView = anchor;
    }

    public void setHorizontalOffset(int offset) {
        mDropDownHorizontalOffset = offset;
    }

    public void setVerticalOffset(int offset) {
        mDropDownVerticalOffset = offset;
        mDropDownVerticalOffsetSet = true;
    }

    public void setContentWidth(int width) {
        Drawable popupBackground = mPopup.getBackground();
        if (popupBackground != null) {
            popupBackground.getPadding(mTempRect);
            mDropDownWidth = mTempRect.left + mTempRect.right + width;
        } else {
            mDropDownWidth = width;
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
        mItemClickListener = clickListener;
    }

    public void show() {
        int height = buildDropDown();

        int widthSpec = 0;
        int heightSpec = 0;

        boolean noInputMethod = isInputMethodNotNeeded();

        if (mPopup.isShowing()) {
            if (mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
                widthSpec = -1;
            } else if (mDropDownWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
                widthSpec = mDropDownAnchorView.getWidth();
            } else {
                widthSpec = mDropDownWidth;
            }

            if (mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
                heightSpec = noInputMethod ? height : ViewGroup.LayoutParams.MATCH_PARENT;
                if (noInputMethod) {
                    mPopup.setWindowLayoutMode(
                            mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT ?
                                    ViewGroup.LayoutParams.MATCH_PARENT : 0, 0);
                } else {
                    mPopup.setWindowLayoutMode(
                            mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT ?
                                    ViewGroup.LayoutParams.MATCH_PARENT : 0,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                }
            } else if (mDropDownHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                heightSpec = height;
            } else {
                heightSpec = mDropDownHeight;
            }

            mPopup.setOutsideTouchable(true);

            mPopup.update(mDropDownAnchorView, mDropDownHorizontalOffset,
                    mDropDownVerticalOffset, widthSpec, heightSpec);
        } else {
            if (mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
                widthSpec = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                if (mDropDownWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    mPopup.setWidth(mDropDownAnchorView.getWidth());
                } else {
                    mPopup.setWidth(mDropDownWidth);
                }
            }

            if (mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
                heightSpec = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                if (mDropDownHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    mPopup.setHeight(height);
                } else {
                    mPopup.setHeight(mDropDownHeight);
                }
            }

            mPopup.setWindowLayoutMode(widthSpec, heightSpec);
            mPopup.setOutsideTouchable(true);
            mPopup.setTouchInterceptor(mTouchInterceptor);
            mPopup.showAsDropDown(mDropDownAnchorView,
                    mDropDownHorizontalOffset, mDropDownVerticalOffset);
            mDropDownList.setSelection(ListView.INVALID_POSITION);

            if (!mModal || mDropDownList.isInTouchMode()) {
                clearListSelection();
            }
            if (!mModal) {
                mHandler.post(mHideSelector);
            }
        }
    }

    public void dismiss() {
        mPopup.dismiss();
        if (mPromptView != null) {
            final ViewParent parent = mPromptView.getParent();
            if (parent instanceof ViewGroup) {
                final ViewGroup group = (ViewGroup) parent;
                group.removeView(mPromptView);
            }
        }
        mPopup.setContentView(null);
        mDropDownList = null;
        mHandler.removeCallbacks(mResizePopupRunnable);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mPopup.setOnDismissListener(listener);
    }

    public void setInputMethodMode(int mode) {
        mPopup.setInputMethodMode(mode);
    }

    public void clearListSelection() {
        final DropDownListView list = mDropDownList;
        if (list != null) {
            list.mListSelectionHidden = true;
            list.requestLayout();
        }
    }

    public boolean isShowing() {
        return mPopup.isShowing();
    }

    private boolean isInputMethodNotNeeded() {
        return mPopup.getInputMethodMode() == PopupWindow.INPUT_METHOD_NOT_NEEDED;
    }

    public ListView getListView() {
        return mDropDownList;
    }

    private int buildDropDown() {
        ViewGroup dropDownView;
        int otherHeights = 0;

        if (mDropDownList == null) {
            Context context = mContext;

            mDropDownList = new DropDownListView(context, !mModal);
            if (mDropDownListHighlight != null) {
                mDropDownList.setSelector(mDropDownListHighlight);
            }
            mDropDownList.setAdapter(mAdapter);
            mDropDownList.setOnItemClickListener(mItemClickListener);
            mDropDownList.setFocusable(true);
            mDropDownList.setFocusableInTouchMode(true);
            mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                    if (position != -1) {
                        DropDownListView dropDownList = mDropDownList;

                        if (dropDownList != null) {
                            dropDownList.mListSelectionHidden = false;
                        }
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            mDropDownList.setOnScrollListener(mScrollListener);

            if (mItemSelectedListener != null) {
                mDropDownList.setOnItemSelectedListener(mItemSelectedListener);
            }

            dropDownView = mDropDownList;

            View hintView = mPromptView;
            if (hintView != null) {
                LinearLayout hintContainer = new LinearLayout(context);
                hintContainer.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f
                );

                switch (mPromptPosition) {
                    case POSITION_PROMPT_BELOW:
                        hintContainer.addView(dropDownView, hintParams);
                        hintContainer.addView(hintView);
                        break;

                    case POSITION_PROMPT_ABOVE:
                        hintContainer.addView(hintView);
                        hintContainer.addView(dropDownView, hintParams);
                        break;

                    default:
                        break;
                }

                int widthSpec = MeasureSpec.makeMeasureSpec(mDropDownWidth, MeasureSpec.AT_MOST);
                int heightSpec = MeasureSpec.UNSPECIFIED;
                hintView.measure(widthSpec, heightSpec);

                hintParams = (LinearLayout.LayoutParams) hintView.getLayoutParams();
                otherHeights = hintView.getMeasuredHeight() + hintParams.topMargin
                        + hintParams.bottomMargin;

                dropDownView = hintContainer;
            }

            mPopup.setContentView(dropDownView);
        } else {
            dropDownView = (ViewGroup) mPopup.getContentView();
            final View view = mPromptView;
            if (view != null) {
                LinearLayout.LayoutParams hintParams =
                        (LinearLayout.LayoutParams) view.getLayoutParams();
                otherHeights = view.getMeasuredHeight() + hintParams.topMargin
                        + hintParams.bottomMargin;
            }
        }

        int padding = 0;
        Drawable background = mPopup.getBackground();
        if (background != null) {
            background.getPadding(mTempRect);
            padding = mTempRect.top + mTempRect.bottom;

            if (!mDropDownVerticalOffsetSet) {
                mDropDownVerticalOffset = -mTempRect.top;
            }
        }

        boolean ignoreBottomDecorations =
                mPopup.getInputMethodMode() == PopupWindow.INPUT_METHOD_NOT_NEEDED;
        final int maxHeight = /*mPopup.*/getMaxAvailableHeight(
                mDropDownAnchorView, mDropDownVerticalOffset, ignoreBottomDecorations);

        if (mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
            return maxHeight + padding;
        }

        final int listContent = /*mDropDownList.*/measureHeightOfChildren(MeasureSpec.UNSPECIFIED,
                0, -1/*ListView.NO_POSITION*/, maxHeight - otherHeights, -1);
        if (listContent > 0) otherHeights += padding;

        return listContent + otherHeights;
    }

    private int getMaxAvailableHeight(View anchor, int yOffset, boolean ignoreBottomDecorations) {
        final Rect displayFrame = new Rect();
        anchor.getWindowVisibleDisplayFrame(displayFrame);

        final int[] anchorPos = new int[2];
        anchor.getLocationOnScreen(anchorPos);

        int bottomEdge = displayFrame.bottom;
        if (ignoreBottomDecorations) {
            Resources res = anchor.getContext().getResources();
            bottomEdge = res.getDisplayMetrics().heightPixels;
        }
        final int distanceToBottom = bottomEdge - (anchorPos[1] + anchor.getHeight()) - yOffset;
        final int distanceToTop = anchorPos[1] - displayFrame.top + yOffset;

        int returnedHeight = Math.max(distanceToBottom, distanceToTop);
        if (mPopup.getBackground() != null) {
            mPopup.getBackground().getPadding(mTempRect);
            returnedHeight -= mTempRect.top + mTempRect.bottom;
        }

        return returnedHeight;
    }

    private int measureHeightOfChildren(int widthMeasureSpec, int startPosition, int endPosition,
                                        final int maxHeight, int disallowPartialChildPosition) {

        final ListAdapter adapter = mAdapter;
        if (adapter == null) {
            return mDropDownList.getListPaddingTop() + mDropDownList.getListPaddingBottom();
        }

        int returnedHeight = mDropDownList.getListPaddingTop() + mDropDownList.getListPaddingBottom();
        final int dividerHeight = ((mDropDownList.getDividerHeight() > 0) && mDropDownList.getDivider() != null) ? mDropDownList.getDividerHeight() : 0;
        int prevHeightWithoutPartialChild = 0;
        int i;
        View child;

        endPosition = (endPosition == -1/*NO_POSITION*/) ? adapter.getCount() - 1 : endPosition;

        for (i = startPosition; i <= endPosition; ++i) {
            child = mAdapter.getView(i, null, mDropDownList);
            if (mDropDownList.getCacheColorHint() != 0) {
                child.setDrawingCacheBackgroundColor(mDropDownList.getCacheColorHint());
            }

            measureScrapChild(child, i, widthMeasureSpec);

            if (i > 0) {
                returnedHeight += dividerHeight;
            }

            returnedHeight += child.getMeasuredHeight();

            if (returnedHeight >= maxHeight) {
                return (disallowPartialChildPosition >= 0) // Disallowing is enabled (> -1)
                        && (i > disallowPartialChildPosition) // We've past the min pos
                        && (prevHeightWithoutPartialChild > 0) // We have a prev height
                        && (returnedHeight != maxHeight) // i'th child did not fit completely
                        ? prevHeightWithoutPartialChild
                        : maxHeight;
            }

            if ((disallowPartialChildPosition >= 0) && (i >= disallowPartialChildPosition)) {
                prevHeightWithoutPartialChild = returnedHeight;
            }
        }

        return returnedHeight;
    }

    private void measureScrapChild(View child, int position, int widthMeasureSpec) {
        ListView.LayoutParams p = (ListView.LayoutParams) child.getLayoutParams();
        if (p == null) {
            p = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0);
            child.setLayoutParams(p);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec,
                mDropDownList.getPaddingLeft() + mDropDownList.getPaddingRight(), p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private static class DropDownListView extends ListView {

        private boolean mListSelectionHidden;

        private boolean mHijackFocus;

        public DropDownListView(Context context, boolean hijackFocus) {
            super(context);
            mHijackFocus = hijackFocus;
            setCacheColorHint(0); // Transparent, since the background drawable could be anything.
            setVerticalFadingEdgeEnabled(false);
            setHorizontalFadingEdgeEnabled(false);
        }

        @Override
        public boolean isInTouchMode() {
            return (mHijackFocus && mListSelectionHidden) || super.isInTouchMode();
        }

        @Override
        public boolean hasWindowFocus() {
            return mHijackFocus || super.hasWindowFocus();
        }

        @Override
        public boolean isFocused() {
            return mHijackFocus || super.isFocused();
        }

        @Override
        public boolean hasFocus() {
            return mHijackFocus || super.hasFocus();
        }
    }

    private class PopupDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            if (isShowing()) {
                show();
            }
        }

        @Override
        public void onInvalidated() {
            dismiss();
        }
    }

    private class ListSelectorHider implements Runnable {
        public void run() {
            clearListSelection();
        }
    }

    private class ResizePopupRunnable implements Runnable {
        public void run() {
            if (mDropDownList != null && mDropDownList.getCount() > mDropDownList.getChildCount() &&
                    mDropDownList.getChildCount() <= mListItemExpandMaximum) {
                mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
                show();
            }
        }
    }

    private class PopupTouchInterceptor implements OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            final int action = event.getAction();
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            if (action == MotionEvent.ACTION_DOWN &&
                    mPopup != null && mPopup.isShowing() &&
                    (x >= 0 && x < mPopup.getWidth() && y >= 0 && y < mPopup.getHeight())) {
                mHandler.postDelayed(mResizePopupRunnable, EXPAND_LIST_TIMEOUT);
            } else if (action == MotionEvent.ACTION_UP) {
                mHandler.removeCallbacks(mResizePopupRunnable);
            }
            return false;
        }
    }

    private class PopupScrollListener implements ListView.OnScrollListener {
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {

        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_TOUCH_SCROLL &&
                    !isInputMethodNotNeeded() && mPopup.getContentView() != null) {
                mHandler.removeCallbacks(mResizePopupRunnable);
                mResizePopupRunnable.run();
            }
        }
    }
}