package ch.inf.usi.mindbricks.model.plan;

import androidx.annotation.NonNull;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.slider.Slider;
import com.google.android.material.textview.MaterialTextView;
/**
 * Record representing a row in the day plan UI, containing the day key, checkbox, slider, and hours label.
 *
 * @author Luca Di Bello
 */
public record DayRow(DayKey dayKey,
                     @NonNull MaterialCheckBox checkBox,
                     @NonNull Slider slider,
                     @NonNull MaterialTextView hoursLabel) {
}
