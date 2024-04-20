package model;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatSpinner;
import android.widget.AdapterView;

/**
 * Custom Spinner class that extends the AppCompatSpinner.
 */
public class Spinner extends AppCompatSpinner {

    // Listener for item selection events
    AdapterView.OnItemSelectedListener listener;

    /**
     * Constructor for the Spinner class.
     * @param context The context in which the spinner will be used.
     * @param attrs The attributes of the spinner defined in XML.
     */
    public Spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Overrides the setSelection method to ensure that the listener's onItemSelected method is called
     * even if the selected item remains unchanged.
     * @param position The position of the selected item.
     */
    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        // Call onItemSelected method of the listener
        if (listener != null)
            listener.onItemSelected(null, null, position, 0);
    }

    /**
     * Sets an OnItemSelectedListener for the spinner.
     * @param listener The listener to be set.
     */
    public void setOnItemSelectedEvenIfUnchangedListener(AdapterView.OnItemSelectedListener listener) {
        this.listener = listener;
    }

}