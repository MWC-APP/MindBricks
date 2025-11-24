package ch.inf.usi.mindbricks.util.validators;

import android.text.TextUtils;

import java.util.List;
import java.util.Objects;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.model.Tag;
import ch.inf.usi.mindbricks.util.ValidationResult;

/**
 * Utility class that provides validation rules for user profile
 * data (name, sprint length, tags) for reusability across the application
 */
public final class ProfileValidator {

    private ProfileValidator() {
        // not instantiable
    }

    /**
     * Validates the name field, ensuring it is not empty.
     * @param name The name to validate
     * @return A ValidationResult indicating the validation result
     */
    public static ValidationResult validateName(String name) {
        if (TextUtils.isEmpty(name == null ? "" : name.trim())) {
            return ValidationResult.error(R.string.validation_error_name_required);
        }
        return ValidationResult.ok();
    }

    /**
     * Validates the sprint length field, ensuring it is a valid integer greater than zero.
     * @param sprintLength The sprint length to validate
     * @return A ValidationResult indicating the validation result
     */
    public static ValidationResult validateSprintLength(String sprintLength) {
        if (TextUtils.isEmpty(sprintLength == null ? "" : sprintLength.trim())) {
            return ValidationResult.error(R.string.validation_error_sprint_required);
        }
        try {
            Objects.requireNonNull(sprintLength, "Sprint length cannot be null");
            int sprintMinutes = Integer.parseInt(sprintLength.trim());
            if (sprintMinutes <= 0) {
                return ValidationResult.error(R.string.validation_error_sprint_invalid);
            }
        } catch (NumberFormatException e) {
            return ValidationResult.error(R.string.validation_error_sprint_invalid);
        }
        return ValidationResult.ok();
    }

    /**
     * Validates tags; current rule allows empty/null (tags are optional).
     * @param tags The list of tags to validate
     * @return A ValidationResult indicating the validation result
     */
    public static ValidationResult validateTags(List<Tag> tags) {
        return ValidationResult.ok();
    }
}
