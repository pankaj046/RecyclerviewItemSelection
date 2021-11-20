package sharma.pankaj.recyclerviewitemselection.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import sharma.pankaj.recyclerviewitemselection.R

class CustomCheckBox : AppCompatCheckBox{

    constructor(context: Context) : super(context) {
        this.setButtonDrawable(R.drawable.checkbox_selector)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.setButtonDrawable(R.drawable.checkbox_selector)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.setButtonDrawable(R.drawable.checkbox_selector)
    }
}